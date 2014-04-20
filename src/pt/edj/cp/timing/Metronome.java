package pt.edj.cp.timing;

import java.util.Timer;
import java.util.TimerTask;
import pt.edj.cp.timing.events.AbstractEventSender;
import pt.edj.cp.timing.events.MetronomeBeatEvent;


public class Metronome extends AbstractEventSender {
    
    private float bpm;
    private int msPerBeat;
    
    private Timer timer;
    
    private long lastBeatTimestamp;
    private int lastBeatNr;
    
    
    private static Metronome instance = new Metronome(120.0f);
    
    
    private Metronome(float bpm) {
        super();
        setBpm(bpm);
        
        this.timer = new Timer();
        timer.schedule(new MetronomeTask(), msPerBeat);
    }
    
    
    public static Metronome getInstance() {
        return instance;
    }
    
    
    public synchronized final void setBpm(float bpm){
        this.bpm = bpm;
        this.msPerBeat = Math.round((60 / bpm) * 1000);
    }
    
    
    private synchronized final void doBeat() {
        lastBeatNr++;
        lastBeatTimestamp = System.currentTimeMillis();
    }
    
    
    public synchronized final float getCurrentBeat() {
        long currTime = System.currentTimeMillis();
        int elapsed = (int) (currTime - lastBeatTimestamp);
        
        float relElapsed = Math.min(elapsed / msPerBeat, 1.0f);
        return lastBeatNr + relElapsed;
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
            doBeat();
            
            //broadcast(new MetronomeBeatEvent());
            
            System.out.println("Beat nr " + lastBeatNr);
       
            timer.schedule(new MetronomeTask(), msPerBeat);
        }
    }

}
