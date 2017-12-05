package snake3D.graphics;

import org.lwjgl.opengl.GL11;

/**
 * Encapsulates the drawing of various polygons on the screen.
 * 
 * @author Joshua_Eddy
 * @see GL11
 *
 */
public enum Polygon {

	/**
	 * Encapsulates the behaviour of a cube {@link Polygon}.
	 */
	CUBE {

		/**
		 * Draws a {@link Polygon#CUBE} on screen using a set of specified data objects.
		 * Also checks the validity of the parameter {@link Polygon#CUBE} details.
		 * 
		 * @param details
		 *            An array of Objects that contains the data required to draw the
		 *            {@link Polygon#CUBE}. <br>
		 *            Array details ordered as follows:
		 *            <ol>
		 *            <li>{@link Colour} of the {@link Polygon#CUBE}</li>
		 *            <li><code>float</code> x coordinate of the
		 *            {@link Polygon#CUBE}</li>
		 *            <li><code>float</code> y coordinate of the
		 *            {@link Polygon#CUBE}</li>
		 *            <li><code>float</code> z coordinate of the
		 *            {@link Polygon#CUBE}</li>
		 *            <li><code>float</code> dimension of the {@link Polygon#CUBE}. AKA
		 *            length, width and depth.</li>
		 *            </ol>
		 */
		@Override
		public void draw(Object[] details) {

			// If the details array is not null.
			if (details != null) {

				// If there is the correct number of details.
				if (details.length == 5) {

					try {

						// Parse all the details. If there is an invalid parameter detail. Throw and
						// illegal argument exception.
						Colour colour = (Colour) details[0];
						float x = (float) details[1];
						float y = (float) details[2];
						float z = (float) details[3];
						float dimension = (float) details[4];

						// If the colour is not null draw the cube.
						if (colour != null) {
							drawCube(colour, x, y, z, dimension);
							return;
						}

					} catch (Exception e) {
						// Do nothing as exception will be thrown if this code is reached.
					}

				}

			}

			throw new IllegalArgumentException();
		}

		/**
		 * Draws the {@link Polygon#CUBE} on the screen.
		 * 
		 * @param colour
		 *            {@link Colour} of the {@link Polygon#CUBE}.
		 * @param x
		 *            <code>float</code> x coordinate of the {@link Polygon#CUBE}.
		 * @param y
		 *            <code>float</code> y coordinate of the {@link Polygon#CUBE}.
		 * @param z
		 *            <code>float</code> z coordinate of the {@link Polygon#CUBE}.
		 * @param dimension
		 *            <code>float</code> dimension of the {@link Polygon#CUBE}. AKA
		 *            length, width and depth.</li>
		 */
		private void drawCube(Colour colour, float x, float y, float z, float dimension) {

			// the vertices for the cube (note that all sides have a length of 1)

			// (x,y) ------------------- (upperX,y)
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// (x,upperY) --------- (upperX,upperY)

			// Holds the upper x bound of the polygon.
			float upperX = Float.sum(x, dimension);

			// Holds the upper y bound of the polygon.
			float upperY = Float.sum(y, dimension);

			// Holds the upper z bound of the polygon.
			float upperZ = Float.sum(z, dimension);

			Vertex lowerV1 = new Vertex(upperX, upperY, z);
			Vertex lowerV2 = new Vertex(upperX, y, z);
			Vertex lowerV3 = new Vertex(x, y, z);
			Vertex lowerV4 = new Vertex(x, upperY, z);

			Vertex upperV1 = new Vertex(upperX, upperY, upperZ);
			Vertex upperV2 = new Vertex(upperX, y, upperZ);
			Vertex upperV3 = new Vertex(x, y, upperZ);
			Vertex upperV4 = new Vertex(x, upperY, upperZ);
			
			// Draw the near face
			drawRectangle(colour, lowerV3, lowerV2, lowerV1, lowerV4);

			// Draw the left face
			drawRectangle(colour, lowerV2, upperV2, upperV1, lowerV1);

			// Draw the right face
			drawRectangle(colour, upperV3, lowerV3, lowerV4, upperV4);

			// Draw the top face
			drawRectangle(colour, upperV3, upperV2, lowerV2, lowerV3);

			// Draw the bottom face
			drawRectangle(colour, lowerV4, lowerV1, upperV1, upperV4);

			// Draw the far face
			drawRectangle(colour, upperV2, upperV3, upperV4, upperV1);

		}

	},
	/**
	 * Encapsulates the behaviour of a cuboid {@link Polygon}.
	 */
	CUBOID {

		/**
		 * Draws a {@link Polygon#CUBOID} on screen using a set of specified data
		 * objects. Also checks the validity of the parameter {@link Polygon#CUBOID}
		 * details.
		 * 
		 * @param details
		 *            An array of Objects that contains the data required to draw the
		 *            {@link Polygon#CUBOID}. <br>
		 *            Array details ordered as follows:
		 *            <ol>
		 *            <li>{@link Colour} of the {@link Polygon#CUBOID}</li>
		 *            <li><code>float</code> x coordinate of the
		 *            {@link Polygon#CUBOID}</li>
		 *            <li><code>float</code> y coordinate of the
		 *            {@link Polygon#CUBOID}</li>
		 *            <li><code>float</code> z coordinate of the
		 *            {@link Polygon#CUBOID}</li>
		 *            <li><code>float</code> width of the {@link Polygon#CUBOID}.</li>
		 *            <li><code>float</code> height of the {@link Polygon#CUBOID}.</li>
		 *            <li><code>float</code> depth of the {@link Polygon#CUBOID}.</li>
		 *            </ol>
		 */
		@Override
		public void draw(Object[] details) {

			// If the details array is not null.
			if (details != null) {

				// If there is the correct number of details.
				if (details.length == 7) {

					try {

						// Parse all the details. If there is an invalid parameter detail. Throw and
						// illegal argument exception.
						Colour colour = (Colour) details[0];
						float x = (float) details[1];
						float y = (float) details[2];
						float z = (float) details[3];
						float width = (float) details[4];
						float height = (float) details[5];
						float depth = (float) details[6];

						// If the colour is not null draw the cube.
						if (colour != null) {
							drawCuboid(colour, x, y, z, width, height, depth);
							return;
						}

					} catch (Exception e) {
						// Do nothing as exception will be thrown if this code is reached.
					}

				}

			}

			throw new IllegalArgumentException();

		}

		/**
		 * Draws the {@link Polygon#CUBOID} on the screen.
		 * 
		 * @param colour
		 *            {@link Colour} of the {@link Polygon#CUBOID}.
		 * @param x
		 *            <code>float</code> x coordinate of the {@link Polygon#CUBOID}.
		 * @param y
		 *            <code>float</code> y coordinate of the {@link Polygon#CUBOID}.
		 * @param z
		 *            <code>float</code> z coordinate of the {@link Polygon#CUBOID}.
		 * 
		 * @param width
		 *            <code>float</code> width coordinate of the {@link Polygon#CUBOID}.
		 * @param height
		 *            <code>float</code> height coordinate of the
		 *            {@link Polygon#CUBOID}.
		 * @param depth
		 *            <code>float</code> depth coordinate of the {@link Polygon#CUBOID}.
		 */
		private void drawCuboid(Colour colour, float x, float y, float z, float width, float height, float depth) {

			// (x,y) ------------------- (upperX,y)
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// | | | | | | | | | | | | | | | | | |
			// (x,upperY) --------- (upperX,upperY)

			// Holds the upper x bound of the polygon.
			float upperX = Float.sum(x, width);

			// Holds the upper y bound of the polygon.
			float upperY = Float.sum(y, height);

			// Holds the upper z bound of the polygon.
			float upperZ = Float.sum(z, depth);

			Vertex lowerV1 = new Vertex(upperX, upperY, z);
			Vertex lowerV2 = new Vertex(upperX, y, z);
			Vertex lowerV3 = new Vertex(x, y, z);
			Vertex lowerV4 = new Vertex(x, upperY, z);

			Vertex upperV1 = new Vertex(upperX, upperY, upperZ);
			Vertex upperV2 = new Vertex(upperX, y, upperZ);
			Vertex upperV3 = new Vertex(x, y, upperZ);
			Vertex upperV4 = new Vertex(x, upperY, upperZ);

			// Draw the near face
			drawRectangle(colour, lowerV3, lowerV2, lowerV1, lowerV4);

			// Draw the left face
			drawRectangle(colour, lowerV2, upperV2, upperV1, lowerV1);

			// Draw the right face
			drawRectangle(colour, upperV3, lowerV3, lowerV4, upperV4);

			// Draw the top face
			drawRectangle(colour, upperV3, upperV2, lowerV2, lowerV3);

			// Draw the bottom face
			drawRectangle(colour, lowerV4, lowerV1, upperV1, upperV4);

			// Draw the far face
			drawRectangle(colour, upperV2, upperV3, upperV4, upperV1);

		}
	};

	/**
	 * Draws the {@link Polygon} on the screen.
	 * 
	 * @param details
	 *            The details used to draw the {@link Polygon}.
	 */
	public abstract void draw(Object[] details);

	/**
	 * Draws a rectangle in 3D space using 4 specified {@link Vertex}s and fills it
	 * a specified {@link Colour}. The parameter {@link Vertex}s should be ordered
	 * so that they can be drawn correctly by {@link GL11}.
	 * 
	 * @param colour
	 *            {@link Colour} of the rectangle.
	 * @param v1
	 *            First corner {@link Vertex} of the rectangle.
	 * @param v2
	 *            Second corner {@link Vertex} of the rectangle.
	 * @param v3
	 *            Third corner {@link Vertex} of the rectangle.
	 * @param v4
	 *            Fourth corner {@link Vertex} of the rectangle.
	 */
	protected void drawRectangle(Colour colour, Vertex v1, Vertex v2, Vertex v3, Vertex v4) {

		// Start drawing the rectangle.
		GL11.glBegin(GL11.GL_POLYGON);
		{
			// Swap to designated colour.
			colour.submit();

			v1.submit();
			v2.submit();
			v3.submit();
			v4.submit();
		}
		GL11.glEnd();

	}

}
