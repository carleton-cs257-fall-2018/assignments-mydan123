#!/usr/bin/env python3
'''
	Daniel Busis, WIll Thompson
'''
import sys
import flask
import json
import psycopg2

app = flask.Flask(__name__)

@app.route('/')
def hello():
	return 'No data requested.'

@app.route('/country/<country_name>')
def data(country_name):
	data_list = []
	
	try:
		select_string = "SELECT * FROM "+country_name+"_data"
		cursor.execute(select_string)
	except Exception as e:
		print(e)
		exit()
	
	for row in cursor:
		data_list.append(row)
	
	return json.dumps(data_list)

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