package pt.edj.cp.world.platforms.sfx;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import java.util.ArrayList;
import java.util.List;


/*
 * sound container for managing melodic instruments sounds
 */
public class SoundContainer {
    
    private List<AudioNode> sounds;
    private int nextIndex;
    
    
    public SoundContainer(AssetManager assetManager){
        this.sounds = new ArrayList<AudioNode>();
        this.sounds.add(new AudioNode(assetManager, "Sounds/Instruments/Other/Short/0.ogg"));
    }
    
    
    public void addSound(AudioNode audioNode){
        sounds.add(audioNode);
    }
    
    
    public void playNextSound(){
        if (sounds.size() > 0)
            sounds.get(nextIndex++ % sounds.size()).playInstance();
    }
}
