package pt.edj.cp.world.platforms.gfx;

import com.jme3.app.Application;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

/**
 *
 * @author boss
 */
public class ExampleGFX extends AbstractPlatformGFX {

    ParticleEmitter fireEffect;
            
    
    public ExampleGFX(Application app){
        super(app);
    }
    
    
    @Override
    public void playGFX() {
        fireEffect.setParticlesPerSec(5);
        fireEffect.emitAllParticles();
        fireEffect.setParticlesPerSec(0);
    }

    
    @Override
    public void initializeGFX() {
        fireEffect = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 10);
        Material fireMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
        fireEffect.setMaterial(fireMat);
        fireEffect.setImagesX(2); fireEffect.setImagesY(2); // 2x2 texture animation
        fireEffect.setEndColor( new ColorRGBA(1f, 0f, 0f, 0f) );   // red
        fireEffect.setStartColor( new ColorRGBA(1f, 1f, 0f, 1f) ); // yellow
        fireEffect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 8, 0));
        fireEffect.setStartSize(0.1f);
        fireEffect.setEndSize(0.4f);
        fireEffect.setGravity(0f,30f,0f);
        fireEffect.setLowLife(0.5f);
        fireEffect.setHighLife(0.5f);
        fireEffect.getParticleInfluencer().setVelocityVariation(0.3f);
        fireEffect.setParticlesPerSec(0);
        
        fireEffect.setLocalTranslation(spatial.getLocalTranslation());
        spatial.getParent().attachChild(fireEffect);
    }
    
    
    

    
}
