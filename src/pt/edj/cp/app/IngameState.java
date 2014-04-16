package pt.edj.cp.app;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import pt.edj.cp.input.IngameInputsState;
import pt.edj.cp.physics.WorldPhysicsState;
import pt.edj.cp.world.PlatformLifecycleManager;


public class IngameState extends AbstractAppState{
    
    private PlatformLifecycleManager lifecycleManager;
    
    private IngameInputsState ingameInputState;
    private WorldPhysicsState worldPhysicsState;
    
    
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.lifecycleManager = new PlatformLifecycleManager();
        
        //initialize world
        constructWorld();
        
        //attach additional states
        stateManager.attach(ingameInputState = new IngameInputsState());
        stateManager.attach(worldPhysicsState = new WorldPhysicsState());
    }
    
    
    @Override
    public void update(float tpf){
        
    }
    
    
    @Override
    public void cleanup() {
    
    }
    
    
    private void constructWorld(){
        //TODO
    }
    
}
