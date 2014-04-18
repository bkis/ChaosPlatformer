package pt.edj.cp.physics;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import java.util.HashSet;
import java.util.Set;
import pt.edj.cp.input.IMovementListener;


public class PlatformerCharacterControl extends BetterCharacterControl implements PhysicsCollisionListener{
    
    // for movement listeners:
    private Set<IMovementListener> movementListeners;
    private Vector3f lastPosition = null;
    
    
    public PlatformerCharacterControl(float radius, float height, float mass){
        super(radius, height, mass);
        movementListeners = new HashSet<IMovementListener>();
    }
    
    
    @Override
    public void jump(){
        super.jump(); //kann erstmal so bleiben
    }
    
    
    @Override
    public void update(float tpf){
        super.update(tpf);
        correctZPos();
        
        if (lastPosition == null) {
            lastPosition = spatial.getLocalTranslation().clone();
        } else {
            Vector3f newPos = spatial.getLocalTranslation();
            Vector3f delta = newPos.subtract(lastPosition);
            delta.z = 0.0f; // ignore Z-axis
            
            if (delta.lengthSquared() > 0.0001f) {
                lastPosition.set(newPos);
                for (IMovementListener listener : movementListeners)
                    listener.movement(newPos, delta);
            } 
        }
    }
    
    
    @Override
    public boolean isOnGround(){
        return super.isOnGround(); //TEMP
        
        //braucht bessere implementierung für gefälle / kugeln
    }
    
    
    private void correctZPos(){
        if (spatial.getLocalTranslation().z < -0.01f
                || spatial.getLocalTranslation().z > 0.01f){
            spatial.move(0, 0, -getSpatialTranslation().z);
            setPhysicsLocation(spatial.getLocalTranslation());
            //System.out.println(spatial.getLocalTranslation().z);
        }
    }
    
    
    public void addMovementListener(IMovementListener l) {
        movementListeners.add(l);
    }
    
    
    public void removeMovementListener(IMovementListener l) {
        movementListeners.remove(l);
    }

    
    public void collision(PhysicsCollisionEvent event) {
        //evtl. für Überschreiben von OnGround() nützlich
    }
    
}
