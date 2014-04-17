package pt.edj.cp.physics;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;


public class WorldPhysicsManager {
    
    //physics parameters
    private static final Vector3f DIR_LEFT          = new Vector3f(-20f, 0, 0);
    private static final Vector3f DIR_RIGHT         = new Vector3f(20f, 0, 0);
    private static final Vector3f WORLD_GRAVITY     = new Vector3f(0, -9.81f, 0);
    private static final Vector3f CHARACTER_GRAVITY = new Vector3f(0, -9.81f, 0);
    private static final Vector3f JUMP_STOP_GRAVITY = new Vector3f(0, -80.0f, 0);
    private static final Vector3f JUMP_FORCE        = new Vector3f(0, 20, 0);
    private static final float    CHARACTER_RADIUS  = 1.5f;
    private static final float    CHARACTER_HEIGHT  = 4.8f;
    private static final float    CHARACTER_MASS    = 10f;
    private static final float    PHYSICS_ACCURACY  = 0.016f; //def=0.016, low=0.005, hi=0.032
    private static final float    PHYSICS_DAMPING   = 0.1f;
    private static final boolean  DEBUG_MODE        = false;
    
    
    private SimpleApplication app;
    private BulletAppState bulletAppState;
    private RigidBodyControl scenePhysics;
    private PlatformerCharacterControl physicsCharacter;
    private Node sceneNode;
    private Node character;
    
    
    public WorldPhysicsManager(Application app,
                              Node sceneNode,
                              Node character){
        this.app = (SimpleApplication) app;
        this.sceneNode = sceneNode;
        this.character = character;
        this.bulletAppState = new BulletAppState();
        
        initialize();
    }
    
    
    public final void initialize() {
        //attach physics engine state
        app.getStateManager().attach(bulletAppState);
        
        //activate physics
        getPhysicsSpace().setGravity(WORLD_GRAVITY);
        getPhysicsSpace().setAccuracy(PHYSICS_ACCURACY);
        
        //add scene physics control
        scenePhysics = new RigidBodyControl(0f);
        sceneNode.addControl(scenePhysics);
        getPhysicsSpace().add(sceneNode);
        
        //add character physics control to character node
        physicsCharacter = new PlatformerCharacterControl(CHARACTER_RADIUS,
                                                          CHARACTER_HEIGHT,
                                                          CHARACTER_MASS);
        physicsCharacter.setJumpForce(JUMP_FORCE);
        physicsCharacter.setGravity(CHARACTER_GRAVITY);
        physicsCharacter.setPhysicsDamping(PHYSICS_DAMPING);
        
        //add character control to character model
        character.addControl(physicsCharacter);
        getPhysicsSpace().add(physicsCharacter);
        
        //set debug mode
        bulletAppState.setDebugEnabled(DEBUG_MODE);
    }
    
    
    public PhysicsSpace getPhysicsSpace() {
        return bulletAppState.getPhysicsSpace();
    }
    
    
}
