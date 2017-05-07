import csv
import requests
import json

url = 'http://192.168.99.100:9200/miniproject4/genomes'

from collections import OrderedDict


with open("1000-genomes.csv","rb") as f:
	reader = csv.reader(f)
	lines=[ line for line in reader]
	header=lines[0][:22]
	values=lines[1:]
	d=OrderedDict()
	for value in values:
		for i in xrange(22):
			d[header[i]]=value[i]
		r = requests.post(url, data = json.dumps(d))
		print r.content
