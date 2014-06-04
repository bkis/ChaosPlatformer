package pt.edj.cp.util;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.post.Filter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import pt.edj.cp.app.IngameState;
import pt.edj.cp.input.IMovementListener;

public class WhiteNoiseFilter extends Filter implements IMovementListener {
    
    private float factor;
    private float soundFactor;
    private SimpleApplication app;

    public WhiteNoiseFilter(SimpleApplication app) {
        super("White Noise Filter");
        
        this.app = app;
    }

    @Override
    protected boolean isRequiresDepthTexture() {
        return false;
    }

    @Override
    protected Material getMaterial() {
        return material;
    }

    @Override
    protected void initFilter(AssetManager assets, RenderManager renderManager,
            ViewPort vp, int w, int h) {
        material = new Material(assets, "Materials/Post/WhiteNoise.j3md");
        
        factor = -0.9f;
        updateNoiseFactor();
    }
    
    private void updateNoiseFactor() {
        if (factor < -1.0f)
            factor = -1.0f;
        if (factor > 1.0f)
            factor = 1.0f;
        
        float trueFactor = Math.max(factor, 0.0f);
        if (material != null)
            material.setFloat("NoiseFactor", trueFactor);
        
        if (Math.abs(trueFactor - soundFactor) > 0.1f) {
            soundFactor = trueFactor;
            app.getStateManager().getState(IngameState.class).getSoundController().changeVolume(1 - soundFactor);
        }
    }
    
    public void update(float tpf) {
        factor += tpf / 10.0f;
        updateNoiseFactor();
    }

    public void movement(Vector3f newPosition, Vector3f delta) {
        factor -= delta.length() / 20.0f;
        updateNoiseFactor();
    }
    
    public float getIntensity(){
        return factor/2;
    }
}
