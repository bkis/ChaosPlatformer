package pt.edj.cp.physics;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsRayTestResult;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.util.TempVars;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import pt.edj.cp.input.IMovementListener;


public class PlatformerCharacterControl extends BetterCharacterControl implements PhysicsCollisionListener{
    
    private static final Vector3f CANCEL_JUMP_GRAVITIY = new Vector3f(0,-60,0);
    
    
    // for movement listeners:
    private Set<IMovementListener> movementListeners;
    private Vector3f lastPosition = null;
    private boolean falling;
    
    
    public PlatformerCharacterControl(float radius, float height, float mass){
        super(radius, height, mass);
        movementListeners = new HashSet<IMovementListener>();
    }
    
    
    @Override
    public void jump(){
        if (this.isOnGround()) jump = true;
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
        
        if (falling &&
                (isOnGround() || getVelocity().y < -8)){
            setGravity(new Vector3f(0, -9.81f, 0));
            falling = false;
        }
    }
    
    
    @Override
    public boolean isOnGround(){
        //modified WIP
        TempVars vars = TempVars.get();
        Vector3f location1 = vars.vect1.add(radius, 0, 0);
        Vector3f location2 = vars.vect1.add(-radius, 0, 0);
        Vector3f rayVector1 = vars.vect2;
        Vector3f rayVector2 = vars.vect2;
        float height = getFinalHeight();
        location1.set(localUp).multLocal(height).addLocal(this.location);
        location2.set(localUp).multLocal(height).addLocal(this.location);
        rayVector1.set(localUp).multLocal(-height - 0.15f).addLocal(location1);
        rayVector2.set(localUp).multLocal(-height - 0.15f).addLocal(location2);
        List<PhysicsRayTestResult> results1 = space.rayTest(location1, rayVector1);
        List<PhysicsRayTestResult> results2 = space.rayTest(location2, rayVector2);
        vars.release();
        for (PhysicsRayTestResult physicsRayTestResult : results1) {
            if (!physicsRayTestResult.getCollisionObject().equals(rigidBody)) {
                return true;
            }
        }
        for (PhysicsRayTestResult physicsRayTestResult : results2) {
            if (!physicsRayTestResult.getCollisionObject().equals(rigidBody)) {
                return true;
            }
        }
        return false;
    }
    
    
    public void cancelJump(){
        if (!isOnGround()){
            setGravity(CANCEL_JUMP_GRAVITIY);
            falling = true;
        }
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
