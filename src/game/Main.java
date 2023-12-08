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
    }
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
