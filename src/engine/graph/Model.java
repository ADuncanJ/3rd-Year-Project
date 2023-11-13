package engine.graph;

import engine.Scene.Entity;

import java.util.*;

public class Model {

    private final String id;
    private List<Entity> entityList;
    private List<Mesh> meshList;

    public Model(String id, List<Mesh> meshList){
        this.id = id;
        this.meshList = meshList;
        entityList = new ArrayList<>();
    }

    public void cleanup(){
        meshList.forEach(Mesh::cleanup);
    }

    public List<Entity> getEntityList(){
        return entityList;
    }

    public String getId(){
        return id;
    }

    public List<Mesh> getMeshList(){
        return meshList;
    }
}
