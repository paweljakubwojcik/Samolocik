package InterFace;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioMeneger {

	URL urls;
	AudioInputStream audioIns;
	DataLine.Info info;

	Clip[] musicClip = new Clip[1];
	Clip[] tekstClip = new Clip[1];
	Clip[] soundsClip = new Clip[2];
	boolean music = false;

	AudioInputStream audioInputStream;
	String musicSource[] = { "music//GameTrack.wav" }; // GameTrack
	String soundSource[] = { "music//icykprostopadla1.wav", "music//imykrownolegla1.wav" };
	String readSource = "music//IntroRead.wav";

	public AudioMeneger() {
		if (music) {
			try {
				for (int i = 0; i < musicSource.length; i++) {
					// Open an audio input stream.
					urls = this.getClass().getClassLoader().getResource(musicSource[i]);
					audioIns = AudioSystem.getAudioInputStream(urls);
					info = new DataLine.Info(Clip.class, audioIns.getFormat());
					musicClip[i] = (Clip) AudioSystem.getLine(info);
					musicClip[i].open(audioIns);
				}

				for (int i = 0; i < soundSource.length; i++) {
					// Open an audio input stream.
					urls = this.getClass().getClassLoader().getResource(soundSource[i]);
					audioIns = AudioSystem.getAudioInputStream(urls);
					info = new DataLine.Info(Clip.class, audioIns.getFormat());
					soundsClip[i] = (Clip) AudioSystem.getLine(info);
					soundsClip[i].open(audioIns);
				}

				urls = this.getClass().getClassLoader().getResource(readSource);
				audioIns = AudioSystem.getAudioInputStream(urls);
				info = new DataLine.Info(Clip.class, audioIns.getFormat());
				tekstClip[0] = (Clip) AudioSystem.getLine(info);
				tekstClip[0].open(audioIns);

			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();

			}
		}

	}

	public void readIntro() {
		if (music) {
			tekstClip[0].start();
		}
	}

	public void readIntroStop() {
		if (tekstClip[0] != null)
			tekstClip[0].stop();
	}

	/**
	 * 
	 * @param i
	 *            - number of track
	 */
	public void play(int i) {
		if (music) {
			// for(int j=0; j<tekstClip.length;j++)
			// tekstClip[j].stop();
			for (int j = 0; j < musicClip.length; j++)
				musicClip[j].stop();

			musicClip[i].loop(Clip.LOOP_CONTINUOUSLY);
			musicClip[i].start();
		}
	}

	/**
	 * 
	 * @param i
	 *            : 1) "i cyk prostopadła" 2) "i myk równoległa"
	 */
	public void playNoRepeat(int i) {
		if (music) {
			try {
				urls = this.getClass().getClassLoader().getResource(soundSource[i - 1]);
				audioIns = AudioSystem.getAudioInputStream(urls);
				info = new DataLine.Info(Clip.class, audioIns.getFormat());
				soundsClip[i - 1] = (Clip) AudioSystem.getLine(info);
				soundsClip[i - 1].open(audioIns);
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
			if (soundsClip[i - 1] != null)
				soundsClip[i - 1].start();
		}
	}

	public void shot() {

	}

	public void stop() {
		if (music) {
			for (int i = 0; i < musicClip.length; i++)
				musicClip[i].stop();
		}
	}

	/**
	 * 
	 * @return true if any track is currantly running
	 */
	public boolean isRunning() {
		if (music) {
			for (int j = 0; j < musicClip.length; j++)
				if (musicClip[j].isRunning())
					return true;
			return false;
		} else
			return false;
	}

}
