
import java.util.*;

import javax.sound.midi.*;

public class Fatso  {


	MidiChannel[] channels;
	Map<String,Twanger> lines;
	Instrument[] instruments;

	public Fatso(){
		lines 		= new HashMap<String, Twanger>();

		try
		{
			Synthesizer synth = MidiSystem.getSynthesizer();
			synth.open();
			
			channels 		= synth.getChannels();
			Soundbank bank 	= synth.getDefaultSoundbank();
			synth.loadAllInstruments(bank);
			
			instruments 	= synth.getLoadedInstruments();
//			for(int i = 0 ; i < instruments.length ; i++) {
//				System.out.println(i+","+instruments[i]);
//			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public Twanger add(String name, MidiChannel channel, Instrument instrument, long sleep ){
		Twanger t = new Twanger(channel,instrument,sleep );
		t.start();
		return t;
	}

	public static void main(String[] args) throws Exception
	{
		Fatso f = new Fatso();
//		f.add( "Piano", f.channels[0], f.instruments[0], 900l);
		f.add( "Piano", f.channels[2], f.instruments[0], 1200l);
		f.add( "NatStlGtr", f.channels[1], f.instruments[24], 1000l);
		f.add( "NatStlGtr", f.channels[0], f.instruments[24], 800l);
	}

}
