#Unittests for API Queries
#@author Will Thompson
#@author Daniel Busis


countries = ['Jordan', 'Algeria', 'Morocco', 'Tunisia', 'Libya', 'Egypt', 'Sudan', 'South Sudan', 'Somalia', 'Israel', 'Lebanon', 'Syria'
             'Iraq', 'Turkey', 'West Bank and Gaza', 'Iran', 'Qatar', 'Saudi Arabia', 'Bahrain', 'United Arab Emirates', 'Oman', 'Yemen', 'Mauritania']
        
        
    
class APIQueryUnitTests(unittest.TestCase)
    
    def test_query_retrieving_GDP_for_one_country_in_one_year(self):
        
        url = 'http://perlman.mathcs.carleton.edu:portnumber/countries/gdp/?country_name=Jordan?year=2017'
        data_from_server = urllib.request.urlopen(url).read()
        decoded_data_from_server = data_from_server.decode('utf-8')
        Jordan_and_its_GDP_in_2017 = json.loads(string_from_server)
        self.assertEqual(Jordan_and_its_GDP_in_2017, {'name' : 'Jordan', 'year' : 2017, 'GDP' :40068308516})
        
    def test_for_query_retrieving_GDP_for_each_country_in_one_given_year(self):
        
        url = 'http://perlman.mathcs.carleton.edu:portnumber/countries/gdp/?country1=Jordan&country2=West_Bank_and_Gaza&country3=Bahrain&country4=Omanyear=2017'
        data_from_server = urllib.request.urlopen(url).read()
        decoded_data_from_server = data_from_server.decode('utf-8')
        some_countries_and_GDPs_in_one_year= json.loads(decoded_data_from_server)
        self.assertEqual(countries_and_GDPs_in_one_year, 
                         [{'name' : 'Jordan', 'year' : 2017, 'GDP' : 40068308516}
                          {'name' : 'West Bank and Gaza', 'year' : 2017, 'GDP' : 14498100000}
                          {'name' : 'Bahrain', 'year' : 2017, 'GDP' : 35307127660}
                          {'name' : 'Oman', 'year' : 2017, 'GDP' : 72642652796}])
        
    def test_for_query_retrieving_GDP_for_a_period_of_years(self):
        
        url = 'http://perlman.mathcs.carleton.edu:portnumber/countries/gdp/country1=Algeria?start_year=2000&end_year=2005'
        data_from_server = urllib.request.urlopen(url).read()
        decoded_data_from_server = data_from_server.decode('utf-8')
        Algeria_and_GDPs_for_a_set_of_years = json.loads(string_from_server)
        self.assertEqual(Algeria_and_GDPs_for_a_set_of_years,
                          [{'name' : 'Algeria', 'year' : 2000, 'GDP' : 54790245601}
                          {'name' : 'Algeria', 'year' : 2001, 'GDP' : 54744714396}
                          {'name' : 'Algeria', 'year' : 2002, 'GDP' : 56760288974}
                          {'name' : 'Algeria', 'year' : 2003, 'GDP' : 67863829880}
                          {'name' : 'Algeria', 'year' : 2004, 'GDP' : 85324998814}
                          {'name' : 'Algeria', 'year' : 2005, 'GDP' : 1.03198E+11}])
        
        
if __name__ == '__main__':
    unittest.main()