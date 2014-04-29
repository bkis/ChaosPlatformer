package pt.edj.cp.input;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import pt.edj.cp.character.CharacterAnimator;
import pt.edj.cp.physics.PlatformerCharacterControl;
import pt.edj.cp.physics.WorldPhysicsManager;
import pt.edj.cp.timing.GameThemeController;


public class IngameInputsState extends AbstractAppState{
    
    //key triggers
    private static final KeyTrigger TRIGGER_W     = new KeyTrigger(KeyInput.KEY_W);
    private static final KeyTrigger TRIGGER_A     = new KeyTrigger(KeyInput.KEY_A);
    private static final KeyTrigger TRIGGER_D     = new KeyTrigger(KeyInput.KEY_D);
    private static final KeyTrigger TRIGGER_UP    = new KeyTrigger(KeyInput.KEY_UP);
    private static final KeyTrigger TRIGGER_LEFT  = new KeyTrigger(KeyInput.KEY_LEFT);
    private static final KeyTrigger TRIGGER_RIGHT = new KeyTrigger(KeyInput.KEY_RIGHT);
    private static final KeyTrigger TRIGGER_SPACE = new KeyTrigger(KeyInput.KEY_SPACE);
    private static final KeyTrigger TRIGGER_ESC   = new KeyTrigger(KeyInput.KEY_ESCAPE);
    private static final KeyTrigger TRIGGER_RET   = new KeyTrigger(KeyInput.KEY_RETURN);
    
    //action mappings
    private static final String MAPPING_JUMP  = "Jump";
    private static final String MAPPING_LEFT  = "Left";
    private static final String MAPPING_RIGHT = "Right";
    private static final String MAPPING_QUIT  = "Quit";
    private static final String MAPPING_DEBUG  = "Debug";
    
    //key states
    private boolean left = false,
                    right = false,
                    jump = false;
    
    private SimpleApplication app;
    private InputManager inputManager;

    private enum Movement {LEFT, RIGHT, NONE};
    private Movement currMov;
    
    private PlatformerCharacterControl playerControl;
    private CharacterAnimator ani;
    
    
    
    public IngameInputsState(PlatformerCharacterControl playerControl,
                             CharacterAnimator ani){
        this.playerControl = playerControl;
        this.ani = ani;
    }
      
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.app = (SimpleApplication) app;
        this.inputManager = app.getInputManager();
        
        //clear key mappings, setup new ones
        inputManager.clearMappings();
        setupKeys();
    }
    
    
    @Override
    public void update(float tpf){
        checkMovement();
    }
    
    
    @Override
    public void cleanup() {
        
    }
    
    
    private void setupKeys() {
        inputManager.addMapping(MAPPING_LEFT, TRIGGER_LEFT, TRIGGER_A);
        inputManager.addMapping(MAPPING_RIGHT, TRIGGER_RIGHT, TRIGGER_D);
        inputManager.addMapping(MAPPING_JUMP, TRIGGER_UP, TRIGGER_W, TRIGGER_SPACE);
        inputManager.addMapping(MAPPING_QUIT, TRIGGER_ESC);
        inputManager.addMapping(MAPPING_DEBUG, TRIGGER_RET);
        
        inputManager.addListener(actionListener, MAPPING_LEFT);
        inputManager.addListener(actionListener, MAPPING_RIGHT);
        inputManager.addListener(actionListener, MAPPING_JUMP);
        inputManager.addListener(actionListener, MAPPING_QUIT);
        inputManager.addListener(actionListener, MAPPING_DEBUG);
    }
    
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals(MAPPING_LEFT)) {
                if (isPressed){
                    if (!left) currMov = Movement.LEFT;
                    left = true;
                } else {
                    if (right) currMov = Movement.RIGHT;
                    else currMov = Movement.NONE;
                    left = false;
                }
            }
            
            if (name.equals(MAPPING_RIGHT)) {
                if (isPressed){
                    if (!right) currMov = Movement.RIGHT;
                    right = true;
                } else {
                    if (left) currMov = Movement.LEFT;
                    else currMov = Movement.NONE;
                    right = false;
                }
            }
            
            if (name.equals(MAPPING_JUMP)) {
               if (isPressed){
                    jump = true;
                } else {
                    jump = false;
                }
            }
            
            if (name.equals(MAPPING_QUIT)) {
                //QUIT GAME (temporary)
                app.stop();
            }
            
            if (name.equals(MAPPING_DEBUG) && !isPressed) {
                GameThemeController.instance().changeSomething();
            }
        }
    };
    
    
    private void checkMovement() {
        if (currMov == Movement.NONE && !jump){
            playerControl.setWalkDirection(Vector3f.ZERO);
            ani.animIdle();
            return;
        }
        
        if (currMov == Movement.LEFT){
            playerControl.setWalkDirection(WorldPhysicsManager.DIR_LEFT);
            ani.turnCharacterLeft();
            if (!jump) ani.animWalk();
        }
        
        if (currMov == Movement.RIGHT){
            playerControl.setWalkDirection(WorldPhysicsManager.DIR_RIGHT);
            ani.turnCharacterRight();
            if (!jump) ani.animWalk();
        }
        
        if (jump){
            playerControl.jump();
            ani.animJump();
        }
    }
    
    
}
