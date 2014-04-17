package pt.edj.cp.world.background;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import pt.edj.cp.timing.events.IEvent;

/**
 *
 * @author rechtslang
 */
public class SolidColorLayer extends BackgroundLayer {

    public SolidColorLayer(AssetManager assetMgr, ColorRGBA color, float sx, float sy) {
        super();
        
        Geometry geom = new Geometry("SolidLayerQuad", new Quad(sx, sy));
        Material mat = new Material(assetMgr, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);
        geom.setLocalTranslation(-0.5f * sx, -0.5f * sy, 0.0f);
        attachChild(geom);
    }
    
    public void receiveEvent(IEvent e) {
    }
    
}
