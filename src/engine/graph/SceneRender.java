package engine.graph;

import engine.Scene.Entity;
import engine.Scene.Scene;
import java.util.*;

import static org.lwjgl.opengl.GL30.*;

public class SceneRender {

    private ShaderProgram shaderProgram;
    private UniformsMap uniformsMap;

    public SceneRender(){
        List<ShaderProgram.ShaderModuleData> shaderModuleDataList = new ArrayList<>();
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("src/resources/shaders/scene.vert", GL_VERTEX_SHADER));
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("src/resources/shaders/scene.frag", GL_FRAGMENT_SHADER));
        shaderProgram = new ShaderProgram(shaderModuleDataList);
        createUniforms();
    }

    public void cleanup(){
        shaderProgram.cleanup();
    }

    private void createUniforms(){
        uniformsMap = new UniformsMap(shaderProgram.getProgramID());
        uniformsMap.createUniform("projectionMatrix");
        uniformsMap.createUniform("modelMatrix");
        uniformsMap.createUniform("viewMatrix");
        uniformsMap.createUniform("txtSampler");
        uniformsMap.createUniform("material.diffuse");
    }

    public void render(Scene scene){
        shaderProgram.bind();

        uniformsMap.setUniform("projectionMatrix", scene.getProjection().getProjMatrix());
        uniformsMap.setUniform("viewMatrix", scene.getCamera().getViewMatrix());

        uniformsMap.setUniform("txtSampler", 0);

        Collection<Model> models = scene.getModelMap().values();
        TextureCache textureCache = scene.getTextureCache();
        for(Model model : models) {
            List<Entity> entities =model.getEntityList();

            for(Material material : model.getMaterialList()){
                uniformsMap.setUniform("material.diffuse", material.getDiffuseColor());
                Texture texture = textureCache.getTexture(material.getTexturePath());
                glActiveTexture(GL_TEXTURE0);
                texture.bind();

                for(Mesh mesh : material.getMeshList()){
                    glBindVertexArray(mesh.getVaoID());
                    for (Entity entity : entities) {
                        uniformsMap.setUniform("modelMatrix", entity.getModelMatrix());
                        glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
                    }
                }
            }
        }
        glBindVertexArray(0);

        shaderProgram.unbind();
    }
}

