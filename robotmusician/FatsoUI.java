
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class FatsoUI {

	JFrame frame;
	JPanel panel;
	JButton button ;
	
	Fatso fatso;

	public FatsoUI() {
		try {
		
			frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize( 200, 100);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
            
            init();
                 
			panel = new JPanel();
                        
			button = new JButton("Play");
			button.addActionListener( new ActionListener(){
                        
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if( button.getText().equals("Play")) {
						button.setText( "Pause" );
						fatso.on();
					}
					else {
						button.setText( "Play" );
						fatso.off();
					}
				}});
                        
			panel.add(button);
			frame.getContentPane().add(panel,  BorderLayout.CENTER);
			frame.setVisible(true);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog( frame, "Something went wrong, the error message is  -\n" + e.getMessage()
                                                              + " \ndrop a line to reddit-user lani at http://www.reddit.com/user/lani/" 
				                              + " " );
			System.exit(0);
		}
	}

        void init() throws Exception {
                try {
                        
			fatso = new Fatso();
//		fatso.add( "Piano", fatso.channels[2], fatso.instruments[0], 1200l);
	//		fatso.add( "NatStlGtr", fatso.channels[1], fatso.instruments[24], 1000l);
	//		fatso.add( "NatStlGtr", fatso.channels[0], fatso.instruments[24], 800l);
                }
                catch(Exception e) {
                        throw e;
                }
        }
			

	public static void main(String[] args) {
		new FatsoUI();
	}

}
