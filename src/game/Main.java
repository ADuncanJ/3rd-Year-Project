package game;

import engine.Scene.lights.PointLight;
import engine.Scene.lights.SceneLights;
import engine.Scene.lights.SpotLight;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiCond;
import org.joml.*;
import engine.*;
import engine.Scene.*;
import engine.graph.*;

import java.lang.Math;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements Logic, GUIInstance {

    private static final float MOUSE_SENSITIVITY = 0.1f;
    private static final float MOVEMENT_SPEED = 0.005f;

    private static final int NUM_CHUNKS = 4;

    private Entity[][] terrainEntities;

    private Entity cubeEntity;
    private LightControls lightControls;
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
        String terrainModelID = "terrain";
        Model terrainModel = ModelLoader.loadModel(terrainModelID, "src/resources/models/terrain/terrain.obj", scene.getTextureCache());
        scene.addModel(terrainModel);

        Entity terrainEntity = new Entity("terrainEntity", terrainModelID);
        terrainEntity.setScale(100.0f);
        terrainEntity.updateModelMatrix();
        scene.addEntity(terrainEntity);

        Model cubeModel = ModelLoader.loadModel("cube-model", "src/resources/models/cube/cube.obj",scene.getTextureCache());
        scene.addModel(cubeModel);

        cubeEntity = new Entity("cube-entity", cubeModel.getId());
        cubeEntity.setPosition(0 ,0, -2);
        cubeEntity.updateModelMatrix();
        scene.addEntity(cubeEntity);

        SceneLights sceneLights = new SceneLights();
        sceneLights.getAmbientLight().setIntensity(0.3f);
        scene.setSceneLights(sceneLights);
        sceneLights.getPointLights().add(new PointLight(new Vector3f(1, 1, 1), new Vector3f(0, 0, -1.4f), 1.0f));
        Vector3f coneDir = new Vector3f(0, 0, 1);
        sceneLights.getSpotLights().add(new SpotLight(new PointLight(new Vector3f(1, 1, 1), new Vector3f(0, 0, -1.4f), 0.0f), coneDir, 140f));
        lightControls = new LightControls(scene);
        scene.setGuiInstance(lightControls);

        Skybox skybox = new Skybox("src/resources/models/skybox/skybox.obj", scene.getTextureCache());
        skybox.getSkyBoxEntity().setScale(50);
        scene.setSkyBox(skybox);

        scene.setFog(new Fog(true, new Vector3f(0.5f, 0.5f, 0.5f), 0.95f));

        scene.getCamera().moveUp(0.1f);

        //updateTerrain(scene);
    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed) {
        if(inputConsumed){
            return;
        }

        float move = diffTimeMillis * MOVEMENT_SPEED;
        Camera camera = scene.getCamera();
        if(window.isKeyPressed(GLFW_KEY_W)){
            camera.moveForward(move);
        } else if (window.isKeyPressed(GLFW_KEY_S)){
            camera.moveBackwards(move);
        }
        if (window.isKeyPressed(GLFW_KEY_D)){
            camera.moveRight(move);
        }else if(window.isKeyPressed(GLFW_KEY_A)){
            camera.moveLeft(move);
        }
        if (window.isKeyPressed(GLFW_KEY_UP)){
            camera.moveUp(move);
        }else if (window.isKeyPressed(GLFW_KEY_DOWN)){
            camera.moveDown(move);
        }

        MouseInput mouseInput = window.getMouseInput();
        if(mouseInput.isRightButtonPressed()){
            Vector2f dispVec = mouseInput.getDispVec();
            camera.addRotation((float) Math.toRadians(-dispVec.x * MOUSE_SENSITIVITY), (float) Math.toRadians(-dispVec.y * MOUSE_SENSITIVITY));
        }

    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        rotation += 1.5;
        if(rotation >= 360){
            rotation = 0;
        }
        cubeEntity.setRotation(1, 1, 1, (float) Math.toRadians(rotation));
        cubeEntity.updateModelMatrix();
        //updateTerrain(scene);
    }

    /*public void updateTerrain(Scene scene){
        int cellSize = 10;
        Camera camera = scene.getCamera();
        Vector3f cameraPos = camera.getPosition();
        int cellCol = (int) (cameraPos.x / cellSize);
        int cellRow = (int) (cameraPos.z / cellSize);

        int numRows = NUM_CHUNKS * 2 + 1;
        int numCols = numRows;
        int zOffset = -NUM_CHUNKS;
        float scale = cellSize / 2.0f;
        for(int j = 0; j < numRows; j++){
            int xOffset = -NUM_CHUNKS;
            for(int i = 0; i < numCols; i++){
                Entity entity = terrainEntities[j][i];
                entity.setScale(scale);
                entity.setPosition((cellCol + xOffset) * 2.0f, 0, (cellRow + zOffset) * 2.0f);
                entity.getModelMatrix().identity().scale(scale).translate(entity.getPosition());
                xOffset++;
            }
            zOffset++;
        }
    }*/

    @Override
    public void drawGui(){
        ImGui.newFrame();
        ImGui.setNextWindowPos(0, 0, ImGuiCond.Always);
        ImGui.showDemoWindow();
        ImGui.endFrame();
        ImGui.render();
    }

    @Override
    public boolean handleGuiInput(Scene scene, Window window){
        ImGuiIO imGuiIO = ImGui.getIO();
        MouseInput mouseInput = window.getMouseInput();
        Vector2f mousePos = mouseInput.getCurrentPos();
        imGuiIO.setMousePos(mousePos.x, mousePos.y);
        imGuiIO.setMouseDown(0, mouseInput.isLeftButtonPressed());
        imGuiIO.setMouseDown(1, mouseInput.isRightButtonPressed());

        return imGuiIO.getWantCaptureMouse() || imGuiIO.getWantCaptureKeyboard();
    }

}
