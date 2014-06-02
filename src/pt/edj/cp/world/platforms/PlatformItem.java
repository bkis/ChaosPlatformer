package pt.edj.cp.world.platforms;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import pt.edj.cp.physics.ICollisionShapeProvider;

/**
 *
 * @author rechtslang
 */
public abstract class PlatformItem extends Node implements ICollisionShapeProvider {
    
    protected boolean active = false;
    protected Platform parentPlatform;
    
    
    
    protected final float rand(float min, float max) {
        return min + (max-min) * (float) Math.random();
    }
    
    
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
    
    public Vector3f getExtents() {
        return new Vector3f();
    }
    
}