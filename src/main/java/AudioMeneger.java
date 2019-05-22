import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioMeneger {

	Clip clips, tekstClip;
	AudioInputStream audioInputStream;
	String filePath = "music//GameTrack.wav"; // GameTrack

	String tekstReadPath = "music//IntroRead.wav";

	AudioMeneger() {
		try {
			// Open an audio input stream.
			URL urls = this.getClass().getClassLoader().getResource(filePath);
			AudioInputStream audioIns = AudioSystem.getAudioInputStream(urls);
			// Clip clips = (Clip) AudioSystem.getClip();
			DataLine.Info info = new DataLine.Info(Clip.class, audioIns.getFormat());
			clips = (Clip) AudioSystem.getLine(info);
			// System.out.println(audioIns.getFormat());
			clips.open(audioIns);

			urls = this.getClass().getClassLoader().getResource(tekstReadPath);
			audioIns = AudioSystem.getAudioInputStream(urls);
			info = new DataLine.Info(Clip.class, audioIns.getFormat());
			tekstClip = (Clip) AudioSystem.getLine(info);
			tekstClip.open(audioIns);

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();

		}

	}

	void readIntro() {
		tekstClip.start();
	}

	void play() {
		tekstClip.close();
		clips.loop(Clip.LOOP_CONTINUOUSLY);
		clips.start();
	}

	void stop() {
		clips.stop();
	}
}
