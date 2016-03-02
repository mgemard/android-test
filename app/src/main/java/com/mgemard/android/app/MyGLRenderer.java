package com.mgemard.android.app;

import android.opengl.EGLConfig;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.opengles.GL10;


public class MyGLRenderer implements GLSurfaceView.Renderer {

    //private Triangle mTriangle;
    private Square   mSquare;
    private Square   mSquare2;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];
    private float[] mTempMatrix = new float[16];


    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color

        // enable face culling feature
        GLES20.glEnable(GL10.GL_CULL_FACE);
        // specify which faces to not draw
        GLES20.glCullFace(GL10.GL_BACK);





    }

    @Override
    public void onDrawFrame(GL10 unused) {
        float[] scratch = new float[16];


        // Set depth's clear-value to farthest
        GLES20.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
        GLES20.glDepthFunc(GL10.GL_LEQUAL);




        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClearColor(0.3f, 0.5f, 0.5f, 0.1f); // +

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -8, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Create a rotation for the triangle
        // long time = SystemClock.uptimeMillis() % 4000L;
        // float angle = 0.090f * ((int) time);
        if (mRotationX > 0 ) {
            mAngleX += mSpeed/960;
        } else {
            mAngleX -= mSpeed/960;
        }
        if (mRotationY > 0 ) {
            mAngleY += mSpeed/960;
        } else {
            mAngleY -= mSpeed/960;
        }

        mSpeed--;
        if (mSpeed < 0) mSpeed = 0;

        Matrix.setRotateM(mTempMatrix, 0, mAngleX, 0, -1.0f, 0);
        Matrix.setRotateM(mRotationMatrix, 0, mAngleY, 1.0f, 0, 0);
        Matrix.multiplyMM(mRotationMatrix, 0, mTempMatrix, 0, mRotationMatrix, 0);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);


        // Draw square
        mSquare.draw(scratch);



        Matrix.translateM(mRotationMatrix, 0, 1.0f, 0.0f, 0.0f);
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        mSquare2.draw(scratch);



        GLES20.glClearDepthf(1.0f);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, javax.microedition.khronos.egl.EGLConfig eglConfig) {

        // initialize a triangle
        //mTriangle = new Triangle();
        // initialize a square
        mSquare = new Square();
        mSquare2 = new Square();


    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 9);

    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public volatile float mSpeed;
    public float getSpeed() {
        return mSpeed;
    }
    public void setSpeed(float mSpeed) {
        this.mSpeed = mSpeed;
    }


    public volatile float mAngleX;
    public float getAngleX() {
        return mAngleX;
    }
    public void setAngleX(float angleX) {
        mAngleX = angleX;
    }


    public volatile float mAngleY;
    public float getAngleY() {
        return mAngleY;
    }
    public void setAngleY(float angleY) {
        mAngleY = angleY;
    }

    public volatile float mRotationX;
    public float getRotationX() {
        return mRotationX;
    }
    public void setRotationX(float rotationX) {
        mRotationX = rotationX;
    }

    public volatile float mRotationY;
    public float getRotationY() {
        return mRotationY;
    }
    public void setRotationY(float rotationY) {
        mRotationY = rotationY;
    }

}