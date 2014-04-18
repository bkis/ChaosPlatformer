package pt.edj.cp.world.background;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import pt.edj.cp.timing.events.IEventListener;

/**
 *
 * @author rechtslang
 */
abstract public class BackgroundLayer extends Node implements IEventListener {
    
    
    protected SimpleApplication app;
    private float shiftFactor;
    
    
    public BackgroundLayer(Application app, float z, float shiftFactor){
        this.app = (SimpleApplication) app;
        
        this.setLocalTranslation(new Vector3f(0.0f, 0.0f, z));
        this.shiftFactor = shiftFactor;
    }
    
    
    abstract protected void doShift(Vector3f vec);
    
    
    public void shiftLayer(Vector3f vec) {
        doShift(vec.mult(shiftFactor));
    }

}
