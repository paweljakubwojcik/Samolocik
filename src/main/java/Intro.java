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

	//po znaku % podany jest czas czekania w sekundach,
	//przerobie to potem tak zeby nie trzeba bylo uzywac tablicy a np rozpoznawalo znak '\n' i zaczynalo po tym od nowej linii
	static String[] message = { "Data:%1 dawno dawnotemu", "Miejsce: PW GG 226", "Misja:%1 zdaæ%2" , "za wszelka cene" };
	static List<String> shownMessage = new ArrayList<>();

	static long time = System.currentTimeMillis();
	static long tempoLiter = 100, fadeOutTempo = 16;
	static int linijka = 0, literka = 0;
	@SuppressWarnings("static-access")
	static int x = win.size_x / 4, y = win.size_y / 4;

	@SuppressWarnings("static-access")
	public static void draw(Graphics2D g) {
		g.setColor(new Color(0, 0, 0, opacity));
		g.fillRect(0, 0, win.size_x, win.size_y);
		g.setFont(new Font(null, Font.TYPE1_FONT, 20));
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
	static long pauza = 0;

	private static boolean animate(String s) {

		if (System.currentTimeMillis() - time > tempoLiter + pauza && s.length() > literka) {

			if (s.charAt(literka) == '%') {
				literka++;
				pauza = 1000 * ((int) s.charAt(literka)-48);
				literka++;
			}

			else {
				pauza = 0;
				buffor += s.charAt(literka);
			}
			if (shownMessage.size() > linijka)
				shownMessage.remove(linijka);
			shownMessage.add(buffor);
			literka++;
			time = System.currentTimeMillis();
		}

		if (s.length() <= literka) {
			pauza = 500;
			literka = 0;
			buffor = "";
			return false;
		} else
			return true;

	}

}
