package engine.Scene;

import org.joml.Matrix4f;

public class Projection {

    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_FAR = 1000.0f;
    private static final float Z_NEAR = 0.01f;

    private Matrix4f projMatrix;

    public Projection(int width, int height){
        projMatrix = new Matrix4f();
        updateProjMatirx(width, height);
    }

    public Matrix4f getProjMatrix(){
        return projMatrix;
    }

    public void updateProjMatirx(int width, int height){
        projMatrix.setPerspective(FOV, (float) width / height, Z_NEAR, Z_FAR);
    }
}
