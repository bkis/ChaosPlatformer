package pt.edj.cp.world.platforms.gfx;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import pt.edj.cp.timing.GameThemeController;
import pt.edj.cp.util.ColorHelper;
import pt.edj.cp.world.platforms.PlatformItem;


public class GlowGFX extends PlatformItem {
    
    private SimpleApplication app;
    private Material mat;
    private Geometry geom;
    private float baseHue;
    
    private float highlightDuration = 0.2f;
    private float highlightTimeLeft = 0.0f;
    
    private float toggleDuration = 0.2f;
    private float toggleValue = 0.0f;
    private float toggleAim = 0.0f;
    
    private static Mesh circleMesh = null;
    
    private static Mesh getCircleMesh() {
        if (circleMesh == null) {
            int vertCount = 128;

            // create vertex arrays for circle-mesh
            Vector3f verts[] = new Vector3f[vertCount+2];

            // fill vertex arrays
            verts[0] = new Vector3f(0, 0, 0);
            for (int i = 0; i <= vertCount; i++) {
                float angle = (float) i / (float) vertCount * 2.0f * 3.141592f;
                verts[i+1] = new Vector3f((float) Math.cos(angle), (float) Math.sin(angle), 0);
            }

            // create triangle mesh
            circleMesh = new Mesh();
            circleMesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(verts));
            circleMesh.setMode(Mesh.Mode.TriangleFan);
            circleMesh.updateBound();
        }
        
        return circleMesh;
    }
    
    public GlowGFX(SimpleApplication app, Vector3f extents) {
        super();
        
        baseHue = rand(0, 1);
        
        // create material
        mat = new Material(app.getAssetManager(), "Materials/Platforms/GlowGFX.j3md");
        mat.setVector2("Extents", new Vector2f(extents.x, extents.y * 1.4f));
        mat.setFloat("Highlight", 0.0f);
        mat.setFloat("ZValue", 0.01f + 0.04f * (float) Math.random());
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.AlphaAdditive);
        updateColor();
        
        // create geometry
        geom = new Geometry();
        geom.setMesh(getCircleMesh());
        geom.setMaterial(mat);
        geom.setCullHint(CullHint.Never);
        geom.setQueueBucket(RenderQueue.Bucket.Transparent);
    }
    
    
    private void updateColor() {
        float temp = GameThemeController.instance().getParameter("Temperature");
        mat.setVector4("Color", ColorHelper.computeFromTemperature(temp, baseHue, 1, 1, 1));
    }

    
    @Override
    public void someEffectHappens() {
        highlightTimeLeft = highlightDuration;
        mat.setFloat("Highlight", 1.0f);
    }

    
    @Override
    public void update(float tpf, float globalBeat, float platformBeat) {
        if (highlightTimeLeft > 0) {
            highlightTimeLeft = Math.max(highlightTimeLeft - tpf, 0.0f);
            float highlight = highlightTimeLeft / highlightDuration;
            mat.setFloat("Highlight", highlight);
        }
        
        updateColor();
    }
    
    @Override
    public void setActive(boolean a) {
        if (!active && a) {
            this.attachChild(geom);
        } else if (active && !a) {
            this.detachChild(geom);
        }
        
        active = a;
    }
    
    
}
