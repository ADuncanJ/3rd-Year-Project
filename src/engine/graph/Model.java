package engine.graph;

import engine.Scene.Entity;

import java.util.*;

public class Model {

    private final String id;
    private List<Entity> entityList;
    private List<Material> materialList;

    public Model(String id, List<Material> materilaList){
        this.id = id;
        this.materialList = materilaList;
        entityList = new ArrayList<>();
    }

    public void cleanup(){
        materialList.forEach(Material::cleanup);
    }

    public List<Entity> getEntityList(){
        return entityList;
    }

    public String getId(){
        return id;
    }

    public List<Material> getMaterialList(){
        return materialList;
    }
}
