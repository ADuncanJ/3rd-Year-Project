package engine.graph;

import org.joml.Matrix4f;
import engine.Scene.*;

import java.util.*;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
/*Derived from:
 * A. H. Benjarano, “3D Game Development with LWJGL 3” https://ahbejarano.gitbook.io/lwjglgamedev*/
public class SkyBoxRender {

    private ShaderProgram shaderProgram;
    private UniformsMap uniformsMap;
    private Matrix4f viewMatrix;

    public SkyBoxRender(){
        List<ShaderProgram.ShaderModuleData> shaderModuleDataList = new ArrayList<>();
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("src/resources/shaders/skybox.vert", GL_VERTEX_SHADER));
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("src/resources/shaders/skybox.frag", GL_FRAGMENT_SHADER));
        shaderProgram = new ShaderProgram(shaderModuleDataList);
        viewMatrix = new Matrix4f();
        createUniforms();
    }

    public void cleanup(){
        shaderProgram.cleanup();
    }

    private void createUniforms(){
        uniformsMap = new UniformsMap(shaderProgram.getProgramID());
        uniformsMap.createUniform("projectionMatrix");
        uniformsMap.createUniform("viewMatrix");
        uniformsMap.createUniform("modelMatrix");
        uniformsMap.createUniform("diffuse");
        uniformsMap.createUniform("txtSampler");
        uniformsMap.createUniform("hasTexture");
    }

    public void render(Scene scene){
        Skybox skyBox = scene.getSkyBox();
        if(skyBox == null){
            return;
        }

        shaderProgram.bind();

        uniformsMap.setUniform("projectionMatrix", scene.getProjection().getProjMatrix());
        viewMatrix.set(scene.getCamera().getViewMatrix());
        viewMatrix.m30(0);
        viewMatrix.m31(0);
        viewMatrix.m32(0);
        uniformsMap.setUniform("viewMatrix", viewMatrix);
        uniformsMap.setUniform("txtSampler", 0);

        Model skyBoxModel = skyBox.getSkyBoxModel();
        Entity skyBoxEntity = skyBox.getSkyBoxEntity();
        TextureCache textureCache = scene.getTextureCache();
        for(Material material : skyBoxModel.getMaterialList()){
            Texture texture = textureCache.getTexture(material.getTexturePath());
            glActiveTexture(GL_TEXTURE0);
            texture.bind();

            uniformsMap.setUniform("diffuse", material.getDiffuseColor());
            uniformsMap.setUniform("hasTexture", texture.getTexturePath().equals(TextureCache.DEFAULT_TEXTURE) ? 0 : 1);

            for(Mesh mesh : material.getMeshList()){
                glBindVertexArray(mesh.getVaoID());

                uniformsMap.setUniform("modelMatrix", skyBoxEntity.getModelMatrix());
                glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
            }
        }

        glBindVertexArray(0);

        shaderProgram.unbind();
    }

}
