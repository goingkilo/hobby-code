package misc;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Patch;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Line {

	MidiChannel[] channels;
	Instrument[] instrs ; 
	int channelCount;

	static java.util.Random r = new java.util.Random( System.currentTimeMillis() ); 
	
	public Line() {
		try {
			Synthesizer synth = MidiSystem.getSynthesizer();
			synth.open();
			channels = synth.getChannels();
			Soundbank bank = synth.getDefaultSoundbank();
			synth.loadAllInstruments(bank);
			instrs = synth.getLoadedInstruments();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int addInstrument( int instrument) {
		Patch patch = instrs[instrument].getPatch();
		channels[channelCount].programChange(patch.getBank(), patch.getProgram());
		channelCount ++;
		return channelCount -1; 
	}

	private void playNoteOnChannel( final int channel, final int note, final int pause) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				channels[channel].noteOn(note, 127);
				try {
					Thread.sleep(pause);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}).start();
	}

	private static int a2i(char c) {
		int i = (c - 32) % 110 ; 
		System.out.println( c + ":"+ i );
		return i == 0 ? r.nextInt(100) : i ;
	}

	public void keyboardPlayer() {
		JFrame frame = new JFrame("Tophat Assorted Notes and Player");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(400,150);



		JPanel panel = new JPanel();
		panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS));

		JTextField text = new JTextField();
		text.addKeyListener( new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				char c =  e.getKeyChar();
				playNoteOnChannel( 0 , a2i(c), 500);
			}});

		panel.add(text);
		frame.getContentPane().add(panel);

		frame.setVisible(true);
	}

	public void playText(String s) {
		char[] c = s.toCharArray();
		for( char c0 : c ) {
			playNoteOnChannel(0, a2i(c0), 900);
			sleepForRandomTime();
			playNoteOnChannel(1, 40+r.nextInt(40), 900);
			sleepForRandomTime();
		}
	}
	public void sleepForRandomTime() {
		try {
			int s1 = (8 + r.nextInt(10))*100;
			Thread.sleep(s1);
		}
		catch(Exception e) {
			
		}
	}

	public static void main(String[] args) {
		String s  = "The Dragon is Inside of Me and You"+
				"What comes first is the idea, the passion, the dream of the work we are so excited to create that it scares the hell out of us."+ 
				"Resistance is the response of the frightened, petty, small-time ego to the brave, generous, magnificent impulse of the creative self."+
				"Fear of success is the essence of Resistance. "+
				"The opposite of fear is love—love of the challenge, love of the work, the pure joyous passion to take a shot at our dream and see if we can pull it off."+
				"Our enemy is not lack of preparation; it’s not the difficulty of the project, or the state of the marketplace or the emptiness of our bank account. The enemy is our chattering brain, which, if we give it so much as a nanosecond, will start producing excuses, alibis, transparent self-justifications and a million reasons why we can’t/shouldn’t/won’t do what we know we need to do."+
				"Resistance is a repelling force. It’s negative. Its aim is to shove us away, distract us, prevent us from doing our work."+
				"Resistance’s goal is not to wound or disable. Resistance aims to kill";
		
		Line l = new Line();
		l.addInstrument(0);
		l.addInstrument(32);
		l.playText(s);
		l.keyboardPlayer();
	}


}
