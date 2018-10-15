#!/usr/bin/env python3
import sys
import re
import csv

def load_from_country_csv_file(csv_file_name):
	csv_file = open(csv_file_name)
	reader = csv.reader(csv_file)
	
	gdp_usd_data = []
	gdp_ppp_data = []
	gdp_usd_capita_data = []
	gdp_ppp_capita_data = []
	year_chart = []
	for row in reader:
		if len(row) < 4:
			continue

		if row[0] == "Country Name":
			year_chart = row
			print(year_chart)
			continue
			
		cur_data_category = row[2]
		if cur_data_category == "GDP per capita, PPP (current international $)":
			for i in range(4, len(row)-1):
				country_name = row[0]
				year = int(year_chart[i])
				gdp_ppp_capita_value = row[i]
				gdp_ppp_capita_data.append([country_name, year, gdp_ppp_capita_value])
		elif cur_data_category == "GDP per capita (current US$)":
			for i in range(4, len(row)-1):
				country_name = row[0]
				year = int(year_chart[i])
				gdp_usd_capita_value = row[i]
				gdp_usd_capita_data.append([country_name, year, gdp_usd_capita_value])
		elif cur_data_category == "GDP, PPP (constant 2011 international $)":
			for i in range(4, len(row)-1):
				country_name = row[0]
				year = int(year_chart[i])
				gdp_ppp_value = row[i]
				gdp_ppp_data.append([country_name, year, gdp_ppp_value])
		elif cur_data_category == "GDP (current US$)":
			for i in range(4, len(row)-1):
				country_name = row[0]
				year = int(year_chart[i])
				gdp_usd_value = row[i]
				gdp_usd_data.append([country_name, year, gdp_usd_value])
	return (gdp_ppp_capita_data, gdp_ppp_data, gdp_usd_capita_data, gdp_usd_data)
	
def save_table(cur_data, file_name):
	output_file = open(file_name, 'w')
	writer = csv.writer(output_file)
	for entry in cur_data:
		writer.writerow(entry)
	output_file.close()

if __name__ == '__main__':
	gdp_ppp_capita_data, gdp_ppp_data, gdp_usd_capita_data, gdp_usd_data = load_from_country_csv_file('arab_data.csv')
	save_table(gdp_usd_data, 'gdp_usd_data.csv')
	save_table(gdp_usd_capita_data, 'gdp_usd_capita_data.csv')
	save_table(gdp_ppp_data, 'gdp_ppp_data.csv')
	save_table(gdp_ppp_capita_data, 'gdp_ppp_capita_data.csv')
	
