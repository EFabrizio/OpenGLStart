package com.txsec.model;

import com.txsec.BufferUtil;
import com.txsec.shaders.ShaderProgram;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TxSec on 27/02/2017.
 */
public class Model {
    /**
     * List that holds all the VAO Objects.
     */
    private List<Integer> vao = new ArrayList<>();
    /**
     * List that holds all the VBO Objects.
     */
    private List<Integer> vbo = new ArrayList<>();
    private int vaoID;
    private int vboID;
    private float[] positions;
    private ModelShaderProgram modelShader;

    /**
     * Load data by ourselves.
     * @param positions
     */
    public Model(float[] positions){
        this.positions = positions;
        vaoID = createVAO();
        modelShader = new ModelShaderProgram("src/com/txsec/shaders/model/vertexShader.vert",
                "src/com/txsec/shaders/model/fragmentShader.frag");
        storeDataInVAO(positions,0);
        destroyVAO();
    }

    /**
     * Load a .OBJ Model
     * @param objPath The path to the .obj file
     */
    public Model(String objPath){

    }



    /**
     * Creates the VAO Object
     * @return VAOID
     */
    private int createVAO(){
        vaoID = GL30.glGenVertexArrays(); // CREATE THE VAO TO STORE DATA TO VERTEX SHADER.
        GL30.glBindVertexArray(vaoID);// PUT THE VAO ACTIVE So we can put data with the buffers in CreateVBO method.
        vao.add(vaoID);
        return vaoID;
    }

    /**
     * Store data in the VAO using VBO Buffer.
     * @param positions
     * @param attributeNumber
     */
    private void storeDataInVAO(float[] positions,int attributeNumber){
        vboID = GL15.glGenBuffers(); //CREATE THE BUFFER, WE USE IT TO STORE DATA INTO SOMETHING
        vbo.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vboID); // WE MAKE THE BUFFER ACTIVE. THE TARGET GL_ARRAY_BUFFER ITS USED FOR VERTEX SHADER.
        FloatBuffer buffer = BufferUtil.createBuffer(positions);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,buffer,GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber,3, GL11.GL_FLOAT,false,0,0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);//Disable the Buffer... Destroy it.
    }

    /**
     * Render the model into the screen.
     */
    public void render(){
        modelShader.startProgram();
        GL30.glBindVertexArray(vaoID);//We activate the VAO
        GL20.glEnableVertexAttribArray(0); //Enable the current attribute of our enabled VAO
        GL11.glDrawArrays(GL11.GL_TRIANGLES,0,6);
        GL20.glDisableVertexAttribArray(0);//Disable the current attribute of our enabled VAO
        GL30.glBindVertexArray(0); // We disable the VAO
        modelShader.stopProgram();
    }

    /**
     * All cleanup after the model is destroy.
     */
    public void cleanUp() {
        for (int vaoClean:vao)
            GL30.glDeleteVertexArrays(vaoClean);
        for(int vboClean:vbo)
            GL15.glDeleteBuffers(vboClean);
    }

    /**
     * Disable the VAO OBject from OpenGL
     */
    private void destroyVAO(){
        GL30.glBindVertexArray(0);
    }


}
