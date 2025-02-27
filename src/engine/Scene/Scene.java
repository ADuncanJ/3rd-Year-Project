package engine.Scene;

import engine.GUIInstance;
import engine.graph.Model;
import engine.graph.TextureCache;
import engine.Scene.lights.SceneLights;

import java.util.*;
/*Derived from:
 * A. H. Benjarano, “3D Game Development with LWJGL 3” https://ahbejarano.gitbook.io/lwjglgamedev*/
public class Scene {

    private Map<String, Model> modelMap;
    private Projection projection;
    private TextureCache textureCache;
    private Camera camera;
    private GUIInstance guiInstance;
    private SceneLights sceneLights;
    private Skybox skyBox;
    private Fog fog;

    public Scene(int width, int height){
        modelMap =new HashMap<>();
        projection = new Projection(width, height);
        textureCache = new TextureCache();
        camera = new Camera();
        fog = new Fog();
    }

    public void addEntity(Entity entity){
        String modelID = entity.getModelID();
        Model model = modelMap.get(modelID);
        if(model == null){
            throw new RuntimeException("Could not find model [" + modelID + "]");
        }
        model.getEntityList().add(entity);

    }

    public void addModel(Model model){
        modelMap.put(model.getId(), model);
    }

    public void cleanup(){
        modelMap.values().forEach(Model::cleanup);
    }

    public Map<String, Model> getModelMap(){
        return modelMap;
    }

    public Projection getProjection(){
        return projection;
    }

    public TextureCache getTextureCache(){return textureCache;}

    public Camera getCamera(){return  camera;}

    public void resize(int width, int height){
        projection.updateProjMatirx(width, height);
    }

    public GUIInstance getGuiInstance(){
        return guiInstance;
    }

    public void setGuiInstance(GUIInstance guiInstance){
        this.guiInstance = guiInstance;
    }

    public SceneLights getSceneLights(){
        return sceneLights;
    }

    public void setSceneLights(SceneLights sceneLights){
        this.sceneLights = sceneLights;
    }

    public Skybox getSkyBox(){
        return skyBox;
    }

    public void setSkyBox(Skybox skyBox){
        this.skyBox = skyBox;
    }

    public Fog getFog(){
        return fog;
    }

    public void setFog(Fog fog){
        this.fog = fog;
    }
}
