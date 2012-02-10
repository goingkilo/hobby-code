import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class Fatso  {

	MidiChannel[] channels;
	Instrument[] instruments;
	List<Twanger> twangers = new ArrayList<Twanger>();

	public Fatso(){
		try
		{
			Synthesizer synth = MidiSystem.getSynthesizer();
			synth.open();
			
			channels 		= synth.getChannels();
			Soundbank bank 	= synth.getDefaultSoundbank();
			synth.loadAllInstruments(bank);
			
			instruments 	= synth.getLoadedInstruments();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	void add(String name, MidiChannel channel, Instrument instrument, long sleep ){
		Twanger t = new Twanger(channel,instrument,sleep );
		t.start();
		twangers.add(t);
	}
	
	void on(){ 
		for( int i = 0 ; i < twangers.size() ; i++ ) {
			twangers.get(i).on = true;
		}
	}
	
	void off(){ 
		for( int i = 0 ; i < twangers.size() ; i++ ) {
			twangers.get(i).on = false;
		}
	}

	public static void main(String[] args) throws Exception
	{
		Fatso f = new Fatso();
		f.add( "Piano", f.channels[2], f.instruments[0], 1200l);
		f.add( "NatStlGtr", f.channels[1], f.instruments[24], 1000l);
		f.add( "NatStlGtr", f.channels[0], f.instruments[24], 800l);
	}

}
