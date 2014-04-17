package pt.edj.cp.app;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import pt.edj.cp.input.IngameInputsState;
import pt.edj.cp.physics.WorldPhysicsState;
import pt.edj.cp.world.PlatformLifecycleManager;
import pt.edj.cp.world.background.BackgroundNode;


public class IngameState extends AbstractAppState {
    
    private SimpleApplication app;
    
    private PlatformLifecycleManager lifecycleManager;
    
    private IngameInputsState ingameInputState;
    private WorldPhysicsState worldPhysicsState;
    
    
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.app = (SimpleApplication) app;
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
        // einfach mal hier rein den background node
        BackgroundNode bgNode = new BackgroundNode(app.getAssetManager(), 10, 7);
        bgNode.setLocalTranslation(-0.5f, -0.5f, 0.0f);
        app.getRootNode().attachChild(bgNode);
        
    }
    
}
