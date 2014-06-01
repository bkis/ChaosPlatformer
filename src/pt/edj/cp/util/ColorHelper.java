package pt.edj.cp.util;

import com.jme3.math.Vector4f;


public class ColorHelper {
    
    private static final float PI2 = 3.141592f * 2.0f;
    
    public static Vector4f computeFromTemperature(float temp, float hue, float sat, float val, float a) {
        // convert X from normal space into distorted space
        float newHue = hue;
        
        if (temp > 0.0f)
            newHue -= (temp / PI2) * (float) Math.sin(hue * PI2);
        else
            newHue -= (temp / PI2) * (float) Math.sin((hue - 0.12f) * PI2);
            
        return fromHsv(newHue * 360.0f, sat, val, a);
    }
    

    public static Vector4f fromHsv(float h, float s, float b, float a) {
        float rgba[] = new float[4];
        
        if (s == 0) {
            // achromatic ( grey )
            return new Vector4f(b, b, b, a);
        }

        float hh = h / 60.0f;
        int i = (int) Math.floor(hh);
        float f = hh - i;
        float p = b * (1 - s);
        float q = b * (1 - s * f);
        float t = b * (1 - s * (1 - f));

        if (i == 0) {
                rgba[0] = b;
                rgba[1] = t;
                rgba[2] = p;
        } else if (i == 1) {
                rgba[0] = q;
                rgba[1] = b;
                rgba[2] = p;
        } else if (i == 2) {
                rgba[0] = p;
                rgba[1] = b;
                rgba[2] = t;
        } else if (i == 3) {
                rgba[0] = p;
                rgba[1] = q;
                rgba[2] = b;
        } else if (i == 4) {
                rgba[0] = t;
                rgba[1] = p;
                rgba[2] = b;
        } else {
                rgba[0] = b;
                rgba[1] = p;
                rgba[2] = q;
        }

        return new Vector4f(rgba[0], rgba[1], rgba[2], a);
    }
}
