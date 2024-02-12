package engine.graph;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.*;

import static org.lwjgl.opengl.GL30.*;

public class Mesh {

    private int numVertices;
    private int vaoID;
    private List<Integer> vboIDList;

    public Mesh(float[] positions,float[] normals, float[] tangents, float[] bitangents, float[]  textCoords,int[] indices){
        try(MemoryStack stack = MemoryStack.stackPush()){
            numVertices = indices.length;
            vboIDList = new ArrayList<>();

            vaoID = glGenVertexArrays();
            glBindVertexArray(vaoID);
            //positions VBO
            int vboID  =glGenBuffers();
            vboIDList.add(vboID);
            FloatBuffer positionsBuffer = stack.callocFloat(positions.length);
            positionsBuffer.put(0, positions);
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW);
            glEnableVertexAttribArray(0);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            //Normals VBO
            vboID = glGenBuffers();
            vboIDList.add(vboID);
            FloatBuffer normalsBuffer = stack.callocFloat(normals.length);
            normalsBuffer.put(0, normals);
            glBindBuffer(GL_ARRAY_BUFFER,vboID);
            glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
            glEnableVertexAttribArray(1);
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

            //tangents VBO
            vboID = glGenBuffers();
            vboIDList.add(vboID);
            FloatBuffer tangentsBuffer = stack.callocFloat(tangents.length);
            tangentsBuffer.put(0, tangents);
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferData(GL_ARRAY_BUFFER, tangentsBuffer, GL_STATIC_DRAW);
            glEnableVertexAttribArray(2);
            glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);

            //bitangents VBO
            vboID = glGenBuffers();
            vboIDList.add(vboID);
            FloatBuffer bitangentsBuffer = stack.callocFloat(bitangents.length);
            bitangentsBuffer.put(0, bitangents);
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferData(GL_ARRAY_BUFFER, bitangentsBuffer, GL_STATIC_DRAW);
            glEnableVertexAttribArray(3);
            glVertexAttribPointer(3, 3, GL_FLOAT, false, 0, 0);

            //texture coordinates VBO
            vboID = glGenBuffers();
            vboIDList.add(vboID);
            FloatBuffer textCoordsBuffer = stack.callocFloat(textCoords.length);
            textCoordsBuffer.put(0, textCoords);
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
            glEnableVertexAttribArray(2);
            glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);

            //Index VBO
            vboID = glGenBuffers();
            vboIDList.add(vboID);
            IntBuffer indicesBuffer = stack.callocInt(indices.length);
            indicesBuffer.put(0, indices);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        }
    }

    public void cleanup(){
        vboIDList.forEach(GL30::glDeleteBuffers);
        glDeleteVertexArrays(vaoID);
    }

    public int getNumVertices(){
        return numVertices;
    }

    public final int getVaoID(){
        return vaoID;
    }
}
