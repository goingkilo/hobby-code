package com.goingkilo.hobby;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class RandomEmailData  {

	public List<Point> getPoints() {

		Random r = new Random(System.currentTimeMillis());
		r.setSeed( System.currentTimeMillis() );

		int topicCount 		= (int)(r.nextFloat() * 10);
		int userCount 		= (int)(r.nextFloat() * 50);
		int messageCount 	= (int)(r.nextFloat() * 100);

		String[] users  = new String[50];
		for( int i = 0 ; i < 50 ; i++ ){
			users[i] = getRandomString(r,7);
		}
		
		String[] topics  = new String[10];
		for( int i = 0 ; i < 50 ; i++ ){
			topics[i] = getRandomString(r,20);
		}
		
		Date[] dates = new Date[100];
		Date date = new Date(System.currentTimeMillis());
		//topic, user, time
		
		return null;
	}
	
	String getRandomString(Random r, int size){
		StringBuffer b = new StringBuffer();
		for(int i =0 ; i < size ; i ++ ) {
			b.append( (char)(r.nextInt(10)));
		}
		return b.toString();
		
	}

	public static void main(String [] args) {

	}

}
