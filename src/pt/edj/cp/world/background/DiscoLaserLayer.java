package pt.edj.cp.world.background;

import com.jme3.app.Application;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Line;
import com.sun.org.apache.bcel.internal.generic.SIPUSH;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import pt.edj.cp.app.IngameState;
import pt.edj.cp.timing.events.IEvent;
import pt.edj.cp.timing.events.MetronomeBeatEvent;

/**
 *
 * @author boss
 */
public class DiscoLaserLayer extends AbstractBackgroundLayer{
    
    private IngameState ingameState;
    private Set<Geometry> lines;
    private float nrOfLines;
    private float sizeY;
    private float sizeX;
    
    private int refreshEveryXBeats = 4;
    private int beatCount;
    
    
    
    public DiscoLaserLayer(Application app, float z, float nrOfLines, float sizeX, float sizeY){
        super(app, z, 0);
        
        this.ingameState = app.getStateManager().getState(IngameState.class);
        this.lines = new HashSet<Geometry>();
        this.nrOfLines = nrOfLines;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        registerMetronomeListener();
    }

    
    @Override
    protected void doShift(Vector3f vec) {
        
    }

    
    public void receiveEvent(IEvent e) {
        if (e instanceof MetronomeBeatEvent){
            beatCount++;
            if (beatCount >= refreshEveryXBeats){
                app.enqueue(callRefresh);
                beatCount = 0;
            }
        } //else if (e instanceof ...)
    }
    
    
    private void refresh(){
        for (Geometry g : lines) g.removeFromParent();
        lines.clear();
        addNewLines();
        for (Geometry g : lines) getParent().attachChild(g);
    }
    
    
    public final void registerMetronomeListener(){
        if (ingameState != null)
            ingameState.getMetronome().register(this);
    }
    
    
    private Geometry createLine(){
        Line line = new Line(
                parent.getParent().getChild("character").getLocalTranslation().add(
                    0,
                    sizeY/2,
                    0),
                parent.getParent().getChild("character").getLocalTranslation().add(
                    -(sizeX/2) + ((float)Math.random()*sizeX),
                    -sizeY,
                    0));
        line.setLineWidth((float)Math.random()*12);
        
        Material lineMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        lineMat.setColor("Color", new ColorRGBA(
                (float)Math.random(),
                (float)Math.random(),
                (float)Math.random(),
                0.8f));
        
        Geometry lineGeo = new Geometry("line", line);
        lineGeo.setMaterial(lineMat);
        
        return lineGeo;
    }
    
    
    private void addNewLines(){
        for (int i = 0; i < nrOfLines; i++)
            lines.add(createLine());
    }
    
    
    Callable<Boolean> callRefresh = new Callable<Boolean>() {
        public Boolean call(){
            refresh();
            return true;
        }
    };
    
    
}
