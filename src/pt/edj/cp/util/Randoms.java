package pt.edj.cp.util;

import com.jme3.math.ColorRGBA;


public class Randoms {
    
    
    public static ColorRGBA rndColorRGBA(float alpha){
        return new ColorRGBA(
                rndFloat(0.2f, 1.0f),
                rndFloat(0.2f, 1.0f),
                rndFloat(0.2f, 1.0f),
                alpha);
    }
    
    
    public static float rndFloat(float min, float max){
        return ((float)Math.random()  * (max - min)) + min;
    }
    
}