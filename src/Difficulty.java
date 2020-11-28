
public class Difficulty {
	private static int difficulty;
	private static String printDiff;
	
	public static int changeDifficulty() {
		switch (difficulty) {
		case 0: {
			printDiff = "Easy";
			return 600;
		}
		case 1: {
			printDiff = "Medium";
			return 400;
		}
		case 2: {
			printDiff = "Hard";
			return 200;
		}
		case 3: {
			printDiff = "Impossible";
			return 50;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + difficulty);
		}
	}
	
	public static int getDifficulty() {
		return difficulty;
	}
	
	public static void setDifficulty(int difficulty) {
		Difficulty.difficulty = difficulty;
	}
	
	public static String getPrintDiff() {
		return printDiff;
	}
}
