package pt.edj.cp.character;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
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
        animChannel.setSpeed(4f);
        animChannel.setAnim(ANI_IDLE, 0);
        animChannel.setLoopMode(LoopMode.DontLoop);
    }
    
    
    public void animIdle(){
        if (!animChannel.getAnimationName().equals(ANI_IDLE)) {
            animChannel.setAnim(ANI_IDLE, 0);
            animChannel.setLoopMode(LoopMode.DontLoop);
        }
    }
    
    
    public void animWalk(boolean walkLeft){
        if (walkLeft) turnCharacterLeft();
        else          turnCharacterRight();
        
        animChannel.setAnim(ANI_WALK, 0);
        animChannel.setLoopMode(LoopMode.Loop);
    }
    
    
    public void animJump(){
        animChannel.setAnim(ANI_JUMP, 0);
        animChannel.setLoopMode(LoopMode.Loop);
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
    
    
//    private void turnCharacterFront(){
//        character.lookAt(new Vector3f(
//                0,
//                character.getLocalTranslation().y,
//                99999999              
//                ), Vector3f.UNIT_Y);
//    }
    
    
    private void animCycleDone(AnimControl control, AnimChannel channel, String animName) {
        //...
    }
    
    
    private void animChange(AnimControl control, AnimChannel channel, String animName) {
        //...
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
