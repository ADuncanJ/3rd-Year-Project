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

import static org.lwjgl.glfw.GLFW.*;

public class Main implements Logic, GUIInstance {

    private static final float MOUSE_SENSITIVITY = 0.1f;
    private static final float MOVEMENT_SPEED = 0.005f;
    private AnimationData animationData;
    private  float lightAngle;

    private static final int NUM_CHUNKS = 4;

    private Entity[][] terrainEntities;

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

        Model leafModel = ModelLoader.loadModel("leaf-model", "src/resources/models/leaf/cube.obj",scene.getTextureCache(), false);
        scene.addModel(leafModel);

        Model brickModel = ModelLoader.loadModel("brick-model", "src/resources/models/house/cube.obj",scene.getTextureCache(), false);
        scene.addModel(brickModel);

        Model logModel = ModelLoader.loadModel("log-model", "src/resources/models/bark/cube.obj",scene.getTextureCache(), false);
        scene.addModel(logModel);

        Model prismModel = ModelLoader.loadModel("prism-model", "src/resources/models/tri_prism/Prism.obj",scene.getTextureCache(), false);
        scene.addModel(prismModel);

        tree(0, 0, -7, scene, logModel, leafModel);
        tree(-15, 0, -5, scene, logModel, leafModel);
        tree(-5, 0, -4, scene, logModel, leafModel);
        tree(-15, 0, -5, scene, logModel, leafModel);
        tree(-12, 0, 56, scene, logModel, leafModel);
        tree(89, 0, -73, scene, logModel, leafModel);
        tree(45, 0, 87, scene, logModel, leafModel);
        tree(32, 0, -4, scene, logModel, leafModel);
        tree(-67, 0, -23, scene, logModel, leafModel);
        tree(91, 0, 68, scene, logModel, leafModel);
        tree(-53, 0, 34, scene, logModel, leafModel);
        tree(17, 0, 95, scene, logModel, leafModel);
        tree(-87, 0, 12, scene, logModel, leafModel);
        tree(-3, 0, 77, scene, logModel, leafModel);
        tree(6, 0, -33, scene, logModel, leafModel);
        tree(-97, 0, -53, scene, logModel, leafModel);
        tree(-29, 0, -98, scene, logModel, leafModel);
        tree(-84, 0, -91, scene, logModel, leafModel);
        tree(-15, 0, 39, scene, logModel, leafModel);
        tree(-20, 0, 53, scene, logModel, leafModel);
        tree(-80, 0, 91, scene, logModel, leafModel);
        tree(73, 0, -33, scene, logModel, leafModel);
        tree(39, 0, -8, scene, logModel, leafModel);
        tree(62, 0, 55, scene, logModel, leafModel);
        tree(9, 0, 72, scene, logModel, leafModel);
        tree(-56, 0, -12, scene, logModel, leafModel);
        tree(-38, 0, 85, scene, logModel, leafModel);
        tree(-100, 0, -41, scene, logModel, leafModel);
        tree(-40, 0, -73, scene, logModel, leafModel);
        tree(-84, 0, -8, scene, logModel, leafModel);
        tree(78, 0, -71, scene, logModel, leafModel);
        tree(-81, 0, -59, scene, logModel, leafModel);
        tree(-46, 0, 39, scene, logModel, leafModel);
        tree(38, 0, -32, scene, logModel, leafModel);
        tree(-32, 0, -15, scene, logModel, leafModel);
        tree(36, 0, 65, scene, logModel, leafModel);
        tree(-39, 0, -94, scene, logModel, leafModel);
        tree(-28, 0, -85, scene, logModel, leafModel);
        tree(-98, 0, -13, scene, logModel, leafModel);
        tree(-55, 0, 67, scene, logModel, leafModel);
        tree(-84, 0, 11, scene, logModel, leafModel);
        tree(-72, 0, -43, scene, logModel, leafModel);
        tree(-55, 0, -22, scene, logModel, leafModel);
        tree(-26, 0, -69, scene, logModel, leafModel);
        tree(-18, 0, 54, scene, logModel, leafModel);
        tree(-97, 0, -23, scene, logModel, leafModel);
        tree(-34, 0, 82, scene, logModel, leafModel);
        tree(-49, 0, -63, scene, logModel, leafModel);
        tree(75, 0, 61, scene, logModel, leafModel);
        tree(-94, 0, 57, scene, logModel, leafModel);
        tree(-50, 0, 53, scene, logModel, leafModel);
        tree(-89, 0, 54, scene, logModel, leafModel);
        tree(13, 0, 35, scene, logModel, leafModel);
        tree(32, 0, -55, scene, logModel, leafModel);


