
package pt.edj.cp.world;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;


public class BackgroundNode extends Node{
    
    private static final float BG_Z_OFFSET = -10.0f;
    
    
    /*
     * Vorschlag - weiß nicht wie das mit deinen Designansprüchen
     * vereinbar ist, haha. Ich finds praktisch - muss eh immer gemacht werden.
     */
    public void addBackgroundSpatial(Spatial spatial){
        this.attachChild(spatial);
        spatial.setLocalTranslation(0, 0,
                BG_Z_OFFSET + (float)Math.random() - 0.5f);
    }
    
}
