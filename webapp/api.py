#!/usr/bin/env python3
'''
	API Implementation
	@author Daniel Busis
	@author Will Thompson
'''
import sys
import flask
import json
import psycopg2

app = flask.Flask(__name__)

@app.route('/')
def hello():
	return 'No data requested.'



'''
	GET arguments:
	 - start_year: return data for years after this date.
	 - end_year: return data for years before this date.
	 - year: for each year given, returns years in the list
	         if start_year and end_year not given, gives only 
			 years in this list.
	 - country_name: returns only data whose country matches the given string(s)
	 - stat_name: returns only data whose stat name matches the given string(s)
	 - country_id: returns only data whose country id matches the given id(s)
	 - stat_id: returns only data whose stat id matches the given id(s)
'''
@app.route('/data/')
def data_for_country():
	data_list = []
	
	start_year = flask.request.args.get('start_year')
	end_year = flask.request.args.get('end_year')
	specified_years_list = flask.request.args.getlist('year')
	
	list_of_years_requested = make_years_list(start_year, end_year, specified_years_list)
	column_string = make_column_string(list_of_years_requested)
	select_string = "SELECT "+column_string+" FROM annual_data"
	
	stat_id = flask.request.args.getlist('stat_id')
	country_id = flask.request.args.getlist('country_id')
	stat_name = flask.request.args.getlist('stat_name')
	country_name = flask.request.args.getlist('country_name')
	
	if len(stat_name) > 0:
		select_string += ",stats"
	if len(country_name) > 0:
		select_string += ",countries"		
	
	optional_args = False
	if (len(stat_id) > 0
			or len(country_id) > 0
			or len(stat_name) > 0
			or len(country_name) > 0):
		select_string += " WHERE"
		optional_args = True		

	for id in stat_id:
		select_string += " stat_id = "+id+" AND"
	for id in country_id:
		select_string += " country_id = "+id+" AND"
	for name in stat_name:
		select_string += " LOWER(stats.name) LIKE LOWER('%"+name+"%') AND"
		select_string += " annual_data.stat_id=stats.id AND"
	for name in country_name:
		select_string += " LOWER(countries.name) LIKE LOWER('%"+name+"%') AND"
		select_string += " annual_data.country_id=countries.id AND"
	
	#These lines works:
	#SELECT year_1960 FROM algeria_data,stat_ids WHERE stats.name LIKE 'GDP%' AND annual_data.stat_id=stats.id;
	#SELECT country_id,stat_id,year_1960 FROM annual_data,stats where stats.id=annual_data.stat_id AND stats.name LIKE 'Population, male%';
	
	#Trims off trailing " AND"
	if optional_args:
		select_string = select_string[:-4]
	
	try:
		cursor.execute(select_string)
	except Exception as e:
		print(e)
		exit()
	
	for row in cursor:
		cur_row_dict = {}
		cur_row_dict['country_id'] = row[0]
		cur_row_dict['stat_id'] = row[1]
		for i in range(len(list_of_years_requested)):
			cur_row_dict[list_of_years_requested[i]] = row[i+2]
		data_list.append(cur_row_dict)
	
	return json.dumps(data_list)
	
#Must return a SORTED list of years!
def make_years_list(start_year, end_year, specified_years_list):
	start_year_was_none = False
	end_year_was_none = False
	if start_year is None:
		start_year = 1960
		start_year_was_none = True
	else:
		start_year = int(start_year)
	if end_year is None:
		end_year = 2017
		end_year_was_none = True
	else:
		end_year = int(end_year)

	year_list = []
	if len(specified_years_list)==0 or not start_year_was_none or not end_year_was_none:
		for i in range(start_year, end_year+1):
			year_list.append("year_"+str(i))
	for i in specified_years_list:
		if int(i) >= 1960 and int(i) <= 2017:
			if ((start_year_was_none and end_year_was_none)
					or int(i) > end_year
					or int(i) < start_year):
				year_list.append("year_"+str(i))
	year_list.sort()
	return year_list

#Turns [1960, 1961, ...] into 'year_1960,year_1961,...'
def make_column_string(year_list):
	column_string = "country_id,stat_id"
	for i in year_list:
		column_string+=","+i
	return column_string
	
	
	
@app.route('/countries/')
def return_all_countries_and_ids():
	country_list = []
	select_string = "SELECT * FROM countries"
    
        country_name = flask.request.args.getlist('country_name')
	country_id = flask.request.args.getlist('country_id')	
	
	if (len(country_id) > 0) or (len(country_name) > 0):
                select_string += " WHERE"


	for id in country_id:
		select_string += " country_id = "+id+" AND"
	for country in country_name:
		select_string += " LOWER(countries.name) LIKE LOWER('%"+country+"%') AND"	 
	
	try:
		cursor.execute(select_string)
	except Exception as e:
		print(e)
		exit()
	
	for row in cursor:
		cur_country_dict = {}
		cur_country_dict['country_id'] = row[0]
		cur_country_dict['country_name'] = row[1]
		country_list.append(cur_country_dict)
		
	return json.dumps(country_list)

@app.route('/countries/<country_name>')
def return_one_country_and_id(country_name):
	country_list = []
	select_string = "SELECT * FROM country_ids"
	select_string += " WHERE LOWER(name) LIKE LOWER('%"+country_name+"%')"
	
	try:
		cursor.execute(select_string)
	except Exception as e:
		print(e)
		exit()
	
	for row in cursor:
		cur_country_dict = {}
		cur_country_dict['country_id'] = row[0]
		cur_country_dict['country_name'] = row[1]
		country_list.append(cur_country_dict)
		
	return json.dumps(country_list)



@app.route('/stats/')
def return_all_stats_and_ids():
	stat_list = []
	select_string = "SELECT * FROM stats"
	
	try:
		cursor.execute(select_string)
	except Exception as e:
		print(e)
		exit()
	
	for row in cursor:
		cur_stat_dict = {}
		cur_stat_dict['stat_id'] = row[0]
		cur_stat_dict['stat_name'] = row[1]
		stat_list.append(cur_stat_dict)
		
	return json.dumps(stat_list)


	
@app.route('/stats/<stat_name>')
def return_one_stat_and_id(stat_name):
	stat_list = []
	select_string = "SELECT * FROM stats"
	select_string += " WHERE LOWER(name) LIKE LOWER('%"+stat_name+"%')"
	
	try:
		cursor.execute(select_string)
	except Exception as e:
		print(e)
		exit()
	
	for row in cursor:
		cur_stat_dict = {}
		cur_stat_dict['stat_id'] = row[0]
		cur_stat_dict['stat_name'] = row[1]
		stat_list.append(cur_stat_dict)
		
	return json.dumps(stat_list)
	
	

conn = None
cursor = None
if __name__ == '__main__':
	if len(sys.argv) != 4:
		print('Usage: {0} host port dbname'.format(sys.argv[0]))
		print('  Example: {0} perlman.mathcs.carleton.edu 5101 dbname'.format(sys.argv[0]))
		exit()
	
	try:
		conn = psycopg2.connect(dbname=sys.argv[3])
		cursor = conn.cursor()
	except Exception as e:
		print(e)
		exit()
	
	host = sys.argv[1]
	port = int(sys.argv[2])
	app.run(host=host, port=port, debug=True)
