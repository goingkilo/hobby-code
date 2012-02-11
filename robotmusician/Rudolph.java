import processing.core.PApplet;

public class Rudolph extends PApplet {
	int x;

	Fatso fatso;

	public void setup() {
		size(500,340);
		background(254);

		try {
			fatso = new Fatso();
			fatso.add( "Piano", 	fatso.channels[2], fatso.instruments[0],  1200l, this, 0);
			fatso.add( "NatStlGtr", fatso.channels[1], fatso.instruments[24], 1000l, this, 100);
			fatso.add( "NatStlGtr", fatso.channels[0], fatso.instruments[24], 800l,  this, 200);

		} catch (Exception e) {
			e.printStackTrace();
		}

		fatso.on();
	}

	@Override
	public void mouseClicked(){
		println("hello world");
		stroke( mouseX % 254, mouseY % 254, 200);
		rect(mouseX,mouseY,5,7);
	}

	public void draw(){

	}

	public synchronized int drawBar(int yOffset, int x, int y) {
//		fill(254);
//		rect(0,yOffset, 500,yOffset);
		
		stroke(x, 50+x,100);
		rect( 500-x ,yOffset + (100 - y), 10, y );

		return x;
	}

}

