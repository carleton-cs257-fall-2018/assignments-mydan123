'''API endpoint Implementation
@author Daniel Busis
@author Will Thompson'''

import sys
import flask
import json

app = flask.Flask(__name__)

@app.route('/countries')
def get_countires():
    return json.dumps(['goose', 'moose'])

if __name__ == '__main__':
    if len(sys.argv) != 3:
        print('Usage: {0} host port'.format(sys.argv[0]))
        print('  Example: {0} perlman.mathcs.carleton.edu 5101'.format(sys.argv[0]))
        exit()
    
    host = sys.argv[1]
    port = int(sys.argv[2])
    app.run(host=host, port=port, debug=True)
