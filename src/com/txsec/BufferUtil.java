package com.txsec;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Created by TxSec on 27/02/2017.
 */
public class BufferUtil {


    public static FloatBuffer createBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

}
