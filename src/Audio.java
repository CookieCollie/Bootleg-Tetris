import javax.sound.sampled.*;
import java.io.IOException;
import java.io.File;

public class Audio {
	long currFrame;
	Clip music;
	
	String status;
	
	AudioInputStream musicInput;
	private String filePath;
	
	public Audio(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		// TODO Auto-generated constructor stub
		this.filePath = filePath;
		musicInput = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
		
		music = AudioSystem.getClip();
		music.open(musicInput);
		music.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void playMusic() {
		music.start();
	}

	public void playMusicDelay() {
		music.start();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		music.stop();
	}
	
	public void pauseMusic() {
		this.currFrame = this.music.getMicrosecondPosition();
		music.stop();
	}
	
	public void resumeMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		music.close();
		resetAudioStream(filePath);
		music.setMicrosecondPosition(currFrame);
		this.playMusic();
	}
	
	public void stopMusic() {
		currFrame = 0L;
		music.stop();
		music.close();
	}
	
	public void resetAudioStream(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		musicInput = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
		music.open(musicInput);
		music.loop(Clip.LOOP_CONTINUOUSLY);
	}
}
