package pt.edj.cp.world.items;

import com.jme3.app.SimpleApplication;
import com.jme3.material.RenderState;
import pt.edj.cp.timing.GameThemeController;
import pt.edj.cp.timing.events.IEvent;


public class SpeedChangePill extends AlphaRectCollectable {
    
    private boolean increaseSpeed;
    
    public SpeedChangePill(SimpleApplication app, boolean increase) {
        super(app, 0.3f, "Materials/Items/SpeedPill.j3md");
        
        increaseSpeed = increase;
        
        rectMaterial.setFloat("SpeedFactor", increase ? 3.0f : 0.2f);
        rectMaterial.setFloat("Trail", increase ? 0.2f : 0.07f);
        rectMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
    }
    

    @Override
    protected void effect() {
        float delta = 0.3f + 0.3f * (float) Math.random();
        GameThemeController.instance().changeParameter("Speed", increaseSpeed ? delta : -delta);
    }

    
    public void receiveEvent(IEvent e) {
    }

}
