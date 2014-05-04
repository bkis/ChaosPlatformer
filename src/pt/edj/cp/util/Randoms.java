package pt.edj.cp.util;

import com.jme3.math.ColorRGBA;


public class Randoms {
    
    
    public static ColorRGBA rndColorRGBA(){
        return new ColorRGBA(
                (float)Math.random(),
                (float)Math.random(),
                (float)Math.random(),
                1);
    }
    
    
    public static float rndFloat(float min, float max){
        return ((float)Math.random()  * (max - min)) + min;
    }
    
}
