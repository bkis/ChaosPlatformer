package pt.edj.cp.world;

import com.jme3.app.SimpleApplication;
import pt.edj.cp.timing.Metronome;
import pt.edj.cp.world.platforms.PlatformFactory;


public class PlatformLifecycleManager {
    
    private Metronome metronome;
    private PlatformFactory platformFactory;
    
    
    public PlatformLifecycleManager(SimpleApplication app, Metronome metronome){
        this.metronome = metronome;
        this.platformFactory = new PlatformFactory(app);
    }
    
    
    
    
}
