package pt.edj.cp.app;

import com.jme3.app.SimpleApplication;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import pt.edj.cp.util.WhiteNoiseFilter;



public class Main extends SimpleApplication {

    
    public static void main(String[] args) {
        Main app = new Main();
        app.setSettings(getSettings());
        app.start();
    }

    
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        
        // attach post processing node
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        viewPort.addProcessor(fpp);
        
        WhiteNoiseFilter whiteNoise = new WhiteNoiseFilter();
        fpp.addFilter(whiteNoise);
        
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
