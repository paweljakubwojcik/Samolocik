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
	AudioInputStream audioIns, strzalAudioIns;
	DataLine.Info info, infoStrzal;

	Clip[] musicClip = new Clip[4];
	// Clip[] tekstClip = new Clip[1];
	Clip[] soundsClip = new Clip[3];
	static boolean music = true;
	int previoslyPlayed;

	AudioInputStream audioInputStream;
	String musicSource[] = { "music//IntroRead.wav", "music//GameTrack.wav", "music//GameTrackPaszko.wav",
			"music//GameTrackPrzegrana.wav" }; // GameTrack
	String soundSource[] = { "music//icykprostopadla1.wav", "music//imykrownolegla1.wav", "music//Strzal.wav" };
	// String readSource = "music//IntroRead.wav";

	public AudioMeneger() {

		try {
			for (int i = 0; i < musicSource.length; i++) {
				// Open an audio input stream.
				urls = this.getClass().getClassLoader().getResource(musicSource[i]);
				audioIns = AudioSystem.getAudioInputStream(urls);
				info = new DataLine.Info(Clip.class, audioIns.getFormat());
				musicClip[i] = (Clip) AudioSystem.getLine(info);
				musicClip[i].open(audioIns);
			}

			// for (int i = 0; i < soundSource.length; i++) {
			// // Open an audio input stream.
			// urls =
			// this.getClass().getClassLoader().getResource(soundSource[i]);
			// audioIns = AudioSystem.getAudioInputStream(urls);
			// info = new DataLine.Info(Clip.class, audioIns.getFormat());
			// soundsClip[i] = (Clip) AudioSystem.getLine(info);
			// soundsClip[i].open(audioIns);
			// }

			urls = this.getClass().getClassLoader().getResource(soundSource[2]);
			strzalAudioIns = AudioSystem.getAudioInputStream(urls);
			infoStrzal = new DataLine.Info(Clip.class, audioIns.getFormat());
			soundsClip[2] = (Clip) AudioSystem.getLine(infoStrzal);
			soundsClip[2].open(strzalAudioIns);

			// urls = this.getClass().getClassLoader().getResource(readSource);
			// audioIns = AudioSystem.getAudioInputStream(urls);
			// info = new DataLine.Info(Clip.class, audioIns.getFormat());
			// tekstClip[0] = (Clip) AudioSystem.getLine(info);
			// tekstClip[0].open(audioIns);

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();

		}
	}

	/**
	 * 
	 * @param i:
	 *            0) intro 1) main track 2) boss track 3) przegrana 4)
	 *            zwyciestwo
	 */
	public void play(int i) {
		previoslyPlayed = i;
		if (music) {
			// for(int j=0; j<tekstClip.length;j++)
			// tekstClip[j].stop();
			for (int j = 0; j < musicClip.length; j++)
				musicClip[j].stop();

			// if(i==1)
			// musicClip[i].setLoopPoints(35*44100,40*44100);
			if (i != 0)
				musicClip[i].loop(Clip.LOOP_CONTINUOUSLY);
			musicClip[i].start();
		}
	}

	/**
	 * gra sciezke po stopie // powinno byc uzywane gdy przy wychodzeniu z pauzy
	 */
	public void play() {
		if (music) {
			// for(int j=0; j<tekstClip.length;j++)
			// tekstClip[j].stop();
			for (int j = 0; j < musicClip.length; j++)
				musicClip[j].stop();

			// if(i==1)
			// musicClip[i].setLoopPoints(35*44100,40*44100);
			if (previoslyPlayed != 0)
				musicClip[previoslyPlayed].loop(Clip.LOOP_CONTINUOUSLY);
			musicClip[previoslyPlayed].start();
		}
	}

	/**
	 * 
	 * @param i
	 *            : 1) "i cyk prostopadła" 2) "i myk równoległa"
	 */
	public void playNoRepeat(int i) {
		previoslyPlayed = i - 1;
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

	public void przegrana() {
		if (music) {
			// for(int j=0; j<tekstClip.length;j++)
			// tekstClip[j].stop();
			for (int j = 0; j < musicClip.length; j++)
				musicClip[j].stop();

			musicClip[3].start();
		}
	}

	public void shot() {
		if (music && !soundsClip[2].isRunning()) {
			try {
				urls = this.getClass().getClassLoader().getResource(soundSource[2]);
				strzalAudioIns = AudioSystem.getAudioInputStream(urls);
				infoStrzal = new DataLine.Info(Clip.class, audioIns.getFormat());
				soundsClip[2] = (Clip) AudioSystem.getLine(infoStrzal);
				soundsClip[2].open(strzalAudioIns);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (soundsClip[2] != null)
				soundsClip[2].start();
		}
	}

	public void stop() {

		for (int i = 0; i < musicClip.length; i++) {
			if (musicClip[i].isRunning())

				musicClip[i].stop();

		}
	}

	public void Mute(boolean b) {
		// audio.setMusic(!b);
		music = !b;
		if (b) {
			new MessageBox("mute", 1000, 20, 550);
			stop();
		}
		if (!b && !isRunning()) {
			play();
			new MessageBox("unmute", 1000, 20,550);
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

	public void setMusic(boolean b) {
		music = b;
	}

	public void setDefault() {
		for (int i = 0; i < musicClip.length; i++) {
			musicClip[i].setFramePosition(0);
		}

	}
}
