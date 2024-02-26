package game;

import engine.Scene.lights.*;
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
    private AnimationData animationData;
    private  float lightAngle;

    private static final int NUM_CHUNKS = 4;

    private Entity[][] terrainEntities;

    private Entity cubeEntity;
    private LightControls lightControls;
    private float rotation;

    public static void main(String[] args) {
        Main main = new Main();
        Window.WindowOptions opts = new Window.WindowOptions();
        opts.antiAliasing = true;
        Engine gameEng = new Engine("", new Window.WindowOptions(), main);
        gameEng.start();
    }

    @Override
    public void cleanup(){

    }

    @Override
    public void init(Window window, Scene scene, Render render) {
        String terrainModelID = "terrain";
        Model terrainModel = ModelLoader.loadModel(terrainModelID, "src/resources/models/terrain/terrain.obj", scene.getTextureCache(), false);
        scene.addModel(terrainModel);

        Entity terrainEntity = new Entity("terrainEntity", terrainModelID);
        terrainEntity.setScale(100.0f);
        terrainEntity.updateModelMatrix();
        scene.addEntity(terrainEntity);

        Model cubeModel = ModelLoader.loadModel("cube-model", "src/resources/models/cube/cube.obj",scene.getTextureCache());
        scene.addModel(cubeModel);

        cubeEntity = new Entity("cube-entity", cubeModel.getId());
        cubeEntity.setPosition(0 ,1, -2);
        cubeEntity.updateModelMatrix();
        scene.addEntity(cubeEntity);

        /*String wallNoNormalsModelID = "quad-no-normals-model";
        Model quadModelNoNormals = ModelLoader.loadModel(wallNoNormalsModelID, "src/resources/models/wall/wall_nonormals.obj", scene.getTextureCache(), false);
        scene.addModel(quadModelNoNormals);

        Entity wallLeftEntity = new Entity("wallLeftEntity", wallNoNormalsModelID);
        wallLeftEntity.setPosition(-3f, 0, 0);
        wallLeftEntity.setScale(2.0f);
        wallLeftEntity.updateModelMatrix();
        scene.addEntity(wallLeftEntity);

        String wallModelID = "quad-model";
        Model quadModel = ModelLoader.loadModel(wallModelID, "src/resources/models/wall/wall.obj", scene.getTextureCache(), false);
        scene.addModel(quadModel);

        Entity wallRightEntity = new Entity("wallRightEntity", wallModelID);
        wallRightEntity.setPosition(3.0f, 0, 0);
        wallRightEntity.setScale(2.0f);
        wallRightEntity.updateModelMatrix();
        scene.addEntity(wallRightEntity);*/

        String bobModelID = "bobModel";
        Model bobModel = ModelLoader.loadModel(bobModelID, "src/resources/models/bob/boblamp.md5mesh", scene.getTextureCache(), true);
        scene.addModel(bobModel);
        Entity bobEntity = new Entity("bobEntity", bobModelID);
        bobEntity.setScale(0.05f);
        bobEntity.updateModelMatrix();
        animationData = new AnimationData(bobModel.getAnimationList().get(0));
        bobEntity.setAnimationData(animationData);
        bobEntity.setRotation(0,1,0, (float)Math.toRadians(90));
        scene.addEntity(bobEntity);

        SceneLights sceneLights = new SceneLights();
        AmbientLight ambientLight = sceneLights.getAmbientLight();
        ambientLight.setIntensity(0.5f);
        ambientLight.setColor(0.3f, 0.3f, 0.3f);

        DirLight dirLight = sceneLights.getDirLight();
        dirLight.setPosition(0, 1, 0);
        dirLight.setIntensity(1.0f);
        scene.setSceneLights(sceneLights);
        //sceneLights.getPointLights().add(new PointLight(new Vector3f(1, 1, 1), new Vector3f(0, 0, -1.4f), 1.0f));
        //Vector3f coneDir = new Vector3f(0, 0, 1);
        //sceneLights.getSpotLights().add(new SpotLight(new PointLight(new Vector3f(1, 1, 1), new Vector3f(0, 0, -1.4f), 0.0f), coneDir, 140f));
        //lightControls = new LightControls(scene);
        //scene.setGuiInstance(lightControls);

        Skybox skybox = new Skybox("src/resources/models/skybox/skybox.obj", scene.getTextureCache());
        skybox.getSkyBoxEntity().setScale(100.0f);
        skybox.getSkyBoxEntity().updateModelMatrix();
        scene.setSkyBox(skybox);

        scene.setFog(new Fog(true, new Vector3f(0.5f, 0.5f, 0.5f), 0.02f));

        Camera camera = scene.getCamera();
        camera.setPosition(-1.5f, 3.0f, 4.5f);
        camera.addRotation((float) Math.toRadians(15.0f), (float)Math.toRadians(390.0f));

        lightAngle = 0;

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
        if(window.isKeyPressed(GLFW_KEY_LEFT)){
            lightAngle -= 2.5f;
            if(lightAngle > 90){
                lightAngle =90;
            }
        }else if(window.isKeyPressed(GLFW_KEY_RIGHT)){
            lightAngle += 2.5f;
            if(lightAngle > 90){
                lightAngle = 90;
            }
        }

        MouseInput mouseInput = window.getMouseInput();
        if(mouseInput.isRightButtonPressed()){
            Vector2f dispVec = mouseInput.getDispVec();
            camera.addRotation((float) Math.toRadians(-dispVec.x * MOUSE_SENSITIVITY), (float) Math.toRadians(-dispVec.y * MOUSE_SENSITIVITY));
        }

        SceneLights sceneLights = scene.getSceneLights();
        DirLight dirLight = sceneLights.getDirLight();
        double angRad = Math.toRadians(lightAngle);
        dirLight.getDirection().x = (float) Math.sin(angRad);
        dirLight.getDirection().y = (float) Math.cos(angRad);

    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        animationData.nextFrame();

        //rotation += 1.5;
        /*if(rotation >= 360){
            rotation = 0;
        }
        cubeEntity.setRotation(1, 1, 1, (float) Math.toRadians(rotation));
        cubeEntity.updateModelMatrix();*/
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
