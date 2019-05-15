import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Pawel & Adrian
 * all right reserved
 *
 */


public class Intro {
	static int opacity = 255;
	static Window win = Samolotoszczalec.win;

	static String[] message = { "prosze zobaczyc jak ten tekst sie fajnie pisze", "taki tekst zasluguje na 5",
			"ten tekst ma byc napisany po literce" };
	static List<String> shownMessage = new ArrayList<>();

	static long time = System.currentTimeMillis();
	static long tempoLiter = 100, fadeOutTempo = 16;
	static int linijka = 0, literka = 0;
	@SuppressWarnings("static-access")
	static int x = win.size_x / 2, y = win.size_y / 2;

	@SuppressWarnings("static-access")
	public static void draw(Graphics2D g) {
		g.setColor(new Color(0, 0, 0, opacity));
		g.fillRect(0, 0, win.size_x, win.size_y);
		g.setColor(new Color(0, 255, 0, opacity));
		for (int i = 0; i < shownMessage.size(); i++) {
			g.drawString(shownMessage.get(i), x, y + i * g.getFontMetrics().getHeight());
		}
		if (linijka < message.length) {
			if (!animate(message[linijka])) {
				linijka++;
			}
		} else {
			if (System.currentTimeMillis() - time > fadeOutTempo && opacity > 0) {
				opacity--;
				time = System.currentTimeMillis();
			} else if (opacity == 0) {
				win.setIntro(false);
			}
		}
	}

	static String buffor = "";

	private static boolean animate(String s) {
		if (System.currentTimeMillis() - time > tempoLiter && s.length() > literka) {
			buffor += s.charAt(literka);
			if (shownMessage.size() > linijka)
				shownMessage.remove(linijka);
			shownMessage.add(buffor);
			literka++;
			time = System.currentTimeMillis();
		}
		if (s.length() <= literka) {
			literka = 0;
			buffor = "";
			return false;
		} else
			return true;

	}

}
