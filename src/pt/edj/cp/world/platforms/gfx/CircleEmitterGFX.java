package pt.edj.cp.world.platforms.gfx;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import java.util.LinkedList;
import pt.edj.cp.timing.GameThemeController;
import pt.edj.cp.util.ColorHelper;
import pt.edj.cp.world.platforms.PlatformItem;


public class CircleEmitterGFX extends PlatformItem {
    
    private SimpleApplication app;
    
    private class Circle extends Geometry {
        private float maxTime;
        private float currTime;
        private boolean major;
        private Material mat;
        
        public Circle(Vector2f pos, float maxRadius, float maxTime, boolean major) {
            float zValue = rand(0.01f, 0.04f);
            this.currTime = 0.0f;
            this.maxTime = maxTime;
            this.major = major;
            
            this.setMesh(new Quad(2*maxRadius, 2*maxRadius));
            this.setLocalTranslation(pos.x - maxRadius, pos.y - maxRadius, zValue);
            
            float temp = GameThemeController.instance().getParameter("Temperature");
            
            mat = new Material(app.getAssetManager(), "Materials/Platforms/CircleEmitterGFX.j3md");
            mat.setVector4("Color", ColorHelper.computeFromTemperature(temp, baseHue, 1.f, 1.f, 1.f));
            mat.setFloat("RelTime", 0.0f);
            mat.setFloat("StartRadius", 0.0f);
            mat.setFloat("MidRadius1", 0.0f);
            mat.setFloat("MidRadius2", 0.0f);
            mat.setFloat("EndRadius", 0.0f);
            mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.AlphaAdditive);
            this.setMaterial(mat);
        }
        
        public boolean advanceTime(float tpf) {
            currTime = Math.min(currTime + tpf, maxTime);
            
            float relTime = currTime / maxTime;
            mat.setFloat("RelTime", relTime);
            
            float rad = (float) Math.pow(relTime, 0.7);
            mat.setFloat("StartRadius", 0.8f * rad);
            mat.setFloat("MidRadius1", 0.85f * rad);
            mat.setFloat("MidRadius2", 0.95f * rad);
            mat.setFloat("EndRadius", 1.0f * rad);
            
            float temp = GameThemeController.instance().getParameter("Temperature");
            float sat = 1 - (float) Math.pow(relTime, 0.7f);
            mat.setVector4("Color", ColorHelper.computeFromTemperature(temp, baseHue, sat, 1, 1.f));
            
            return (currTime >= maxTime);
        }
        
        public boolean isMajor() {
            return major;
        }
    }
    
    
    private LinkedList<Circle> circles = new LinkedList<Circle>();
    private Vector2f maxExtents;
    private int numActiveCircles;
    private boolean addMajorCircleNextFrame = false;
    private float baseHue;
    
    
    private void addCircle(boolean beat) {
        float x = maxExtents.x * rand(-0.4f, 0.4f);
        float y = maxExtents.y * rand(-0.3f, 0.3f);
        
        float radFac = beat ? 0.7f : 0.2f;
        float radius = maxExtents.length() * radFac * rand(0.8f, 1.2f);
        
        float time = beat ? rand(1.2f, 1.6f) : rand(0.4f, 0.6f);
        
        Circle c = new Circle(new Vector2f(x, y), radius, time, beat);
        this.attachChild(c);
        circles.add(c);
    }
    
    
    public CircleEmitterGFX(SimpleApplication app, Vector3f extents, int minorPoppings) {
        super();
        
        this.app = app;
        this.maxExtents = new Vector2f(extents.x, extents.y);
        this.baseHue = rand(0, 1);
        
        numActiveCircles = minorPoppings;
        
        this.setQueueBucket(RenderQueue.Bucket.Transparent);
        this.setCullHint(CullHint.Never);
    }

    
    @Override
    public void someEffectHappens() {
        addMajorCircleNextFrame = true;
    }

    
    @Override
    public void update(float tpf, float globalBeat, float platformBeat) {
        LinkedList<Circle> removeCircles = new LinkedList<Circle>();
        
        for (Circle c : circles) {
            if (c.advanceTime(tpf)) {
                removeCircles.add(c);
            }
        }
        
        circles.removeAll(removeCircles);
        for (Circle c : removeCircles) {
            detachChild(c);
            if (!c.isMajor() && active)
                addCircle(false);
        }
        
        if (addMajorCircleNextFrame) {
            addMajorCircleNextFrame = false;
            addCircle(true);
        }
    }
    
    @Override
    public void setActive(boolean a) {
        if (!active && a) {
            for (int i = 0; i < numActiveCircles; i++)
                addCircle(false);
        }
        
        active = a;
    }
}

