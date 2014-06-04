package pt.edj.cp.audio;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import java.util.HashMap;
import java.util.Map;
import pt.edj.cp.timing.events.IEvent;
import pt.edj.cp.timing.events.IEventListener;
import pt.edj.cp.timing.events.ThemeParameterUpdate;


public class SoundController implements IEventListener {
    
    private Map<String, AudioNode> nodes;
    private float pitchOffset;
    private SimpleApplication app;
    
    
    public SoundController(Application app){
        this.app = (SimpleApplication) app;
        this.nodes = new HashMap<String, AudioNode>();
        this.pitchOffset = 1;
    }
    
    public void register(AudioNode audioNode){
        nodes.put(audioNode.getName(), audioNode);
        if (!audioNode.getName().startsWith("melodic"))
            audioNode.setPitch(pitchOffset);
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
    
    
    public void changePitch(float factor){
        pitchOffset = (float) Math.pow(1.5, factor);
        applyPitchOffset();
    }
        
    
    private void applyPitchOffset(){
        for (String key : nodes.keySet()){
            if (!nodes.get(key).getName().startsWith("melodic"))
                nodes.get(key).setPitch(pitchOffset);
        }
    }

    
    public float getPitchOffset(){
        return pitchOffset;
    }

    public void receiveEvent(IEvent e) {
        if (e instanceof ThemeParameterUpdate) {
            ThemeParameterUpdate tpu = (ThemeParameterUpdate) e;
            
            float paramTemp = tpu.getTemperature().getValue();
            changePitch(0.5f + 0.5f * paramTemp);
        }
    }
    
}
