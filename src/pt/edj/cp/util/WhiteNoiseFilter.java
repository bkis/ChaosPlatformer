package pt.edj.cp.util;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.post.Filter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import pt.edj.cp.input.IMovementListener;

public class WhiteNoiseFilter extends Filter implements IMovementListener {
    
    private float factor;

    public WhiteNoiseFilter() {
        super("White Noise Filter");
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
        
        factor = -1.0f;
        updateNoiseFactor();
    }
    
    private void updateNoiseFactor() {
        if (factor < -1.0f)
            factor = -1.0f;
        if (factor > 1.0f)
            factor = 1.0f;
        
        if (material != null)
            material.setFloat("NoiseFactor", Math.max(factor, 0.0f));
    }
    
    public void update(float tpf) {
        factor += tpf / 10.0f;
        updateNoiseFactor();
    }

    public void movement(Vector3f newPosition, Vector3f delta) {
        factor -= delta.length() / 20.0f;
        updateNoiseFactor();
    }
}
