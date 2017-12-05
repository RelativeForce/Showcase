package snake3D.graphics;

import java.io.File;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * A Window that will display the {@link Game}.
 * 
 * @author Joshua_Eddy
 *
 */
public enum Window {

	TINY(320, 240), SMALL(512, 384), MEDIUM(800, 600), LARGE(1366, 768);

	/**
	 * The {@link DisplayMode} assigned to this {@link Window}.
	 */
	public final DisplayMode displayMode;

	/**
	 * Constructs a new {@link Window}.
	 * 
	 * @param width
	 *            <code>int</code> width of the display.
	 * @param height
	 *            <code>int</code> height of the display.
	 */
	private Window(int width, int height) {

		// Windows colour depth.
		final int windowsColorDepth = 32;

		// Linux colour depth.
		final int linuxColorDepth = 24;

		// Get the colour depth based on OS
		int colorDepth = (File.separatorChar == '/') ? linuxColorDepth : windowsColorDepth;

		// Set the available display modes to empty.
		DisplayMode[] availableModes = new DisplayMode[0];

		// The temporary holder for this windows display mode.
		DisplayMode tempMode = null;

		try {

			// Get the available display modes.
			availableModes = Display.getAvailableDisplayModes();

			// iterate through all the display modes
			for (DisplayMode mode : availableModes) {

				// If the specified width and height are the same as the current display mode's.
				if (mode.getWidth() == width && mode.getHeight() == height) {

					// If the Colour depth is the same as the specified colour depth set this
					// display mode to the temp display mode.
					if (mode.getBitsPerPixel() == colorDepth) {
						tempMode = mode;
						break;
					}
				}

			}
		} catch (Exception e) {

		}

		// If a display mode was found with the correct details set it as this windows
		// display mode.
		if (tempMode != null) {
			displayMode = tempMode;
		} else {
			displayMode = null;
		}
	}

}
