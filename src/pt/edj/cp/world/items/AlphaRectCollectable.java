package pt.edj.cp.world.items;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;


public abstract class AlphaRectCollectable extends Collectable {

    protected float radius;
    protected Material rectMaterial;
    
    public AlphaRectCollectable(SimpleApplication app, float radius, String materialSrc) {
        this.radius = radius;
        
        Geometry geom = new Geometry("Collectable", new Quad(2*radius, 2*radius));
        rectMaterial = new Material(app.getAssetManager(), materialSrc);
        rectMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geom.setMaterial(rectMaterial);
        geom.setLocalTranslation(-radius, -radius, 0.0f);
        geom.setQueueBucket(Bucket.Transparent);
        
        attachChild(geom);
    }
    
    public CollisionShape getCollisionShape() {
        return new SphereCollisionShape(radius);
    }
}
