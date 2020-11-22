import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PauseScreen extends GridBoard {
	private static ImageIcon pauseImage;
	static JLabel pauseScreen = new JLabel("", JLabel.CENTER);
	static PauseScreen p = new PauseScreen();

	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return pauseImage = new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public static void drawPauseScreen() {
		//p.createImageIcon("resources/5.png", "");
		pauseScreen.setText(" ");
	}

	public static void removePauseScreen() {
		pauseScreen.setText(null);
	}

}
