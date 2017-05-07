import csv
import requests
import json

url = 'http://192.168.99.100:9200/miniproject4/genomes'

from collections import OrderedDict


with open("1000-genomes.csv","rb") as f:
	lines=[[ word.strip() for word in line.split(',')] for line in f]
	header=lines[0][:22]
	values=lines[1:]
	d=OrderedDict()
	for value in values:
		print zip(header[:22],value[:22])
	
