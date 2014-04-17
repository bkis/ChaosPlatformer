package pt.edj.cp.physics;

import com.jme3.bullet.control.BetterCharacterControl;


public class PlatformerCharacterControl extends BetterCharacterControl{
    
    
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
        super.update(tpf);
        correctZPos();
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
    
}
