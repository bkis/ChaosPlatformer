package pt.edj.cp.world.platforms;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import pt.edj.cp.util.SoundAssetManager;
import pt.edj.cp.world.platforms.gfx.SimpleParticleGFX;
import pt.edj.cp.world.platforms.sfx.RhythmPattern;
import pt.edj.cp.world.platforms.sfx.SoundContainer;


public class PlatformFactory {
    
    private SimpleApplication app;
    private SoundAssetManager sam;
    
    
    public PlatformFactory(SimpleApplication app){
        this.app = app;
        this.sam = new SoundAssetManager(app.getAssetManager());
    }
    
    
    public Platform createPlatform(Vector3f pos) {
        Platform plat = new Platform(
                pos,
                new BoxPlatform(app),
                debugGetDummyMelodicSoundContainer(),
                new RhythmPattern(16, (float)Math.random()+0.02f));
        
        plat.addGFX(new SimpleParticleGFX(app));
        
        return plat;
    }
    
    
    private SoundContainer debugGetDummyMelodicSoundContainer(){
        SoundContainer sc = new SoundContainer(app);
        sc.addSound(sam.getRndSound(SoundAssetManager.INSTR_PERC_S));
        return sc;
    }
    
    
}
