package com.txsec;

import com.txsec.model.Model;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import javax.swing.text.AbstractDocument;

public class Main {




    private void start() {
        try {
            ContextAttribs attribs = new ContextAttribs(1,2);
            Display.setDisplayMode(new DisplayMode(800,600));
            Display.create();
            Display.setTitle("OpenGL Learning");

            GL11.glViewport(0, 0, 800, 600);
        } catch (LWJGLException e ) {
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
                        0.5f, 0.5f, 0f,
                                     };

        int[] indices = {0,1,2,2,3,0};

        Model model = new Model(data,indices);

        while (!Display.isCloseRequested()) {
            // render OpenGL here
            GL11.glClearColor(0,0,0,0);
            model.render(); //Render our model.
            Display.update();
            Display.sync(60);
        }
        model.cleanUp();
        Display.destroy();
    }





    /**
     * The entry point of the application
     * @param argv Arguments.
     */
    public static void main(String[] argv) {
        new Main().start();
    }
}
