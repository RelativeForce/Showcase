/* Original name: GraphicsLab.java
 * Base functionality for Dan Cornford's Computer Graphics labs
 * 23/08/2007
 * 
 * Adapted for 3d Snake 10/10/2017
 */
package snake3D.graphics;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.lwjgl.input.Keyboard;

/**
 * Originally defined the base class for Dan Cornford's Computer Graphics labs
 * but has bee adapted to be the base class for 3D snake.
 * 
 * <p>
 * Encapsulates basic functionality that should be separated from the teaching
 * goals of the individual labs, including creating and maintaining the
 * application window, the application control loop, the use of axis aligned
 * views, and the loading of textures.
 * 
 * <p>
 * Adapted from Mark Bernard's LWJGL NeHe samples
 * 
 * @author Anthony Jones, Dan Cornford and Joshua Eddy
 */
public abstract class Graphic {

	/**
	 * The {@link Graphic#viewingMode} that puts the game in full screen mode..
	 */
	public static final int FULLSCREEN = 0;

	/**
	 * The {@link Graphic#viewingMode} that puts the game in windowed mode.
	 */
	public static final int WINDOWED = 1;

	/**
	 * Whether the games main loop is running or not.
	 */
	private boolean running = true;

	/**
	 * Holds whether the {@link Graphic#displayMode} will be windowed or full screen
	 */
	private int viewingMode;

	/**
	 * The name of the the sample's application instance and its window title.
	 */
	private String windowTitle;

	/**
	 * The games current display mode, including its resolution and bit depth: it is
	 * protected, which means that programs that extend this base class can have
	 * access
	 */
	protected DisplayMode displayMode;

	/**
	 * The width height ratio.
	 */
	private float hwratio;

	/**
	 * The current camera offset for axis aligned views
	 */
	private float viewingAxisDistance;

	/**
	 * the minimum camera offset for axis aligned views
	 */
	private final float viewingAxisDistanceMin;

	/**
	 * The maximum camera offset for axis aligned views
	 */
	private final float viewingAxisDistanceMax;
	
	/**
	 * the rate at which all animations should take place. This value should be used
	 * to scale animation speeds up or down in order to accommodate a range of
	 * target hardware with differing capabilities
	 */
	private float animationScale;

	/**
	 * Constructs a new {@link Graphic}.
	 */
	protected Graphic(Window window) {
		viewingAxisDistanceMin = 0.1f;
		animationScale = 1.0f;
		viewingAxisDistanceMax = 100.0f;
		viewingAxisDistance = 1.0f;
		viewingMode = WINDOWED;
		displayMode = window.displayMode;
	}

	/**
	 * @return the current camera offset for axis aligned views
	 */
	public final float getViewingAxisDistance() {
		return viewingAxisDistance;
	}

	/**
	 * Set the viewing distance
	 */
	public final void setViewingAxisDistance(float dist) {
		viewingAxisDistance = dist;
	}

	/**
	 * @return the minimum camera offset for axis aligned views
	 */
	public final float getViewingAxisDistanceMin() {
		return viewingAxisDistanceMin;
	}

	/**
	 * @return the maximum camera offset for axis aligned views
	 */
	public final float getViewingAxisDistanceMax() {
		return viewingAxisDistanceMax;
	}

	/**
	 * @return the current scale for animations
	 */
	public final float getAnimationScale() {
		return animationScale;
	}

	/**
	 * allows the animation scale to be changed at runtime
	 * 
	 * @param animationScale
	 *            the new scale for animations
	 */
	public final void setAnimationScale(float animationScale) {
		this.animationScale = animationScale;
	}

