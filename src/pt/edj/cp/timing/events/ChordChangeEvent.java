package pt.edj.cp.timing.events;


public class ChordChangeEvent implements IEvent{
    
    private float[] chord;
    
    public ChordChangeEvent(float[] chord){
        this.chord = chord;
    }
    
    public float[] getChordPitches(){
        return chord;
    }
    
}
