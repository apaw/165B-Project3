from pyparsing import *

data_file = open("email-Enron.txt")

grammar = Word(nums) + Word(nums)

buffer = ""

for line in data_file:
	buffer += line
	match = next(grammar.scanString(buffer), None)
	while match:
		tokens, start, end = match
		if (int(tokens[0]) < int(tokens[1])):
			print tokens[0] + ' ' + tokens[1]
		buffer = buffer[end:]
		match = next(grammar.scanString(buffer), None)
