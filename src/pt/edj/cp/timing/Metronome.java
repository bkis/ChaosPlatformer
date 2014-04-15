package pt.edj.cp.timing;

import pt.edj.cp.timing.events.AbstractEventSender;


public class Metronome extends AbstractEventSender {
    
    float bpm;
    
    public Metronome(float bpm) {
        super();
        this.bpm = bpm;
    }
    
}
