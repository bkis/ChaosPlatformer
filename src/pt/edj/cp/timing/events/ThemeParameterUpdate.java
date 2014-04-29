package pt.edj.cp.timing.events;

import pt.edj.cp.timing.IThemeParameter;


public class ThemeParameterUpdate implements IEvent {
    protected IThemeParameter speed;
    protected IThemeParameter temperature;
    protected IThemeParameter excitement;
    
    public ThemeParameterUpdate(IThemeParameter speed, IThemeParameter temp, IThemeParameter exc) {
        this.speed = speed;
        this.temperature = temp;
        this.excitement = exc;
    }
    
    public IThemeParameter getSpeed() {
        return speed;
    }
    
    public IThemeParameter getTemperature() {
        return temperature;
    }
    
    public IThemeParameter getExcitement() {
        return excitement;
    }
}
