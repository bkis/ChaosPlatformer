package pt.edj.cp.world.platforms.rhythm;


public class Unit {
    private int length;     // in metronome-beats
    private Unit next;
    
    public final int getLength() {
        return length;
    }
    
    protected Unit(int l) {
        length = l;
    }
    
    public final void setNext(Unit next) {
        this.next = next;
    }
    
    public final Unit getNext() {
        return next;
    }
}
