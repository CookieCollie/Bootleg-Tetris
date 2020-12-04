//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;
//import javax.sound.sampled.LineUnavailableException;
//import javax.sound.sampled.UnsupportedAudioFileException;
//import java.io.File;
//import java.io.IOException;
//
//public class AudioPlayer {
//
//    // to store current position
//    Long currentFrame;
//    Clip clip;
//
//    // current status of clip
//    String status;
//
//    AudioInputStream audioInputStream;
//
//    // constructor to initialize streams and clip
//    public AudioPlayer(String filePath) {
//        // create AudioInputStream object
//        try {
//            audioInputStream =
//                    AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
//        } catch (UnsupportedAudioFileException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // create clip reference
//        try {
//            clip = AudioSystem.getClip();
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//        }
//
//        // open audioInputStream to the clip
//        try {
//            clip.open(audioInputStream);
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        clip.loop(Clip.LOOP_CONTINUOUSLY);
//    }
//
//    public void play()
//    {
//        clip.start();
//        status = "play";
//    }
//}