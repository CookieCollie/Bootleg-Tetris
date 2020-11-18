import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GridBoard extends JPanel implements KeyListener {
	private BufferedImage Block0, Block1, Block2, Block3, Block4, Block5, Block6;
	public int COLUMNS = 15, ROWS = 30, BLOCKSIZE = 20;
	public int[][] GRID = new int[ROWS][COLUMNS];

	private FormBlock[] Shape = new FormBlock[7];
	private FormBlock CurrentShape;

	private Random rand = new Random();
	int RandomNum = rand.nextInt(7);

	private int FPS = 60;
	private int delay = 1000 / FPS;
	private Timer GameLoop;
	
	private boolean gameOver = false;


	public GridBoard() {
		try {
			Block0 = ImageIO.read(new FileInputStream("resources/0.png"));
			Block1 = ImageIO.read(new FileInputStream("resources/1.png"));
			Block2 = ImageIO.read(new FileInputStream("resources/2.png"));
			Block3 = ImageIO.read(new FileInputStream("resources/3.png"));
			Block4 = ImageIO.read(new FileInputStream("resources/4.png"));
			Block5 = ImageIO.read(new FileInputStream("resources/5.png"));
			Block6 = ImageIO.read(new FileInputStream("resources/6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error in printing blocks");
		}

		GameLoop = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BlockUpdate();
				repaint();
			}
		});

		GameLoop.start();

		// Shapes
		Shape[0] = new FormBlock(Block0.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0), new int[][] { { 1, 1, 1, 1 } },
				this); // I
		Shape[1] = new FormBlock(Block1.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0), new int[][] { { 1, 1 }, { 1, 1 } },
				this); // O
		Shape[2] = new FormBlock(Block2.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0),
				new int[][] { { 0, 0, 1 }, { 1, 1, 1 } }, this); // L
		Shape[3] = new FormBlock(Block3.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0),
				new int[][] { { 1, 1, 1 }, { 0, 0, 1 } }, this); // J
		Shape[4] = new FormBlock(Block4.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0),
				new int[][] { { 0, 1, 1 }, { 1, 1, 0 } }, this); // S
		Shape[5] = new FormBlock(Block5.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0),
				new int[][] { { 1, 1, 0 }, { 0, 1, 1 } }, this); // Z
		Shape[6] = new FormBlock(Block6.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0),
				new int[][] { { 1, 1, 1 }, { 0, 1, 0 } }, this); // T

		SpawnNextBlock();
	}

	public void paintComponent(Graphics Draw) {
		super.paintComponent(Draw);
		
		Draw.drawImage(Block1, 0, 0, BLOCKSIZE*COLUMNS, BLOCKSIZE*ROWS, 0, 0, 100, 100, null);
		
		CurrentShape.BlockRender(Draw);

		for (int x=0; x<GRID.length; x++) {
			for (int y=0; y<GRID[x].length; y++) {
				if (GRID[x][y] != 0) {
					Draw.drawImage(Block0.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0), y*BLOCKSIZE, x*BLOCKSIZE, null);
				}
			}
		}
		for (int i = 0; i < ROWS; i++) {
			Draw.drawLine(0, i * BLOCKSIZE, COLUMNS * BLOCKSIZE, i * BLOCKSIZE);
		}

		for (int i = 0; i < COLUMNS; i++) {
			Draw.drawLine(i * BLOCKSIZE, 0, i * BLOCKSIZE, ROWS * BLOCKSIZE);
		}
		
		Draw.drawLine(BLOCKSIZE*COLUMNS, 0, BLOCKSIZE*COLUMNS, BLOCKSIZE*ROWS);
		Draw.drawString("Score: ", BLOCKSIZE*COLUMNS+10, 20);
		Draw.drawString("Level: ", BLOCKSIZE*COLUMNS+10, 40);
		
		Draw.drawRect(BLOCKSIZE*COLUMNS, 0, 100, BLOCKSIZE*ROWS/2);
		Draw.drawString("Highest scores: ", BLOCKSIZE*COLUMNS+10, BLOCKSIZE*ROWS/2+20);
		
	}

	public int GetBlockSize() {
		return BLOCKSIZE;
	}

	public void BlockUpdate() {
		CurrentShape.BlockUpdate();
	}
	
	public void SpawnNextBlock() {
		RandomNum = rand.nextInt(7);
		FormBlock NextShape = new FormBlock(Shape[RandomNum].getBlockImg(), Shape[RandomNum].getBlockCoordinates(), this);
		CurrentShape = NextShape;
		
		for (int row=0; row<CurrentShape.getBlockCoordinates().length; row++) {
			for (int col=0; col<CurrentShape.getBlockCoordinates()[row].length; col++) {
				if (CurrentShape.getBlockCoordinates()[row][col] != 0) {
					if (GRID[row][col+3]!=0) {
						gameOver = true;
					}
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			CurrentShape.ShiftBlockX(-1);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			CurrentShape.ShiftBlockX(1);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			CurrentShape.SpeedPressKeyDown();
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			CurrentShape.RotateBlock();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			CurrentShape.KeyDownReleased();
		}
	}
	
	public int[][] getGrid() {
		return GRID;
	}
}
