package pt.edj.cp.input;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;


public class IngameInputState extends AbstractAppState{
    
    //key triggers
    private static final KeyTrigger TRIGGER_W     = new KeyTrigger(KeyInput.KEY_W);
    private static final KeyTrigger TRIGGER_A     = new KeyTrigger(KeyInput.KEY_A);
    private static final KeyTrigger TRIGGER_D     = new KeyTrigger(KeyInput.KEY_D);
    private static final KeyTrigger TRIGGER_UP    = new KeyTrigger(KeyInput.KEY_UP);
    private static final KeyTrigger TRIGGER_LEFT  = new KeyTrigger(KeyInput.KEY_LEFT);
    private static final KeyTrigger TRIGGER_RIGHT = new KeyTrigger(KeyInput.KEY_RIGHT);
    private static final KeyTrigger TRIGGER_SPACE = new KeyTrigger(KeyInput.KEY_SPACE);
    private static final KeyTrigger TRIGGER_ESC   = new KeyTrigger(KeyInput.KEY_ESCAPE);
    
    //action mappings
    private static final String MAPPING_JUMP  = "Jump";
    private static final String MAPPING_LEFT  = "Left";
    private static final String MAPPING_RIGHT = "Right";
    private static final String MAPPING_QUIT  = "Quit";
    
    //key states
    private boolean left = false,
                    right = false,
                    jump = false;
    
    private SimpleApplication app;
    private InputManager inputManager;
    
    
      
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
        if (left){
            //TODO
        } if (right){
            //TODO
        } if (jump){
            //TODO
        } else {
            //TODO
        }
    }
    
    
    @Override
    public void cleanup() {
        
    }
    
    
    private void setupKeys() {
        inputManager.addMapping(MAPPING_LEFT, TRIGGER_LEFT, TRIGGER_A);
        inputManager.addMapping(MAPPING_RIGHT, TRIGGER_RIGHT, TRIGGER_D);
        inputManager.addMapping(MAPPING_JUMP, TRIGGER_UP, TRIGGER_W, TRIGGER_SPACE);
        inputManager.addMapping(MAPPING_QUIT, TRIGGER_ESC);
        
        inputManager.addListener(actionListener, MAPPING_LEFT);
        inputManager.addListener(actionListener, MAPPING_RIGHT);
        inputManager.addListener(actionListener, MAPPING_JUMP);
        inputManager.addListener(actionListener, MAPPING_QUIT);
    }
    
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals(MAPPING_LEFT)) {
                if (isPressed)
                    left = true;
                else
                    left = false;
            }
            
            if (name.equals(MAPPING_RIGHT)) {
                if (isPressed)
                    right = true;
                else
                    right = false;
            }
            
            if (name.equals(MAPPING_JUMP)) {
                if (isPressed)
                    jump = true;
                else
                    jump = false;
            }
            
            if (name.equals(MAPPING_QUIT)) {
                //QUIT GAME (temporary)
                app.stop();
            }
        }
    };
    
    
}
