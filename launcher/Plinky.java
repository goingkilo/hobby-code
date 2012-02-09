
import processing.core.*;
import javax.sound.midi.*;


public class Plinky extends PApplet 
{

        MidiChannel channel;
	java.util.Random r=  new java.util.Random(System.currentTimeMillis());

	public Plinky() 
	{
		try {
			Synthesizer synth = MidiSystem.getSynthesizer();
                        synth.open();
                        MidiChannel[] channels = synth.getChannels();
                        Soundbank bank = synth.getDefaultSoundbank();
                        synth.loadAllInstruments(bank);
                        Instrument[] instrs = synth.getLoadedInstruments();
			
               	       	Patch patch = instrs[0].getPatch();
               	       	channels[0].programChange(patch.getBank(), patch.getProgram());
			channel = channels[0];
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setup() {
		size(600,200);
		background(254);
	}

	public void draw()
	{
		float f = r.nextFloat()  * 100;
		playNote( 20+(int)f );
	}

	void playNote(int note) 
	{
		channel.noteOn(note, 127);
                try { Thread.sleep(1000); } catch(InterruptedException e){}
	}

	public void mouseClicked() 
	{
		fill(0,200,0);
		rect(mouseX, mouseY, 10,10);
	}
	public void keyPressed() 
	{
		int note = ((int)key) - 65; 
		playNote( note );
	}
}
