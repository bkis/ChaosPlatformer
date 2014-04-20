package pt.edj.cp.world.platforms.rhythm;

import java.util.ArrayList;


public class RhythmPattern {
    
    private int length;                 // in metronome-beats
    private ArrayList<Unit> units;
    
    public Unit getUnitAt(float time) {
        Unit curr = units.get(0);
        int total = curr.getLength();
        
        while (total <= time % length) {
            curr = curr.getNext();
            total += curr.getLength();
        }
        
        return curr;
    }
}