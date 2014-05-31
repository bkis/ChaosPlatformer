package pt.edj.cp.world.platforms.shapes;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import java.nio.FloatBuffer;
import java.util.Random;
import pt.edj.cp.timing.GameThemeController;
import pt.edj.cp.util.ColorHelper;
import pt.edj.cp.world.platforms.PlatformItem;


public class TriangleSpikesPlatform extends PlatformItem {
    private Geometry triGeometry;
    private Geometry lineGeometry;
    private Material trianglesMat;
    
    private float width;
    private float height;
    private float depth;
    
    private static Random random = new Random();
    
    private static float rdm(float min, float max) {
        return min + (max - min) * random.nextFloat();
    }
    
    public TriangleSpikesPlatform(SimpleApplication app) {
        super();
        
        int tris = 3 + random.nextInt(4);
        width = tris * rdm(0.4f, 0.6f);
        height = rdm(0.4f, 0.6f);
        depth = 1.0f;
        
        // create vertex arrays 
        Vector3f verts[] = new Vector3f[3 * tris];
        Vector4f colors[] = new Vector4f[3 * tris];
        Vector4f lineColors[] = new Vector4f[3 * tris];
        int lineIndices[] = new int[6 * tris];
        
        float temperature = GameThemeController.instance().getParameter("Temperature");
        
        // fill vertex arrays
        for (int i = 0; i < tris; i++) {
            float startX = (float) i / (1 + tris);
            float endX = (float) (i+2) / (1 + tris);
            
            if (i != 0)
                startX += rdm(-0.3f, 0.3f) / (1 + tris);
            if (i != tris-1)
                endX += rdm(-0.3f, 0.3f) / (1 + tris);
            
            float x1 = (-0.5f + startX) * width;
            float x2 = (-0.5f + endX) * width;
            float xm = x1 + (x2-x1) * rdm(0.3f, 0.7f);
            float topX = 0.5f * height;
            float bottomX = (-0.4f - 0.2f * random.nextFloat()) * height;
            float d = rdm(0.01f, 0.02f);
            
            verts[3*i + 0] = new Vector3f(x1, topX, d);
            verts[3*i + 1] = new Vector3f(xm, bottomX, d);
            verts[3*i + 2] = new Vector3f(x2, topX, d);
            
            float hue = random.nextFloat();
            float sat = 0.4f + 0.2f * random.nextFloat();
            float alpha = 0.4f + 0.2f * random.nextFloat();
            
            Vector4f color = ColorHelper.computeFromTemperature(
                    temperature, hue, sat, 0.5f, alpha);
            Vector4f lineColor = ColorHelper.computeFromTemperature(
                    temperature, hue, sat, 0.8f, alpha);
            
            for (int k = 0; k < 3; k++) {
                colors[3*i + k] = color;
                lineColors[3*i + k] = lineColor;
            }
            
            lineIndices[6*i + 0] = 3*i + 0;
            lineIndices[6*i + 1] = 3*i + 1;
            lineIndices[6*i + 2] = 3*i + 1;
            lineIndices[6*i + 3] = 3*i + 2;
            lineIndices[6*i + 4] = 3*i + 2;
            lineIndices[6*i + 5] = 3*i + 0;
        }
        
        // create buffer objects
        FloatBuffer vertsBuffer = BufferUtils.createFloatBuffer(verts);
        FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(colors);
        FloatBuffer lineColorBuffer = BufferUtils.createFloatBuffer(lineColors);
        
        // create triangle mesh
        Mesh triMesh = new Mesh();
        triMesh.setBuffer(VertexBuffer.Type.Position, 3, vertsBuffer);
        triMesh.setBuffer(VertexBuffer.Type.Color, 4, colorBuffer);
        triMesh.setMode(Mesh.Mode.Triangles);
        
        // create line mesh
        Mesh lineMesh = new Mesh();
        lineMesh.setBuffer(VertexBuffer.Type.Position, 3, vertsBuffer);
        lineMesh.setBuffer(VertexBuffer.Type.Color, 4, lineColorBuffer);
        lineMesh.setBuffer(VertexBuffer.Type.Index, 2, lineIndices);
        lineMesh.setMode(Mesh.Mode.Lines);
        lineMesh.setLineWidth(4.0f);

        // create material
        trianglesMat = new Material(app.getAssetManager(), "Materials/Platforms/TriangleSpikes.j3md");
        trianglesMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        
        // create geometry
        triGeometry = new Geometry();
        triGeometry.setMesh(triMesh);
        triGeometry.setMaterial(trianglesMat);
        this.attachChild(triGeometry);
        
        // create geometry
        lineGeometry = new Geometry();
        lineGeometry.setMesh(lineMesh);
        lineGeometry.setMaterial(trianglesMat);
        this.attachChild(lineGeometry);
    }
    
    @Override
    public CollisionShape getCollisionShape() {
        return new BoxCollisionShape(getExtents().mult(0.5f));
    }
    
    public Vector3f getExtents() {
        return new Vector3f(width, height, depth);
    }
}
