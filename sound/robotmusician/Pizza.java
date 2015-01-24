
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import processing.core.PApplet;


public class Pizza {

	JFrame frame;
	JPanel panel;

	JFileChooser fc;

	PApplet p;

	public Pizza(){}

	public Pizza(String title, int w, int h, PApplet p)  {

		frame = new JFrame();
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize (w,h);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		//menus();
		panel(p);

		frame.setVisible(true);
	}

	public void menus()
	{
		JMenuBar menubar  = new JMenuBar();

		JPopupMenu.setDefaultLightWeightPopupEnabled (false);
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		addMenuItem( file, "New", KeyEvent.VK_N, "New  ", 
				new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				System.out.println("new I");
			}
		}
				);

		addMenuItem(file, "Load", KeyEvent.VK_L, "Load",
				new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) 
				{
					File file = fc.getSelectedFile(); //This grabs the File you typed
					try
					{	
						FileInputStream fis = new FileInputStream(file);
						byte[] b= new byte[fis.available()];
						fis.read(b);
						fis.close();
						//document = new String(b);
					}	
					catch(Exception e)
					{
						System.out.println("unable to load file:"+file.getAbsolutePath() + " , Error :"+e.getMessage());
					}	
				}
			}
		}
				);

		addMenuItem(file, "Save", KeyEvent.VK_S, "Save",
				new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) 
				{
					File file = fc.getSelectedFile(); //This grabs the File you typed
					try
					{
						FileOutputStream fos = new FileOutputStream( file );
						//fos.write(document.getBytes());
						fos.close();
					}	
					catch(Exception e)
					{
						System.out.println("unable to save to file:"+file.getAbsolutePath() + " , Error :"+e.getMessage());
					}	
				}
			}
		}
				);

		addMenuItem(file, "Quit", KeyEvent.VK_X, "Exit",
				new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				frame.dispose();
				System.exit(0);
			}
		}
				);


		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);

		addMenuItem(help, "Credits", KeyEvent.VK_C, "Credits",  new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame,"dev : raghunathan.karthik@gmail.com");
			}
		});

		menubar.add(file);
		menubar.add(help);

		frame.setJMenuBar(menubar);
	}

	private void addMenuItem(JMenu menu, String name, int mnemonic, String toolTipText, ActionListener a)
	{
		JMenuItem item = new JMenuItem(name, null);
		item.setMnemonic(mnemonic);
		item.setToolTipText(toolTipText);
		item.addActionListener(a);
		menu.add(item);

	}

	private void panel( PApplet p) 
	{
		p.init();
		frame.add(p, BorderLayout.CENTER);
	}

	public static void main(String args[]) throws Exception
	{
		//		String papplet = args[0];
		//		Class c = Class.forName(papplet);
		//		PApplet object = (PApplet)c.newInstance();
		Rudolph r = new Rudolph();
		Pizza o = new Pizza("Robot Piano", 500, 350, r);

	}

}

