import java.awt.Graphics;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class GridBoard extends JPanel {
	private BufferedImage Block;
	public int COLUMNS = 15, ROWS = 30, BLOCKSIZE = 20;
	public int[][] GRID = new int[COLUMNS][ROWS];
	private FormBlock[] Shape = new FormBlock[7];
	
	
	public GridBoard() {
		try {
			Block = ImageIO.read(new FileInputStream("resources/0.png"));
			Block = ImageIO.read(new FileInputStream("resources/1.png"));
			Block = ImageIO.read(new FileInputStream("resources/2.png"));
			Block = ImageIO.read(new FileInputStream("resources/3.png"));
			Block = ImageIO.read(new FileInputStream("resources/4.png"));
			Block = ImageIO.read(new FileInputStream("resources/5.png"));
			Block = ImageIO.read(new FileInputStream("resources/6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error in printing blocks");
		}
		
		//Shapes
		//Shape[0] = new FormBlock(Block.get);
	}
	
	public void paintComponent(Graphics Draw) {
		super.paintComponent(Draw);
		Draw.drawImage(Block,0,0,null);
		for (int i=0; i<ROWS; i++) {
			Draw.drawLine(0, i*BLOCKSIZE, COLUMNS*BLOCKSIZE, i*BLOCKSIZE);
		}
		
		for (int i=0; i<COLUMNS; i++) {
			Draw.drawLine(i*BLOCKSIZE, 0, i*BLOCKSIZE, ROWS*BLOCKSIZE);
		}
	}
}
