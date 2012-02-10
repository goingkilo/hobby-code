import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.Patch;
import java.util.Random;

public class Twanger extends Thread{

	MidiChannel channel;
	Random r = new Random(System.currentTimeMillis());
	public boolean 		playing = false;

	long sleep;


	public Twanger( MidiChannel c, Instrument i, long sleep ){
		this.channel = c;
		Patch patch = i.getPatch();
		channel.programChange(patch.getBank(), patch.getProgram());
		this.sleep = sleep;

		this.setOn(true);
	}


	public void run(){
		while(true) 
		{
			int note = (int)(r.nextFloat()  * 100);
			if( note > 88 ) note = 88;
			playNote( note );
		}
	}

	void playNote(int note) 
	{
		channel.noteOn(note, 127);
		try { Thread.sleep(sleep); } catch(InterruptedException e){}
	}	

	public void setOn(boolean b){
		this.playing = b;
	}
}
