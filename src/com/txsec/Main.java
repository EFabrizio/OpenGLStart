package com.txsec;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private List<Integer> vao = new ArrayList<Integer>();
    private List<Integer> vbo = new ArrayList<Integer>();
    private int vaoID;
    private int vboID;


    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(800,600));
            Display.create();
            Display.setTitle("OpenGL Learning");
            GL11.glViewport(0, 0, 800, 600);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // init OpenGL here


        //CREATE THE VAO
        float[] data =
                {
                        // Left bottom triangle
                        -0.5f, 0.5f, 0f,
                        -0.5f, -0.5f, 0f,
                        0.5f, -0.5f, 0f,
                        // Right top triangle
                        0.5f, -0.5f, 0f,
                        0.5f, 0.5f, 0f,
                        -0.5f, 0.5f, 0f
                };

        int vao = createVAO();
        createVBO(data);
        destroyVAO();

        while (!Display.isCloseRequested()) {
            // render OpenGL here
            GL11.glClearColor(0,0,0,0);
            GL30.glBindVertexArray(vaoID);//We activate the VAO
            GL20.glEnableVertexAttribArray(0); //Enable the current attribute of our enabled VAO

            GL11.glDrawArrays(GL11.GL_TRIANGLES,0,6);
            GL20.glDisableVertexAttribArray(0);//Disable the current attribute of our enabled VAO
            GL30.glBindVertexArray(0); // We disable the VAO

            Display.update();
            Display.sync(60);
        }
        cleanUp();
        Display.destroy();
    }

    private void cleanUp() {
        for (int vao:vao)
        GL30.glDeleteVertexArrays(vao);
        for(int vbo:vbo)
        GL15.glDeleteBuffers(vbo);
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

    private void createVBO(float[] positions){
        vboID = GL15.glGenBuffers(); //CREATE THE BUFFER, WE USE IT TO STORE DATA INTO SOMETHING
        vbo.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vboID); // WE MAKE THE BUFFER ACTIVE. THE TARGET GL_ARRAY_BUFFER ITS USED FOR VERTEX SHADER.
        FloatBuffer buffer = createBuffer(positions);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,buffer,GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0,3, GL11.GL_FLOAT,false,0,0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);//Disable the Buffer... Destroy it.
    }

    private void destroyVAO(){
        GL30.glBindVertexArray(0);
    }


    private FloatBuffer createBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * The entry point of the application
     * @param argv Arguments.
     */
    public static void main(String[] argv) {
        new Main().start();
    }
}
