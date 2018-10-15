'''
	Daniel Busis
	Messing around with psycopg2
	Pretty sure this works, as long as it's run from inside perlman
	(At least, it worked by typing into a pytohn3 shell there)
'''

import psycopg2



try:
	conn = psycopg2.connect(dbname="busisd")
except Exception as e:
    print(e)
    exit()

try:
	cursor = conn.cursor()
	cursor.execute('SELECT * FROM data_gdp_usd')
except Exception as e:
	print(e)
	exit()
	
for row in cursor:
	print(row)
	
try:
	cursor.execute('SELECT * FROM data_gdp_usd WHERE year=1960')
except Exception as e:
	print(e)
	exit()

for row in cursor:
	print(row)

conn.close()
