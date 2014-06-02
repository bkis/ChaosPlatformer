package pt.edj.cp.world.items;

import com.jme3.app.SimpleApplication;
import com.jme3.material.RenderState;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import pt.edj.cp.timing.GameThemeController;
import pt.edj.cp.timing.events.IEvent;


public class TemperatureChangePill extends AlphaRectCollectable {
    
    private boolean increaseTemp;
    
    public TemperatureChangePill(SimpleApplication app, boolean increase) {
        super(app, 0.3f, "Materials/Items/FunnyPill.j3md");
        
        increaseTemp = increase;
        
        rectMaterial.setVector2("NoiseOffset", 
                new Vector2f(10.f * (float) Math.random(), 
                             10.f * (float) Math.random()));
        rectMaterial.setFloat("NoiseFactor", 1.f + 0.5f * (float) Math.random());
        rectMaterial.setVector3("Color", 
                increase ? new Vector3f(1.0f, 0.2f, 0) : new Vector3f(0, 0.3f, 1));
        rectMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.AlphaAdditive);
    }
    

    @Override
    protected void effect() {
        float delta = 0.3f + 0.3f * (float) Math.random();
        GameThemeController.instance().changeParameter("Temperature", increaseTemp ? delta : -delta);
    }

    
    public void receiveEvent(IEvent e) {
    }

}
