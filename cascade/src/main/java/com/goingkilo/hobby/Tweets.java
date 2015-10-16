package com.goingkilo.hobby;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import processing.core.PApplet;

public class Tweets {

	Tweet[] tweets;
	long range, base;
	List<Point> points ;
	PApplet p;

	int ptr, pointToDisplay = 4;
	World world;


	public Tweets() {
		tweets = load();
		points = getPoints();
	}

	public Tweets(PApplet p) {
		this.p = p;
		tweets = load();
		points = getPoints();

		pointToDisplay = 0;

	}

	public Tweet[] load()  {
		try {
			FileInputStream fis = new FileInputStream(new File("c:/users/karthik.raghunathan/Desktop/cascade/cascadedata.txt"));
			byte[] b = new byte[fis.available()];
			fis.read(b);
			fis.close();
			String s = new String(b);
			List<Tweet> tweets = new ArrayList<Tweet>();
			String[] lines = s.split("\n");
			for( int i = 0 ; i < lines.length ; i+= 3){
				String date = lines[i].trim();
				String name = lines[i+1].trim();
				String text = lines[i+2].trim();
				Tweet tweet = new Tweet( name,date,text);
				//System.out.println( date + "/"+name);
				tweets.add(tweet);
			}
			return tweets.toArray( new  Tweet[tweets.size()] );
		}
		catch(Exception e) { e.printStackTrace(); }
		return null;
	}

	public List<Point> getPoints() {

		List<Long> dates = new ArrayList<Long>();

		for( Tweet tweet : tweets ) {
			dates.add( (long) tweet.convertDate() );
		}
		Collections.sort( dates, new Comparator<Long>() {
			@Override
			public int compare(Long a, Long b) {
				return (int) (a.longValue() - b.longValue());
			}
		});

		base = dates.get(0).longValue();
		//		System.out.println("base - " + base);
		long max = dates.get( dates.size()-1).longValue();
		range = max - base;
		long step = range / dates.size();

		// x - time
		// y - topic
		// z - user

		List<Point> points = new ArrayList<Point>();
		for( int i = 0 ; i < tweets.length ; i++ ) {

			Point p = new Point();
			p.tweet = tweets[i];

			p.x  = 100 + ((p.tweet.convertDate() - base)/range)*800;

			if( p.tweet.text.contains("eggs") ) {
				p.z = 200;
			}
			else if( p.tweet.text.contains("pancakes") ) {
				p.z = 400;
			}
			else {
				p.z = 600;
			}

			p.y = -i*40;
			///System.out.println( p.toString() );
			points.add(p);
		}

		return points;
	}

	public void draw(int tick) {
		if( tick > (ptr * 25)) {
			ptr ++;
		}

		for( int i = 0 ; i < points.size() ; i++ ) {
			if( i > ptr) break;

			p.pushMatrix();

			p.translate( points.get(i).x, points.get(i).y, points.get(i).z);

			switch( (int)points.get(i).z ){
			case 200:
				p.fill( 200, 0, 0);
				break;
			case 400:
				p.fill( 0, 200, 0);
				break;
			case 600:
				p.fill( 0, 0, 200);
				break;
			}

			p.stroke(254);

			p.box(10);

			//show tweets flat
//			p.pushMatrix();
//			p.rotateY( PApplet.radians(-world.ry));
			p.text(  points.get(i).tweet.user +"->"+ points.get(i).tweet.text, 10, 10, 0);
//			p.popMatrix();

			//show line stem
			p.popMatrix();
			p.stroke(180);
			p.strokeWeight(3);
			p.line( 0, 0, 0, points.get(i).x, 0, points.get(i).z );
			p.line( points.get(i).x, 0, points.get(i).z, points.get(i).x, points.get(i).y, points.get(i).z );

			drawCursor();

		}
	}

	private void drawCursor() {
		p.pushMatrix();
		Point cursor = points.get(pointToDisplay);
		p.translate( cursor.x, cursor.y, cursor.z);
		p.stroke(254);
		p.box(20);
		p.popMatrix();
//		System.out.println(" cursor is at " + pointToDisplay);
	}

	public static void main1(String[] args) throws IOException {
		Tweets t = new Tweets();
		List<Point> points = t.getPoints();
		for( Point point : points) {
			System.out.println( point.toString() );
		}
	}

	public void cursorNext(boolean forward ) {
		
		if( forward ){
			pointToDisplay ++;
			pointToDisplay = pointToDisplay % points.size();
		}
		else {
			pointToDisplay --;
			if( pointToDisplay < 0 ){
				pointToDisplay  = 0;
			}
		}
	}

	public void reset() {
		ptr = 0;
	}

	public void setWorld(World world) {
		this.world  = world;
	}
}
