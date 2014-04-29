package pt.edj.cp.world.background;

import com.jme3.app.Application;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Image.Format;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import pt.edj.cp.timing.events.IEvent;
import pt.edj.cp.util.AngleF;


public class MovingShapesLayer extends AbstractBackgroundLayer {
    
    private Vector2f layerSize;
    private float movingSpeed;
    private float shiftingSpeed;
    private HashSet<Shape> shapes = new HashSet<Shape>();
    
    private Random random = new Random();
    
    private Node rttScene;
    
    
    public MovingShapesLayer(Application app, float z, float sizeX, float sizeY) {
        super(app, z, 0.06f);
        layerSize = new Vector2f(sizeX, sizeY);
        
        movingSpeed = 0.6f;
        shiftingSpeed = 1.0f;
        
        // Create offscreen renderer nodes
        rttScene = new Node();
        rttScene.setName("RTTScene");
        Texture rttTex = createOffscreenRenderer(rttScene, 800, 600);
        this.addControl(new UpdateRTTControl());
        
        // add actual quad
        Geometry geom = new Geometry("MovingShapesQuad", new Quad(sizeX, sizeY));
        Material mat = new Material(app.getAssetManager(), "Materials/MovShapes/FinalMat.j3md");
        mat.setTexture("PreRenderedScene", rttTex);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geom.setMaterial(mat);
        geom.setLocalTranslation(-0.5f * sizeX, -0.5f * sizeY, 0.0f);
        attachChild(geom);
        
        // Create shapes
        for (int i = 0; i < 20; i++)
            addRandomShape();
    }
    
    
    private void addRandomShape() {
        float layerDimension = (float) Math.sqrt(layerSize.x * layerSize.y);
        float maxLayerSize = (float) Math.max(layerSize.x, layerSize.y);
        float avgShapeSize = layerDimension / 20.0f;
        float size = (0.3f + 1.7f * random.nextFloat()) * avgShapeSize;
        
        AngleF spawnAngle = AngleF.random();
        AngleF dirAngle = spawnAngle.add((3f + 2f * random.nextFloat()) / 8.0f * AngleF.PI2);
        
        Vector2f spawnDir = spawnAngle.toCoord(1.0f);
        if (spawnDir.x * layerSize.y > spawnDir.y * layerSize.x) {
            spawnDir.multLocal(layerSize.x + size * 2f);
        } else {
            spawnDir.multLocal(layerSize.y + size * 2f);
        }
        
        Shape shape = new Shape(
                3 + random.nextInt(3), 
                size*0.8f, size*1.2f, 
                spawnDir,
                dirAngle.toCoord(0.8f + 0.4f * random.nextFloat()).mult(avgShapeSize));
        
        
        rttScene.attachChild(shape);
        shapes.add(shape);
        
//        System.out.println("NEW: " + shape.getLocalTranslation() + "(spawn: " + spawnDir);
    }
    
    
    private void removeShape(Shape s) {
        rttScene.detachChild(s);
        shapes.remove(s);
//        System.out.println("DEL: " + s.getLocalTranslation());
    }
    
    
    /**
     * Can be of many forms
     */
    private class Shape extends Geometry {
        private Vector2f speed;
        private float maxRadius;
        
        public Shape(int corners, float minD, float maxD, Vector2f start, Vector2f direction) {
            super();
            
            this.maxRadius = maxD;
            this.setMesh(createMesh(corners, 0.1f, 1.5f, 2.5f));
            this.setCullHint(CullHint.Never);
            
            // create material
            Material mat = new Material(app.getAssetManager(), "Materials/MovShapes/Shape.j3md");
            float r = random.nextFloat() * 0.2f;
            float g = random.nextFloat() * 0.2f;
            float b = random.nextFloat() * 0.2f;
            mat.setVector4("Color", new Vector4f(r, g, b, 0.2f));
            mat.setFloat("Scale", 1.0f);
            this.setMaterial(mat);
            
            // Enable blending
            RenderState rstate = mat.getAdditionalRenderState();
            rstate.setBlendMode(RenderState.BlendMode.Additive);
            rstate.setDepthTest(false);
            
            // position
            this.setLocalTranslation(start.x, start.y, 0.0f);
            this.speed = direction.clone();
        }
        
