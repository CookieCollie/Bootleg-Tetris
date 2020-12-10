import java.awt.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.util.Arrays;

public class GridBoard extends JPanel implements KeyListener, MouseMotionListener, MouseListener {

	String PicBGPath = "resources/BGP.png";

	private boolean leftClick = false;

	Audio audio;

	private BufferedImage Block0, Block1, Block2, Block3, Block4, Block5, Block6, blocks, BGPg, pauseMenu;

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
	
	private static boolean gameOver = false, gridOn = true;
	private boolean pause = false;
	
	private static GridBoard boardSingle = null;
	
	private int currBlock = rand.nextInt(7);
	
	private int[] combination = new int[4];
	private int[] compareComb = {4,3,1,2};
	private int combCounter = -1;

	private HighScore highScore = new HighScore();

	private GridBoard() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		try {
			Block0 = ImageIO.read(new File("resources/I.png"));
			Block1 = ImageIO.read(new File("resources/Z.png"));
			Block2 = ImageIO.read(new File("resources/S.png"));
			Block3 = ImageIO.read(new File("resources/J.png"));
			Block4 = ImageIO.read(new File("resources/L.png"));
			Block5 = ImageIO.read(new File("resources/T.png"));
			Block6 = ImageIO.read(new File("resources/O.png"));

			// Test
			blocks = ImageIO.read(new File("resources/Block.png"));
			BGPg = ImageIO.read(new File("resources/BGPicture.png"));
			pauseMenu = ImageIO.read(new File("resources/pause.png"));

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error in printing blocks");
		}

		GameLoop = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					blockUpdate();
				} catch (UnsupportedAudioFileException unsupportedAudioFileException) {
					unsupportedAudioFileException.printStackTrace();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				} catch (LineUnavailableException lineUnavailableException) {
					lineUnavailableException.printStackTrace();
				}
				repaint();
			}
		});

		GameLoop.start();

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

		Draw.drawImage(BGPg, 0, 0, BLOCKSIZE*COLUMNS, BLOCKSIZE*ROWS, 0, 0, 100, 100, null); //Draw background
		
		CurrentShape.BlockRender(Draw);

		for (int x=0; x<GRID.length; x++) {
			for (int y=0; y<GRID[x].length; y++) {
				if (GRID[x][y] != 0) {
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
            Draw.drawImage(pauseMenu, 0, -100, 336, 660, 0, 0, 336, 660, null);
            GameLoop.stop();
            //System.out.println("Pause in Draw GridBoard");
        } else {
			GameLoop.start();
		}
		
		Draw.drawLine(BLOCKSIZE*COLUMNS, 0, BLOCKSIZE*COLUMNS, BLOCKSIZE*ROWS);

		Draw.drawString("Score: " + FormBlock.getScoreFB(), BLOCKSIZE*COLUMNS+10, 20); //Draw score

		Draw.drawString("Difficulty: " + Difficulty.getPrintDiff(), BLOCKSIZE*COLUMNS+10, 40);
		
		Draw.drawLine(BLOCKSIZE*COLUMNS, BLOCKSIZE*ROWS/2, 2000, BLOCKSIZE*ROWS/2);

		try {
			Draw.drawString("Highest scores: " + highScore.compareScore(FormBlock.getScoreFB()), BLOCKSIZE*COLUMNS+10, BLOCKSIZE*ROWS/2+20);
		} catch (IOException e) {
			e.printStackTrace();
		}

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

	public void blockUpdate() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (gameOver) {
			MainGame.getBGM().stopMusic();
			audio = new Audio("audio/FAILURE NOTIFICATION.wav");
			audio.playMusicDelay();
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
		//System.out.println(currBlock);
		
		
		if (checkComb()) {
			FormBlock.setScoreFB(100);
			gameOver = true;
		}
	}

	public void startGame(){
		stopGame();
		SpawnNextBlock();
		gameOver = false;
		Arrays.fill(combination, 0);
		combCounter = -1;
		GameLoop.start();
		FormBlock.setScoreFB(0);
		//Arrays.fill(GRID, 0);
		pause = false;
		
		for (int i=0; i<GRID.length; i++) {
			Arrays.fill(GRID[i], 0);
		}
	}

	public void stopGame(){
		GameLoop.stop();
	}


	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			CurrentShape.ShiftBlockX(-1);
			if (Difficulty.getDifficulty() == 3) {
				combCounter++;
				if (combCounter<combination.length) {
					combination[combCounter] = 1;
				}
				else {
					Arrays.fill(combination, 0);
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			CurrentShape.ShiftBlockX(1);
			if (Difficulty.getDifficulty() == 3) {
				combCounter++;
				if (combCounter<combination.length) {
					combination[combCounter] = 2;
				}
				else {
					Arrays.fill(combination, 0);
				}
			}
			
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			CurrentShape.SpeedPressKeyDown();
			if (Difficulty.getDifficulty() == 3) { 
				combCounter++;
				if (combCounter<combination.length) {
					combination[combCounter] = 3;
				}
				else {
					Arrays.fill(combination, 0);
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			CurrentShape.RotateBlock();
			if (Difficulty.getDifficulty() == 3) {
				combCounter++;
				if (combCounter<combination.length) {
					combination[combCounter] = 4;
				}
				else {
					Arrays.fill(combination, 0);
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_P) {
		    pause = !pause;
            setPause(pause);
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			gridOn = !gridOn;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			CurrentShape.KeyDownReleased();
	}
	
	private boolean checkComb() {
		boolean checkComb = false;
		if (Arrays.equals(combination, compareComb)) {
			checkComb = true;
		}
		else
			checkComb = false;
		return checkComb;
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
	
	public boolean getPause() {
		return pause;
	}
	
	public void setPause(boolean pause) {
		this.pause = pause;
        if(pause) {
            //PauseScreen.drawPauseScreen();
            MainGame.getBGM().pauseMusic();
            //GameLoop.stop(); // When GameLoop stops, we cannot draw anything
        } else {
           // PauseScreen.removePauseScreen();
            try {
                MainGame.getBGM().resumeMusic();
            } catch (UnsupportedAudioFileException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (LineUnavailableException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            GameLoop.start();
        }
	}
	
	public static Timer getGameLoop() {
		return GameLoop;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftClick = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftClick = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}

