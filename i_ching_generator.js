//think of a question, write it down if you want to, then generate a hexagram
// then look up the hexagram in an I Ching book . I recommend Wilhelm's translation.
// deoxy.org has a translation
lines = {6:'- - *' ,7 : '---' ,8 : '- -' ,9 : '--- *' }  

toss = function(){if  ( Math.random() > 0.5 ) return 3 ; else return 2;}     

hex = function () {  

 ret = [];   

	for(var x = 0;  x < 6 ; x ++)    {        
		sum = 0;        
		sum += toss();        
		sum+= toss();        
		sum += toss();        
		ret.push(sum);              
	}    

	for( var x = 0 ; x < 6 ; x ++) {        
		console.log( lines[ret[5-x]]);        
	} 
};   

hex()
