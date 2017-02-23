package com.txsec;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;

public class Main {

    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(800,600));
            Display.create();
            Display.setTitle("OpenGL Learning");
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // init OpenGL here

        byte[] data = new byte[1024];

        //CREATE THE VAO
        int vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);


        int vbo = GL15.glGenBuffers();//BUFFER
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, ByteBuffer.wrap(data),GL15.GL_STATIC_DRAW);
        GL20.glUseProgram(0);

        GL20.glDeleteProgram(0);

        GL15.glDeleteBuffers(0);//BUFFER


        GL30.glDeleteVertexArrays(0);

        GL30.glVertexAttribI2i(0, 1, 2);
        GL20.glEnableVertexAttribArray(0);

        while (!Display.isCloseRequested()) {
            // render OpenGL here
            GL11.glClearColor(0,0,0,0);
            Display.update();
            Display.sync(60);
        }

        Display.destroy();
    }

    public static void main(String[] argv) {
        new Main().start();
    }
}
