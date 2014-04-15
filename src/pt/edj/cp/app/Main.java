package pt.edj.cp.app;

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
        //attach ingame state
        IngameState ingameState = new IngameState();
        stateManager.attach(ingameState);
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