	/**
	 * Runs the deriving lab sample by handing runtime control over to GraphicsLab's
	 * application loop
	 * 
	 * @param viewingMode
	 *            How the sample should be viewed - full screen, or in a window
	 * @param windowTitle
	 *            The title for the sample's application instance and window
	 * @param animationScale
	 *            A value that should be used to correctly scale animations across
	 *            different machines. 1.0 corresponds to animations running at full
	 *            speed, 0.5 at half speed, and so on
	 */
	public final void run(int viewingMode, String windowTitle, float animationScale) {
		this.viewingMode = viewingMode;
		this.windowTitle = windowTitle;
		this.animationScale = animationScale;

		try {
			// initialise the application and lab sample
			init();
			while (running) {
				// check for user input
				checkInput();

				// update everything for this frame
				setSceneCamera();
				updateScene();

				// render this frame
				renderFrame();
			}
			// allow the application to perform any last-minute actions
			cleanup();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Creates application resources and sets some initial OpenGL states
	 * 
	 * @throws Exception
	 */
	private void init() throws Exception {
		// create the application window and image library instance to load any textures
		// if needed
		createWindow();
		// IL.create();

		// set OpenGL's clear colour, depth settings, and enable back face culling
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		// initialise the sample's scene
		initScene();
	}

	/**
	 * Creates a windowed or full screen display for this sample
	 * 
	 * @throws Exception
	 */
	private void createWindow() throws Exception {

		Display.setFullscreen(viewingMode == FULLSCREEN);

		// Window height-to-width ratio
		hwratio = (float) displayMode.getWidth() / (float) displayMode.getHeight();
		Display.setDisplayMode(displayMode);
		Display.setTitle(windowTitle);
		Display.create();
	}

	/**
	 * Checks for user input corresponding to terminating the application and
	 * controlling the axis aligned views
	 */
	private void checkInput() {
		// check for input corresponding to terminating the application
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			running = false;
		}
		if (Display.isCloseRequested()) {
			running = false;
		}

		// don't allow the axis viewing distance to fall outside its predefined limits
		viewingAxisDistance = Math.max(viewingAxisDistance, viewingAxisDistanceMin);
		viewingAxisDistance = Math.min(viewingAxisDistance, viewingAxisDistanceMax);

		// check user input corresponding to the sample scene
		checkSceneInput();
	}

	/**
	 * Sets default values for the sample's viewpoint and projection settings. This
	 * behaviour may be overriden by each sample to provide customised camera
	 * control; alternatively, deriving classes may call super.setSceneCamera and
	 * then provide custom camera position and orientation
	 */
	protected void setSceneCamera() {
		// default projection is a perspective projection with a 90 (45*2) degree field
		// of view, width/height
		// aspect ratio and visible range of 0.1 to 100.0 scene units
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(-viewingAxisDistance * hwratio, viewingAxisDistance * hwratio, -viewingAxisDistance,
				viewingAxisDistance, 0.1f, 100.0f);

		// default viewpoint is positioned at the scene origin facing along the negative
		// Z axis
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GLU.gluLookAt(viewingAxisDistance, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

	}

	/**
	 * Renders a single frame to the application's display. If an axis aligned view
	 * is in use, appropriate axis lines will also be drawn
	 */
	private void renderFrame() {
		// clear the previous frame from the display
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		/*
		 * // if an axis aligned view is in use, then draw the axis lines if
		 * (viewingAxis) { drawAxisLines(); }
		 */

		// allow the sample to draw the scene
		// the call is surrounded by a push/pop pair to isolate any transform calls,
		// preventing their effects from 'leaking' into subsequent frames
		GL11.glPushMatrix();
		{
			renderScene();
		}
		GL11.glPopMatrix();

		// inform the display that this frame is ready for rendering to screen
		Display.update();
	}

	/**
	 * Draws the X,Y and Z axis lines for axis aligned views
	 */
	protected void drawAxisLines() {

		Vertex origin = new Vertex(0.0f, 0.0f, 0.0f);
		Vertex xAxisLimit = new Vertex(viewingAxisDistance, 0.0f, 0.0f);
		Vertex yAxisLimit = new Vertex(0.0f, viewingAxisDistance, 0.0f);
		Vertex zAxisLimit = new Vertex(0.0f, 0.0f, viewingAxisDistance);

		// disable lighting and texturing when drawing the axis lines
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT | GL11.GL_TEXTURE_BIT);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_LINES);
		{
			// x axis
			Colour.RED.submit();
			origin.submit();
			xAxisLimit.submit();
			// y axis
			Colour.GREEN.submit();
			origin.submit();
			yAxisLimit.submit();
			// z axis
			Colour.BLUE.submit();
			origin.submit();
			zAxisLimit.submit();
		}
		GL11.glEnd();
		GL11.glPopAttrib();
	}

	/**
	 * Allows the application to perform any last-minute operations before it is
	 * destroyed
	 */
	private void cleanup() {
		Display.destroy();
	}

	/**
	 * Initialises the scene
	 */
	protected abstract void initScene() throws Exception;

	/**
	 * Checks for user input corresponding to the scene
	 */
	protected abstract void checkSceneInput();

	/**
	 * Updates the scene
	 */
	protected abstract void updateScene();

	/**
	 * Renders the scene
	 */
	protected abstract void renderScene();

	/**
	 * Loads a texture from a given image file. Note: when using this function,
	 * prefer to use square textures whose width and height are both a power of 2;
	 * otherwise, your graphics card may not support the texture, or it may affect
	 * the performance of your animated scenes
	 * 
	 * <p>
	 * Texture loading code adapted from LWJGL documentation of the Slick-util
	 * library
	 * </p>
	 * 
	 * @param path
	 *            The absolute or relative path of the image file to load as a
	 *            texture
	 * @return A Texture object
	 */
	protected final Texture loadTexture(String path) throws Exception {
		Texture tex = TextureLoader.getTexture("BMP", ResourceLoader.getResourceAsStream(path), true);
		return tex;
	}

	/**
	 * Loads a texture from a given image file. Note: when using this function,
	 * prefer to use square textures whose width and height are both a power of 2;
	 * otherwise, your graphics card may not support the texture, or it may affect
	 * the performance of your animated scenes
	 * 
	 * <p>
	 * Texture loading code adapted from LWJGL documentation of the Slick-util
	 * library
	 * </p>
	 * 
	 * @param path
	 *            The absolute or relative path of the image file to load as a
	 *            texture
	 * @param imageType
	 *            The type of image, e.g. "BMP", "JPG", "PNG"
	 * @return A Texture object
	 */
	protected final Texture loadTexture(String path, String imageType) throws Exception {
		Texture tex = TextureLoader.getTexture(imageType, ResourceLoader.getResourceAsStream(path), true);
		return tex;
	}

}
