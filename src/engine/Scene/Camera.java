package engine.Scene;

import org.joml.*;
/*Derived from:
 * A. H. Benjarano, “3D Game Development with LWJGL 3” https://ahbejarano.gitbook.io/lwjglgamedev*/
public class Camera {

    private Vector2f rotation;
    private Vector3f direction;
    private Vector3f position;
    private Vector3f right;
    private Vector3f up;
    private Matrix4f viewMatrix;

    public Camera(){
        rotation = new Vector2f();
        direction = new Vector3f();
        position = new Vector3f();
        right = new Vector3f();
        up = new Vector3f();
        viewMatrix = new Matrix4f();
    }

    public void addRotation(float x, float y){
        rotation.add(x, y);
        recalculate();
    }

    public Vector3f getPosition(){
        return position;
    }

    public Matrix4f getViewMatrix(){
        return viewMatrix;
    }

    public void moveForward(float inc){
        viewMatrix.positiveZ(direction).negate().mul(inc);
        position.add(direction);
        recalculate();
    }

    public void moveBackwards(float inc){
        viewMatrix.positiveZ(direction).negate().mul(inc);
        position.sub(direction);
        recalculate();
    }

    public void moveUp(float inc){
        viewMatrix.positiveY(up).mul(inc);
        position.add(up);
        recalculate();
    }

    public void moveDown(float inc){
        viewMatrix.positiveY(up).mul(inc);
        position.sub(up);
        recalculate();
    }

    public void moveRight(float inc){
        viewMatrix.positiveX(right).mul(inc);
        position.add(right);
        recalculate();
    }

    public void moveLeft(float inc){
        viewMatrix.positiveX(right).mul(inc);
        position.sub(right);
        recalculate();
    }

    public void setPosition(float x, float y, float z){
        position.set(x, y, z);
        recalculate();
    }

    public void setRotation(float x, float y){
        rotation.set(x, y);
        recalculate();
    }

    private void recalculate(){
        viewMatrix.identity()
                .rotateX(rotation.x)
                .rotateY(rotation.y)
                .translate(-position.x, -position.y, -position.z);
    }

}
