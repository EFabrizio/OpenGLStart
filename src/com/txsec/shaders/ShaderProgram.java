package com.txsec.shaders;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.*;

/**
 * Created by TxSec on 25/02/2017.
 */
public abstract class ShaderProgram {

        private int VERTEX_SHADER;
        private int FRAGMENT_SHADER;
        private int programID;

        public ShaderProgram(String vertexShader,String fragmentShader){
            this.programID = GL20.glCreateProgram();
            VERTEX_SHADER = readShaderProgram(vertexShader,GL20.GL_VERTEX_SHADER);
            FRAGMENT_SHADER  = readShaderProgram(fragmentShader,GL20.GL_FRAGMENT_SHADER);
            GL20.glAttachShader(programID,VERTEX_SHADER);
            GL20.glAttachShader(programID,FRAGMENT_SHADER);
            GL20.glLinkProgram(programID);
            GL20.glValidateProgram(programID);
            bindVertexAttributes();
        }

    protected abstract void  bindVertexAttributes();

    protected void bindAttributeLocation(int attributeNumber,String variableName){
        GL20.glBindAttribLocation(programID,attributeNumber,variableName);
    }

    /*
     * Example of how to use uniform variable for the shaders.
     */
    protected  void uniformVariable(){
        int uniformVariable  = GL20.glGetUniformLocation(programID,"Text");
        GL20.glUniform1f(uniformVariable,2.0F);
    }

    public int getProgramID(){
            return programID;
        }

    private int readShaderProgram(String source,int type) {
            String programSource = null;
        try(BufferedReader br = new BufferedReader(new FileReader(source))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            programSource = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int shaderType = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderType,programSource);
        GL20.glCompileShader(shaderType);
        if(GL20.glGetShader(shaderType,GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderType,500));
            System.err.print("Cannot Compile this shader.");
            System.exit(-1);
        }
        return shaderType;
    }

    public void startProgram(){

            GL20.glUseProgram(programID);
    }

    public void stopProgram() {
        GL20.glUseProgram(0);
    }
}
