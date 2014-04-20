package pt.edj.cp.util;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import java.util.Random;


public class AngleF {
    public static final float PI2 = 2.0f * 3.14159265f;
    
    private static Random random = new Random();
    private float angle;
    
    public static AngleF random() {
        return new AngleF(random.nextFloat() * PI2);
    }
    
    public AngleF() {
        angle = 0.0f;
    }
    
    public AngleF(float v) {
        set(v);
    }
    
    public float get() {
        return angle;
    }
    
    public void set(float f) {
        angle = f % PI2;
        if (angle < 0.0f)
            angle += PI2;
    }
    
    public AngleF add(float f) {
        return new AngleF(angle + f);
    }
    
    public AngleF add(AngleF o) {
        return new AngleF(angle + o.angle);
    }
    
    public AngleF sub(float f) {
        return new AngleF(angle - f);
    }
    
    public AngleF sub(AngleF o) {
        return new AngleF(angle - o.angle);
    }
    
    public Vector2f toCoord(float radius) {
        return new Vector2f(
                (float) Math.sin(angle) * radius, 
                (float) Math.cos(angle) * radius);
    }
    
    public Vector3f toCoord(float radius, float z) {
        return new Vector3f(
                (float) Math.sin(angle) * radius, 
                (float) Math.cos(angle) * radius,
                z);
    }
    
    public float sin() {
        return (float) Math.sin(angle);
    }
    
    public float cos() {
        return (float) Math.cos(angle);
    }
}
