
package pt.edj.cp.world.background;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.HashMap;
import pt.edj.cp.timing.events.Event;
import pt.edj.cp.timing.events.IEventListener;


public class BackgroundNode extends Node implements IEventListener {
    
    private static final float BG_Z_OFFSET = -10.0f;
    
    private AssetManager assetManager;
    
    private HashMap<BackgroundLayer,Float> layers
            = new HashMap<BackgroundLayer,Float>();
    
    private float sizeX;
    private float sizeY;
    
    public final void addLayer(BackgroundLayer l, float alpha, float z) {
        layers.put(l, alpha);
        l.setLocalTranslation(0, 0, z);
        attachChild(l);
    }
    
    public BackgroundNode(AssetManager assetMgr, float sx, float sy) {
        super();
        
        assetManager = assetMgr;
        sizeX = sx;
        sizeY = sy;
        
        addLayer(new SolidColorLayer(assetMgr, ColorRGBA.DarkGray, sizeX, sizeY), 1.0f, -1.0f);
        addLayer(new LinesLayer(assetMgr, 1, sizeX, sizeY), 1.0f, -0.5f);
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

    public void receiveEvent(Event e) {
        
    }
    
}
