package pt.edj.cp.app;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import java.util.HashSet;
import pt.edj.cp.audio.BackgroundSoundsPlayer;
import pt.edj.cp.audio.SoundController;
import pt.edj.cp.character.CharacterAnimator;
import pt.edj.cp.input.IngameInputsState;
import pt.edj.cp.physics.PlatformerCharacterControl;
import pt.edj.cp.physics.WorldPhysicsManager;
import pt.edj.cp.timing.GameThemeController;
import pt.edj.cp.timing.ChordController;
import pt.edj.cp.timing.Metronome;
import pt.edj.cp.util.SoundPathManager;
import pt.edj.cp.util.WhiteNoiseFilter;
import pt.edj.cp.world.PlatformLifecycleManager;
import pt.edj.cp.world.background.BackgroundNode;
import pt.edj.cp.world.items.TemperatureChangePill;
import pt.edj.cp.world.items.Collectable;
import pt.edj.cp.world.platforms.Platform;
import pt.edj.cp.world.platforms.PlatformCollisionListener;
import pt.edj.cp.world.platforms.PlatformFactory;


public class IngameState extends AbstractAppState {
    
    //private static final String CHAR_MODEL = "Models/Character/Layer_0.001.mesh.j3o";
    private static final String CHAR_MODEL = "Models/Oto/Oto.mesh.j3o";
    private static final float CAM_Y_OFFSET = 1f;
    private static final float CAM_Z_OFFSET = 25f; //18
    private static final float BG_SIZE_Y = 28;
    
    private SimpleApplication app;
    private PlatformLifecycleManager lifecycleManager;
    private PlatformFactory platformFactory;
    private HashSet<Platform> allPlatforms;
    
    private IngameInputsState ingameInputState;
    private WorldPhysicsManager physicsMgr;
    private PlatformerCharacterControl characterControl;
    
    private Node sceneNode;
    private Node characterNode;
    private BackgroundNode backgroundNode;
    
    private Metronome metronome;
    private ChordController chordCtrl;
    private BackgroundSoundsPlayer bgSound;
    
    private WhiteNoiseFilter whiteNoiseFilter;
    
    private SoundController soundController;
    
    public IngameState(WhiteNoiseFilter noiseFilter) {
        super();
        whiteNoiseFilter = noiseFilter;
    }
    
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.app = (SimpleApplication) app;
        this.metronome = Metronome.getInstance();
        
        this.soundController = new SoundController();
        
        chordCtrl = new ChordController();
        metronome.register(chordCtrl);
        
        bgSound = new BackgroundSoundsPlayer(app, whiteNoiseFilter);
        metronome.register(bgSound);
        chordCtrl.register(bgSound);
        bgSound.start();
        
        this.characterNode = new Node("characterNode");
        this.sceneNode = new Node("sceneNode");
        
        //initialize character
        loadCharacterModel();
        
        //initialize world
        constructScene();
        
        //load and apply physics
        physicsMgr = new WorldPhysicsManager(app, sceneNode, characterNode);
        physicsMgr.addChildrenToPhysicsScene(sceneNode);
        physicsMgr.getPhysicsSpace().addCollisionListener(new PlatformCollisionListener());
        
        characterControl = (PlatformerCharacterControl) physicsMgr.getCharacterControl();
        characterControl.addMovementListener(whiteNoiseFilter);
        
        //(temp) add bg and light
        addLightTEMP();
        addBackgroundNode();
        
        //create animation manager
        CharacterAnimator charAnim = new CharacterAnimator(
                (Node) characterNode.getChild("character"));
        
        //attach input state
        stateManager.attach(ingameInputState = new IngameInputsState(
                characterControl,
                charAnim));
        
        //setup camera
        setupCamera();
        
        // Connect platform creation engine with character movement
        allPlatforms = new HashSet<Platform>();
        SoundPathManager spm = new SoundPathManager();
        metronome.register(spm);
        platformFactory = new PlatformFactory(this.app, spm);
        lifecycleManager = new PlatformLifecycleManager(this, 6.0f, new Vector2f(25, 20));
        characterControl.addMovementListener(lifecycleManager);
    }
    
    
    @Override
    public void update(float tpf){
        super.update(tpf);
        
        float beat = metronome.getCurrentBeat();
        
        for (Platform plat : allPlatforms)
            plat.update(tpf, beat);
        
        GameThemeController.instance().frame(tpf);
        
        whiteNoiseFilter.update(tpf);
    }
    
    
    @Override
    public void cleanup() {
        super.cleanup();
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
    
    
    private void addLightTEMP(){
        //test-light1
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        app.getRootNode().addLight(sun); 
        
        //test-light2
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        app.getRootNode().addLight(ambient); 
    }
    
    
    private void addBackgroundNode() {
        float aspect = (float) app.getCamera().getWidth() / (float) app.getCamera().getHeight();
        backgroundNode = new BackgroundNode(app, BG_SIZE_Y * aspect, BG_SIZE_Y);
        backgroundNode.setLocalTranslation(0, 0, -5f);
        app.getRootNode().attachChild(backgroundNode);
        
        characterControl.addMovementListener(backgroundNode);
    }
    
    
    public Metronome getMetronome(){
        return metronome;
    }
    
    
    private void loadCharacterModel(){
        Node character = (Node) app.getAssetManager().loadModel(CHAR_MODEL);
        character.setName("character");
        character.scale(0.1f);
        Material charMat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        charMat.setBoolean("UseMaterialColors", true);
        charMat.setColor("Diffuse", ColorRGBA.White);
        character.setMaterial(charMat);
        characterNode.attachChild(character);
        characterNode.setLocalTranslation(0, 4, 0);
        character.setLocalTranslation(0, character.getLocalScale().y+0.40f, 0);
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
    
    
    public SoundController getSoundController(){
        return soundController;
    }
    
    
    public Platform addPlatform(Vector3f pos) {
        // create new platform
        Platform p = platformFactory.createPlatform(pos);
        
        // register with everything
        metronome.register(p);
        chordCtrl.register(p);
        sceneNode.attachChild(p.getTopNode());
        physicsMgr.addToPhysicsScene(p.getPlatformSpatial());
        
        allPlatforms.add(p);
        
        return p;
    }
    
    public void removePlatform(Platform platform) {
        platform.killSoundLink();
        metronome.unregister(platform);
        chordCtrl.unregister(platform);
        sceneNode.detachChild(platform.getTopNode());
        physicsMgr.removeFromPhysicsScene(platform.getPlatformSpatial());
        
        allPlatforms.remove(platform);
    }
    
    
    private Collectable createNewCollectable() {
        return new TemperatureChangePill(app, (Math.random() > 0.5));
    }
    
    
    public Collectable addCollectable(Vector3f position) {
        Collectable collectable = createNewCollectable();
        collectable.setLocalTranslation(position);
        
        // register with everything
        metronome.register(collectable);
        sceneNode.attachChild(collectable);
        physicsMgr.addGhost(collectable);
        
        return collectable;
    }
    
    
    public void removeCollectable(Collectable c) {
        metronome.unregister(c);
        sceneNode.detachChild(c);
        physicsMgr.removeFromPhysicsScene(c);
    }
    
    
    public ChordController getChordController(){
        return chordCtrl;
    }
    
}
