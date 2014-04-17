package pt.edj.cp.app;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import pt.edj.cp.input.IngameInputsState;
import pt.edj.cp.physics.WorldPhysicsManager;
import pt.edj.cp.world.PlatformLifecycleManager;
import pt.edj.cp.world.background.BackgroundNode;


public class IngameState extends AbstractAppState {
    
    private SimpleApplication app;
    
    private static final String CHAR_MODEL = "Models/Oto/Oto.j3m";
    
    private PlatformLifecycleManager lifecycleManager;
    
    private IngameInputsState ingameInputState;
    private WorldPhysicsManager worldPhysicsManager;
    
    private Node sceneNode;
    private Node character;
    
    
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.app = (SimpleApplication) app;
        this.lifecycleManager = new PlatformLifecycleManager();
        
        //initialize world
        constructWorld();
        
        //initialize character
        character = loadCharacterModel();
        this.app.getRootNode().attachChild(character);
        
        //load and apply physics
        worldPhysicsManager = new WorldPhysicsManager(app, character, sceneNode);
        
        //attach input state
        stateManager.attach(ingameInputState = new IngameInputsState());
    }
    
    
    @Override
    public void update(float tpf){
        
    }
    
    
    @Override
    public void cleanup() {
    
    }
    
    
    private void constructWorld(){
        sceneNode = new Node();
        app.getRootNode().attachChild(sceneNode);
        
        // einfach mal hier rein den background node
        BackgroundNode bgNode = new BackgroundNode(app.getAssetManager(), 10, 7);
        bgNode.setLocalTranslation(-0.5f, -0.5f, 0.0f);
        sceneNode.attachChild(bgNode);
        
    }
    
    
    private Node loadCharacterModel(){
        return (Node) app.getAssetManager().loadModel(CHAR_MODEL);
    }
    
}
