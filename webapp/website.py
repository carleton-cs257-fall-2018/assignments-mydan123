#!/usr/bin/env python3
import sys
import flask

app = flask.Flask(__name__, static_folder='static', template_folder='templates')

@app.route('/')
def get_main_page():
	global api_port
	global port
	return flask.render_template('front_page.html', api_port=api_port, port=port)

@app.route('/country/<country>')
def get_country_page(country):
	global api_port
	return flask.render_template('country_page.html', api_port=api_port, port=port, country_name=str(country))
	
if __name__ == '__main__':
	if len(sys.argv) != 4:
		print('Usage: {0} host port api-port'.format(sys.argv[0]), file=sys.stderr)
		exit()

	host = sys.argv[1]
	port = sys.argv[2]
	api_port = sys.argv[3]
	app.run(host=host, port=int(port), debug=True)
