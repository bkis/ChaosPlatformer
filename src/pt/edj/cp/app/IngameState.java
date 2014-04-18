package pt.edj.cp.app;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import pt.edj.cp.character.CharacterAnimator;
import pt.edj.cp.input.IngameInputsState;
import pt.edj.cp.physics.PlatformerCharacterControl;
import pt.edj.cp.physics.WorldPhysicsManager;
import pt.edj.cp.timing.Metronome;
import pt.edj.cp.world.PlatformLifecycleManager;
import pt.edj.cp.world.background.BackgroundNode;


public class IngameState extends AbstractAppState {
    
    private static final String CHAR_MODEL = "Models/Oto/Oto.mesh.j3o";
    private static final float CAM_Y_OFFSET = 1f;
    private static final float CAM_Z_OFFSET = 10f;
    
    private SimpleApplication app;
    private PlatformLifecycleManager lifecycleManager;
    
    private IngameInputsState ingameInputState;
    private WorldPhysicsManager physicsMgr;
    
    private Node sceneNode;
    private Node characterNode;
    
    private Metronome metronome;
    
    
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.app = (SimpleApplication) app;
        this.lifecycleManager = new PlatformLifecycleManager();
        this.characterNode = new Node("characterNode");
        this.sceneNode = new Node("sceneNode");
        
        //initialize metronome
        this.metronome = new Metronome(120); //metronome at 120 bpm
        
        //initialize character
        loadCharacterModel();
        
        //initialize world
        constructScene();
        
        //load and apply physics
        physicsMgr = new WorldPhysicsManager(app, sceneNode, characterNode);
        physicsMgr.addChildrenToPhysicsScene(sceneNode);
        
        //(temp) add bg and light
        addBackgroundAndLightTEMP();
        
        //create animation manager
        CharacterAnimator charAnim = new CharacterAnimator(
                (Node) characterNode.getChild("character"));
        
        //attach input state
        stateManager.attach(ingameInputState = new IngameInputsState(
                (PlatformerCharacterControl) physicsMgr.getCharacterControl(),
                charAnim));
        
        //setup camera
        setupCamera();
        
        
    }
    
    
    @Override
    public void update(float tpf){
        
    }
    
    
    @Override
    public void cleanup() {
        metronome.destroy();
    }
    
    
    private void constructScene(){
        app.getRootNode().attachChild(sceneNode);
        //test-scene
        Box boxMesh = new Box(5f,0.5f,1f); 
        Geometry boxGeo = new Geometry("Colored Box", boxMesh); 
        Material boxMat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Texture tex = app.getAssetManager().loadTexture("Interface/splash.png"); 
        boxMat.setTexture("DiffuseMap", tex); 
        boxGeo.setMaterial(boxMat); 
        boxGeo.setLocalTranslation(0, 0, 0);
        sceneNode.attachChild(boxGeo);
        
    }
    
    
    private void addBackgroundAndLightTEMP(){
        //test-light1
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        app.getRootNode().addLight(sun); 
        
        //test-light2
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        app.getRootNode().addLight(ambient); 
        
        // einfach mal hier rein den background node
        BackgroundNode bgNode = new BackgroundNode(app, 30, 30);
        bgNode.setLocalTranslation(0, 0, -5f);
        app.getRootNode().attachChild(bgNode);
    }
    
    
    private void loadCharacterModel(){
        Node character = (Node) app.getAssetManager().loadModel(CHAR_MODEL);
        character.setName("character");
        character.scale(0.1f);
        characterNode.attachChild(character);
        characterNode.setLocalTranslation(0, 4, 0);
        character.setLocalTranslation(0, character.getLocalScale().y+0.45f, 0);
        app.getRootNode().attachChild(characterNode);
    }
    
    
    public void setupCamera(){
        CameraNode camNode = new CameraNode("cam", app.getCamera());
        characterNode.attachChild(camNode);
        
        camNode.setLocalTranslation(new Vector3f(
                characterNode.getChild("character").getLocalTranslation().x,
                characterNode.getChild("character").getLocalTranslation().y + CAM_Y_OFFSET,
                CAM_Z_OFFSET
                ));
        camNode.lookAt(characterNode.getLocalTranslation(), Vector3f.UNIT_Y);
    }
    
}
