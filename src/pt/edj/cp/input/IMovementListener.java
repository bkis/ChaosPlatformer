package pt.edj.cp.input;

import com.jme3.math.Vector3f;

/**
 *
 * @author rechtslang
 */
public interface IMovementListener {
    public void movement(Vector3f newPosition, Vector3f delta);
}
