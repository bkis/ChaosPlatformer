package pt.edj.cp.world.platforms;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import pt.edj.cp.world.platforms.gfx.SimpleParticleGFX;
import pt.edj.cp.world.platforms.sfx.RhythmPattern;
import pt.edj.cp.world.platforms.sfx.SoundContainer;


public class PlatformFactory {
    
    private SimpleApplication app;
    
    
    public PlatformFactory(SimpleApplication app){
        this.app = app;
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
        sc.addSound(createAudioNode("Sounds/Instruments/Melodic/0/A.ogg"));
        sc.addSound(createAudioNode("Sounds/Instruments/Melodic/0/B.ogg"));
        sc.addSound(createAudioNode("Sounds/Instruments/Melodic/0/C#.ogg"));
        sc.addSound(createAudioNode("Sounds/Instruments/Melodic/0/F.ogg"));
        sc.addSound(createAudioNode("Sounds/Instruments/Melodic/0/G.ogg"));
        return sc;
    }
    
    
    private AudioNode createAudioNode(String soundPath){
        AudioNode an = new AudioNode(app.getAssetManager(), soundPath);
        an.setPositional(false);
        return an;
    }
    
}
