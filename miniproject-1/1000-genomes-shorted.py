with open("1000-genomes.csv","r") as f1:
	with open("1000-genomes-shortned.csv", "w") as f2:
		f2.write('\n'.join([','.join(line.split(',')[:22]) for line in f1]))