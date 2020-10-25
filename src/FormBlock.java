import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class FormBlock {
	private int[][] BlockCoordinates;
	private GridBoard GridBoard;
	private Image BlockImg;
	private int XShift, x, y;
	private long CurrentTime, PassedTime;
	private int Speed, SpeedBoost, CurrentSpeed;
	
	private boolean HitGround = false;

	public FormBlock(Image BlockImg, int[][] BlockCoordinates, GridBoard GridBoard) {
		this.BlockImg = BlockImg;
		this.BlockCoordinates = BlockCoordinates;
		this.GridBoard = GridBoard;

		x = 6;
		y = 0;

		CurrentTime = 0;
		PassedTime = System.currentTimeMillis();
		Speed = 600; // Implement speed/difficulty change in future
					 // The smaller the number, the faster the blocks move
		SpeedBoost = 10;
		CurrentSpeed = Speed;
	}

	public void BlockUpdate() {
		CurrentTime += System.currentTimeMillis() - PassedTime;
		PassedTime = System.currentTimeMillis();
		
		if (HitGround) {
			for (int i=0; i<BlockCoordinates.length; i++) {
				for (int j=0; j<BlockCoordinates[i].length; j++) {
					if (BlockCoordinates[i][j] != 0) {
						GridBoard.getGrid()[i+y][j+x] = 1;
					}
				}
			}
		
			GridBoard.SpawnNextBlock();
		}
		
		
		if (x + XShift + BlockCoordinates[0].length <= GridBoard.COLUMNS && x + XShift >= 0)
			x += XShift;
		XShift = 0;
		
		
		if (y+BlockCoordinates.length+1 <= GridBoard.ROWS) {
			for (int i=0; i<BlockCoordinates.length; i++) {
				for (int j=0; j<BlockCoordinates[i].length; j++) { 
					if (BlockCoordinates[i][j] != 0) {}
					if (GridBoard.getGrid()[y + i + 1][j + x] != 0) {
						HitGround = true;
					}
				}
			}
			if (CurrentTime > CurrentSpeed) {
				y++;
				CurrentTime = 0;
			}
		}
		else {
			HitGround = true;
		}
		

	}

	public void BlockRender(Graphics Render) {
		for (int j = 0; j < BlockCoordinates.length; j++) {
			for (int i = 0; i < BlockCoordinates[j].length; i++) {
				if (BlockCoordinates[j][i] != 0) {
					Render.drawImage(BlockImg, i * GridBoard.GetBlockSize() + x * GridBoard.GetBlockSize(),
							j * GridBoard.GetBlockSize() + y * GridBoard.GetBlockSize(), null);
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
		int[][] RotatedMatrix;
		RotatedMatrix = TransposeMatrix(BlockCoordinates);
		RotatedMatrix = ReverseMatrix(RotatedMatrix);
		if (x+RotatedMatrix[0].length > GridBoard.COLUMNS || y+RotatedMatrix.length > GridBoard.ROWS) {
			return;
		}
		BlockCoordinates = RotatedMatrix;
	}

	public int[][] getBlockCoordinates() {
		return BlockCoordinates;
	}

	public Image getBlockImg() {
		return BlockImg;
	}
	
	
}
