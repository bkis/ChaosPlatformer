package pt.edj.cp.world.platforms;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Spatial;
import pt.edj.cp.timing.events.IEvent;
import pt.edj.cp.timing.events.IEventListener;
import pt.edj.cp.timing.events.MetronomeBeatEvent;
import pt.edj.cp.world.platforms.gfx.AbstractPlatformGFX;


public class Platform implements IEventListener{
    
    private Spatial spatial;
    private AbstractPlatformGFX gfx;
    private AudioNode sfx;
    private SimpleApplication app;
    private RhythmPattern pattern;
    
    
    public Platform(Application app,
                    Spatial spatial,
                    AbstractPlatformGFX gfx,
                    AudioNode sfx,
                    RhythmPattern pattern){
        this.app = (SimpleApplication) app;
        this.spatial = spatial;
        this.gfx = gfx;
        this.sfx = sfx;
        this.pattern = pattern;
    }
    
    
    @Override
    public void receiveEvent(IEvent e) {
        if (e instanceof MetronomeBeatEvent){
            if (pattern.nextEvent()){
                heartbeat();
            }
        } //else if (e instanceof ...
    }
    
    
    public Spatial getSpatial(){
        return spatial;
    }
    
    
    private void heartbeat(){
        //play sound
        sfx.playInstance();
        
        //play visual platform feedback effect
        //TODO
        
        //play platform GFX
        //TODO
    }

    
}
