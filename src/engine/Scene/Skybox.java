package engine.Scene;

import engine.graph.*;

public class Skybox {

    private Entity skyBoxEntity;
    private Model skyBoxModel;

    public Skybox(String skyBoxModelPath, TextureCache textureCache){
        skyBoxModel = ModelLoader.loadModel("skybox-model", skyBoxModelPath, textureCache, false);
        skyBoxEntity = new Entity("skyBoxEnitiy-entity", skyBoxModel.getId());
    }

    public Entity getSkyBoxEntity(){
        return skyBoxEntity;
    }

    public Model getSkyBoxModel(){
        return skyBoxModel;
    }
}