        house(0,0,0, scene, brickModel, prismModel);
        house(-8,0,0, scene, brickModel, prismModel);
        house(8,0,0, scene, brickModel, prismModel);
        house(-16,0,0, scene, brickModel, prismModel);
        house(16,0,0, scene, brickModel, prismModel);
        house(0,0,16, scene, brickModel, prismModel);
        house(-8,0,16, scene, brickModel, prismModel);
        house(8,0,16, scene, brickModel, prismModel);
        house(-16,0,16, scene, brickModel, prismModel);
        house(16,0,16, scene, brickModel, prismModel);

        /*Model houseModel = ModelLoader.loadModel("tree-model", "src/resources/models/tree/Lowpoly_tree_sample.obj", scene.getTextureCache());
        scene.addModel(houseModel);

        Entity houseEntity = new Entity("tree-entity", houseModel.getId());
        houseEntity.setPosition(10, 0, -10);
        houseEntity.updateModelMatrix();
        scene.addEntity(houseEntity);*/

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

        /*Derived from:
         * A. H. Benjarano, “3D Game Development with LWJGL 3” https://ahbejarano.gitbook.io/lwjglgamedev*/
        SceneLights sceneLights = new SceneLights();
        AmbientLight ambientLight = sceneLights.getAmbientLight();
        ambientLight.setIntensity(3f);
        ambientLight.setColor(0.3f, 0.3f, 0.3f);

