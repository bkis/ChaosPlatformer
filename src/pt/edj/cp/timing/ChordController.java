package pt.edj.cp.timing;

import java.util.HashSet;
import java.util.Set;
import pt.edj.cp.timing.events.ChordChangeEvent;
import pt.edj.cp.timing.events.IEvent;
import pt.edj.cp.timing.events.IEventListener;
import pt.edj.cp.timing.events.IEventSender;
import pt.edj.cp.timing.events.MetronomeBeatEvent;
import pt.edj.cp.util.MusicScales;


public class ChordController implements IEventListener, IEventSender{

    private static final int CHANGE_CHORD_EVERY_X_BEATS = 64; //16 = 1 bar; 32 = 2 bars...
    
    private Set<IEventListener> listeners;
    private double nextIndex;
    private float[] currChord;
    
    
    public ChordController(){
        this.nextIndex = 0;
        this.currChord = MusicScales.CHORD_MINOR_TONIC;
        this.listeners = new HashSet<IEventListener>();
    }
    
    
    public void receiveEvent(IEvent e) {
        if (e instanceof MetronomeBeatEvent
                && nextIndex++ % CHANGE_CHORD_EVERY_X_BEATS == 0){
            changeChord();
        }
    }

    
    public void register(IEventListener l) {
        listeners.add(l);
    }

    
    public void unregister(IEventListener l) {
        listeners.remove(l);
    }

    
    public void broadcast(IEvent e) {
        for (IEventListener l : listeners)
            l.receiveEvent(e);
    }
    
    
    private void changeChord(){
        double rnd = Math.random();
        
        if (rnd < 0.3)      currChord = MusicScales.CHORD_MINOR_TONIC;
        else if (rnd < 0.6) currChord = MusicScales.CHORD_MINOR_DOMINANT;
        else                currChord = MusicScales.CHORD_MINOR_SUBDOMINANT;
        
        broadcast(new ChordChangeEvent(currChord));
    }
    
    
    public float[] getCurrentChord(){
        return currChord;
    }
    
    
}
