package engine.graph;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.util.*;
import static org.lwjgl.opengl.GL20.*;

public class UniformsMap {

    private int programID;
    private Map<String, Integer> uniforms;

    public UniformsMap(int programID){
        this.programID = programID;
        uniforms = new HashMap<>();
    }

    public void createUniform(String uniformName){
        int uniformLocation = glGetUniformLocation(programID, uniformName);
        if(uniformLocation < 0){
            throw new RuntimeException("Could not find Uniform [" + uniformName +"] in shader program [" + programID + "]");
        }
        uniforms.put(uniformName,uniformLocation);
    }

    public void setUniform(String uniformName, Matrix4f value){
        try(MemoryStack stack = MemoryStack.stackPush()){
            Integer location = uniforms.get(uniformName);
            if (location == null) {
                throw new RuntimeException("Could not find uniform [" + uniformName + "]");
            }
            glUniformMatrix4fv(location.intValue(), false, value.get(stack.callocFloat(16)));
        }
    }
}
