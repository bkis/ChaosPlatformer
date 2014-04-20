package pt.edj.cp.world.platforms;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.scene.Spatial;


public class PlatformCollisionListener implements PhysicsCollisionListener{

    public void collision(PhysicsCollisionEvent event) {
        checkForCollision(event.getNodeA(), event.getNodeB());
        checkForCollision(event.getNodeB(), event.getNodeA());
    }
    
    private boolean checkForCollision(Spatial character, Spatial platform) {
        if (character.getName() == null
                || !character.getName().startsWith("character"))
            return false;
        
        Platform plat = platform.getUserData("platform");
        if (plat == null)
            return false;
        
        plat.activate();
        
        return true;
    }

}