        DirLight dirLight = sceneLights.getDirLight();
        dirLight.setPosition(0, 0.5f, 0.5f);
        dirLight.setIntensity(1.0f);
        scene.setSceneLights(sceneLights);
        sceneLights.getPointLights().add(new PointLight(new Vector3f(1, 1, 1), new Vector3f(0, 0, -1.4f), 1.0f));
        Vector3f coneDir = new Vector3f(0, 0, 1);
        sceneLights.getSpotLights().add(new SpotLight(new PointLight(new Vector3f(1, 1, 1), new Vector3f(0, 0, -1.4f), 0.0f), coneDir, 140f));
        lightControls = new LightControls(scene);
        scene.setGuiInstance(lightControls);

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
    public void house(float x, float y, float z, Scene scene, Model brickModel, Model prismModel){
        Entity brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-5+x ,0.5f+y, -2+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-5+x ,0.5f+y, -3+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-4+x ,0.5f+y, -2+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-2+x ,0.5f+y, -2+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-1+x ,0.5f+y, -2+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-1+x ,0.5f+y, -3+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-1+x ,0.5f+y, -4+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-1+x ,0.5f+y, -5+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-5+x ,0.5f+y, -4+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-5+x ,0.5f+y, -5+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-4+x ,0.5f+y, -5+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-3+x ,0.5f+y, -5+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-2+x ,0.5f+y, -5+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);


        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-5+x ,1.5f+y, -2+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-5+x ,1.5f+y, -3+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-4+x ,1.5f+y, -2+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("cube-entity", brickModel.getId());
        brickEntity.setPosition(-2+x ,1.5f+y, -2+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-1+x ,1.5f+y, -2+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-1+x ,1.5f+y, -3+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-1+x ,1.5f+y, -4+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-1+x ,1.5f+y, -5+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-5+x ,1.5f+y, -4+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-5+x ,1.5f+y, -5+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-4+x ,1.5f+y, -5+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-3+x ,1.5f+y, -5+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-2+x ,1.5f+y, -5+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-5+x ,2.5f+y, -2+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-5+x ,2.5f+y, -3+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-4+x ,2.5f+y, -2+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-2+x ,2.5f+y, -2+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-1+x ,2.5f+y, -2+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("cube-entity", brickModel.getId());
        brickEntity.setPosition(-1+x ,2.5f+y, -3+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-1+x ,2.5f+y, -4+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-1+x ,2.5f+y, -5+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-5+x ,2.5f+y, -4+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-5+x ,2.5f+y, -5+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-4+x ,2.5f+y, -5+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-3+x ,2.5f+y, -5+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("brick-entity", brickModel.getId());
        brickEntity.setPosition(-2+x ,2.5f+y, -5+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        brickEntity = new Entity("cube-entity", brickModel.getId());
        brickEntity.setPosition(-3+x ,2.5f+y, -2+z);
        brickEntity.updateModelMatrix();
        scene.addEntity(brickEntity);

        Entity prismEntity = new Entity("prism-entity", prismModel.getId());
        prismEntity.setPosition(-3+x, 3+y, -3.5f+z);
        prismEntity.setScale(0.6f);
        prismEntity.setRotation(1,0,0,(float)Math.toRadians(-90));
        prismEntity.updateModelMatrix();
        scene.addEntity(prismEntity);
    }

    public void tree(float x, float y, float z, Scene scene,Model logModel, Model leafModel){
        Entity logEntity = new Entity("log-entity", logModel.getId());
        logEntity.setPosition(5+x ,0.5f+y, -7+z);
        logEntity.updateModelMatrix();
        scene.addEntity(logEntity);

        logEntity = new Entity("log-entity", logModel.getId());
        logEntity.setPosition(5+x ,1.5f+y, -7+z);
        logEntity.updateModelMatrix();
        scene.addEntity(logEntity);

        logEntity = new Entity("log-entity", logModel.getId());
        logEntity.setPosition(5+x ,2.5f+y, -7+z);
        logEntity.updateModelMatrix();
        scene.addEntity(logEntity);

        logEntity = new Entity("log-entity", logModel.getId());
        logEntity.setPosition(5+x ,3.5f+y, -7+z);
        logEntity.updateModelMatrix();
        scene.addEntity(logEntity);

        logEntity = new Entity("log-entity", logModel.getId());
        logEntity.setPosition(5+x ,4.5f+y, -7+z);
        logEntity.updateModelMatrix();
        scene.addEntity(logEntity);

        Entity leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(6+x ,4.5f+y, -7+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(4+x ,4.5f+y, -7+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,4.5f+y, -6+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,4.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,4.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(4+x ,4.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(6+x ,4.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(4+x ,4.5f+y, -6+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(6+x ,4.5f+y, -6+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(6+x ,4.5f+y, -5+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,4.5f+y, -5+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(4+x ,4.5f+y, -5+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(3+x ,4.5f+y, -5+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(3+x ,4.5f+y, -6+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(3+x ,4.5f+y, -7+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(3+x ,4.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(3+x ,4.5f+y, -9+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(4+x ,4.5f+y, -9+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,4.5f+y, -9+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(6+x ,4.5f+y, -9+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(7+x ,4.5f+y, -9+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(7+x ,4.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(7+x ,4.5f+y, -7+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(7+x ,4.5f+y, -6+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(7+x ,4.5f+y, -5+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,5.5f+y, -7+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(6+x ,5.5f+y, -7+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(4+x ,5.5f+y, -7+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,5.5f+y, -6+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,5.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,5.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(4+x ,5.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(6+x ,5.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(4+x ,5.5f+y, -6+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(6+x ,5.5f+y, -6+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(6+x ,5.5f+y, -5+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,5.5f+y, -5+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(4+x ,5.5f+y, -5+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(3+x ,5.5f+y, -5+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(3+x ,5.5f+y, -6+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(3+x ,5.5f+y, -7+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(3+x ,5.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(3+x ,5.5f+y, -9+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(4+x ,5.5f+y, -9+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,5.5f+y, -9+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(6+x ,5.5f+y, -9+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(7+x ,5.5f+y, -9+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(7+x ,5.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(7+x ,5.5f+y, -7+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(7+x ,5.5f+y, -6+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(7+x ,5.5f+y, -5+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);


        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,6.5f+y, -7+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(6+x ,6.5f+y, -7+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(4+x ,6.5f+y, -7+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,6.5f+y, -6+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,6.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,6.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(6+x ,6.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(6+x ,6.5f+y, -6+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(4+x ,6.5f+y, -6+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(4+x ,6.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,7.5f+y, -7+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(6+x ,7.5f+y, -7+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(4+x ,7.5f+y, -7+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,7.5f+y, -6+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,7.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);

        leafEntity = new Entity("cube-entity", leafModel.getId());
        leafEntity.setPosition(5+x ,7.5f+y, -8+z);
        leafEntity.updateModelMatrix();
        scene.addEntity(leafEntity);
    }


    /*Derived from:
     * A. H. Benjarano, “3D Game Development with LWJGL 3” https://ahbejarano.gitbook.io/lwjglgamedev*/
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
    /*Derived from:
     * A. H. Benjarano, “3D Game Development with LWJGL 3” https://ahbejarano.gitbook.io/lwjglgamedev*/
    @Override
    public void drawGui(){
        ImGui.newFrame();
        ImGui.setNextWindowPos(0, 0, ImGuiCond.Always);
        ImGui.showDemoWindow();
        ImGui.endFrame();
        ImGui.render();
    }
    /*Derived from:
     * A. H. Benjarano, “3D Game Development with LWJGL 3” https://ahbejarano.gitbook.io/lwjglgamedev*/
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
