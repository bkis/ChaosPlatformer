package pt.edj.cp.bonus;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.texture.Texture;
import pt.edj.cp.app.IngameState;
import pt.edj.cp.timing.events.IEvent;
import pt.edj.cp.timing.events.IEventListener;
import pt.edj.cp.timing.events.NewBarEvent;
import pt.edj.cp.util.Randoms;


public class Bonus implements IEventListener{
    
    private ParticleEmitter emitter;
    private SimpleApplication app;
    private int lock;
    
    public Bonus(Application app){
        this.app = (SimpleApplication) app;
        this.lock = 0;
        
            /** Uses Texture from jme3-test-data library! */
        emitter = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", getRndGimmick());
        emitter.setMaterial(mat);
        emitter.setEndColor( new ColorRGBA(1f, 1f, 1f, 1f) );  
        emitter.setStartColor( new ColorRGBA(1f, 1f, 1f, 1f) );
        emitter.setRotateSpeed(1);
        emitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        emitter.setStartSize(0.4f);
        emitter.setEndSize(0.4f);
        emitter.setGravity(0f,1f,0f);
        emitter.setLowLife(10f);
        emitter.setHighLife(10f);
        emitter.getParticleInfluencer().setVelocityVariation(1);
        emitter.setParticlesPerSec(0);
        
        this.app.getRootNode().attachChild(emitter);
    }
    
    
    public void trigger(){
        if (lock > 10)
            lock = 0;
        else
            return;
        
        System.out.println("BONUS");
        Vector3f pos = app.getStateManager().getState(IngameState.class).getCharacterNodeLocation();
        emitter.setLocalTranslation(pos.add(0, 15, 12));
        emitter.setParticlesPerSec(20);
        emitter.emitAllParticles();
        emitter.setParticlesPerSec(0);
    }
    
    
    private Texture getRndGimmick(){
        int rnd = Randoms.rndInt(0, 9);
        return app.getAssetManager().loadTexture("Textures/Bonus/" + rnd + ".png");
    }

    public void receiveEvent(IEvent e) {
        if (e instanceof NewBarEvent){
            lock++;
        }
    }
    
}
