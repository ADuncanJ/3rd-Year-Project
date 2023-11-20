package engine.Scene;

import engine.graph.Model;
import engine.graph.TextureCache;

import java.util.*;

public class Scene {

    private Map<String, Model> modelMap;
    private Projection projection;
    private TextureCache textureCache;

    public Scene(int width, int height){
        modelMap =new HashMap<>();
        projection = new Projection(width, height);
        textureCache = new TextureCache();
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

    public void resize(int width, int height){
        projection.updateProjMatirx(width, height);
    }
}
