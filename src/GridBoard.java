import java.awt.Graphics;
import javax.swing.*;


public class GridBoard extends JPanel {
	public int COLUMNS = 15, ROWS = 30, BLOCKSIZE = 20;
	public int[][] GRID = new int[COLUMNS][ROWS];
	
	public void paintComponent(Graphics Draw) {
		super.paintComponent(Draw);
		for (int i=0; i<ROWS; i++) {
			Draw.drawLine(0, i*BLOCKSIZE, COLUMNS*BLOCKSIZE, i*BLOCKSIZE);
		}
		
		for (int i=0; i<COLUMNS; i++) {
			Draw.drawLine(i*BLOCKSIZE, 0, i*BLOCKSIZE, ROWS*BLOCKSIZE);
		}
	}
}
