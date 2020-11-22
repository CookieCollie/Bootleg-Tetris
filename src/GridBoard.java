import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GridBoard extends JPanel implements KeyListener {

	private BufferedImage Block0, Block1, Block2, Block3, Block4, Block5, Block6;

	public final static int COLUMNS = 10, ROWS = 20, BLOCKSIZE = 29;
	public int[][] GRID = new int[ROWS][COLUMNS];
	static Graphics Draw;

	private FormBlock[] Shape = new FormBlock[7];
	private FormBlock CurrentShape;

	private Random rand = new Random();
	int RandomNum = rand.nextInt(7);

	private int FPS = 60;
	private int delay = 1000 / FPS;
	private Timer GameLoop;
	
	private boolean gameOver = false, pause = false;


	public GridBoard() {
		try {
			Block0 = ImageIO.read(new File("resources/0.png"));
			Block1 = ImageIO.read(new File("resources/1.png"));
			Block2 = ImageIO.read(new File("resources/2.png"));
			Block3 = ImageIO.read(new File("resources/3.png"));
			Block4 = ImageIO.read(new File("resources/4.png"));
			Block5 = ImageIO.read(new File("resources/5.png"));
			Block6 = ImageIO.read(new File("resources/6.png"));
			
			/*Block0 = ImageIO.read(GridBoard.class.getResourceAsStream("/0.png"));
			Block1 = ImageIO.read(GridBoard.class.getResourceAsStream("/1.png"));
			Block2 = ImageIO.read(GridBoard.class.getResourceAsStream("/2.png"));
			Block3 = ImageIO.read(GridBoard.class.getResourceAsStream("/3.png"));
			Block4 = ImageIO.read(GridBoard.class.getResourceAsStream("/4.png"));
			Block5 = ImageIO.read(GridBoard.class.getResourceAsStream("/5.png"));
			Block6 = ImageIO.read(GridBoard.class.getResourceAsStream("/6.png"));*/
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error in printing blocks");
		}

		GameLoop = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				blockUpdate();
				repaint();
			}
		});

		GameLoop.start();

		// Shapes
		Shape[0] = new FormBlock(Block0.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0), new int[][] { { 1, 1, 1, 1 } },
				this, 1); // I
		Shape[1] = new FormBlock(Block1.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0), new int[][] { { 1, 1 }, { 1, 1 } },
				this, 2); // O
		Shape[2] = new FormBlock(Block2.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0),
				new int[][] { { 0, 0, 1 }, { 1, 1, 1 } }, this, 3); // L
		Shape[3] = new FormBlock(Block3.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0),
				new int[][] { { 1, 1, 1 }, { 0, 0, 1 } }, this, 4); // J
		Shape[4] = new FormBlock(Block4.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0),
				new int[][] { { 0, 1, 1 }, { 1, 1, 0 } }, this, 5); // S
		Shape[5] = new FormBlock(Block5.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0),
				new int[][] { { 1, 1, 0 }, { 0, 1, 1 } }, this, 6); // Z
		Shape[6] = new FormBlock(Block6.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0),
				new int[][] { { 1, 1, 1 }, { 0, 1, 0 } }, this, 7); // T

		SpawnNextBlock();
	}


	public void paintComponent(Graphics g) {
		Draw = g;
		BufferedImage[] Blocks = {Block0, Block1, Block2, Block3, Block4, Block5, Block6};
		super.paintComponent(Draw);
		
		//Draw.drawImage(Block1, 0, 0, BLOCKSIZE*COLUMNS, BLOCKSIZE*ROWS, 0, 0, 100, 100, null);
		
		CurrentShape.BlockRender(Draw);

		for (int x=0; x<GRID.length; x++) {
			for (int y=0; y<GRID[x].length; y++) {
				if (GRID[x][y] != 0) {
					Draw.drawImage((Blocks[GRID[x][y]-1]).getScaledInstance(BLOCKSIZE, BLOCKSIZE, Image.SCALE_SMOOTH), y*BLOCKSIZE, x*BLOCKSIZE, null);
				}
//				else if (GRID[x][y] == 2) {
//					Draw.drawImage(Block1.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0), y*BLOCKSIZE, x*BLOCKSIZE, null);
//				}
//				else if (GRID[x][y] == 3) {
//					Draw.drawImage(Block2.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0), y*BLOCKSIZE, x*BLOCKSIZE, null);
//				}
//				else if (GRID[x][y] == 4) {
//					Draw.drawImage(Block3.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0), y*BLOCKSIZE, x*BLOCKSIZE, null);
//				}
//				else if (GRID[x][y] == 5) {
//					Draw.drawImage(Block4.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0), y*BLOCKSIZE, x*BLOCKSIZE, null);
//				}
//				else if (GRID[x][y] == 6) {
//					Draw.drawImage(Block5.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0), y*BLOCKSIZE, x*BLOCKSIZE, null);
//				}
//				else if (GRID[x][y] == 7) {
//					Draw.drawImage(Block6.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0), y*BLOCKSIZE, x*BLOCKSIZE, null);
//				}
			}
		}
		for (int i = 0; i < ROWS; i++) {
			Draw.drawLine(0, i * BLOCKSIZE, COLUMNS * BLOCKSIZE, i * BLOCKSIZE);
		}

		for (int i = 0; i < COLUMNS; i++) {
			Draw.drawLine(i * BLOCKSIZE, 0, i * BLOCKSIZE, ROWS * BLOCKSIZE);
		}
		
		if (pause) {
			Draw.drawImage(Block5, 0, 0, 100, 100, 0, 0, 100, 100, null);
		}
		
		Draw.drawLine(BLOCKSIZE*COLUMNS, 0, BLOCKSIZE*COLUMNS, BLOCKSIZE*ROWS);

		Draw.drawString("Score: ", BLOCKSIZE*COLUMNS+10, 20);
		Draw.drawString(String.valueOf(FormBlock.getScoreFB()), BLOCKSIZE*COLUMNS+50, 20);

		Draw.drawString("Level: ", BLOCKSIZE*COLUMNS+10, 40);
		
		Draw.drawRect(BLOCKSIZE*COLUMNS, 0, 120, BLOCKSIZE*ROWS/2);
		Draw.drawString("Highest scores: ", BLOCKSIZE*COLUMNS+10, BLOCKSIZE*ROWS/2+20);
		
		
		//PauseScreen.drawPauseScreen();
		
			
		
	}

	public void blockUpdate() {
		CurrentShape.BlockUpdate();
		if (gameOver) {
			GameLoop.stop();
		}
	}
	
	public void SpawnNextBlock() {
		RandomNum = rand.nextInt(7);
		FormBlock NextShape = new FormBlock(Shape[RandomNum].getBlockImg(), Shape[RandomNum].getBlockCoordinates(), this, 
											Shape[RandomNum].getColor());
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
		if (e.getKeyCode() == KeyEvent.VK_P) {
			pause = !pause;
			if (pause) {
				PauseScreen.drawPauseScreen();
				GameLoop.stop();
			}
			else {
				PauseScreen.removePauseScreen();
				GameLoop.start();
			}
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
	
	public static int getCOLUMNS() {
		return COLUMNS;
	}
	
	public static int getROWS() {
		return ROWS;
	}
	
	public static int getBLOCKSIZE() {
		return BLOCKSIZE;
	}
}

