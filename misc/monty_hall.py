
"""
Monty Hall problem
I now understand how this works. 
The probability of your NOT picking a car is 2/3
Hence, 2/3rd of the time, the car is in the two doors you have not picked
And monty conveniently eliminates one of the two doors for you 
- he always eliminates a goat.
So you now have your door - 30% car, and Monty's door, 60% car.

Such zen
"""

import random

pick = lambda x : random.randint(0,x)
# car is 1 in 3, user picks 1 in 3, monty eliminates 1 in 3, user switches or not, 
def go(d):
        car = pick(2)
        choice1 = pick(2)

        a = [0,1,2]
	print choice1
        a.remove( choice1)
        monty1 = [x for x in a if not car == x][0]
        a.remove(monty1)
        choice2 = a[0]
        if d:
                print "car, choice1, monty-door, choice2 "
                print car, choice1, monty1, choice2
        return car, choice1, monty1, choice2

switchwins = 0
noswitchwins = 0
for i in range(1000):
        car, choice1, monty1, choice2 = go(0)
        if car == choice2:
                switchwins += 1
        if car == choice1:
                noswitchwins += 1
print 'switchwins,no switchwins' 
print switchwins ,'/', noswitchwins
