package cascade1;

import processing.core.PApplet;
import processing.core.PFont;

public class World {

	PApplet p;
	float  x = -150, y = 550, z = -850;
	float rx, ry = 39f, rz;

	float speed = 50, tilt = 1.5f;

	int tick;

	PFont font;
	String fontfile = "C:/Users/karthik.raghunathan/Downloads/processing-1.5.1-windows-expert/processing-1.5.1/modes/java/examples/Books/Visualizing Data/ch03-usmap/step08_rollovers/data/Univers-Bold-12.vlw";

	Tweets data;

	public World(PApplet p) {
		this.p = p;

		font = p.loadFont(fontfile);
		p.textFont(font,20);

		data = new Tweets(p);
		data.setWorld(this);
	}

	public void update() {
		tick ++;
	}

	public void draw() {
//		System.out.println("tick:" + tick);
		
		p.background(0);

		p.translate(x,y,z);

		p.rotateX( PApplet.radians(rx) );
		p.rotateY( PApplet.radians(ry) );
		p.rotateZ( PApplet.radians(rz) );

		//		axes();
		data.draw(tick);

	}

	void axes() {
		p.stroke(254);
		p.noFill();

		p.line( 0,0,0,  0,0,1000);   
		p.line( 0,0,0,  0,1000,0);
		p.line( 0,0,0,  1000,0,0);
	}

	void rings() {
		p.pushMatrix();
		p.rotateX( PApplet.radians(70));
		p.stroke(150);
		p.noFill();
		for( int i = 0 ; i < 1000 ; i+= 40) {
			p.ellipse(0,0, i,i);
		}
		p.popMatrix();
	}

	public void tilt() {
		p.rotateX( PApplet.radians(70));
	}

	public void up() {
		y -= speed;
	}

	public void down() {
		y += speed;
	}

	public void left() {
		x -= speed;
	}

	public void right() {
		x += speed;
	}

	public void forward() {
		z += speed;
	}

	public void backwards() {
		z -= speed;
	}

	public void curveLeft() {
		ry += tilt;
	}

	public void curveRight() {
		ry -= tilt;
	}

	public void tiltFront() {
		rx += tilt;
	}

	public void tiltBack() {
		rx -= tilt;
	}

	public void reset() {
		x = 0;
		y = 0;
		z = -100;

		rx = 0f;
		ry = 0f;
		rz = 0f;
		
		tick = 0;
		data.reset();

	}
	
	public void step(boolean t) {
		data.cursorNext(t);
	}
	
	public void debug() {
		System.out.println( "x " +  x);
		System.out.println( "y " +  y);
		System.out.println( "z " +  z);
		System.out.println( "rx " +  rx);
		System.out.println( "ry " +  ry);
		System.out.println( "rz " +  rz);
	}

}
