package cascade1;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import processing.core.PApplet;

public class Cascade1 extends PApplet {
	
	World world ;
	
	public void setup () {
		size(800,600,P3D);
		background(0);
		
		world = new World(this);

	}

	public void draw(){
		smooth();
		world.update();
		world.draw();
	}

	public void keyPressed() {
		if( key == CODED ){
			if( keyCode  == UP ) {
				world.forward();
			}
			else if( keyCode  == DOWN ) {
				world.backwards();
			}
			else if( keyCode  == LEFT ) {
				world.right(); 
			}
			else if( keyCode  == RIGHT ) {
				world.left();
			}
			else if ( keyCode == KeyEvent.VK_PAGE_UP){
				world.down();
			}
			else if ( keyCode == KeyEvent.VK_PAGE_DOWN){
				world.up();
			}
		}
		else {
			switch(key) {
			
			case 'w':
				world.tiltBack();
				break;
			case 's':
				world.tiltFront();
				break;
			case 'q':
				break;
			case 'a':
				world.curveRight();
				break;
			case 'd':
				world.curveLeft();
				break;
			case 'z':
				world.reset();
				break;
			case 'n':
				world.step(true);
				break;
			case 'b':
				world.step(false);
			}
		}
	}
	
	public void mouseClicked() {
		world.debug();
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("tweets");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize ( 800, 600);
		frame.setLocationRelativeTo(null);

		Cascade1 c = new Cascade1();
		c.init();
		frame.add(c, BorderLayout.CENTER);

		
		frame.setVisible(true);


	}
	
}

