package pt.edj.cp.world.platforms.gfx;

import com.jme3.app.SimpleApplication;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import java.util.concurrent.Callable;
import pt.edj.cp.world.platforms.PlatformItem;


public class SimpleParticleGFX extends PlatformItem {
    
    private ParticleEmitter throwEffect;
    private SimpleApplication app;
//    private boolean throwing = false;
//    private float throwingTimeLeft;
    
    public SimpleParticleGFX(SimpleApplication app) {
        this.app = app;
        
        //particles
        throwEffect = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 1);
        Material fireMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
        throwEffect.setMaterial(fireMat);
        throwEffect.setImagesX(2); throwEffect.setImagesY(2);
        throwEffect.setEndColor(new ColorRGBA(0,0,0,0)); 
        throwEffect.setStartColor(ColorRGBA.White); 
        throwEffect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, 0));
        throwEffect.setStartSize(0.4f);
        throwEffect.setEndSize(3.5f);
        throwEffect.setGravity(0f,0f,0f);
        throwEffect.setLowLife(1f);
        throwEffect.setHighLife(1f);
        throwEffect.getParticleInfluencer().setVelocityVariation(0);
        throwEffect.setParticlesPerSec(0);
        throwEffect.setRotateSpeed(FastMath.DEG_TO_RAD*60);
        
        this.attachChild(throwEffect);
    }

    
    @Override
    public void someEffectHappens() {
        app.enqueue(playGFX);
    }

    
    @Override
    public void update(float tpf, float globalBeat, float platformBeat) {
//        if (throwing) {
//            if ((throwingTimeLeft -= tpf) <= 0.0f) {
//                throwing = false;
//                throwEffect.setParticlesPerSec(0);
//            }
//        }
    }
    
    
    Callable<Boolean> playGFX = new Callable<Boolean>() {
        public Boolean call(){
            playGFX();
            return true;
        }
    };
    
    
    public void playGFX(){
        throwEffect.setParticlesPerSec(11);
        throwEffect.emitAllParticles();
        throwEffect.setParticlesPerSec(0);
    }

}
