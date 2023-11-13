package game;

import org.joml.*;
import engine.*;
import engine.Scene.*;
import engine.graph.*;

import java.lang.Math;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements Logic {

    private Entity cubeEntity;
    private Vector4f dispInc = new Vector4f();
    private float rotation;

    public static void main(String[] args) {
        Main main = new Main();
        Engine gameEng = new Engine("", new Window.WindowOptions(), main);
        gameEng.start();
    }

    @Override
    public void cleanup(){

    }

    @Override
    public void init(Window window, Scene scene, Render render) {
        float[] positions = new float[]{
                //V0
                -0.5f, 0.5f, 0.5f,
                //V1
                -0.5f, -0.5f, 0.5f,
                //V2
                0.5f, -0.5f, 0.5f,
                //V3
                0.5f, 0.5f, 0.5f,
                //V4
                -0.5f, 0.5f, -0.5f,
                //V5
                0.5f, 0.5f, -0.5f,
                //V6
                -0.5f, -0.5f, -0.5f,
                //V7
                0.5f, -0.5f, -0.5f,
        };
        float[] colors = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };

        int[] indices = new int[]{
                //front face
                0, 1, 3, 3, 1, 2,
                //Top face
                4, 0, 3, 5, 4, 3,
                //Right face
                3, 2, 7, 5, 3, 7,
                //Left face
                6, 1, 0, 6, 0, 4,
                //Bottom face
                2, 1, 6, 2, 6, 7,
                //Back face
                7, 6, 4, 7, 4, 5,
        };
        List<Mesh> meshList = new ArrayList<>();
        Mesh mesh = new Mesh(positions, colors, indices);
        meshList.add(mesh);
        String cubeModelID = "cube-model";
        Model model = new Model(cubeModelID, meshList);
        scene.addModel(model);

        cubeEntity = new Entity("cube-entity", cubeModelID);
        cubeEntity.setPosition(0 ,0, -2);
        scene.addEntity(cubeEntity);
    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis) {
        dispInc.zero();
        if(window.isKeyPressed(GLFW_KEY_UP)){
            dispInc.y = 1;
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)){
            dispInc.y = -1;
        }
        if (window.isKeyPressed(GLFW_KEY_RIGHT)){
            dispInc.x = 1;
        }else if(window.isKeyPressed(GLFW_KEY_LEFT)){
            dispInc.x = -1;
        }
        if (window.isKeyPressed(GLFW_KEY_Q)){
            dispInc.z = 1;
        }else if (window.isKeyPressed(GLFW_KEY_A)){
            dispInc.z = -1;
        }
        if(window.isKeyPressed(GLFW_KEY_X)){
            dispInc.w = 1;
        }else if(window.isKeyPressed(GLFW_KEY_Z)){
            dispInc.w = -1;
        }

        dispInc.mul(diffTimeMillis / 1000.0f);

        Vector3f entityPos = cubeEntity.getPosition();
        cubeEntity.setPosition(dispInc.x + entityPos.x, dispInc.y + entityPos.y, dispInc.z + entityPos.z );
        cubeEntity.setScale(cubeEntity.getScale() + dispInc.w);
        cubeEntity.updateModelMatrix();

    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        rotation += 1.5;
        if(rotation >= 360){
            rotation = 0;
        }
        cubeEntity.setRotation(1, 1, 1, (float) Math.toRadians(rotation));
        cubeEntity.updateModelMatrix();
    }

}
