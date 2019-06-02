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
	static String message = "Data:%1000 dawno dawnotemu/nMiejsce: PW GG 226/nMisja:%1000 zda�%2000/nza wszelka cene";
	static MessageTypingIn m = new MessageTypingIn(message); // zasady pisania
																// wiadomosci w
																// java docu

	static boolean itHappened = false;

	@SuppressWarnings("static-access")
	public static void draw(Graphics2D g) {
		if (m.opacity > 0) {
			g.setColor(new Color(0, 0, 0, m.opacity));
			g.fillRect(0, 0, win.size_x, win.size_y);
			Sterowanie.PominDraw(g);
			// m.draw(g); jest wykonywane w Window
		} else
			win.setIntro(false);

		if (m.isFadeOutBegun && !itHappened) {
			win.audio.play(1);
			itHappened = true;
			win.setInstrukcja(true);
		}
	}
}
