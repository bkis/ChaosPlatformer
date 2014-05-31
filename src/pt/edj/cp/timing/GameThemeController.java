package pt.edj.cp.timing;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import pt.edj.cp.timing.events.AbstractEventSender;
import pt.edj.cp.timing.events.ThemeParameterUpdate;


public class GameThemeController extends AbstractEventSender {

    private static GameThemeController _instance = new GameThemeController();
    
    protected GameThemeController() {
        super();
        
        parameters = new TreeMap<String, ThemeParameter>();
        
        speed = addParameter("Speed", 0.0f);
        temperature = addParameter("Temperature", 0.0f);
        excitement = addParameter("Excitement", 0.0f);
    }
    
    private ThemeParameter addParameter(String name, float v) {
        ThemeParameter tp = new ThemeParameter(name, v);
        parameters.put(name, tp);
        return tp;
    }
    
    public static GameThemeController instance() {
        return _instance;
    }
    
    
    private class ThemeParameter implements IThemeParameter {
        private String name;
        private float value;
        private boolean changed;
        
        private float targetValue;
        private float targetSwitchPerSec;

        public ThemeParameter(String n, float v) {
            name = n;
            value = v;
            changed = true;
            
            targetValue = v;
            targetSwitchPerSec = 0.f;
        }

        public String getName() {
            return name;
        }

        public float getValue() {
            return value;
        }

        public boolean hasChanged() {
            return changed;
        }
        
        void setTarget(float v, float t) {
            targetValue = v;
            targetSwitchPerSec = (targetValue - value) / t;
        }
        
        boolean update(float tpf) {
            float diff = Math.abs(value - targetValue);
            
            if (diff > 0.f) {
                float change = targetSwitchPerSec * tpf;
                if (Math.abs(change) > diff)
                    value = targetValue;
                else
                    value += change;
                
                return true;
            }
            
            return false;
        }
    }
    
    private ThemeParameter speed;
    private ThemeParameter temperature;
    private ThemeParameter excitement;
    private Map<String, ThemeParameter> parameters;
    
    public void frame(float tpf) {
        // if something changed, send notifications to all listeners
        boolean change = false;
        for (ThemeParameter tp : parameters.values()) {
            if (tp.update(tpf))
                change = true;
        }
        
        broadcast(new ThemeParameterUpdate(speed, temperature, excitement));
        
        for (ThemeParameter tp : parameters.values()) {
            tp.changed = false;
        }
    }
    
    Random random = new Random();
    
    public boolean changeParameter(String name, float delta) {
        ThemeParameter p = parameters.get(name);
        
        if (p == null)
            return false;
        
        float aim = Math.min(Math.max(p.getValue() + delta, -1), 1);
        p.setTarget(aim, 2.0f);
        
        return true;
    }
}
