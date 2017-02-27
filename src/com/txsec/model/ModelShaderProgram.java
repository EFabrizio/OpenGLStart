package com.txsec.model;

import com.txsec.shaders.ShaderProgram;
import org.lwjgl.opengl.GL20;

/**
 * Created by TxSec on 27/02/2017.
 */
public class ModelShaderProgram extends ShaderProgram {

    public ModelShaderProgram(String vertexShader, String fragmentShader) {
        super(vertexShader, fragmentShader);
    }

    @Override
    protected void bindVertexAttributes() {
        bindAttributeLocation(0,"position");
    }
}
