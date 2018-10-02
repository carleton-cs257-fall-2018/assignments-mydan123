#!/usr/bin/env python3
'''
    api-test.py
    Daniel Busis
    10/1/18
'''

import sys
import json
import urllib.request


def main():
    base_url = 'https://data.cdc.gov/resource/u4d7-xz8k.json?cause_name=Stroke&state=Minnesota'
    if len(sys.argv) > 1:
        base_url += "&year="+sys.argv[1]
    data_from_server = urllib.request.urlopen(base_url).read()
    string_from_server = data_from_server.decode('utf-8')
    deaths_data_list = json.loads(string_from_server)
    if len(sys.argv) == 1:
        print(deaths_data_list)
    else:
        death_stat = deaths_data_list[0]
        print("State: " + death_stat['state'])
        print("Year: " + death_stat['year'])
        print("Cause of Death: " + death_stat['cause_name'])
        print("Deaths: " + death_stat['deaths'])
        print("Age-adjusted Death Rate: " + death_stat['aadr'])
    
def _print_argv_error():
    raise Exception("Incorrect command line arguments!")

if __name__ == '__main__':
    print("This program outputs stroke deaths in Minnesota, optionally for a given year.")
    print("Correct usage: api-test.py optional: [year]")
    print("Optional arguments:")
    print("[year]: year the deaths occured in. Must be between 1999 and 2016.")
    print()
    if (len(sys.argv) != 1 and len(sys.argv) != 2):
        _print_argv_error()
    if len(sys.argv) == 2 and (int(sys.argv[1]) < 1999 or int(sys.argv[1]) > 2016):
        _print_argv_error()    
    main()

