package InterFace;

import java.awt.Color;
import java.awt.Graphics2D;

import Program.Samolotoszczalec;
import Program.Window;

/**
 * 
 * @author Pawel & Adrian all right reserved
 *
 */

public class Intro {
	static int opacity = 255;
	static Window win = Samolotoszczalec.win;

	// po znaku % podany jest czas czekania w milisekundach,
	static String message = "Data:%1000 dawno dawnotemu/nMiejsce: PW GG 226/nMisja:%1000 zdać%2000/nza wszelką cene";
	static MessageTypingIn m = new MessageTypingIn(message); // zasady pisania
																// wiadomosci w
																// java docu

	static boolean itHappened = false;
	static boolean introAudio = false;
	static boolean zmienna = true;

	@SuppressWarnings("static-access")
	public static void draw(Graphics2D g) {

		if (zmienna) {
			new MessageBox("Gra powstała w celach humorystycznych", 3000, 300, 500, "red");
			new MessageBox("Nie ma na celu oczerniać osób trzecich ", 3000, 300, 600, "red");
			zmienna = false;
		}

		if (m.opacity > 0) {
			g.setColor(new Color(0, 0, 0, m.opacity));
			g.fillRect(0, 0, win.size_x, win.size_y);
			Sterowanie.PominDraw(g);
			// m.draw(g); jest wykonywane w Window
			if (!introAudio) {
				win.audio.play(0); // to jest intro
				introAudio = true;

			}
		} else {
			win.setIntro(false);
			new MessageBox("Enjoy ", 5000, 300, 600, "red");
		}

		if (m.isFadeOutBegun && !itHappened) {
			win.audio.play(1);
			itHappened = true;
			win.setInstrukcja(true);
		}
	}
}
