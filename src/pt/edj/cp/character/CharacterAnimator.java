package pt.edj.cp.character;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * class for character animation management and execution
 */
public class CharacterAnimator {
    
    //animation IDs for model: "Oto"  (samples)
    private static final String ANI_IDLE = "stand";
    private static final String ANI_WALK = "Walk";
    private static final String ANI_JUMP = "push";
    
    private AniState aniState;
    public static enum AniState {IDLE,
                                 MOVE_LEFT,
                                 MOVE_RIGHT,
                                 JUMP};
    
    private AnimChannel animChannel;
    private AnimControl animControl;
    private Node character;
    private SimpleApplication app;
    
    
    public CharacterAnimator(Application app, Node character){
        super();
        this.app = (SimpleApplication) app;
        this.character = character;
        initialize();
    }
    
    
    private void initialize(){
        animControl = character.getControl(AnimControl.class);
        animControl.addListener(animationListener);
        animChannel = animControl.createChannel();
        animChannel.setAnim(ANI_IDLE);
        animChannel.setSpeed(1f);
    }
    
    
    public void animStand(){
        //TODO
    }
    
    
    public void animWalk(boolean walkLeft){
        //TODO
    }
    
    
    public void animJump(){
        //TODO
    }
    
    
    private void turnCharacterLeft(){
        character.lookAt(new Vector3f(
                -99999999,
                character.getLocalTranslation().y,
                0              
                ), Vector3f.UNIT_Y);
    }
    
    
    private void turnCharacterRight(){
        character.lookAt(new Vector3f(
                99999999,
                character.getLocalTranslation().y,
                0              
                ), Vector3f.UNIT_Y);
    }
    
    
    private void turnCharacterFront(){
        character.lookAt(new Vector3f(
                0,
                character.getLocalTranslation().y,
                99999999              
                ), Vector3f.UNIT_Y);
    }
    
    
    private void animCycleDone(AnimControl control, AnimChannel channel, String animName) {
        //TODO
    }
    
    
    private void animChange(AnimControl control, AnimChannel channel, String animName) {
        //TODO
    }
    

    private AnimEventListener animationListener = new AnimEventListener(){
        public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
            animCycleDone(control, channel, animName);
        }

        public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
            animChange(control, channel, animName);
        }
    };
    
}
