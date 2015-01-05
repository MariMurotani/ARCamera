package com.example.main;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.opengles.GL10;

import com.example.test.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.opengl.EGLConfig;
import android.opengl.GLES10;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.GLSurfaceView.Renderer;

public class OverLaySurfaceViewGL extends GLSurfaceView implements Renderer{
	private int iWidth = -1;
	private int iHeight = -1;
    
	private int iPositionTop = 0;   //表示位置(TOP:Y座標)
	private int iPositionLeft = 100;  //表示位置(LEFT:X座標)
	
	private Context mContext;
	private int program;
    private int aPositionHandle;
    FloatBuffer vertexBuffer;
    
    // 頂点シェーダのソースコード。
    private static final String vertexShaderSrc =
        "attribute vec3 aPosition;             \n" +
        "void main() {                         \n" +
        "   gl_Position = vec4(aPosition, 1.0);\n" +
        "}                                     ";
     
    // フラグメントシェーダのソースコード。
    private static final String fragmentShaderSrc =
        "precision mediump float;                   \n" +
        "void main() {                              \n" +
        "   gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);\n" +
        "}                                          ";
     
    // 三角形の頂点座標
    private float[] vertices = new float[] {
        0.0f, 0.8f, 0.0f,
        0.8f, -0.8f, 0.0f,
        -0.8f, -0.8f, 0.0f,
    };

	//2次元スプライト
    SampleSprite droid_img = new SampleSprite();
 
	public OverLaySurfaceViewGL(Context context) {
		super(context);
		this.mContext = context;
		
		try{
			this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
			//this.setEGLConfigChooser(GLSurfaceView.EGLConfigChooser.d)
			this.setEGLContextClientVersion(2);
	        //this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
			this.setRenderer(this);
		}catch(Exception e){
			Log.v(Const.logTag,e.getMessage());
			
		}catch(Error e){
			Log.v(Const.logTag,e.getMessage());
		}
	}

	/**
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Renderer ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 
	 * */
    public void onSurfaceCreated(GL10 gl,javax.microedition.khronos.egl.EGLConfig config){
    	try{/*
	        //背景色をクリア
	        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	        //ディザを無効化
	        gl.glDisable(GL10.GL_DITHER);
	        //深度テストを有効化
	        gl.glEnable(GL10.GL_DEPTH_TEST);
	        //テクスチャ機能ON
	        gl.glEnable(GL10.GL_TEXTURE_2D);
	        //透明可能に
	        gl.glEnable(GL10.GL_ALPHA_TEST);
	        //ブレンド可能に
	        gl.glEnable(GL10.GL_BLEND);
	        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	        
	        droid_img.setTexture(gl,this.mContext.getResources(),R.drawable.ic_launcher);
	        */
    		// プログラムオブジェクトを作成
            program = createProgram(vertexShaderSrc, fragmentShaderSrc);
             
            // プログラムオブジェクトからattribute変数のハンドルを取得
            aPositionHandle = GLES20.glGetAttribLocation(program, "aPosition");
             
            // 頂点データのバッファを作成
            ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length * 4);
            vertexBuffer = buffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
            vertexBuffer.put(vertices).position(0);
             
            // 背景色を設定
            GLES20.glClearColor(0.6f, 0.7f, 0.8f, 1.0f);
    	}catch(Exception e){
    		Log.v(Const.logTag,"onSurfaceCreated" + e.getMessage());
    	}catch(Error e){
    		Log.v(Const.logTag,"onSurfaceCreated" + e.getMessage());
    	}
    }

    public void onDrawFrame(GL10 gl) {
    	// 描画用バッファをクリア
    	try{
	        //gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	        //droid_img.draw(gl);
	        
    		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            GLES20.glUseProgram(program);
             
            GLES20.glVertexAttribPointer(aPositionHandle, 3, GLES20.GL_FLOAT, false, 3 * 4, vertexBuffer);
            GLES20.glEnableVertexAttribArray(aPositionHandle);
             
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
		}catch(Exception e){
			Log.v(Const.logTag,"onSurfaceCreated" + e.getMessage());
		}catch(Error e){
			Log.v(Const.logTag,"onSurfaceCreated" + e.getMessage());
		}
	}
   
    // シェーダオブジェクトを作成してコンパイルする。
    private int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e(Const.logTag, "Could not compile shader " + shaderType + ":");
                Log.e(Const.logTag, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
         
        return shader;
    }
     
    // プログラムオブジェクトを作成してリンクする。
    private int createProgram(String vertexSource, String fragmentSource) {
        // 頂点シェーダをコンパイルする
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }
         
        // フラグメントシェーダをコンパイルする
        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == 0) {
            return 0;
        }
         
        int program = GLES20.glCreateProgram();
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader");
            GLES20.glAttachShader(program, pixelShader);
            checkGlError("glAttachShader");
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e(Const.logTag, "Could not link program: ");
                Log.e(Const.logTag, GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
         
        return program;
    }
     
    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(Const.logTag, op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }
    public void onSurfaceChanged(GL10 gl, int width, int height) {
    	try{
			/*gl.glViewport(0, 0, width, height);
			gl.glMatrixMode(GL10.GL_PROJECTION);//プロジェクションモードに設定
			GLU.gluOrtho2D(gl, 0.0f, width, 0.0f, height);//平行投影用のパラメータをセット
			*/
    		GLES20.glViewport(0, 0, width, height);
		}catch(Exception e){
			Log.v(Const.logTag,"onSurfaceCreated" + e.getMessage());
		}catch(Error e){
			Log.v(Const.logTag,"onSurfaceCreated" + e.getMessage());
		}
    }
    
    
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		this.iWidth = w;
		this.iHeight = h;
	}
}