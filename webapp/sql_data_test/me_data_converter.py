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
	"Population, total",

]

def load_from_data_folder(folder):
	file_list = os.listdir(folder)
	list_of_all_countries = []
	
	for file_name in file_list:
		
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
				cur_country_data.append(row[0])
				
			current_data_category = row[2]
			if current_data_category in categories_list:
				current_category_list = []
				current_category_list.append(row[2])
				current_category_list.extend(row[4:])
				cur_country_data.append(current_category_list)
						
		list_of_all_countries.append(cur_country_data)
	return (list_of_all_countries)


def save_tables(list_of_all_countries):
	
	for  list in list_of_all_countries:
		
		filename = "data_output/"+list[0]+"_data.csv"		
		output_file = open(filename, 'w')
		writer = csv.writer(output_file)
		for entry in list[1:]:
			writer.writerow(entry)
		output_file.close()

if __name__ == '__main__':
	all_countries_list = load_from_data_folder('source_data')
	save_tables(all_countries_list)
	
#Note: To put tables into SQL, use:
#\copy data_gdp_usd from 'gdp_usd_data.csv' DELIMITER ',' CSV NULL as ''
