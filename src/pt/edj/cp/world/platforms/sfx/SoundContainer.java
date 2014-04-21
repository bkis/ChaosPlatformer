package pt.edj.cp.world.platforms.sfx;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;


/*
 * sound container for managing and playing sounds
 */
public class SoundContainer {
    
    private List<AudioNode> sounds;
    private int nextIndex;
    private SimpleApplication app;
    
    
    public SoundContainer(Application app){
        this.app = (SimpleApplication) app;
        this.sounds = new ArrayList<AudioNode>();
    }
    
    
    public void addSound(AudioNode audioNode){
        sounds.add(audioNode);
    }
    
    
    public void addSounds(AudioNode[] audioNodes){
        sounds.addAll(Arrays.asList(audioNodes));
    }
    
    
    public void playNextSound(){
        app.enqueue(playSound);
    }
    
    
    public void limitNrOfSounds(int limit){
        while (sounds.size() > limit)
            sounds.remove(0);
    }
    
    
    Callable<Boolean> playSound = new Callable<Boolean>() {
        public Boolean call(){
            sounds.get(nextIndex++ % sounds.size()).playInstance();
            return true;
        }
    };
    
}
