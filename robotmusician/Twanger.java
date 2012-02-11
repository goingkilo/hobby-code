import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.Patch;
import java.util.Random;

public class Twanger extends Thread{

	MidiChannel channel;
	Random r = new Random(System.currentTimeMillis());
	boolean on = false;
	long sleep;

	public Twanger( MidiChannel c, Instrument i, long sleep ){
		this.channel = c;
		Patch patch = i.getPatch();
		channel.programChange(patch.getBank(), patch.getProgram());
		this.sleep = sleep;
	}

	public void run(){
		while(true) 
		{
			if( on ) {
				int note = (int)(r.nextFloat()  * 80);
				playNote( note );
			}
			else {
				try{ Thread.sleep(10); } catch(InterruptedException e){}
			}
		}
	}

	void playNote(int note) 
	{
		channel.noteOn(note, 127);
		try { Thread.sleep(sleep); } catch(InterruptedException e){}
	}	
}

