
package pt.edj.cp.world.background;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.HashMap;
import pt.edj.cp.input.IMovementListener;
import pt.edj.cp.timing.events.IEvent;
import pt.edj.cp.timing.events.IEventListener;


public class BackgroundNode extends Node implements IEventListener, IMovementListener {
    
    private SimpleApplication app;
    
    private HashMap<BackgroundLayer,Float> layers
            = new HashMap<BackgroundLayer,Float>();
    
    private float sizeX;
    private float sizeY;
    
    
    
    public BackgroundNode(Application app, float sizeX, float sizeY) {
        super();
        
        this.app = (SimpleApplication) app;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        
        addLayer(new SolidColorLayer(app, -0.3f, ColorRGBA.DarkGray, sizeX, sizeY), 1.0f);
        addLayer(new MovingShapesLayer(app, -0.2f, sizeX, sizeY), 1.0f);
        addLayer(new LinesLayer(app, -0.1f, 4, sizeX, sizeY), 1.0f);
    }
    
    
    public final void addLayer(BackgroundLayer l, float alpha) {
        layers.put(l, alpha);
        attachChild(l);
        l.setCullHint(CullHint.Never);
    }
    

    public void receiveEvent(IEvent e) {
    }

    
    public void movement(Vector3f newPosition, Vector3f delta) {
        setLocalTranslation(newPosition);
        
        for (BackgroundLayer layer : layers.keySet()) {
            float z = layers.get(layer);
            layer.shiftLayer(delta.negate());
        }
    }
    
}
