package de.ggj14bremen.withoutplan.view;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Square
{
	private float[] color = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
	private FloatBuffer vertexBuffer; // buffer holding the vertices

	private float vertices[] =
	{ 		-1.0f, -1.0f, 0.0f, // V1 - bottom left
			-1.0f, 1.0f, 0.0f, // V2 - top left
			1.0f, -1.0f, 0.0f, // V3 - bottom right
			1.0f, 1.0f, 0.0f // V4 - top right
	};

	public Square()
	{
		// a float has 4 bytes so we allocate for each coordinate 4 bytes
		ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		vertexByteBuffer.order(ByteOrder.nativeOrder());

		// allocates the memory from the byte buffer
		vertexBuffer = vertexByteBuffer.asFloatBuffer();

		// fill the vertexBuffer with the vertices
		vertexBuffer.put(vertices);

		// set the cursor position to the beginning of the buffer
		vertexBuffer.position(0);
	}

	/** The draw method for the square with the GL context */
	public void draw(GL10 gl)
	{
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		// set the colour for the square
		gl.glColor4f(color[0],color[1],color[2],color[3]);

		// Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

		// Draw the vertices as triangle strip
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	public void setColor(float r, float g, float b)
	{
		this.color[0] = r;
		this.color[1] = g;
		this.color[2] = b;
	}
	public void setColor(float r, float g, float b, float alpha)
	{
		this.color[0] = r;
		this.color[1] = g;
		this.color[2] = b;
		this.color[3] = alpha;
	}

	public void setColor(float[] ccolor)
	{
		this.color[0] = ccolor[0];
		this.color[1] = ccolor[1];
		this.color[2] = ccolor[2];
	}
	public void setAlpha(float alpha)
	{
		this.color[3] = alpha;
	}
}
