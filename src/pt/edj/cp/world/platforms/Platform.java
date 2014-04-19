package pt.edj.cp.world.platforms;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import pt.edj.cp.timing.events.IEvent;
import pt.edj.cp.timing.events.IEventListener;
import pt.edj.cp.timing.events.MetronomeBeatEvent;
import pt.edj.cp.world.platforms.sfx.RhythmPattern;
import pt.edj.cp.world.platforms.sfx.SoundContainer;


public class Platform implements IEventListener, Savable {
    
    private RhythmPattern pattern;
    
    private Node topNode;
    private PlatformItem platformSpatial;
    private Set<PlatformItem> gfxNodes;
    private Set<PlatformItem> allPlatformItems;
    
    private SoundContainer sfx;
    private boolean active;
    
    
    public Platform(Vector3f position,
                    PlatformItem spatial,
                    SoundContainer sfx,
                    RhythmPattern pattern){
        this.platformSpatial = spatial;
        this.gfxNodes = new HashSet<PlatformItem>();
        this.allPlatformItems = new HashSet<PlatformItem>();
        this.sfx = sfx;
        this.pattern = pattern;
        this.active = false;
        
        topNode = new Node();
        topNode.setLocalTranslation(position);
        topNode.attachChild(platformSpatial);
        
        allPlatformItems.add(platformSpatial);
        
        platformSpatial.setUserData("platform", this);
    }
    
    
    public void activate() {
        if (!active) {
            System.out.println("activate!");
            sfx.playNextSound();
            active = true;
            
            for (PlatformItem item : allPlatformItems)
                item.setActive(true);
        }
    }
    
    
    public void addGFX(PlatformItem node) {
        topNode.attachChild(node);
        gfxNodes.add(node);
        allPlatformItems.add(node);
    }
    
    
    @Override
    public void receiveEvent(IEvent e) {
        if (e instanceof MetronomeBeatEvent){
            if (pattern.nextEvent()){
                for (PlatformItem item : allPlatformItems)
                    item.someEffectHappens();
            }
        } //else if (e instanceof ...
    }
    
    
    public void update(float tpf, float globalBeat) {
        // TODO
        for (PlatformItem item : allPlatformItems)
            item.update(tpf, globalBeat, 0.0f);
    }
    
    
    public Node getTopNode() {
        return topNode;
    }
    
    
    public Spatial getPlatformSpatial() {
        return platformSpatial;
    }

    
    public void write(JmeExporter ex) throws IOException {
    }

    public void read(JmeImporter im) throws IOException {
    }

}
