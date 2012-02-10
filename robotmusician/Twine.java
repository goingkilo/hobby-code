
import processing.core.*;
import javax.sound.midi.*;


public class Twine implements Runnable
{

    MidiChannel channel;
    java.util.Random r=  new java.util.Random(System.currentTimeMillis());
    long sleep;
    int instrument;
    int channelNum;

    public Twine(long sleep, int instrument, int pchannel) 
    {
        this.sleep = sleep;
        this.instrument = instrument;
        this.channelNum = pchannel;

        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();
            Soundbank bank = synth.getDefaultSoundbank();
            synth.loadAllInstruments(bank);
            Instrument[] instrs = synth.getLoadedInstruments();
           
            Patch patch = instrs[instrument].getPatch();
            channels[channelNum].programChange(patch.getBank(), patch.getProgram());
            channel = channels[channelNum];
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void run()
    {
        while(true) 
        {
            float f = r.nextFloat()  * 100;
            if( f > 78f ) f = 78f;
            playNote( 20+(int)f );
        }
    }

    void playNote(int note) 
    {
        channel.noteOn(note, 127);
                try { Thread.sleep(1000); } catch(InterruptedException e){}
    }

    public static void main(String[] args) 
    {
        if(args.length != 3) 
        {
            System.out.println( "args : sleep instrument channel" );
            System.exit(0);
        }
        Thread t = new Thread( new Twine( Long.parseLong(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2] ) ) );
        t.start();
    }

}
