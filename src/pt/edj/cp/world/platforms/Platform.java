package pt.edj.cp.world.platforms;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import pt.edj.cp.timing.events.IEvent;
import pt.edj.cp.timing.events.IEventListener;
import pt.edj.cp.timing.events.MetronomeBeatEvent;
import pt.edj.cp.world.platforms.gfx.AbstractPlatformGFX;
import pt.edj.cp.world.platforms.sfx.RhythmPattern;
import pt.edj.cp.world.platforms.sfx.SoundContainer;


public class Platform implements IEventListener, PhysicsCollisionListener{
    
    private Spatial spatial;
    private AbstractPlatformGFX gfx;
    private SoundContainer sfx;
    private RhythmPattern pattern;
    
    
    public Platform(Spatial spatial,
                    AbstractPlatformGFX gfx,
                    SoundContainer sfx,
                    RhythmPattern pattern){
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
        sfx.playNextSound();
        
        //play visual platform feedback effect
        //TODO
        
        //play platform GFX
        //TODO
    }

    
    public void collision(PhysicsCollisionEvent event) {
        if (event.getNodeA().getName().contains("character")
                && event.getNodeB().getName().equals(spatial.getName())){
            //TODO
        } else if (event.getNodeB().getName().contains("character")
                && event.getNodeA().getName().equals(spatial.getName())){
            //TODO
        }
    }

    
}
