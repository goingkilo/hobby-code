
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Pizza extends JPanel {

	JFrame frame;
	Fatso fatso;

	public Pizza(){}

	public Pizza(String title, int w, int h)  {

		frame = new JFrame();
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize (w,h);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		frame.add(this);

		try {
			fatso = new Fatso();
			fatso.add( "Piano", 	fatso.channels[2], fatso.instruments[0],  3200l, this, 0);
//			fatso.add( "NatStlGtr", fatso.channels[1], fatso.instruments[24], 400l, this, 100);
			fatso.add( "NatStlGtr", fatso.channels[0], fatso.instruments[24], 5800l,  this, 200);

		} catch (Exception e) {
			e.printStackTrace();
		}

		fatso.on();

		frame.setVisible(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)this.getGraphics();

		g2d.setBackground(Color.white);

	}

	Color getColor(int offset) {
		switch (offset) {
		case 0:
			return Color.BLUE;
		case 100:
			return Color.RED;
		default:
			return Color.GREEN;
		}
	}

	int finkle(int offset, int x, int y){

		Graphics2D g2d = (Graphics2D)this.getGraphics();

		g2d.setColor(getColor(offset));
		g2d.drawRect( 500-x, offset + (100-y), 10, y);

		return x;
	}

	public static void main(String args[]) throws Exception
	{
		Pizza pizza = new Pizza("", 500, 350);

	}

}


