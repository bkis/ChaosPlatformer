package pt.edj.cp.world.platforms;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import pt.edj.cp.app.IngameState;
import pt.edj.cp.util.SoundAssetManager;
import pt.edj.cp.world.platforms.gfx.SimpleParticleGFX;
import pt.edj.cp.world.platforms.sfx.SoundObject;


public class PlatformFactory {
    
    private static final float MELODIC_QUOTE = 0.3f;
    
    private SimpleApplication app;
    private SoundAssetManager sam;
    
    public PlatformFactory(SimpleApplication app){
        this.app = app;
        this.sam = new SoundAssetManager(app.getAssetManager());
    }
    
    
    public Platform createPlatform(Vector3f pos) {
        SoundObject soundObject = new SoundObject(
                app,
                sam.getRndSoundPath((Math.random() <= MELODIC_QUOTE ? 
                    SoundAssetManager.INSTR_MELODIC :
                    SoundAssetManager.INSTR_PERCUSSIVE)),
                app.getStateManager().getState(IngameState.class)
                .getCordController().getCurrentChord());
        
        Platform plat = new Platform(
                pos,
                new BoxPlatform(app),
                soundObject);
        
        plat.addGFX(new SimpleParticleGFX(app));
        
        return plat;
    }

    
}