        private Mesh createMesh(int corners, float angleDiff, float minD, float maxD) {
            float startAngle = random.nextFloat() * 2 * (float) Math.PI;
            
            // Create vertex array
            float verts[] = new float[3 * (2 + corners)];
            Arrays.fill(verts, 0.0f);
            
            for (int i = 0; i < corners; i++) {
                float angle = startAngle + (i + angleDiff * (random.nextFloat()-0.5f))
                              * 2.0f * (float) Math.PI / corners;
                float dist = minD + (maxD-minD) * random.nextFloat();
                
                verts[3*(i+1)    ] = (float) Math.sin(angle) * dist * 0.5f;
                verts[3*(i+1) + 1] = (float) Math.cos(angle) * dist * 0.5f;
                verts[3*(i+1) + 2] = 0.0f;
            }
            
            for (int i = 0; i < 3; i++)
                verts[3*(1+corners) + i] = verts[3 + i];
            
            Mesh result = new Mesh();
            result.setBuffer(VertexBuffer.Type.Position, 3, verts);
            result.setMode(Mesh.Mode.TriangleFan);
            
            return result;
        }
        
        public void shift(Vector3f ofs) {
            this.move(ofs.negate());
        }
        
        public void doMove(float factor) {
            this.move(speed.x * factor, speed.y * factor, 0f);
        }
        
        public boolean isOutsideLayer() {
            Vector3f pos = this.getLocalTranslation();
            
            return (Math.abs(pos.x) > layerSize.x*0.8 + maxRadius
                    || Math.abs(pos.y) > layerSize.y*0.8 + maxRadius);
        }
    }
    

    protected void doShift(Vector3f vec) {
        for (Shape s : shapes)
            s.shift(vec);
    }

    public void receiveEvent(IEvent e) {
    }

    
    private Texture createOffscreenRenderer(Node scene, int x, int y) {
        // Create Camera
        Camera offCamera = new Camera(x, y);
        
        // Create ViewPort
        ViewPort offView = app.getRenderManager().createPreView("MovingShapesView", offCamera);
        offView.setClearFlags(true, true, true);
        offView.setBackgroundColor(new ColorRGBA(0f, 0f, 0f, 0f));
        
//        offCamera.setParallelProjection(false);
//        offCamera.setFrustumPerspective(45f, 1f, 1f, 1000f);
//        offCamera.setLocation(Vector3f.UNIT_Z.mult(-10));
//        offCamera.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        
        offCamera.setParallelProjection(true);
        float w = layerSize.x * 0.5f;
        float h = layerSize.y * 0.5f;
        offCamera.setFrustum(-1000, 1000, -w, w, h, -h);
        
        // Create Texture
        Texture2D offTex = new Texture2D(x, y, Format.RGBA8);
        offTex.setMinFilter(Texture.MinFilter.Trilinear);
        offTex.setMagFilter(Texture.MagFilter.Bilinear);
        
        // Create Frame Buffer
        FrameBuffer offBuffer = new FrameBuffer(x, y, 1);
        offBuffer.setDepthBuffer(Format.Depth);
        offBuffer.setColorTexture(offTex);
        
        offView.setOutputFrameBuffer(offBuffer);
        offView.attachScene(scene);
        
        return offTex;
    }
    
    
    class UpdateRTTControl extends AbstractControl {

        protected void controlUpdate(float tpf) {
            LinkedList<Shape> toDelete = new LinkedList<Shape>();
            
            for (Shape s : shapes) {
                s.doMove(tpf * movingSpeed);
                if (s.isOutsideLayer()) {
                    toDelete.add(s);
                }
            }
            
            for (Shape td : toDelete) {
                removeShape(td);
                addRandomShape();
            }
            
            rttScene.updateGeometricState();
        }

        protected void controlRender(RenderManager rm, ViewPort vp) {
        }
        
    }
}
