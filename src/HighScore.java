//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.File;
//import java.util.Scanner;
//
//
//public class HighScore {
//    Scanner scanner;
//    private int highScore;
//
//    public HighScore() {
//    }
//
//    public int compareScore(int score) throws IOException {
//        scanner = new Scanner(new File("HighScore.txt"));
//        while (scanner.hasNext()) {
//            System.out.println(scanner.nextInt());
//            highScore = Integer.parseInt(scanner.next());
//            if (score > highScore) {
//                highScore = score;
//            }
//        }
//        try (FileWriter writer = new FileWriter("HighScore.txt")) {
//            writer.flush();
//            writer.write(highScore);
//            writer.close();
//        } catch (IOException ioEx) {
//            ioEx.printStackTrace();
//        }
//        return highScore;
//    }
//}
