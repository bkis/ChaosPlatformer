package pt.edj.cp.world.platforms.gfx;

import com.jme3.app.SimpleApplication;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import pt.edj.cp.world.platforms.PlatformItem;


public class SimpleParticleGFX extends PlatformItem {
    
    private ParticleEmitter throwEffect;
    private boolean throwing = false;
    private float throwingTimeLeft;
    
    public SimpleParticleGFX(SimpleApplication app) {
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
        
        this.attachChild(throwEffect);
    }

    
    public void someEffectHappens() {
        throwing = true;
        throwingTimeLeft = 0.2f;
        throwEffect.setParticlesPerSec(20);
    }

    
    public void update(float tpf, float globalBeat, float platformBeat) {
        if (throwing) {
            if ((throwingTimeLeft -= tpf) <= 0.0f) {
                throwing = false;
                throwEffect.setParticlesPerSec(0);
            }
        }
    }

}
