package com.example.arcamera;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
public class MyRenderer implements Renderer{
	// size of types
	static final int SIZE_OF_FLOAT = 4;
	static final int SIZE_OF_BYTE = 1;
    // for vertex buffer
	static private FloatBuffer vertexBuffer;
	static final int VERTEX_DIMENSION = 3;
	static final int NUM_VERTICES_OR_INDICES = 4;
	// for color buffer
	static private FloatBuffer colorBuffer;
	static final int COLOR_DIMENSION = 4;
	// for index buffer
	static private ByteBuffer  indexBuffer;

	// for current stroke color
	class Color{
		public float r;
		public float g;
		public float b;
		public float a;
		public Color(float red, float green, float blue, float alpha){
			r = red;
			g = green;
			b = blue;
			a = alpha;
		}
	}
	protected Color currentColor = null;

	public MyRenderer() {
		currentColor = new Color(0.0f, 0.0f, 0.0f, 1.0f); // default color
	}
	
	//	サーフェスクリエート
	public void onSurfaceCreated(GL10 gl, EGLConfig config){
	}
	
	//   ドローフレーム
	public void onDrawFrame(GL10 gl){
		gl.glClearColor(0.0f, 0.2f, 0.1f, 1.0f); // default background color
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// View Transform
		gl.glMatrixMode(GL10.GL_MODELVIEW);gl.glLoadIdentity();
		// Modeling Transform
		gl.glMatrixMode(GL10.GL_MODELVIEW);gl.glLoadIdentity();
		
		fillRect(gl, 0.0f, 0.0f, 1.0f, 1.0f);
	}
	
	//		サーフェスチェンジ
	public void onSurfaceChanged(GL10 gl, int width, int height){
		// view port transform
		gl.glViewport(0, 0, width, height);
		// projection transform
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity(); // default
	}
	
	
	void fillRect(GL10 gl,float x, float y, float width, float height){
		{    // allocate vertex buffer
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(NUM_VERTICES_OR_INDICES * VERTEX_DIMENSION * SIZE_OF_FLOAT);
			byteBuffer.order(ByteOrder.nativeOrder());
			vertexBuffer = byteBuffer.asFloatBuffer();
			// set raw data
			vertexBuffer.put(new float[]{
					x + width, y, 0.0f,
					x, y, 0.0f,
					x + width, y + height, 0.0f,
					x, y + height, 0.0f	});
			vertexBuffer.position(0);
		}
		{   // allocate color buffer
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect( NUM_VERTICES_OR_INDICES * COLOR_DIMENSION * SIZE_OF_FLOAT);
			byteBuffer.order(ByteOrder.nativeOrder());
			colorBuffer = byteBuffer.asFloatBuffer();
			// set raw data
			float colorVertices[] = new float[NUM_VERTICES_OR_INDICES *COLOR_DIMENSION];
			int vertexId = 0;
			for( int i = 0; i< NUM_VERTICES_OR_INDICES; i++ ){
				colorVertices[ vertexId++ ] = currentColor.r;
				colorVertices[ vertexId++ ] = currentColor.g;
				colorVertices[ vertexId++ ] = currentColor.b;
				colorVertices[ vertexId++ ] = currentColor.a;
			}
			colorBuffer.put(colorVertices);
			colorBuffer.position(0);
		}
		{   // create index buffer
			indexBuffer = ByteBuffer.allocateDirect(NUM_VERTICES_OR_INDICES * SIZE_OF_BYTE);
			indexBuffer.put(new byte[]{ 0, 1, 2, 3 });
			indexBuffer.position(0);
		}
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(VERTEX_DIMENSION, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glColorPointer(COLOR_DIMENSION, GL10.GL_FLOAT, 0, colorBuffer);
		// draw arrays according to the order of indices;
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, NUM_VERTICES_OR_INDICES,
				GL10.GL_UNSIGNED_BYTE, indexBuffer);
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}