import java.io.*;

public class HighScore {
    private int highScore;

    public HighScore() throws IOException {
    }

    public int compareScore(int score) throws IOException {
        highScore = readLong("resources/HighScore.txt",-1);
        if (score > highScore) {
            highScore = score;
        }
        writeLong("resources/HighScore.txt",highScore);
        return highScore;
    }

    public static void writeLong(String filename, int number) throws IOException {
        try (FileWriter dos = new FileWriter(filename)) {
            PrintWriter pw = new PrintWriter(dos);

            pw.write(number);
            pw.write("\n");
            pw.close();
        }
    }

    public static int readLong(String filename, int valueIfNotFound) {
        if (!new File(filename).canRead()) return valueIfNotFound;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            int result = Integer.parseInt(String.valueOf(dis.read()));
            return result;
        } catch (IOException ignored) {
            return valueIfNotFound;
        }
    }
}
