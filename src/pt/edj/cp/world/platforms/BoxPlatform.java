package pt.edj.cp.world.platforms;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;


public class BoxPlatform extends PlatformItem {
    
    Geometry boxGeo;
    Material normalMat;
    Material gfxMat;
    
    float flashTimeLeft = 0.0f;
    boolean flashing = false;
    
    public BoxPlatform(SimpleApplication app) {
        super();
        
        // create normal material
        normalMat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Texture tex = app.getAssetManager().loadTexture("Interface/splash.png"); 
        normalMat.setTexture("DiffuseMap", tex); 
        
        // create blink material
        ColorRGBA col = new ColorRGBA((float)Math.random(),(float)Math.random(),(float)Math.random(),1.0f);
        gfxMat = normalMat.clone();
        gfxMat.setColor("Diffuse", col);
        gfxMat.setColor("Ambient", col);
        gfxMat.setBoolean("UseMaterialColors", true);
        
        // test box
        Box boxMesh = new Box(
                1+((float)Math.random()*2),
                1+((float)Math.random()*2),
                1+((float)Math.random()*2)); 
        boxGeo = new Geometry("BoxPlatform " + this.hashCode(), boxMesh);
        boxGeo.setMaterial(normalMat); 
        this.attachChild(boxGeo);
    }

    
    @Override
    public void someEffectHappens() {
        flashing = true;
        flashTimeLeft = 0.2f;
        boxGeo.setMaterial(gfxMat);
    }

    
    @Override
    public void update(float tpf, float globalBeat, float platformBeat) {
        if (flashing) {
            if ((flashTimeLeft -= tpf) <= 0.0f) {
                flashing = false;
                boxGeo.setMaterial(normalMat);
            }
        }
    }
}
