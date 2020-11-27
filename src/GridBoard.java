import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class GridBoard extends JPanel implements KeyListener {

	AudioPlayer audioPlayer;

	private BufferedImage Block0, Block1, Block2, Block3, Block4, Block5, Block6, blocks;

	public final static int COLUMNS = 10, ROWS = 20, BLOCKSIZE = 30;
	public int[][] GRID = new int[ROWS][COLUMNS];
	static Graphics Draw;

	private FormBlock[] Shape = new FormBlock[7];
	private FormBlock CurrentShape;

	private Random rand = new Random();
	int RandomNum = rand.nextInt(7);

	private int FPS = 60;
	private int delay = 1000 / FPS;
	private static Timer GameLoop;
	
	private static boolean gameOver = false, pause = false, gridOn = true;
	
	private static GridBoard boardSingle = null;
	
	private int currBlock = rand.nextInt(7);
	private int nextBlock = rand.nextInt(7);

	private GridBoard() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		try {
			Block0 = ImageIO.read(new File("resources/I.png"));
			Block1 = ImageIO.read(new File("resources/Z.png"));
			Block2 = ImageIO.read(new File("resources/S.png"));
			Block3 = ImageIO.read(new File("resources/J.png"));
			Block4 = ImageIO.read(new File("resources/L.png"));
			Block5 = ImageIO.read(new File("resources/T.png"));
			Block6 = ImageIO.read(new File("resources/O.png"));
			
			/*Block0 = ImageIO.read(GridBoard.class.getResourceAsStream("/0.png"));
			Block1 = ImageIO.read(GridBoard.class.getResourceAsStream("/1.png"));
			Block2 = ImageIO.read(GridBoard.class.getResourceAsStream("/2.png"));
			Block3 = ImageIO.read(GridBoard.class.getResourceAsStream("/3.png"));
			Block4 = ImageIO.read(GridBoard.class.getResourceAsStream("/4.png"));
			Block5 = ImageIO.read(GridBoard.class.getResourceAsStream("/5.png"));
			Block6 = ImageIO.read(GridBoard.class.getResourceAsStream("/6.png"));*/

			// Test
			blocks = ImageIO.read(new File("resources/Block.png"));

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
		audioPlayer = new AudioPlayer("audio/Phạm-Nguyên-Ngọc_-Vanh_-B.-Sao-Em-Lại-Tắt-Máy-.wav");
		audioPlayer.play();

		// Shapes
//		Shape[0] = new FormBlock(Block0.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0), new int[][] { { 1, 1, 1, 1 } },
//				this, 1); // I
//		Shape[1] = new FormBlock(Block1.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0), new int[][] { { 1, 1 }, { 1, 1 } },
//				this, 2); // O
//		Shape[2] = new FormBlock(Block2.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0),
//				new int[][] { { 0, 0, 1 }, { 1, 1, 1 } }, this, 3); // L
//		Shape[3] = new FormBlock(Block3.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0),
//				new int[][] { { 1, 1, 1 }, { 0, 0, 1 } }, this, 4); // J
//		Shape[4] = new FormBlock(Block4.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0),
//				new int[][] { { 0, 1, 1 }, { 1, 1, 0 } }, this, 5); // S
//		Shape[5] = new FormBlock(Block5.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0),
//				new int[][] { { 1, 1, 0 }, { 0, 1, 1 } }, this, 6); // Z
//		Shape[6] = new FormBlock(Block6.getScaledInstance(BLOCKSIZE, BLOCKSIZE, 0),
//				new int[][] { { 1, 1, 1 }, { 0, 1, 0 } }, this, 7); // T

		Shape[0] = new FormBlock(blocks.getSubimage(0, 0, BLOCKSIZE, BLOCKSIZE), new int[][] { { 1, 1, 1, 1 } // I shape
		}, this, 1);
		Shape[1] = new FormBlock(blocks.getSubimage(BLOCKSIZE, 0, BLOCKSIZE, BLOCKSIZE),
				new int[][] { { 1, 1, 0 }, { 0, 1, 1 } // Z shape
				}, this, 2);
		Shape[2] = new FormBlock(blocks.getSubimage(BLOCKSIZE * 2, 0, BLOCKSIZE, BLOCKSIZE),
				new int[][] { { 0, 1, 1 }, { 1, 1, 0 } // S shape
				}, this, 3);
		Shape[3] = new FormBlock(blocks.getSubimage(BLOCKSIZE * 3, 0, BLOCKSIZE, BLOCKSIZE),
				new int[][] { { 1, 1, 1 }, { 0, 0, 1 } // J shape
				}, this, 4);
		Shape[4] = new FormBlock(blocks.getSubimage(BLOCKSIZE * 4, 0, BLOCKSIZE, BLOCKSIZE),
				new int[][] { { 1, 1, 1 }, { 1, 0, 0 } // L shape
				}, this, 5);
		Shape[5] = new FormBlock(blocks.getSubimage(BLOCKSIZE * 5, 0, BLOCKSIZE, BLOCKSIZE),
				new int[][] { { 1, 1, 1 }, { 0, 1, 0 } // T shape
				}, this, 6);
		Shape[6] = new FormBlock(blocks.getSubimage(BLOCKSIZE * 6, 0, BLOCKSIZE, BLOCKSIZE),
				new int[][] { { 1, 1 }, { 1, 1 } // O shape
				}, this, 7);


		SpawnNextBlock();
	}
	
	public static GridBoard getInstance() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (boardSingle == null) {
			boardSingle = new GridBoard();
		}
		
		return boardSingle;
	}


	public void paintComponent(Graphics g) {
		Draw = g;
		BufferedImage[] Blocks = {Block0, Block1, Block2, Block3, Block4, Block5, Block6};
		super.paintComponent(Draw);
		
		//Draw.drawImage(blocks, 0, 0, BLOCKSIZE*COLUMNS, BLOCKSIZE*ROWS, 0, 0, 100, 100, null); //Draw background
		
		CurrentShape.BlockRender(Draw);

		for (int x=0; x<GRID.length; x++) {
			for (int y=0; y<GRID[x].length; y++) {
				if (GRID[x][y] != 0) {
					//Draw.drawImage((Blocks[GRID[x][y]-1]).getScaledInstance(BLOCKSIZE, BLOCKSIZE, Image.SCALE_SMOOTH), y*BLOCKSIZE, x*BLOCKSIZE, null);

					//Test
					Draw.drawImage(blocks.getSubimage((GRID[x][y] - 1)*BLOCKSIZE, 0, BLOCKSIZE, BLOCKSIZE), y*BLOCKSIZE, BLOCKSIZE*x, null);
				}
			}
		}
		
		//Toggle grid on/off
		if (gridOn) {
			for (int i = 0; i < ROWS; i++) {
				Draw.drawLine(0, i * BLOCKSIZE, COLUMNS * BLOCKSIZE, i * BLOCKSIZE);
			}

			for (int i = 0; i < COLUMNS; i++) {
				Draw.drawLine(i * BLOCKSIZE, 0, i * BLOCKSIZE, ROWS * BLOCKSIZE);
			}
		}
		
		//Draw pause menu background
		if (pause) {
			Draw.drawImage(blocks, 0, 0, 100, 100, 0, 0, 100, 100, null);
		}
		
		Draw.drawLine(BLOCKSIZE*COLUMNS, 0, BLOCKSIZE*COLUMNS, BLOCKSIZE*ROWS);

		Draw.drawString("Score: " + FormBlock.getScoreFB(), BLOCKSIZE*COLUMNS+10, 20); //Draw score

		Draw.drawString("Difficulty: " + Difficulty.getPrintDiff(), BLOCKSIZE*COLUMNS+10, 40);
		
		Draw.drawLine(BLOCKSIZE*COLUMNS, BLOCKSIZE*ROWS/2, 2000, BLOCKSIZE*ROWS/2);
		Draw.drawString("Highest scores: ", BLOCKSIZE*COLUMNS+10, BLOCKSIZE*ROWS/2+20);
		
		if (currBlock != 0 && currBlock != 6) {
			Draw.drawImage(Blocks[currBlock], BLOCKSIZE*COLUMNS+30, 150, BLOCKSIZE*COLUMNS+30+90, 210, 0, 0, 90, 60, null);
		}
		else
			if (currBlock == 0)
				Draw.drawImage(Block0, BLOCKSIZE*COLUMNS+10, 150, BLOCKSIZE*COLUMNS+10+120, 180, 0, 0, 120, 30, null);
			else
				Draw.drawImage(Block6, BLOCKSIZE*COLUMNS+30, 150, BLOCKSIZE*COLUMNS+30+60, 210, 0, 0, 60, 60, null);
	
		//PauseScreen.drawPauseScreen();
		
			
		
	}

	public void blockUpdate() {
		if (gameOver) {
			GameLoop.stop();
		}
		else
			CurrentShape.BlockUpdate();
	}
	
	public void SpawnNextBlock() {
		//RandomNum = rand.nextInt(7);
		FormBlock NextShape = new FormBlock(Shape[currBlock].getBlockImg(), Shape[currBlock].getBlockCoordinates(), this, 
											Shape[currBlock].getColor());
		CurrentShape = NextShape;
		
		for (int row=0; row<CurrentShape.getBlockCoordinates().length; row++) {
			for (int col=0; col<CurrentShape.getBlockCoordinates()[row].length; col++) {
				if (CurrentShape.getBlockCoordinates()[row][col] != 0) {
					if (GRID[row][col+5]!=0) {
						gameOver = true;
					}
				}
			}
		}
		currBlock = rand.nextInt(7);
		System.out.println(currBlock);
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
		if (e.getKeyCode() == KeyEvent.VK_E) {
			gridOn = !gridOn;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		/*if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			CurrentShape.KeyDownReleased();
		}*/
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
	
	public static boolean getPause() {
		return pause;
	}
	
	public static void setPause(boolean pause) {
		GridBoard.pause = pause;
	}
	
	public static Timer getGameLoop() {
		return GameLoop;
	}
}

