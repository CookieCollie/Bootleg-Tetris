import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class FormBlock {

	private Audio audio;

	private int[][] BlockCoordinates;
	private GridBoard GridBoard;
	private Image BlockImg;
	private int XShift=0, x, y;
	private long CurrentTime, PassedTime;
	private static int Speed;
	private int SpeedBoost;
	private static int CurrentSpeed;
	private int color;

	private static int scoreFB;
	
	private boolean HitGround = false, moveX = true;

	public FormBlock(Image BlockImg, int[][] BlockCoordinates, GridBoard GridBoard, int color) {
		this.BlockImg = BlockImg;
		this.BlockCoordinates = BlockCoordinates;
		this.GridBoard = GridBoard;
		this.color = color;

        // Starting position
        x = GridBoard.getCOLUMNS()/2-1;;
        y = 0;

		CurrentTime = 0;
		PassedTime = System.currentTimeMillis();
		Speed = Difficulty.changeDifficulty(); // The smaller the number, the faster the blocks move
		SpeedBoost = 0;
		CurrentSpeed = Speed;
	}

	public void BlockUpdate() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		CurrentTime += System.currentTimeMillis() - PassedTime;
		PassedTime = System.currentTimeMillis();
		
		if (HitGround) {
			for (int i=0; i<BlockCoordinates.length; i++) {
				for (int j=0; j<BlockCoordinates[i].length; j++) {
					if (BlockCoordinates[i][j] != 0) {
						GridBoard.getGrid()[i+y][j+x] = this.color;
					}
				}
			}
			CheckLine();
			GridBoard.SpawnNextBlock();
		}
		
		
		if (!(x + XShift + BlockCoordinates[0].length > GridBoard.COLUMNS) && !(x + XShift < 0)) {
		    for (int row = 0; row < BlockCoordinates.length; row++) {
                for (int col = 0; col < BlockCoordinates[row].length; col++) {
                    if(BlockCoordinates[row][col] != 0){
                        if(GridBoard.getGrid()[y + row][x + XShift + col] != 0){
                            moveX = false;
                        }
                    }
                }
            }
            if (moveX) {
                x += XShift;
            }
		}
		
		
		if (!(y+BlockCoordinates.length+1 > GridBoard.ROWS)) {
			for (int i=0; i<BlockCoordinates.length; i++) {
				for (int j=0; j<BlockCoordinates[i].length; j++) { 
					if (BlockCoordinates[i][j] != 0) {
						if (GridBoard.getGrid()[y + i + 1][j + x] != 0) {
							HitGround = true;
							CurrentSpeed = 700;
							CurrentSpeed = Speed;
						}
					}
				}
			}
			if (CurrentTime > CurrentSpeed) {
				y++;
				CurrentTime = 0;
			}
		} else {
			HitGround = true;
		}
		XShift = 0;
		moveX = true;
	}
	
	private void CheckLine() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		int height = GridBoard.getGrid().length - 1;
		 for (int i=height; i>0; i--) {
			 int count=0;
			 for (int j=0; j<GridBoard.getGrid()[0].length; j++) {
				 if (GridBoard.getGrid()[i][j] != 0) {
					 count++;
				 }
				 GridBoard.getGrid()[height][j] = GridBoard.getGrid()[i][j];
			 }
			 if (count==GridBoard.COLUMNS) {
			 	scoreFB += 10;
			 	audio = new Audio("audio/OPEN GAME.wav");
			 	audio.playMusicDelay();
			 }
			 if (count<GridBoard.getGrid()[0].length) {
				 height--;
			 }
		 }
	}

	public void BlockRender(Graphics Render) {
		for (int j = 0; j < BlockCoordinates.length; j++) {
			for (int i = 0; i < BlockCoordinates[j].length; i++) {
				if (BlockCoordinates[j][i] != 0) {
					Render.drawImage(BlockImg, i * GridBoard.getBLOCKSIZE() + x * GridBoard.getBLOCKSIZE(),
							j * GridBoard.getBLOCKSIZE() + y * GridBoard.getBLOCKSIZE(), null);
				}
			}
		}
	}

	public void ShiftBlockX(int XShift) {
		this.XShift = XShift;
	}

	public void SpeedPressKeyDown() {
		CurrentSpeed = SpeedBoost;
	}

	public void KeyDownReleased() {
		CurrentSpeed = Speed;
	}

	
	private int[][] TransposeMatrix(int[][] matrix) {
		int[][] TransposedMatrix = new int[matrix[0].length][matrix.length];
		for (int x=0; x<matrix.length; x++) {
			for (int y=0; y<matrix[0].length; y++) {
				TransposedMatrix[y][x] = matrix[x][y];
			}
		}
		return TransposedMatrix;
	}
	
	private int[][] ReverseMatrix(int[][] matrix) {
		int SplitMatrix = matrix.length/2;
		for (int i=0; i<SplitMatrix; i++) {
			int[] temp = matrix[i];
			matrix[i] = matrix[matrix.length-i-1];
			matrix[matrix.length-i-1] = temp;
		}
		return matrix;
	}
	
	public void RotateBlock() {
		if (HitGround) {
			return;
		}
		
		int[][] RotatedMatrix = null;
		RotatedMatrix = TransposeMatrix(BlockCoordinates);
		RotatedMatrix = ReverseMatrix(RotatedMatrix);
		if (x+RotatedMatrix[0].length > GridBoard.COLUMNS || y+RotatedMatrix.length > GridBoard.ROWS) { // 10 20 is gridboard's column and row
			return;
		}
		
		for (int row=0; row<RotatedMatrix.length; row++) {
			for (int col=0; col<RotatedMatrix[0].length; col++) {
				if (GridBoard.getGrid()[row+y][col+x] != 0) {
					return;
				}
			}
		}
		
		BlockCoordinates = RotatedMatrix;
	}

	public int[][] getBlockCoordinates() {
		return BlockCoordinates;
	}

	public Image getBlockImg() {
		return BlockImg;
	}
	
	public int getColor() {
		return color;
	}

	public static int getScoreFB() {
		return scoreFB;
	}
	
	public static void setScoreFB(int scoreFB) {
		FormBlock.scoreFB = scoreFB;
	}
	
	public static void setSpeed(int speed) {
		Speed = speed;
	}
	
	public static void setCurrentSpeed(int currentSpeed) {
		CurrentSpeed = currentSpeed;
	}
}
