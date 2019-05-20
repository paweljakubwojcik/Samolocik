import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioMeneger {

	Clip clips;
	AudioInputStream audioInputStream;
	String filePath = "music//GameTrack.wav"; // GameTrack

	void playMusic() {
		try {
			// Open an audio input stream.
			URL urls = this.getClass().getClassLoader().getResource(filePath);
			AudioInputStream audioIns = AudioSystem.getAudioInputStream(urls);
			// Clip clips = (Clip) AudioSystem.getClip();
			DataLine.Info info = new DataLine.Info(Clip.class, audioIns.getFormat());
			clips = (Clip) AudioSystem.getLine(info);
			//System.out.println(audioIns.getFormat());
			clips.open(audioIns);

			clips.loop(Clip.LOOP_CONTINUOUSLY);
			// clips.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();

		}

//		File sound = new File(filePath).getAbsoluteFile();
//		clips = AudioSystem.getClip();
//		audioInputStream = AudioSystem.getAudioInputStream(sound);
//		clips.open(audioInputStream);
//		clips.loop(Clip.LOOP_CONTINUOUSLY);
//		clips.start();

	}
}
