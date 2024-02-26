package engine.graph;

import engine.Scene.Entity;
import org.joml.Matrix4f;

import java.util.*;

public class Model {

    private final String id;
    private List<Entity> entityList;
    private List<Material> materialList;
    private List<Animation> animationList;

    public Model(String id, List<Material> materilaList, List<Animation> animationList){
        this.id = id;
        this.materialList = materilaList;
        entityList = new ArrayList<>();
        this.animationList = animationList;
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

    public List<Animation> getAnimationList() {
        return animationList;
    }

    public record AnimatedFrame(Matrix4f[] boneMatrices) {
    }

    public record Animation(String name, double duration, List<AnimatedFrame> frames) {
    }
}
