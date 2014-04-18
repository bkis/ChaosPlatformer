package pt.edj.cp.world.platforms;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import pt.edj.cp.world.platforms.sfx.RhythmPattern;
import pt.edj.cp.world.platforms.sfx.SoundContainer;


public class PlatformFactory {
    
    private SimpleApplication app;
    
    
    public PlatformFactory(SimpleApplication app){
        this.app = app;
    }
    
    
    public Platform createPlatform(Vector3f pos) {
        //test-scene
        Box boxMesh = new Box(0.4f,0.4f,1f); 
        Geometry boxGeo = new Geometry("Colored Box", boxMesh); 
        Material boxMat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Texture tex = app.getAssetManager().loadTexture("Interface/splash.png"); 
        boxMat.setTexture("DiffuseMap", tex); 
        boxGeo.setMaterial(boxMat); 
        boxGeo.setLocalTranslation(pos);
        
        Platform platf = new Platform((Spatial)boxGeo, null,
                new SoundContainer(app.getAssetManager()),
                new RhythmPattern(16,0.5f));
        
        return platf;
    }
    
}
