package engine.Scene;

import org.joml.*;
/*Derived from:
 * A. H. Benjarano, “3D Game Development with LWJGL 3” https://ahbejarano.gitbook.io/lwjglgamedev*/
public class Entity {

    private final String id;
    private final String modelID;
    private Matrix4f modelMatrix;
    private Vector3f position;
    private Quaternionf rotation;
    private float scale;
    private AnimationData animationData;

    public Entity(String id, String modelID){
        this.id = id;
        this.modelID = modelID;
        modelMatrix = new Matrix4f();
        position = new Vector3f();
        rotation = new Quaternionf();
        scale = 1;
    }

    public String getId(){
        return id;
    }

    public String getModelID(){
        return modelID;
    }

    public Matrix4f getModelMatrix(){
        return modelMatrix;
    }

    public Vector3f getPosition(){
        return position;
    }

    public Quaternionf getRotation(){
        return rotation;
    }

    public float getScale(){
        return scale;
    }

    public AnimationData getAnimationData() {
        return animationData;
    }

    public final void setPosition(float x, float y, float z){
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void setRotation(float x, float y, float z, float angle){
        this.rotation.fromAxisAngleRad(x, y, z, angle);
    }

    public void setScale(float scale){
        this.scale = scale;
    }

    public void setAnimationData(AnimationData animationData) {
        this.animationData = animationData;
    }

    public void updateModelMatrix(){
        modelMatrix.translationRotateScale(position, rotation, scale);
    }
}
