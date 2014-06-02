package pt.edj.cp.timing;

import java.util.Timer;
import java.util.TimerTask;
import pt.edj.cp.timing.events.AbstractEventSender;
import pt.edj.cp.timing.events.MetronomeBeatEvent;
import pt.edj.cp.timing.events.NewBarEvent;


public class Metronome extends AbstractEventSender {
    
    private static final float INIT_BPM        = 100;
    private static final float BPM_CHANGE_STEP = 20;
    
    private float bpm;
    private int msPerBeat;
    
    private Timer timer;
    
    private long lastBeatTimestamp;
    private long lastBeatNr;
    
    
    private static Metronome instance = new Metronome(INIT_BPM*4); // * 4 (weil 16 beats pro takt)
    
    
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
    
    
    public synchronized void changeBpm(boolean faster){
        float newBpm = bpm;
        if (faster && newBpm < 640){
            newBpm += BPM_CHANGE_STEP;
            setBpm(newBpm);
        }
        if (!faster && newBpm > 300){
            newBpm -= BPM_CHANGE_STEP;
            setBpm(newBpm);
        }
        System.out.println("NEW BPM: " + (newBpm / 4));
    } 
    
    
    private synchronized void doBeat() {
        lastBeatNr++;
        lastBeatTimestamp = System.currentTimeMillis();
        
        if (lastBeatNr % 16 == 0){
            broadcast(new NewBarEvent());
            changeBpm(true);
        }
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
            broadcast(new MetronomeBeatEvent());
            //System.out.println("Beat nr " + lastBeatNr);
            timer.schedule(new MetronomeTask(), msPerBeat);
        }
    }

}
