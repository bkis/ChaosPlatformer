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
    
    private float tpfCount;
    private float gfxDelay;
    private boolean delayGfx;
    
    
    
    public AbstractPlatformGFX(Application app, float gfxDurationInSecs){
        this.app = (SimpleApplication) app;
        this.gfxDelay = gfxDurationInSecs;
    }
    
    
    public abstract void playGFX();
    public abstract void stopGFX();
    public abstract void initializeGFX();
    
    
    public void fire(){
         playGFX();
         delayGfx = true;
    }
    
    
    @Override
    protected void controlUpdate(float tpf) {
        if (!init && spatial != null){
            initializeGFX();
            init = true;
        }
        
        if (delayGfx){
            delayGfx(tpf);
        }
    }

    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
    
    
    private void delayGfx(float tpf){
        tpfCount += tpf;
        
        if (tpfCount >= gfxDelay){
            stopGFX();
            tpfCount = 0;
            delayGfx = false;
        }
    }
    
    
}
