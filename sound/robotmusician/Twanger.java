import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.Patch;
import java.util.Random;

public class Twanger extends Thread{

	MidiChannel channel;
	Random r = new Random(System.currentTimeMillis());
	boolean on = false;
	long sleep;
	
	Rudolph rudolph;
	int offset;

	public Twanger( MidiChannel c, Instrument i, long sleep ){
		this.channel = c;
		Patch patch = i.getPatch();
		channel.programChange(patch.getBank(), patch.getProgram());
		this.sleep = sleep;
	}

	public void setDrawParameters(Rudolph r, int offset) {
		this.rudolph = r;
		this.offset = offset;
	}
	
	public void run(){
		int x = 0; 
		while(true) 
		{
			if( on ) {
				int note = (int)(r.nextFloat()  * 80);
				playNote( note );
				x = rudolph.drawBar(offset, x, note);
				x += 4;
				if(x > 500) x = 0;
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


