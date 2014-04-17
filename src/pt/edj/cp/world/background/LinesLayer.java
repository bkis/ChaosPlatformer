package pt.edj.cp.world.background;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.control.AbstractControl;
import com.jme3.terrain.noise.basis.ImprovedNoise;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.Random;
import pt.edj.cp.timing.events.IEvent;

/**
 *
 * @author rechtslang
 */
public class LinesLayer extends BackgroundLayer {
    
    private AssetManager assetMgr;
    
    private class Line extends Geometry {
        private int maxSegments;
        private float segLength;
        private int currLength;
        private Vector3f currPos;
        private float currAngle;
        
        private ImprovedNoise noise;
        private float noiseY;
        private float noiseZ;
        
        
        public Line(int segs, Random rnd) {
            super();
        
            maxSegments = segs;
            segLength = (float) Math.sqrt(maxX * maxY) * 0.01f;
            
            noise = new ImprovedNoise();
            noise.init();
            noiseY = rnd.nextFloat() * 10;
            noiseZ = rnd.nextFloat() * 10;
            
            currLength = 0;
            currAngle = rnd.nextFloat() * 2.0f * (float) Math.PI;
            currPos = new Vector3f((rnd.nextFloat() - 0.5f) * maxX,
                                   (rnd.nextFloat() - 0.5f) * maxY, 0.0f);

            // create mesh
            mesh = createMesh();
            this.setMesh(mesh);
            
            // create material
            Material mat = new Material(assetMgr, "Materials/LineMat.j3md");
            mat.setVector3("Color", new Vector3f(0.f, 0.4f, 0.8f));
            mat.setFloat("SegmentCount", (float) segs);
            mat.setFloat("TotalLength", currLength);
            mat.setFloat("CurrentSegment", currSegment);
            this.setMaterial(mat);
        }
        
        
        private Mesh createMesh() {
            // fill with initial values
            float verts[] = new float[3 * maxSegments];
            float tcs[] = new float[2 * maxSegments];
            for (int i = 0; i < maxSegments; i++) {
                verts[3*i+0] = currPos.getX();
                verts[3*i+1] = currPos.getY();
                verts[3*i+2] = currPos.getZ();
                tcs[2*i+0] = 0.0f;
                tcs[2*i+1] = 1.0f;
            }
            
            // create mesh
            Mesh result = new Mesh();
            result.setBuffer(VertexBuffer.Type.Position, 3, verts);
            result.setBuffer(VertexBuffer.Type.TexCoord, 2, tcs);
            result.setMode(Mesh.Mode.LineStrip);
            result.setLineWidth(1.0f);
            
            return result;
        }

        
        public void createControlPoints(int additionalSegments, float sharpness, float alternating) {
            float maxAngleChange = (float) Math.PI * (sharpness / 2.0f);
            
            // get old data:
            FloatBuffer oldVerts = (FloatBuffer) mesh.getBuffer(VertexBuffer.Type.Position).getData();
            FloatBuffer oldTCs = (FloatBuffer) mesh.getBuffer(VertexBuffer.Type.TexCoord).getData();
            
            // buffers for new data:
            FloatBuffer newVerts = (FloatBuffer) VertexBuffer.createBuffer(VertexBuffer.Format.Float, 3, maxSegments);
            FloatBuffer newTCs = (FloatBuffer) VertexBuffer.createBuffer(VertexBuffer.Format.Float, 2, maxSegments);
            
            // copy data:
            int newSegments = Math.min(additionalSegments, maxSegments);
            oldVerts.position(3*newSegments);
            newVerts.put(oldVerts);
            oldTCs.position(2*newSegments);
            newTCs.put(oldTCs);
            
            // create temp buffers
            float verts[] = new float[3 * newSegments];
            float texCoords[] = new float[2 * newSegments];
            
            // start creating new control points
            for (int i = 0; i < newSegments; i++) {
                double outsidenessX = Math.max(Math.abs(currPos.getX() / (0.5*maxX)) - 1, 0);
                double outsidenessY = Math.max(Math.abs(currPos.getY() / (0.5*maxY)) - 1, 0);
                double outsideness = outsidenessX + outsidenessY;
                
                // if we are outside boundaries, steer back to center
                if (outsideness > 0.3) {
                    currAngle = (float) (1.5*Math.PI - Math.atan2(currPos.getY(), currPos.getX()));
                    System.out.println(outsideness);
                }
                // change angle using noise - make sure to slightly head for center always
                else if (outsideness < 0.05) {
                    float dAngle = noise.value(alternating * currLength, noiseY, noiseZ);
                    currAngle += maxAngleChange * dAngle;
                    currAngle = (currAngle + 2.0f * (float)Math.PI) % (2.0f * (float)Math.PI);
                }
                
                // next control point:
                currPos = currPos.add(new Vector3f(
                        segLength * (float) Math.sin(currAngle), 
                        segLength * (float) Math.cos(currAngle), 
                        0.0f));
                
                currLength++;
                
                // add control point
                verts[3*i  ] = currPos.getX();
                verts[3*i+1] = currPos.getY();
                verts[3*i+2] = currPos.getZ();
                
                texCoords[2*i  ] = currLength;
                texCoords[2*i+1] = 1.0f;
            }

            // copy new control points into buffer
            newVerts.put(verts);
            newTCs.put(texCoords);
            
            // update vertex buffer data
            mesh.getBuffer(VertexBuffer.Type.Position).updateData(newVerts);
            mesh.getBuffer(VertexBuffer.Type.TexCoord).updateData(newTCs);
            
            getMaterial().setFloat("TotalLength", currLength);
        }
        
        public float segmentsLeft() {
            return currLength - currSegment;
        }
        
        public void updateCurrSegment() {
            getMaterial().setFloat("CurrentSegment", currSegment);
        }
        
    }
    
    
    private int numLines;
    private LinkedList<Line> lines;
    private Random random;
    
    private float maxX;
    private float maxY;
    
    private float currSegment;
    private float segmentsPerSecond;
    
    
    private class UpdateControl extends AbstractControl {
        protected void controlUpdate(float tpf) {
            currSegment += segmentsPerSecond * tpf;
            int minimumSegmentsAvailable = (int) (0.5*segmentsPerSecond + 1);
            
            for (Line line : lines) {
                if (line.segmentsLeft() < minimumSegmentsAvailable) {
                    line.createControlPoints(minimumSegmentsAvailable, 0.5f, 0.2f);
                }
                line.updateCurrSegment();
            }
        }

        protected void controlRender(RenderManager rm, ViewPort vp) {
        }
    }
    
    
    public LinesLayer(AssetManager assetMgr, int numLines, float sx, float sy) {
        super();
        
        maxX = sx;
        maxY = sy;
        currSegment = 0;
        segmentsPerSecond = 25.0f;
        
        this.assetMgr = assetMgr;
        this.numLines = numLines;
        this.lines = new LinkedList<Line>();
        this.random = new Random();
        
        for (int i = 0; i < numLines; i++)
            addLine();
        
        addControl(new UpdateControl());
    }
    
    
    private void addLine() {
        Line line = new Line(150 + random.nextInt(100), random);
        line.createControlPoints(10, 0.5f, 0.2f);
        lines.add(line);
        attachChild(line);
    }

    
    public void receiveEvent(IEvent e) {
    }
    
}
