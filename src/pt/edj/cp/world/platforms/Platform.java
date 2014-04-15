package pt.edj.cp.world.platforms;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Spatial;
import pt.edj.cp.world.platforms.gfx.AbstractPlatformGFX;


public class Platform {
    
    private Spatial spatial;
    private AbstractPlatformGFX gfx;
    private AudioNode sfx;
    private SimpleApplication app;
    private RhythmPattern pattern;
    
    
    public Platform(Application app,
                    Spatial spatial,
                    AbstractPlatformGFX gfx,
                    AudioNode sfx){
        this.app = (SimpleApplication) app;
        this.spatial = spatial;
        this.gfx = gfx;
        this.sfx = sfx;
    }
    
    
    public Spatial getSpatial(){
        return spatial;
    }
    
    
    
    
    //...
    
}
