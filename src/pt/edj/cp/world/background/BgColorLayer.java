package pt.edj.cp.world.background;

import com.jme3.app.Application;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import pt.edj.cp.timing.events.IEvent;


public class BgColorLayer extends AbstractBackgroundLayer{
    
    private ViewPort viewPort;
    
    public BgColorLayer(Application app){
        super(app, -10, 0);
        this.viewPort = app.getViewPort();
        viewPort.setBackgroundColor(ColorRGBA.DarkGray);
    }

    @Override
    protected void doShift(Vector3f vec) {
        
    }

    public void receiveEvent(IEvent e) {
        
    }
    
}
