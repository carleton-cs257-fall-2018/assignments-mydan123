#!/usr/bin/env python3
import sys
import re
import csv
import os

def load_from_data_folder(folder):
	file_list = os.listdir(folder)

	gdp_usd_data = []
	gdp_ppp_data = []
	gdp_usd_capita_data = []
	gdp_ppp_capita_data = []
	for file_name in file_list:
		csv_file = open(folder+'/'+file_name)
		reader = csv.reader(csv_file)
		
		year_chart = []
		for row in reader:
			if len(row) < 4:
				continue

			if row[0] == "Country Name":
				year_chart = row
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
	gdp_ppp_capita_data, gdp_ppp_data, gdp_usd_capita_data, gdp_usd_data = load_from_data_folder('source_data')
	save_table(gdp_usd_data, 'gdp_usd_data.csv')
	save_table(gdp_usd_capita_data, 'gdp_usd_capita_data.csv')
	save_table(gdp_ppp_data, 'gdp_ppp_data.csv')
	save_table(gdp_ppp_capita_data, 'gdp_ppp_capita_data.csv')
	
#Note: To put tables into SQL, use:
#\copy data_gdp_usd from 'gdp_usd_data.csv' DELIMITER ',' CSV NULL as ''