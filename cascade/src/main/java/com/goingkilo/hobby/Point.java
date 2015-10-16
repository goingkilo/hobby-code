package com.goingkilo.hobby;

public class Point {
    float x,y,z;
    Tweet tweet;
    public Point(){}
    public Point(float a, float b, float c) {
        x = a;
        y = b;
        z = c;
    }
    
    public String toString() {
    	return x + " ,"+ y  + " ," + z;
//    	return x + " ,"+ y  + " ," + z + "\n"+tweet.user + "/" + tweet.date + "/" + tweet.text;
    }
}
