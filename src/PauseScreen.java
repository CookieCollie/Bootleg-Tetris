import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PauseScreen {
	private static ImageIcon pauseImage;
	static JLabel pauseScreen = new JLabel("", JLabel.CENTER);
	
	public static void drawPauseScreen() {
		pauseScreen.setText("PAUSE");
	}

	public static void removePauseScreen() {
		pauseScreen.setText(null);
	}

}
