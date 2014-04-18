package pt.edj.cp.physics;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import java.util.HashSet;
import pt.edj.cp.input.IMovementListener;


public class PlatformerCharacterControl extends BetterCharacterControl{
    
    private HashSet<IMovementListener> movementListeners = new HashSet<IMovementListener>();
    
    
    public PlatformerCharacterControl(float radius, float height, float mass){
        super(radius, height, mass);
    }
    
    
    @Override
    public void jump(){
        super.jump(); //TEMP
        
        //TODO
    }
    
    
    @Override
    public void update(float tpf){
        Vector3f oldPos = spatial.getLocalTranslation();
        
        super.update(tpf);
        correctZPos();
        
        Vector3f newPos = spatial.getLocalTranslation();
        Vector3f delta = newPos.subtract(oldPos);
        if (delta.lengthSquared() > 0.0f) {
            for (IMovementListener listener : movementListeners)
                listener.movement(newPos, delta);
        }
    }
    
    
    @Override
    public boolean isOnGround(){
        return super.isOnGround(); //TEMP
        
        //TODO
    }
    
    
    private void correctZPos(){
        if (spatial.getLocalTranslation().z < -0.01f
                || spatial.getLocalTranslation().z > 0.01f){
            spatial.move(0, 0, -getSpatialTranslation().z);
            setPhysicsLocation(spatial.getLocalTranslation());
            System.out.println(spatial.getLocalTranslation().z);
        }
    }
    
    
    void addMovementListener(IMovementListener l) {
        movementListeners.add(l);
    }
    
    
    void removeMovementListener(IMovementListener l) {
        movementListeners.remove(l);
    }
    
}
