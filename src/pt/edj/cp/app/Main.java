package pt.edj.cp.app;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import java.io.File;
import java.util.List;
import pt.edj.cp.util.SoundManager;



public class Main extends SimpleApplication {

    
    public static void main(String[] args) {
        Main app = new Main();
        app.setSettings(getSettings());
        app.start();
    }

    
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        
        //attach ingame state
        IngameState ingameState = new IngameState();
        stateManager.attach(ingameState);
    }

    
    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);
        //main update loop access
    }

    
    @Override
    public void simpleRender(RenderManager rm) {
        super.simpleRender(rm);
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
        //set.setFrameRate(2); //debug
        return set;
    }
}
