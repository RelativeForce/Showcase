package snake3D.environment;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import snake3D.graphics.Colour;
import snake3D.graphics.Polygon;

import java.awt.Rectangle;

/**
 * The plane that contains all the references to the objects from the 2D snake
 * {@link Game} which allows the objects to all be rotated through 3D space
 * together.
 * 
 * @author Joshua_Eddy
 *
 */
public final class Plane {

	/**
	 * The amount the {@link Game#rotationSpeed} of the {@link Plane}'s rotation
	 * increases by when the {@link Snake} 'eats' a piece of food. Occurs until the
	 * snake has 'eaten' 10 pieces of food.
	 */
	private static final float ROTATIONAL_SPEED_INCREMENT = 0.01f;

	/**
	 * The amount the {@link Game#rotationSpeed} of the {@link Plane}'s rotation
	 * increases by when the {@link Snake} 'eats' a piece of food. Occurs after the
	 * snake has 'eaten' 10 pieces of food.
	 */
	private static final float MOVEMENT_SPEED_INCREMENT = 0.00002f;

	/**
	 * The maximum value that {@link Plane#getRotationSpeed()} will return before
	 * the {@link Plane} starts to move as well as rotate.
	 */
	public static final float START_MOVING_BOUNDRY = 0.05f;

	/**
	 * The X coordinate of the {@link Plane}.
	 */
	private float x;

	/**
	 * The Y coordinate of the {@link Plane}.
	 */
	private float y;

	/**
	 * The z coordinate of the {@link Plane}.
	 */
	private float z;

	/**
	 * The 1d vector along the z axis from the origin to the {@link Plane}'s
	 * position.
	 */
	private float vectorZ;

	/**
	 * The 1d vector along the y axis from the origin to the {@link Plane}'s
	 * position.
	 */
	private float vectorY;

	/**
	 * The x speed that the plane moves in the {@link Game}.
	 */
	private float zSpeed;

	/**
	 * The y speed that the plane moves in the {@link Game}.
	 */
	private float ySpeed;

	/**
	 * The speed of the {@link Plane}s rotation. The plane initially rotates at 0.01
	 * degrees per refresh.
	 * 
	 * @see Game#angle
	 */
	private double rotationSpeed;

	/**
	 * The {@link Map} that contains the objects from the {@link Game} as the key
	 * and the {@link Colour} of those objects as the value.
	 * 
	 * @see Rectangle
	 * @see Colour
	 * 
	 */
	private Map<Rectangle, Colour> objects;

	/**
	 * The current angle of the {@link Plane}. This angle is in degrees and should
	 * not exceed 360.
	 */
	private double angle;

	/**
	 * Constructs a the instance of the {@link Plane}.
	 */
	public Plane(float x, float y, float z) {

		this.objects = new IdentityHashMap<Rectangle, Colour>();

		// Initialise the coordinates of the plane so that the centre of the arena is in
		// the centre of the screen.
		this.x = x;
		this.y = y;
		this.z = z;
		this.vectorZ = 0f;
		this.vectorY = 0f;
		this.rotationSpeed = 0.01;
		this.zSpeed = 0;
		this.ySpeed = 0;
		this.angle = 0;

	}

	/**
	 * Adds an object and its assigned {@link Colour} to the {@link Plane} so that
	 * they may be drawn when the {@link Plane} is rendered.
	 * 
	 * @param object
	 *            {@link Rectangle} that denotes a 2d object in the {@link Game}.
	 * @param colour
	 *            {@link Colour} assigned to the {@link Rectangle}.
	 * @see Rectangle
	 */
	public void addObject(Rectangle object, Colour colour) {

		// Null parameters are illegal.
		if (object == null || colour == null) {
			throw new IllegalArgumentException("");
		}

		// Add the object to the list of objects.
		objects.put(object, colour);
	}

