package pt.edj.cp.world.platforms;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.scene.Spatial;
import pt.edj.cp.world.items.Collectable;


public class PlatformCollisionListener implements PhysicsCollisionListener{
    
    static private long sTime = System.currentTimeMillis();

    public void collision(PhysicsCollisionEvent event) {
        if (checkForCollision(event.getNodeA(), event.getNodeB(), event)
                || checkForCollision(event.getNodeB(), event.getNodeA(), event)) {
            /*
            float ai = event.getAppliedImpulse();
            int lt = event.getLifeTime();
            int t = event.getType();
            
            System.out.printf("[%6d] Collision T=%d lt=%d ai=%g\n", System.currentTimeMillis()-sTime, t, lt, ai);
            */
        }
    }
    
    private boolean checkForCollision(Spatial character, Spatial object, PhysicsCollisionEvent event) {
        if (character.getName() == null
                || !character.getName().startsWith("character"))
            return false;
        
        Platform plat = object.getUserData("platform");
        if (plat != null) {
            plat.playerContact();
            return true;
        }
        
        Collectable coll = object.getUserData("collectable");
        if (coll != null) {
            coll.collect();
            return true;
        }
        
        return false;
    }

}
