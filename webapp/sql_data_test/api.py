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

@app.route('/country/<country_name>')
def data(country_name):
	data_list = []
	select_string = "SELECT * FROM "+country_name+"_data"
	
	stat_id = flask.request.args.getlist('stat_id')
	if len(stat_id) > 0:
		select_string += " WHERE stat_id = "+str(stat_id[0])
		for stat in stat_id[1:]:
			select_string += " OR stat_id = "+str(stat)
	
	else:
		#This line works:
		#SELECT year_1960 FROM algeria_data,stat_ids WHERE stat_ids.stat_name LIKE 'GDP%' AND algeria_data.stat_id=stat_ids.stat_id;
		stat_name = flask.request.args.get('stat_name')
		if stat_name is not None:
			select_string += " WHERE stat_id = "+str(stat_id[0])
		
	try:
		cursor.execute(select_string)
	except Exception as e:
		print(e)
		exit()
	
	for row in cursor:
		data_list.append(row)
	
	return json.dumps(data_list)

@app.route('/country/<country_name>')

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