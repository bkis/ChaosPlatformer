package pt.edj.cp.world.platforms.rhythm;

import com.jme3.audio.AudioNode;


public class Sound extends Unit {
    private boolean percussive;
    
    public Sound(int len, AudioNode sound, boolean percussive) {
        super(len);
        this.percussive = percussive;
    }
    
    public final boolean isPercussive() {
        return percussive;
    }
}
