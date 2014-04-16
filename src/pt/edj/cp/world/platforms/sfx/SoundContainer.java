package pt.edj.cp.world.platforms.sfx;

import com.jme3.audio.AudioNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/*
 * sound container for managing melodic instruments sounds
 */
public class SoundContainer {
    
    private List<AudioNode> sounds;
    private int nextIndex;
    
    
    public SoundContainer(){
        this.sounds = new ArrayList<AudioNode>();
    }
    
    
    public void addSound(AudioNode audioNode){
        sounds.add(audioNode);
    }
    
    
    public void playNextSound(){
        if (sounds.size() > 0)
            sounds.get(nextIndex++ % sounds.size()).playInstance();
    }
}
