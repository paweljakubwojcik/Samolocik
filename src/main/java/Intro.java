import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Pawel & Adrian all right reserved
 *
 */

public class Intro {
	static int opacity = 255;
	static Window win = Samolotoszczalec.win;

	// po znaku % podany jest czas czekania w milisekundach,
	static String message = "Data:%1000 dawno dawnotemu/n Miejsce: PW GG 226/n Misja:%1000 zdaæ%2000/n za wszelka cene";
	static MessageTypingIn m = new MessageTypingIn(message); // zasady pisania
																// wiadomosci w
																// java docu

	static boolean itHappened = false;

	@SuppressWarnings("static-access")
	static void draw(Graphics2D g) {
		if (m.opacity > 0) {
			g.setColor(new Color(0, 0, 0, m.opacity));
			g.fillRect(0, 0, win.size_x, win.size_y);
			// m.draw(g); jest wykonywane w Window
		} else
			win.setIntro(false);

		if (m.isFadeOutBegun && !itHappened) {
			win.audio.stop();
			win.audio.play(0);
			itHappened = true;
		}
	}
}
