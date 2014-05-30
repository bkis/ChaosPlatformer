package pt.edj.cp.world.platforms;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import pt.edj.cp.app.IngameState;
import pt.edj.cp.util.SoundPathManager;
import pt.edj.cp.world.platforms.gfx.GlowGFX;
import pt.edj.cp.world.platforms.gfx.SimpleParticleGFX;
import pt.edj.cp.world.platforms.sfx.SoundObject;
import pt.edj.cp.world.platforms.shapes.TriangleSpikesPlatform;


public class PlatformFactory {
    
    private static final float MELODIC_QUOTE = 0.3f;
    
    private SimpleApplication app;
    private SoundPathManager sam;
    
    public PlatformFactory(SimpleApplication app){
        this.app = app;
        this.sam = new SoundPathManager();
    }
    
    
    public Platform createPlatform(Vector3f pos) {
        SoundObject soundObject = new SoundObject(
                app,
                sam.getRndSoundPath((Math.random() <= MELODIC_QUOTE ? 
                    SoundPathManager.INSTR_MELODIC :
                    SoundPathManager.INSTR_PERCUSSIVE)),
                app.getStateManager().getState(IngameState.class)
                .getCordController().getCurrentChord());
        
        Platform plat = new Platform(
                pos,
                new TriangleSpikesPlatform(app),
                soundObject);
        
        //plat.addGFX(new SimpleParticleGFX(app));
        plat.addGFX(new GlowGFX(app, plat.getPlatformSpatial().getExtents()));
        
        return plat;
    }

    
}
