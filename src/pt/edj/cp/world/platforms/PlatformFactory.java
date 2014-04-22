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
        SoundContainer sc = (Math.random() < 0.4f ?
                            debugGetDummyMelodicSoundContainer() :
                            debugGetDummyPercussiveSoundContainer());
        
        Platform plat = new Platform(
                pos,
                new BoxPlatform(app),
                sc,
                new RhythmPattern(16, sc.getSampleLength()));
        
        plat.addGFX(new SimpleParticleGFX(app));
        
        return plat;
    }
    
    
    private SoundContainer debugGetDummyMelodicSoundContainer(){
        SoundContainer sc = new SoundContainer(app);
        String soundID = sam.getRndMelodicInstrumentID();
        sc.addSounds(sam.getRndMelodicNotes(soundID, 16));
        return sc;
    }
    
    
    private SoundContainer debugGetDummyPercussiveSoundContainer(){
        SoundContainer sc = new SoundContainer(app);
        sc.addSound(sam.getRndSound(SoundAssetManager.INSTR_PERC));
        return sc;
    }
    
    
}
