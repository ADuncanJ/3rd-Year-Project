package engine.graph;

import org.lwjgl.opengl.GL30;
import engine.Utils;

import java.util.*;
import static org.lwjgl.opengl.GL30.*;
/*Derived from:
 * A. H. Benjarano, “3D Game Development with LWJGL 3” https://ahbejarano.gitbook.io/lwjglgamedev*/
public class ShaderProgram {

    private final int programID;

    public ShaderProgram(List<ShaderModuleData> shaderModuleDataList){
        programID = glCreateProgram();
        if(programID == 0){
            throw new RuntimeException("Could not create Reference.Shader");
        }

        List<Integer> shaderModules = new ArrayList<>();
        shaderModuleDataList.forEach(s -> shaderModules.add(createShader(Utils.readFile(s.shaderFile),s.shaderType)));

        link(shaderModules);

    }

    public void bind(){
        glUseProgram(programID);
    }

    public void unbind(){
        glUseProgram(0);
    }

    public void cleanup(){
        unbind();
        if(programID != 0){
            glDeleteProgram(programID);
        }
    }

    protected int createShader(String shaderCode, int shaderType){
        int shaderID = glCreateShader(shaderType);
        if(shaderID == 0){
            throw new RuntimeException("Error creating shader. Type: " + shaderType);
        }

        glShaderSource(shaderID, shaderCode);
        glCompileShader(shaderID);

        if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == 0){
            throw new RuntimeException("Error compiling Reference.Shader code: " + glGetShaderInfoLog(shaderID, 1024));
        }

        glAttachShader(programID, shaderID);

        return shaderID;
    }

    public int getProgramID(){
        return programID;
    }

    private void link(List<Integer> shaderModules){
        glLinkProgram(programID);
        if(glGetProgrami(programID, GL_LINK_STATUS) == 0){
            throw new RuntimeException("Error linking Reference.Shader code: " + glGetProgramInfoLog(programID, 1024));
        }

        shaderModules.forEach(s -> glDetachShader(programID, s));
        shaderModules.forEach(GL30::glDeleteShader);
    }

    public void validate(){
        glValidateProgram(programID);
        if(glGetProgrami(programID, GL_VALIDATE_STATUS) == 0){
            throw new RuntimeException("Error Validating Reference.Shader code: " + glGetShaderInfoLog(programID, 1024));
        }
    }

    public record ShaderModuleData(String shaderFile, int shaderType){

    }

}
