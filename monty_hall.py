#monty hall proved in code 

from java.util import Random
from java.lang import System

r = Random(System.currentTimeMillis())
pick = lambda n : r.nextInt(n)

def go(d):
	car = pick(3)
	choice1 = pick(3)

	a = [0,1,2]
	a.remove(choice1)
	monty1 = [x for x in a if not car == x][0]
	a.remove(monty1)
	choice2 = a[0]
	if d:
		print "car, choice1, monty-door, choice2 "
		print car, choice1, monty1, choice2
	return car, choice1, monty1, choice2

wins = 0
for i in range(1000):
	car, choice1, monty1, choice2 = go(0)	
	# no switch
	if car == choice2:
		wins += 1
print wins,' in 1000'

