package pt.edj.cp.audio;

import com.jme3.audio.AudioNode;
import java.util.HashMap;
import java.util.Map;


public class SoundController {
    
    Map<String, AudioNode> nodes;
    
    
    public SoundController(){
        this.nodes = new HashMap<String, AudioNode>();
    }
    
    public void register(AudioNode audioNode){
        nodes.put(audioNode.getName(), audioNode);
    }
    
    public void unregister(AudioNode audioNode){
        nodes.remove(audioNode.getName());
    }
    
    /**
     * 0 <= factor <= 1
     * @param factor 
     */
    public void changeVolume(float factor){
        if (factor >= 0 && factor <= 1)
            for (String key : nodes.keySet())
                nodes.get(key).setVolume(factor);
    }
    
    /**
     * 0 <= factor <= 1
     * @param factor 
     */
    public void changePitch(float factor){
        if (factor >= 0 && factor <= 1){
            factor -= 0.5f;
            factor /= 5;
            for (String key : nodes.keySet()){
                if (nodes.get(key).getPitch() < 1.9f
                && nodes.get(key).getPitch() > 0.2f){
                    nodes.get(key).setPitch(
                            nodes.get(key).getPitch() + factor);
                }
            }
        }              
    }
    
}
