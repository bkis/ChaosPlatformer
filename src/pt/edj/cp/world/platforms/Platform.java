package pt.edj.cp.world.platforms;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.scene.Spatial;
import pt.edj.cp.physics.WorldPhysicsManager;
import pt.edj.cp.timing.Metronome;
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
    private boolean active;
    
    
    public Platform(Spatial spatial,
                    AbstractPlatformGFX gfx,
                    SoundContainer sfx,
                    RhythmPattern pattern){
        this.spatial = spatial;
        this.gfx = gfx;
        this.sfx = sfx;
        this.pattern = pattern;
        
        spatial.addControl(gfx);
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
        if (active){
            //play sound
            sfx.playNextSound();
            
            //play visual platform feedback effect
            gfx.fire();

            //play platform GFX
            //TODO
        }
    }
    
    
    public void destroy(WorldPhysicsManager phys, Metronome metro){
        active = false;
        spatial.removeFromParent();
        phys.removeFromPhysicsScene(spatial);
        metro.unregister(this);
    }

    
    public void collision(PhysicsCollisionEvent event) {
        if (event.getNodeA().getName().contains("aracter")
                && event.getNodeB() == spatial){
            activate();
        } else if (event.getNodeB().getName().contains("aracter")
                && event.getNodeA() == spatial){
            activate();
        }
    }
    
    
    private void activate(){
        if (!active){
            active = true;
            heartbeat();
        }
    }

    
}
