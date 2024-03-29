package pt.edj.cp.physics;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;


public class WorldPhysicsManager {
    
    //physics parameters
    private static final float    PLAYER_SPEED      = 5.0f;
    public  static final Vector3f DIR_LEFT          = new Vector3f(-PLAYER_SPEED, 0, 0);
    public  static final Vector3f DIR_RIGHT         = new Vector3f(PLAYER_SPEED, 0, 0);
    private static final Vector3f WORLD_GRAVITY     = new Vector3f(0, -9.81f, 0);
    private static final Vector3f CHARACTER_GRAVITY = new Vector3f(0, -9.81f, 0);
    private static final Vector3f JUMP_STOP_GRAVITY = new Vector3f(0, -80.0f, 0);
    private static final Vector3f JUMP_FORCE        = new Vector3f(0, 30, 0);
    private static final float    CHARACTER_RADIUS  = 0.3f;
    private static final float    CHARACTER_HEIGHT  = 1.0f;
    private static final float    CHARACTER_MASS    = 10f;
    private static final float    PHYSICS_ACCURACY  = 0.016f; //def=0.016, low=0.005, hi=0.032
    private static final float    PHYSICS_DAMPING   = 0.5f;
    private static final boolean  DEBUG_MODE        = false;
    private static final float    CCD_MOTION_THRESH = 0.1f;
    
    
    private SimpleApplication app;
    private BulletAppState bulletAppState;
    private RigidBodyControl scenePhysics;
    private PlatformerCharacterControl playerControl;
    private Node sceneNode;
    private Node characterNode;
    
    
    
    public WorldPhysicsManager(Application app,
                              Node sceneNode,
                              Node characterNode){
        this.app = (SimpleApplication) app;
        this.sceneNode = sceneNode;
        this.characterNode = characterNode;
        initialize();
    }
    
    
    public final void initialize() {
        //load physics engine
        this.bulletAppState = new BulletAppState();
        app.getStateManager().attach(bulletAppState);
        
        //activate physics
        getPhysicsSpace().setGravity(WORLD_GRAVITY);
        getPhysicsSpace().setAccuracy(PHYSICS_ACCURACY);
        
        //add scene physics control
        scenePhysics = new RigidBodyControl(0f);
        sceneNode.addControl(scenePhysics);
        getPhysicsSpace().add(sceneNode);
        
        //add character physics control to character node
        playerControl = new PlatformerCharacterControl(CHARACTER_RADIUS,
                                                          CHARACTER_HEIGHT,
                                                          CHARACTER_MASS);
        playerControl.setJumpForce(JUMP_FORCE);
        playerControl.setGravity(CHARACTER_GRAVITY);
        playerControl.setPhysicsDamping(PHYSICS_DAMPING);
        
        //add character control to character model
        characterNode.addControl(playerControl);
        getPhysicsSpace().add(playerControl);
        
        //set debug mode
        bulletAppState.setDebugEnabled(DEBUG_MODE);
    }
    
    
    public PhysicsSpace getPhysicsSpace() {
        return bulletAppState.getPhysicsSpace();
    }
    
    
    public BetterCharacterControl getCharacterControl(){
        return playerControl;
    }
    
    
    public void addChildrenToPhysicsScene(Node sceneNode){
        for (Spatial s : sceneNode.getChildren())
            addToPhysicsScene(s);
    }
    
    
    private RigidBodyControl createRigidBodyControl(Spatial spatial) {
        if (spatial instanceof ICollisionShapeProvider) {
            CollisionShape cs = ((ICollisionShapeProvider) spatial).getCollisionShape();
            if (cs != null)
                return new RigidBodyControl(cs, 0);
        }
        
        return new RigidBodyControl(0);
    }
    
    public void addToPhysicsScene(Spatial spatial){
        RigidBodyControl platformPhysics = createRigidBodyControl(spatial);
        spatial.addControl(platformPhysics);

        platformPhysics.setKinematic(true);
        platformPhysics.setKinematicSpatial(true);
        platformPhysics.setFriction(1f);
        //platformPhysics.setCcdMotionThreshold(CCD_MOTION_THRESH);
        getPhysicsSpace().add(platformPhysics);
    }
    
    
    public void addGhost(Spatial spatial) {
        CollisionShape cs = null;
        if (spatial instanceof ICollisionShapeProvider) {
            cs = ((ICollisionShapeProvider) spatial).getCollisionShape();
        }
        if (cs != null) {
            GhostControl gc = new GhostControl(cs);
            spatial.addControl(gc);
            getPhysicsSpace().add(gc);
        }
    }
    
    
    public void removeFromPhysicsScene(Spatial spatial) {
        if (spatial == null)
            return;
        
        RigidBodyControl rbc = spatial.getControl(RigidBodyControl.class);
        GhostControl gc = spatial.getControl(GhostControl.class);
        PhysicsControl pc = (rbc != null) ? rbc : gc;
        
        if (pc != null) {
            getPhysicsSpace().remove(pc);
            spatial.removeControl(pc);
        }
    }
}
