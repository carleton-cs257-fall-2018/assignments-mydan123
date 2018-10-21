'''Data Converter program that translates raw data into tables storing that data
@author Will Thompson
@author Daniel Busis
10/19/2018'''


import sys
import re
import csv
import os

categories_list = [
	"GDP per capita, PPP (current international $)",
	"GDP per capita (current US$)",
	"GDP, PPP (constant 2011 international $)",
	"GDP (current US$)",
	"Central government debt, total (% of GDP)",
	"Consumer price index (2010 = 100)",
	"Unemployment, total (% of total labor force) (national estimate)",
	"Military expenditure (% of GDP)",
	"Literacy rate, adult total (% of people ages 15 and above)",
	"Literacy rate, adult male (% of males ages 15 and above)",
	"Literacy rate, adult female (% of females ages 15 and above)",
	"Literacy rate, youth male (% of males ages 15-24)",
	"Literacy rate, youth (ages 15-24), gender parity index (GPI)",
	"Literacy rate, youth female (% of females ages 15-24)",
	"Mortality rate, adult, male (per 1,000 male adults)",
	"Mortality rate, adult, female (per 1,000 female adults)",
	"Mortality rate, infant (per 1,000 live births)",
	"School enrollment, secondary, male (% gross)",
	"School enrollment, secondary, female (% gross)",
	"School enrollment, secondary (% gross)",
	"Educational attainment, at least completed short-cycle tertiary, population 25+, total (%) (cumulative)",
	"Educational attainment, at least completed short-cycle tertiary, population 25+, male (%) (cumulative)",
	"Educational attainment, at least completed short-cycle tertiary, population 25+, female (%) (cumulative)",
	"Educational attainment, at least Master's or equivalent, population 25+, total (%) (cumulative)",
	"Educational attainment, at least Master's or equivalent, population 25+, male (%) (cumulative)",
	"Educational attainment, at least Master's or equivalent, population 25+, female (%) (cumulative)",
	"Educational attainment, Doctoral or equivalent, population 25+, total (%) (cumulative)",
	"Educational attainment, Doctoral or equivalent, population 25+, male (%) (cumulative)",
	"Educational attainment, Doctoral or equivalent, population 25+, female (%) (cumulative)",
	"Educational attainment, at least Bachelor's or equivalent, population 25+, total (%) (cumulative)",
	"Educational attainment, at least Bachelor's or equivalent, population 25+, male (%) (cumulative)",
	"Educational attainment, at least Bachelor's or equivalent, population 25+, female (%) (cumulative)",
	"Population, male (% of total)",
	"Population, male",
	"Population, female (% of total)",
	"Population, female",
	"Population, total"
]

def load_from_data_folder(folder):
	file_list = os.listdir(folder)
	list_of_all_countries = []
	
	dict_of_stat_ids = {}
	dict_of_country_ids = {}
	
	for file_name in file_list:
		cur_country_id = len(dict_of_country_ids)
		
		csv_file = open(folder+'/'+file_name)
		reader = csv.reader(csv_file)
		cur_country_data = []
		list_of_years = []
		
		for row in reader:
			if len(row) < 4:
				continue

			if row[0] == "Country Name":
				list_of_years = row
				continue
			
			if len(cur_country_data) == 0:
				dict_of_country_ids[row[0]] = cur_country_id
				
			cur_data_category = row[2]
			if cur_data_category in categories_list:
				if cur_data_category not in dict_of_stat_ids:
					dict_of_stat_ids[cur_data_category] = len(dict_of_stat_ids)
					
				cur_category_list = []
				cur_category_list.append(cur_country_id)
				cur_category_list.append(dict_of_stat_ids[cur_data_category])
				cur_category_list.extend(row[4:len(row)-1])
				cur_country_data.append(cur_category_list)
						
		list_of_all_countries.extend(cur_country_data)
	return (list_of_all_countries, dict_of_stat_ids, dict_of_country_ids)


def save_country_tables(list_of_all_countries):
	filename = "data_output/annual_data.csv"		
	output_file = open(filename, 'w')
	writer = csv.writer(output_file)
	for  list in list_of_all_countries:
		writer.writerow(list)
	output_file.close()

def save_stat_id_tables(dict_of_stat_ids):
	filename = "data_output/stat_ids.csv"
	output_file = open(filename, 'w')
	writer = csv.writer(output_file)
	for key in dict_of_stat_ids:
		writer.writerow([dict_of_stat_ids[key], key])
	output_file.close()

def save_country_id_tables(dict_of_country_ids):
	filename = "data_output/country_ids.csv"
	output_file = open(filename, 'w')
	writer = csv.writer(output_file)
	for key in dict_of_country_ids:
		writer.writerow([dict_of_country_ids[key], key])
	output_file.close()

if __name__ == '__main__':
	all_countries_list, all_stats_dict, all_countries_dict = load_from_data_folder('source_data')
	save_country_tables(all_countries_list)
	save_stat_id_tables(all_stats_dict)
	save_country_id_tables(all_countries_dict)
	
#Note: To put tables into SQL, use:
#\copy data_gdp_usd from 'gdp_usd_data.csv' DELIMITER ',' CSV NULL as ''
