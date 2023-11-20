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

                //top face text coords
                //V8: V4 repeated
                -0.5f, 0.5f, -0.5f,
                //V9: V5 repeated
                0.5f, 0.5f, -0.5f,
                //V10: V0 repeated
                -0.5f, 0.5f, 0.5f,
                //V11 : V3 repeated
                0.5f, 0.5f, 0.5f,

                // For text coords in right face
                // V12: V3 repeated
                0.5f, 0.5f, 0.5f,
                // V13: V2 repeated
                0.5f, -0.5f, 0.5f,

                // For text coords in left face
                // V14: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V15: V1 repeated
                -0.5f, -0.5f, 0.5f,

                // For text coords in bottom face
                // V16: V6 repeated
                -0.5f, -0.5f, -0.5f,
                // V17: V7 repeated
                0.5f, -0.5f, -0.5f,
                // V18: V1 repeated
                -0.5f, -0.5f, 0.5f,
                // V19: V2 repeated
                0.5f, -0.5f, 0.5f,
        };
        float[] textCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,

                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,

                //top face
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,

                //right face
                0.0f, 0.0f,
                0.0f, 0.5f,

                //left face
                0.5f, 0.0f,
                0.5f, 0.5f,

                //bottom face
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
        };

        int[] indices = new int[]{
                //front face
                0, 1, 3, 3, 1, 2,
                //Top face
                8, 10, 11, 9, 8, 11,
                //Right face
                12, 13, 7, 5, 12, 7,
                //Left face
                14, 15, 6, 4, 14, 6,
                //Bottom face
                16, 18, 19, 17, 16, 19,
                //Back face
                4, 6, 7, 5, 4, 7,
        };
        Texture texture = scene.getTextureCache().createTexture("src/resources/models/cube/cube.png");
        Material material = new Material();
        material.setTexturePath(texture.getTexturePath());
        List<Material> materialList = new ArrayList<>();
        materialList.add(material);

        Mesh mesh = new Mesh(positions, textCoords, indices);
        material.getMeshList().add(mesh);
        Model cubeModel = new Model("cube-model", materialList);
        scene.addModel(cubeModel);

        cubeEntity = new Entity("cube-entity", cubeModel.getId());
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
