package pt.edj.cp.world.items;

import com.jme3.bullet.control.GhostControl;
import com.jme3.scene.Node;
import pt.edj.cp.physics.ICollisionShapeProvider;
import pt.edj.cp.timing.events.IEventListener;


public abstract class Collectable extends Node implements ICollisionShapeProvider, IEventListener {
    
    private boolean collected;
    
    protected Collectable() {
        setUserData("collectable", this);
        collected = false;
    }
    
    public void collect() {
        if (collected)
            return;
        
        collected = true;
        this.setCullHint(CullHint.Always);
        
        getControl(GhostControl.class).setEnabled(false);
        
        effect();
    }
    
    protected abstract void effect();
}
