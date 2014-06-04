package pt.edj.cp.timing;

import java.util.Timer;
import java.util.TimerTask;
import pt.edj.cp.timing.events.AbstractEventSender;
import pt.edj.cp.timing.events.MetronomeBeatEvent;
import pt.edj.cp.timing.events.NewBarEvent;


public class Metronome extends AbstractEventSender {
    
    private static final float MIN_BPM         = 60;
    private static final float MAX_BPM         = 160;
    
    private Timer timer;
    
    private long lastBeatTimestamp;
    private long lastBeatNr;

    
    private static Metronome instance = new Metronome(); // * 4 (weil 16 beats pro takt)
    
    
    private Metronome() {
        super();
        
        this.timer = new Timer();
        timer.schedule(new MetronomeTask(), getMsPerBeat());
    }
    
    
    public static Metronome getInstance() {
        return instance;
    }

    
    private synchronized int getMsPerBeat() {
        float bpm = 4 * getBpm();
        return Math.round((60.f / bpm) * 1000.f);
    }
    
    
    private synchronized void doBeat() {
        lastBeatNr++;
        lastBeatTimestamp = System.currentTimeMillis();
        
        if (lastBeatNr % 16 == 0){
            broadcast(new NewBarEvent());
        }
    }
    
    
    public synchronized final float getCurrentBeat() {
        long currTime = System.currentTimeMillis();
        int elapsed = (int) (currTime - lastBeatTimestamp);
        
        float relElapsed = Math.min(elapsed / getMsPerBeat(), 1.0f);
        return lastBeatNr + relElapsed;
    }
    
    
    public float getBpm(){
        float speedParam = GameThemeController.instance().getParameter("Speed");
        float mid = (MIN_BPM + MAX_BPM) * 0.5f;
        return mid + (MAX_BPM - MIN_BPM) * speedParam * 0.5f;
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
            timer.schedule(new MetronomeTask(), getMsPerBeat());
        }
    }

}
