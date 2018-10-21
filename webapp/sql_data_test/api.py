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
	
	optional_args = False
	if len(stat_id) > 0
			or len(country_id) > 0
			or len(stat_name) > 0
			or len(country_name) > 0:
		select_string += " WHERE"
		optional_args = True

	for stat in stat_id:
		select_string += " stat_id = "+str(stat)+" OR"
	for stat in country_id:
		select_string += " country_id = "+str(stat)+" OR"
	
	#This line works:
	#SELECT year_1960 FROM algeria_data,stat_ids WHERE stat_ids.stat_name LIKE 'GDP%' AND algeria_data.stat_id=stat_ids.stat_id;
	
	#Trims off trailing "OR"
	if optional_args:
		select_string = select_string[:-3]
	
	try:
		cursor.execute(select_string)
	except Exception as e:
		print(e)
		exit()
	
	for row in cursor:
		data_list.append(row)
	
	return json.dumps(data_list)

def make_years_list(start_year, end_year):
	if start_year is None:
		start_year = 1960
	if end_year is None:
		end_year = 2017
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