
import urllib
import json

class RedditLink:
        def __init__(self, a,b,c):
                self.title =a
                self.url   =b
                self.subreddit =c

        def dbg(self):
                print self.title +'\n' + self.url + '\n' + self.subreddit

def get():
        ret = []
        a = urllib.urlopen('http://www.reddit.com/.json')
        b = a.read()
        j = json.loads(b)
        for i in j['data']['children']:
                x = i['data']['title']
                y = i['data']['url']
                c = i['data']['permalink']
                z = c[c.find('/r/')+3:c.find('/',c.find('/r/')+3)]
                ret.append( RedditLink(x,y,z) )
        return ret

a = get()
for i in a:
        i.dbg()
