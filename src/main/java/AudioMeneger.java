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
	Clip[] clips = new Clip[1];
	Clip[] tekstClip = new Clip[1];
	
	AudioInputStream audioInputStream;
	String filePath[] = {"music//GameTrack.wav"}; // GameTrack

	String tekstReadPath = "music//IntroRead.wav";

	AudioMeneger() {
		try {
			for(int i=0; i<filePath.length;i++){
			// Open an audio input stream.
			urls = this.getClass().getClassLoader().getResource(filePath[i]);
			audioIns = AudioSystem.getAudioInputStream(urls);
			// Clip clips = (Clip) AudioSystem.getClip();
			info = new DataLine.Info(Clip.class, audioIns.getFormat());
			clips[i] = (Clip) AudioSystem.getLine(info);
			// System.out.println(audioIns.getFormat());
			clips[i].open(audioIns);
			}
			
			urls = this.getClass().getClassLoader().getResource(tekstReadPath);
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

	void readIntro() {
		tekstClip[0].start();
	}

	/**
	 * 
	 * @param i - number of track
	 */
	void play(int i) {
		//for(int j=0; j<tekstClip.length;j++)
		//tekstClip[j].stop();
		for(int j=0; j<clips.length;j++)
			clips[j].stop();
		
		clips[i].loop(Clip.LOOP_CONTINUOUSLY);
		clips[i].start();
	}

	void stop() {
		for(int i=0;i<clips.length;i++)
		clips[i].stop();
	}
	
	/**
	 * 
	 * @return true if any track is currantly running
	 */
	boolean isRunning()
	{
		for(int j=0; j<clips.length;j++)
			if(clips[j].isRunning()) return true;
		return false;
	}
}