	/**
	 * Iterates through all the items in {@link Plane#objects} and draws them on the
	 * screen.
	 */
	public void draw() {

		// The view is along the x axis hence movement is z an y.
		GL11.glTranslatef(0, vectorY, vectorZ);
		GL11.glRotated(angle, 1, 1, 0.5);

		// Iterate through all the keys in objects.
		for (Rectangle object : objects.keySet()) {

			// Retrieve the colour assigned to the current colour.
			Colour colour = objects.get(object);

			// Draw that object on the screen.
			drawObject(object, colour);
		}

	}

	/**
	 * Rotates and translates the {@link Plane}.
	 */
	public void move() {

		rotate();
		translate();

	}

	/**
	 * Increases the speed at which the {@link Plane} rotates or translates based on
	 * how fast it is all ready going.
	 */
	public void increaseSpeed() {

		// If the rotation speed is less than the start moving boundary.
		if (rotationSpeed <= START_MOVING_BOUNDRY) {
			increaseRotationSpeed();
		} else {
			increaseTravelSpeed();
		}
	}

	/**
	 * Draws a specified object a specified colour on the screen.
	 * 
	 * @param object
	 *            {@link Rectangle} to be drawn.
	 * @param colour
	 *            {@link Colour} of the {@link Rectangle}.
	 */
	private void drawObject(Rectangle object, Colour colour) {

		// Parameters cannot be null.
		if (object == null || colour == null) {
			throw new IllegalArgumentException();
		}

		// Change the coordinates and size of the object to float numbers.
		float width = Dimension.getAbsoluteValue(object.width);
		float height = Dimension.getAbsoluteValue(object.height);
		float x = Float.sum(Dimension.getAbsoluteValue(object.x), this.x);
		float y = Float.sum(Dimension.getAbsoluteValue(object.y), this.y);

		if (object.width == object.height) {
			Polygon.CUBE.draw(new Object[] { colour, x, y, z, width });
		} else {
			Polygon.CUBOID.draw(new Object[] { colour, x, y, z, width, height, Dimension.GRID.asFloat() });
		}

	}

	/**
	 * Increases the speed at which the plane moves though 3D space.
	 */
	public void increaseTravelSpeed() {

		Random gen = new Random();

		// Make a random increment to the speed of the increment.
		float yIncrease = MOVEMENT_SPEED_INCREMENT * (gen.nextInt(5) + 1);
		float zIncrease = MOVEMENT_SPEED_INCREMENT * (gen.nextInt(5) + 1);

		// If the plane is moving in the positive y direction add the increment,
		// otherwise subtract it.
		if (ySpeed >= 0) {
			ySpeed = Float.sum(ySpeed, yIncrease);
		} else {
			ySpeed = Float.sum(ySpeed, -yIncrease);
		}

		// If the plane is moving in the positive x direction add the increment,
		// otherwise subtract it.
		if (zSpeed >= 0) {
			zSpeed = Float.sum(zSpeed, zIncrease);
		} else {
			zSpeed = Float.sum(zSpeed, -zIncrease);
		}

	}

	/**
	 * Increments the {@link Plane#rotationSpeed} by
	 * {@link Plane#ROTATIONAL_SPEED_INCREMENT}.
	 */
	private void increaseRotationSpeed() {
		rotationSpeed += ROTATIONAL_SPEED_INCREMENT;
	}

	/**
	 * Rotates the {@link Plane}.
	 */
	private void rotate() {
		angle += rotationSpeed;
	}

	/**
	 * Translates the {@link Plane} based on the {@link Plane#zSpeed} and
	 * {@link Plane#ySpeed}.
	 */
	private void translate() {

		// If there z speed is larger than zero.
		if (zSpeed != 0) {

			// Check if it has reached the boundaries of the play space. If so invert the
			// movement direction.
			if (vectorZ >= 0.25f || vectorZ <= -0.25f) {
				zSpeed *= -1;
			}
			vectorZ = Float.sum(vectorZ, zSpeed);
		}

		// If there y speed is larger than zero.
		if (ySpeed != 0) {

			// Check if it has reached the boundaries of the play space. If so invert the
			// movement direction.
			if (vectorY >= 0.25f || vectorY <= -0.25f) {
				ySpeed *= -1;
			}
			vectorY = Float.sum(vectorY, ySpeed);
		}

	}

}
