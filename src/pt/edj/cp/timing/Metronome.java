package pt.edj.cp.timing;

import java.util.Timer;
import java.util.TimerTask;
import pt.edj.cp.timing.events.AbstractEventSender;
import pt.edj.cp.timing.events.MetronomeBeatEvent;


public class Metronome extends AbstractEventSender {
    
    private float bpm;
    private Timer timer;
    private int beatDelay;
    
    private long tempCounter = 0; //kommt wech, nur für debug
    
    
    public Metronome(float bpm) {
        super();
        setBpm(bpm);
        
        this.timer = new Timer();
        timer.scheduleAtFixedRate(new MetronomeTask(), 0, beatDelay);
    }
    
    
    public final void setBpm(float bpm){
        this.bpm = bpm;
        beatDelay = Math.round((60 / bpm) * 1000);
    }
    
    
    public float getBpm(){
        return bpm;
    }
    
    
    public void destroy(){
        timer.cancel();
    }
    
    
    private class MetronomeTask extends TimerTask {
        @Override
        public void run() {
            broadcast(new MetronomeBeatEvent());
            
            //debug output
            /*
            System.out.println("[METRONOME] beat event no."
                    + tempCounter++
                    + " (" + getBpm() + " bpm)");
            */
        }
    }

    
}
