package pt.edj.cp.app;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import pt.edj.cp.world.PlatformLifecycleManager;


public class IngameState extends AbstractAppState{
    
    private PlatformLifecycleManager lifecycleManager;
    
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.lifecycleManager = new PlatformLifecycleManager();
        
        //sample ingame code
        //actually, additional ingame states would be attached, here...
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", app.getAssetManager().loadTexture("Interface/splash.png"));
        geom.setMaterial(mat);

        ((SimpleApplication)app).getRootNode().attachChild(geom);
        
        app.getCamera().setLocation(new Vector3f(4.1243386f, 3.9462457f, 9.040403f));
        app.getCamera().setRotation(new Quaternion(-0.0459402f, 0.95401675f, -0.20476358f, -0.21404065f));
    }
    
    
    @Override
    public void update(float tpf){
        
    }
    
    
    @Override
    public void cleanup() {
    
    }
    
}
