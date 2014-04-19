package pt.edj.cp.world.platforms.gfx;

import com.jme3.app.Application;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;


public class ExampleGFX extends AbstractPlatformGFX {

    private ParticleEmitter throwEffect;
    private Material matGfx;
    private Material matDefault;
            
    
    public ExampleGFX(Application app){
        super(app, 0.2f);
    }
    
    
    @Override
    public void playGFX() {
        //particles
        throwEffect.setParticlesPerSec(20);
        
        //platform color
        ((Geometry)spatial).setMaterial(matGfx);
    }
    
    
    @Override
    public void stopGFX() {
        //particles
        throwEffect.setParticlesPerSec(0);
        
        //platform color
        ((Geometry)spatial).setMaterial(matDefault);
    }

    
    @Override
    public void initializeGFX() {
        //particles
        throwEffect = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 20);
        Material fireMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
        throwEffect.setMaterial(fireMat);
        throwEffect.setImagesX(2); throwEffect.setImagesY(2); // 2x2 texture animation
        throwEffect.setEndColor( new ColorRGBA(1f, 0f, 0f, 0f) );   // red
        throwEffect.setStartColor( new ColorRGBA(1f, 1f, 0f, 1f) ); // yellow
        throwEffect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 8, 0));
        throwEffect.setStartSize(0.1f);
        throwEffect.setEndSize(0.4f);
        throwEffect.setGravity(0f,30f,0f);
        throwEffect.setLowLife(0.5f);
        throwEffect.setHighLife(0.5f);
        throwEffect.getParticleInfluencer().setVelocityVariation(0.3f);
        throwEffect.setParticlesPerSec(0);
        throwEffect.setLocalTranslation(spatial.getLocalTranslation());
        spatial.getParent().attachChild(throwEffect);
        
        //platform colors
        matDefault = ((Geometry)spatial).getMaterial().clone();
        matGfx = ((Geometry)spatial).getMaterial().clone();
        matGfx.setColor("Diffuse", ColorRGBA.White);
        matGfx.setColor("Ambient", ColorRGBA.Yellow);
        matGfx.setBoolean("UseMaterialColors", true);
    }

}
