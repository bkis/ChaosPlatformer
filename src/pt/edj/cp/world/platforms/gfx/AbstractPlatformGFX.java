package pt.edj.cp.world.platforms.gfx;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;


public abstract class AbstractPlatformGFX extends AbstractControl {
    
    protected SimpleApplication app;
    private boolean active;
    private boolean init;
    
    
    public AbstractPlatformGFX(Application app){
        this.app = (SimpleApplication) app;
    }
    
    
    public abstract void playGFX();
    public abstract void initializeGFX();
    
    
    public void fire(){
        active = true;
    }
    
    
    @Override
    protected void controlUpdate(float tpf) {
        if (!init && spatial != null){
            initializeGFX();
            init = true;
        }
        
        if (active){
            playGFX();
            active = false;
        }
    }

    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
    
    
    
    
    
}
