##
## brainfuck interpreter. very nice.
## always append to the last item in the list

def valid(i):
    return i in '+-><[].,'

#always append to the last item of a
def cmpl(a):
    ll =[[]]
    for i in a:
        #if not valid(i):
        #   continue
	print 'o->',ll,'/',i
        if i == '[':
            ll.append([])
	   		print '->',ll
        elif i == ']':
            x = ll.pop()
	    	print '\t<-' ,x
            ll[len(ll)-1].append(x)
        else:
            ll[len(ll)-1].append(i)
    return ll[0]
        
        

a = 'a[b[c]d]e[f]g'
import sys
print cmpl(sys.argv[1])
