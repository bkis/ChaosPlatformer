package pt.edj.platformer.app;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;



public class Main extends SimpleApplication {

    
    public static void main(String[] args) {
        Main app = new Main();
        app.setSettings(getSettings());
        app.start();
    }

    
    @Override
    public void simpleInitApp() {
        //sample init code...
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", assetManager.loadTexture("Interface/splash.png"));
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
        
        cam.setLocation(new Vector3f(4.1243386f, 3.9462457f, 9.040403f));
        cam.setRotation(new Quaternion(-0.0459402f, 0.95401675f, -0.20476358f, -0.21404065f));
    }

    
    @Override
    public void simpleUpdate(float tpf) {
        //main update loop access
    }

    
    @Override
    public void simpleRender(RenderManager rm) {
        //main render loop access
    }
    
    
    /*
     * prepare default application settings
     */
    private static AppSettings getSettings(){
        AppSettings set = new AppSettings(true);
        set.setSettingsDialogImage("Interface/splash.png");
        set.setVSync(true);
        set.setFullscreen(false);
        set.setMinResolution(800, 600);
        set.setResolution(800, 600);
        set.setTitle("Chaos Platformer (working title)");
        return set;
    }
}
