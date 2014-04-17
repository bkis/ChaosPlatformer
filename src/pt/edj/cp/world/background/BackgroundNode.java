
package pt.edj.cp.world.background;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.HashMap;
import pt.edj.cp.timing.events.IEvent;
import pt.edj.cp.timing.events.IEventListener;


public class BackgroundNode extends Node implements IEventListener {
    
    private static final float BG_Z_OFFSET = -10.0f;
    
    private SimpleApplication app;
    private AssetManager assetManager;
    
    private HashMap<BackgroundLayer,Float> layers
            = new HashMap<BackgroundLayer,Float>();
    
    private float sizeX;
    private float sizeY;
    
    
    
    public BackgroundNode(Application app, float sizeX, float sizeY) {
        super();
        
        this.app = (SimpleApplication) app;
        this.assetManager = app.getAssetManager();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        
        addLayer(new SolidColorLayer(app, ColorRGBA.DarkGray, sizeX, sizeY), 1.0f, -1.0f);
        addLayer(new LinesLayer(app, 1, sizeX, sizeY), 1.0f, -0.5f);
    }
    
    
    public final void addLayer(BackgroundLayer l, float alpha, float z) {
        layers.put(l, alpha);
        l.setLocalTranslation(0, 0, z);
        attachChild(l);
    }
    
    
    /*
     * Vorschlag - weiß nicht wie das mit deinen Designansprüchen
     * vereinbar ist, haha. Ich finds praktisch - muss eh immer gemacht werden.
     */
    public void addBackgroundSpatial(Spatial spatial){
        this.attachChild(spatial);
        spatial.setLocalTranslation(0, 0,
                BG_Z_OFFSET + (float)Math.random() - 0.5f);
    }

    public void receiveEvent(IEvent e) {
        
    }
    
}
