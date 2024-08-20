package engine.graph;

import org.joml.Vector4f;
import org.lwjgl.util.freetype.FreeType;

import java.util.*;
/*Derived from:
 * A. H. Benjarano, “3D Game Development with LWJGL 3” https://ahbejarano.gitbook.io/lwjglgamedev*/
public class Material {

    public static final Vector4f DEFAULT_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);

    private List<Mesh> meshList;
    private String texturePath;
    private Vector4f diffuseColor;
    private Vector4f ambientColor;
    private Vector4f specularColor;
    private float reflectance;
    private  String normalMapPath;

    public Material(){
        diffuseColor = DEFAULT_COLOR;
        ambientColor = DEFAULT_COLOR;
        specularColor = DEFAULT_COLOR;
        meshList = new ArrayList<>();
    }

    public void cleanup(){
        meshList.forEach(Mesh::cleanup);
    }

    public List<Mesh> getMeshList(){
        return meshList;
    }

    public String getTexturePath(){
        return texturePath;
    }

    public Vector4f getDiffuseColor(){
        return diffuseColor;
    }

    public Vector4f getAmbientColor(){return ambientColor;}

    public Vector4f getSpecularColor(){return specularColor;}

    public float getReflectance() {
        return reflectance;
    }

    public String getNormalMapPath(){
        return  normalMapPath;
    }

    public void setTexturePath(String texturePath){
        this.texturePath = texturePath;
    }

    public void setDiffuseColor(Vector4f diffuseColor){
        this.diffuseColor = diffuseColor;
    }

    public void setAmbientColor(Vector4f ambientColor){
        this.ambientColor = ambientColor;
    }

    public void setSpecularColor(Vector4f specularColor){
        this.specularColor = specularColor;
    }

    public void setReflectance(float reflectance){
        this.reflectance = reflectance;
    }

    public  void setNormalMapPath(String normalMapPath) {
        this.normalMapPath = normalMapPath;
    }
}
