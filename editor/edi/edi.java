package edi;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;

public class edi extends JFrame {

	String splChars = "!@@#$%^&*()_-+=\"':;>.<,/?[]{}`~\\|";
	String fontName = "Purisa Bold Oblique";
	int fontsize = 15;
	
	JButton eval = new JButton("Eval");
	JScrollPane sp ;
	JTextPane tp ;
	JFileChooser fc;
	JFrame frame;
	JPanel panel;

	Font[] fonts ;

	Document doc ;

	// Document
	// contain the name,path,history and diffs of itself.
	//
	// document persistence interface
	// disk, sqlite, http
	// load menus as an xml file
	// + jython, scheme (SISC) and c-l (abcl)
	// + processing ( a teditor, a game (battleship ?) , chess,sidescroller-mario...
	//	mspaint.
	// there can be an os-interface - take unix commands, run them into a
	// process, replay back.
	// typewriter sound	
	// ooad that shit

	public edi() {

		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fonts = e.getAllFonts(); 

		doc = new Document("Untitled.txt");

		frame = new JFrame();
		frame.setTitle("Elaine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 600);
		frame.setLocationRelativeTo(null);

		menus();
		makePanel();

		frame.setVisible(true);
	}

	void makePanel() {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		tp = new JTextPane();
//		tp.addKeyListener( typewriterListener );	
		//addToolbar();
		
		sp = new JScrollPane(tp);
		//	tp.setFont( fonts[11]);
		tp.setFont( new java.awt.Font( fontName, 0, fontsize));

		panel.add(new JScrollPane(sp), BorderLayout.CENTER);
		frame.getContentPane().add(panel);
	}

	void menus() {
		JMenuBar menubar = new JMenuBar();

		JPopupMenu.setDefaultLightWeightPopupEnabled(false);

		JMenu file = createMenu("File", KeyEvent.VK_F);

		addMenuItem(file, "New", 	KeyEvent.VK_N, "New", newL);
		addMenuItem(file, "Open", 	KeyEvent.VK_O, "Open", openL);
		addMenuItem(file, "Save", 	KeyEvent.VK_S, "Save", saveL);
		addMenuItem(file, "Save As",KeyEvent.VK_A, "Save As", saveAsL);
		addMenuItem(file, "Quit", 	KeyEvent.VK_Q, "Exit", quitL);

		JMenu run = createMenu("Run", KeyEvent.VK_R);
		addMenuItem(run, "Run -1",  KeyEvent.VK_R, "Run1", run1L);

		JMenu help = createMenu("Help", KeyEvent.VK_H);

		addMenuItem(help, "Credits", KeyEvent.VK_C, "Credits", creditsL);

		menubar.add(file);
		menubar.add(run);
		menubar.add(help);

		frame.setJMenuBar(menubar);
	}

	JMenu createMenu(String s, int mnemonic) {
		JMenu m = new JMenu(s);
		if( mnemonic != -1) {
			m.setMnemonic(mnemonic);
		}
		return m;
	}

	void addMenuItem(JMenu menu, String name, int mnemonic, String toolTipText,
			ActionListener a) {
		JMenuItem item = new JMenuItem(name, null);
		item.setMnemonic(mnemonic);
		item.setToolTipText(toolTipText);
		item.addActionListener(a);
		menu.add(item);

	}


	void addToolbar() {
		JToolBar toolbar = new JToolBar();

		JButton button = new JButton("run1");
		toolbar.setAlignmentX(0);
		toolbar.addSeparator();
		toolbar.setFloatable(false);

		toolbar.add(button);
		panel.add(toolbar, BorderLayout.PAGE_START);

	}

	ActionListener run1L = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(frame, "Royal Northumberland Fusiliers");
		}
	};

	ActionListener newL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// check if saved
			doc = new Document();
			tp.setText(doc.data);
		}
	};
	ActionListener openL = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				try {
					FileInputStream fis = new FileInputStream(file);
					byte[] b = new byte[fis.available()];
					fis.read(b);
					fis.close();
					doc.name = file.getAbsolutePath().substring(
							file.getAbsolutePath().lastIndexOf("/"));
					doc.path = file.getAbsolutePath().substring(0,
							file.getAbsolutePath().lastIndexOf("/"));
					doc.data = new String(b);
					tp.setText(doc.data);
				} catch (Exception e) {
					System.out.println("unable to load file:"
							+ file.getAbsolutePath() + " , Error :"
							+ e.getMessage());
					e.printStackTrace();
				}
			}
		}
	};
	ActionListener saveAsL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile(); // This grabs the File you
				// typed
				try {
					doc.data = tp.getText();
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(doc.data.getBytes());
					fos.close();
				} catch (Exception e) {
					System.out.println("unable to save to file:"
							+ file.getAbsolutePath() + " , Error :"
							+ e.getMessage());
					e.printStackTrace();
				}
			}
		}
	};
	ActionListener saveL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (doc == null || doc.path == null || doc.name == null) {
				JOptionPane
				.showMessageDialog(frame,
						"Please use save-as");
				return;
			}
			File file = new File(doc.path + File.separatorChar + doc.name);
			try {
				doc.data = tp.getText();
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(doc.data.getBytes());
				fos.close();
			} catch (Exception e) {
				System.out.println("unable to save to file:"
						+ file.getAbsolutePath() + " , Error :"
						+ e.getMessage());
				e.printStackTrace();
			}
		}
	};
	ActionListener quitL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			frame.dispose();
			System.exit(0);
		}
	};
	ActionListener creditsL = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane
			.showMessageDialog(frame,
					"I was trying to make a dopewars clone.\n\tgoingkilo@gmail.com");
		}
	};
	
	
	KeyListener typewriterListener = new KeyListener() {

		@Override
		public void keyPressed(KeyEvent arg0) {
			char key = arg0.getKeyChar();
			if( 
					((int)key >= (int)'a' && (int)key <= (int)'z' )
					||
					( (int)key >= (int)'A' && (int)key <= (int)'Z' ) 
					||
					((int)key >= (int)'0' && (int)key <= (int)'9')
					||
					( splChars.indexOf(key) >= 0)
				) 
			{
				playSoundNow();
			}
			//if key is in 0-9a-zA-Z!@@#$%^&*()_-+="':;>.<,/?[]{}`~\|
			//also do a wait-notify
			//or a simple non thread audioclip play
			//playSound();			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}};
	
	public static void main(String[] args) {
		new edi();
	}

	
	void playSoundNow() {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream( 
					new FileInputStream( 
							new File(("/home/kilo/typekey.wav"))));
			clip.open(inputStream);
			clip.start(); 
		} 
		catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	static synchronized void playSound() {
		new Thread(new Runnable() { 
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream( 
							new FileInputStream( 
									new File(("/home/kilo/typekey.wav"))));
					clip.open(inputStream);
					clip.start(); 
				} 
				catch (Exception e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}).start();
	}

}
