#!/usr/bin/env python3
import sys
import re
import csv
import os

categories_list = [
	"GDP per capita, PPP (current international $)",
	"GDP per capita (current US$)",
	"GDP, PPP (constant 2011 international $)",
	"GDP (current US$)"
]

def load_from_data_folder(folder):
	file_list = os.listdir(folder)
	list_of_all_countries = []
	
	for file_name in file_list:
		cur_country_list = []
		csv_file = open(folder+'/'+file_name)
		reader = csv.reader(csv_file)
		
		year_chart = []
		for row in reader:
			if len(row) < 4:
				continue

			if row[0] == "Country Name":
				year_chart = row
				continue
				
			if len(cur_country_list) == 0:
				cur_country_list.append(row[0])
				
			cur_data_category = row[2]
			if cur_data_category in categories_list:
				cur_category_list = []
				cur_category_list.append(row[2])
				cur_category_list.extend(row[4:])
				cur_country_list.append(cur_category_list)
						
		list_of_all_countries.append(cur_country_list)
	return (list_of_all_countries)


def save_table (list_of_all_countries):
	for  list in list_of_all_countries:
		filename = list_of_all_countries[0]+".csv"
		
		output_file = open(filename, 'w')
		writer = csv.writer(output_file)
		for entry in cur_data[1:]:
			writer.writerow(entry)
		output_file.close()

if __name__ == '__main__':
	all_countries_list = load_from_data_folder('source_data')
	#save_table(gdp_usd_data, 'gdp_usd_data.csv')
	#save_table(gdp_usd_capita_data, 'gdp_usd_capita_data.csv')
	#save_table(gdp_ppp_data, 'gdp_ppp_data.csv')
	#save_table(gdp_ppp_capita_data, 'gdp_ppp_capita_data.csv')
	
	#[["Bahrain", rest of list ] ["Arab World", rest of list] [] [] []]
	print(all_countries_list[0]	)
	
#Note: To put tables into SQL, use:
#\copy data_gdp_usd from 'gdp_usd_data.csv' DELIMITER ',' CSV NULL as ''