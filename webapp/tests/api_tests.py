#Unittests for API Queries
#@author Will Thompson
#@author Daniel Busis


countries = ['Jordan', 'Algeria', 'Morocco', 'Tunisia', 'Libya', 'Egypt', 'Sudan', 'South Sudan', 'Somalia', 'Israel', 'Lebanon', 'Syria'
             'Iraq', 'Turkey', 'West Bank and Gaza', 'Iran', 'Qatar', 'Saudi Arabia', 'Bahrain', 'United Arab Emirates', 'Oman', 'Yemen', 'Mauritania']
        
        
    
class APIQueryUnitTests(unittest.TestCase)
    
    def test_query_retrieving_GDP_for_one_country_in_one_year(self):
        url = 'http://perlman.mathcs.carleton.edu:portnumber/countries/gdp/?country=Jordan?year=2017'
        data_from_server = urllib.request.urlopen(url).read()
        decoded_data_from_server = data_from_server.decode('utf-8')
        server_return_string = json.loads(string_from_server)
        self.assertEqual(server_return_string, [{'name' : 'Jordan', 'year' : 2017, 'GDP_USD' :40068308516}])
        
    def test_for_query_retrieving_GDP_for_each_country_in_one_given_year(self):
        url = 'http://perlman.mathcs.carleton.edu:portnumber/countries/gdp/?country=Jordan&country=West_Bank_and_Gaza&country=Bahrain&country=Oman&year=2017'
        data_from_server = urllib.request.urlopen(url).read() 
        decoded_data_from_server = data_from_server.decode('utf-8')
        server_return_string= json.loads(decoded_data_from_server)
        self.assertEqual(server_return_string, 
                         [{'name' : 'Jordan', 'year' : 2017, 'GDP_USD' : 40068308516}
                          {'name' : 'West Bank and Gaza', 'year' : 2017, 'GDP_USD' : 14498100000}
                          {'name' : 'Bahrain', 'year' : 2017, 'GDP_USD' : 35307127660}
                          {'name' : 'Oman', 'year' : 2017, 'GDP_USD' : 72642652796}])
        
    def test_for_query_retrieving_GDP_for_a_period_of_years(self):
        url = 'http://perlman.mathcs.carleton.edu:portnumber/countries/gdp/country=Algeria?start_year=2000&end_year=2005'
        data_from_server = urllib.request.urlopen(url).read()
        decoded_data_from_server = data_from_server.decode('utf-8')
        server_return_string = json.loads(string_from_server)
        self.assertEqual(server_return_string,
                          [{'name' : 'Algeria', 'year' : 2000, 'GDP_USD' : 54790245601}
                          {'name' : 'Algeria', 'year' : 2001, 'GDP_USD' : 54744714396}
                          {'name' : 'Algeria', 'year' : 2002, 'GDP_USD' : 56760288974}
                          {'name' : 'Algeria', 'year' : 2003, 'GDP_USD' : 67863829880}
                          {'name' : 'Algeria', 'year' : 2004, 'GDP_USD' : 85324998814}
                          {'name' : 'Algeria', 'year' : 2005, 'GDP_USD' : 1.03198E+11}])
        
    def test_query_retrieving_Spending_for_one_country_in_one_year(self):
        url = 'http://perlman.mathcs.carleton.edu:portnumber/economicdata/Spending/?country=Jordan?year=2017'
        data_from_server = urllib.request.urlopen(url).read()
        decoded_data_from_server = data_from_server.decode('utf-8')
        server_return_string = json.loads(string_from_server)
        self.assertEqual(server_return_string, [{'name' : 'Jordan', 'year' : 2017, 'spending' :48663097248.6698}])
		
    def test_query_retrieving_government_debt_for_one_country_in_one_year(self):
        url = 'http://perlman.mathcs.carleton.edu:portnumber/economicdata/govt_debt/?country=Jordan?year=2017'
        data_from_server = urllib.request.urlopen(url).read()
        decoded_data_from_server = data_from_server.decode('utf-8')
        server_return_string = json.loads(string_from_server)
        self.assertEqual(server_return_string, [{'name' : 'Jordan', 'year' : 2017, 'govt_debt' :12340520389.1094}])

    def test_query_retrieving_military_expenditure_for_one_country_in_one_year(self):
        url = 'http://perlman.mathcs.carleton.edu:portnumber/economicdata/military_expenditure/?country=Jordan?year=2017'
        data_from_server = urllib.request.urlopen(url).read()
        decoded_data_from_server = data_from_server.decode('utf-8')
        server_return_string = json.loads(string_from_server)
        self.assertEqual(server_return_string, [{'name' : 'Jordan', 'year' : 2017, 'military_expenditure' :1939718309.85915}])

    def test_query_retrieving_literacy_rate_for_one_country_in_one_year(self):
        url = 'http://perlman.mathcs.carleton.edu:portnumber/economicdata/literacy_rate/?country=Jordan?year=2017'
        data_from_server = urllib.request.urlopen(url).read()
        decoded_data_from_server = data_from_server.decode('utf-8')
        server_return_string = json.loads(string_from_server)
        self.assertEqual(server_return_string, [{'name' : 'Jordan', 'year' : 2017, 'literacy_rate' : None}])

    def test_query_retrieving_unemployment_for_one_country_in_one_year(self):
        url = 'http://perlman.mathcs.carleton.edu:portnumber/economicdata/unemployment/?country=Jordan?year=2014'
        data_from_server = urllib.request.urlopen(url).read()
        decoded_data_from_server = data_from_server.decode('utf-8')
        server_return_string = json.loads(string_from_server)
        self.assertEqual(server_return_string, [{'name' : 'Jordan', 'year' : 2014, 'unemployment' : 11.89999962}])

    def test_query_retrieving_population_below_poverty_line_for_one_country_in_one_year(self):
        url = 'http://perlman.mathcs.carleton.edu:portnumber/economicdata/population_below_poverty_line/?country=Jordan?year=2010'
        data_from_server = urllib.request.urlopen(url).read()
        decoded_data_from_server = data_from_server.decode('utf-8')
        server_return_string = json.loads(string_from_server)
        self.assertEqual(server_return_string, [{'name' : 'Jordan', 'year' : 2010, 'population_below_poverty_line' : 14.4}])

    def test_query_retrieving_infant_mortality_per_1000_for_one_country_in_one_year(self):
        url = 'http://perlman.mathcs.carleton.edu:portnumber/healthdata/infant_mortality_per_1000/?country=Jordan?year=2017'
        data_from_server = urllib.request.urlopen(url).read()
        decoded_data_from_server = data_from_server.decode('utf-8')
        server_return_string = json.loads(string_from_server)
        self.assertEqual(server_return_string, [{'name' : 'Jordan', 'year' : 2017, 'infant_mortality_per_1000' : 14.6}])

	def test_query_retrieving_death_rate_per_1000_people_for_one_country_in_one_year(self):
        url = 'http://perlman.mathcs.carleton.edu:portnumber/healthdata/death_rate_per_1000_people/?country=Jordan?year=2016'
        data_from_server = urllib.request.urlopen(url).read()
        decoded_data_from_server = data_from_server.decode('utf-8')
        server_return_string = json.loads(string_from_server)
        self.assertEqual(server_return_string, [{'name' : 'Jordan', 'year' : 2016, 'death_rate_per_1000_people' : 3.828}])

	def test_query_retrieving_Percent_population_using_basic_sanitation_services_for_one_country_in_one_year(self):
        url = 'http://perlman.mathcs.carleton.edu:portnumber/healthdata/Percent_population_using_basic_sanitation_services/?country=Jordan?year=2016'
        data_from_server = urllib.request.urlopen(url).read()
        decoded_data_from_server = data_from_server.decode('utf-8')
        server_return_string = json.loads(string_from_server)
        self.assertEqual(server_return_string, [{'name' : 'Jordan', 'year' : 2016, 'Percent_population_using_basic_sanitation_services' : 96.70805802}])

	def test_query_retrieving_description_of_gdp_usd_for_one_country_in_one_year(self):
        url = 'http://perlman.mathcs.carleton.edu:portnumber/description/?data_type=gdp_usd'
        data_from_server = urllib.request.urlopen(url).read()
        decoded_data_from_server = data_from_server.decode('utf-8')
        server_return_string = json.loads(string_from_server)
        self.assertEqual(server_return_string, [{'name' : 'gdp_usd', 'description':'GDP (current US$)'])

		
if __name__ == '__main__':
    unittest.main()