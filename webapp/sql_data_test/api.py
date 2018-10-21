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

	
#You can't use start_year/end_year and year arguments
@app.route('/country/data/')
def data_for_one_country():
	data_list = []
	
	start_year = flask.request.args.get('start_year')
	end_year = flask.request.args.get('end_year')

	list_of_years_requested = make_years_list(start_year, end_year)
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
		print(select_string)
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
def make_years_list(start_year, end_year):
	if start_year is None:
		start_year = 1960
	else:
		start_year = int(start_year)
	if end_year is None:
		end_year = 2017
	else:
		end_year = int(end_year)

	year_list = []
	for i in range(start_year, end_year+1):
		year_list.append("year_"+str(i))
	return year_list

def make_column_string(year_list):
	column_string = "country_id,stat_id"
	for i in year_list:
		column_string+=","+i
	return column_string
	
@app.route('/country/')
def return_all_countries_and_ids():
    pass
    
@app.route('/country/<country_name>')
def return_one_country_and_id(country_name):
    pass
    
conn = None
cursor = None
if __name__ == '__main__':
	if len(sys.argv) != 3:
		print('Usage: {0} host port'.format(sys.argv[0]))
		print('  Example: {0} perlman.mathcs.carleton.edu 5101'.format(sys.argv[0]))
		exit()
	
	try:
		conn = psycopg2.connect(dbname="busisd")
		cursor = conn.cursor()
	except Exception as e:
		print(e)
		exit()
	
	host = sys.argv[1]
	port = int(sys.argv[2])
	app.run(host=host, port=port, debug=True)