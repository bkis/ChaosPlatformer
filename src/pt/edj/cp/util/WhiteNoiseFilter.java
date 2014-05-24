package pt.edj.cp.util;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.post.Filter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;

public class WhiteNoiseFilter extends Filter {

    // These values are set internally based on the
    // viewport size.
    private float xScale;
    private float yScale;

    /**
     * Creates a DepthOfField filter
     */
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

        xScale = 1.0f / w;
        yScale = 1.0f / h;

        material.setFloat("XScale", xScale);
        material.setFloat("YScale", yScale);
    }
}
