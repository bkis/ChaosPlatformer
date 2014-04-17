package pt.edj.cp.world.background;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;
import pt.edj.cp.timing.events.IEventListener;

/**
 *
 * @author rechtslang
 */
abstract public class BackgroundLayer extends Node implements IEventListener {
    
    
    protected SimpleApplication app;
    
    
    public BackgroundLayer(Application app){
        this.app = (SimpleApplication) app;
    }

}
