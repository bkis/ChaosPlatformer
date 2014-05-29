package pt.edj.cp.world.platforms;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.scene.Node;

/**
 *
 * @author rechtslang
 */
public abstract class PlatformItem extends Node {
    
    protected boolean active = false;
    protected Platform parentPlatform;
    
    
    public final boolean isActive() {
        return active;
    }
    
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    
    /**
     * Could be more than just one of those kinds of definitions
     */
    public void someEffectHappens() {
    }

    /**
     * Gets called regularly
     */
    public void update(float tpf, float globalBeat, float platformBeat) {
    }
    
    
    public void setParentPlatform(Platform pf){
        this.parentPlatform = pf;
    }
    
    public CollisionShape getCollisionShape() {
        return null;
    }
    
}