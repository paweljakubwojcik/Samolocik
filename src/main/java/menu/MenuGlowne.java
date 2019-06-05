package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Program.Window;

/**
 * Okienko z MenuGĹ‚Ăłwnym
 * 
 * @author adrian
 *
 */
public class MenuGlowne {
	static ArrayList<MenuGlowne> meni = new ArrayList<MenuGlowne>();
	private static BufferedImage imPrzed, imMenu;

	public static boolean hoverStart, hoverWyjscie, hoverLatwy, hoverSredni, hoverTrudny;

	public static Rectangle start = new Rectangle(233, 365, 353, 160);
	public static Rectangle wyjscie = new Rectangle(233, 355 + 175, 185, 35);
	public static Rectangle latwy = new Rectangle(285, 380, 250, 40);
	public static Rectangle sredni = new Rectangle(285, 430, 250, 40);
	public static Rectangle trudny = new Rectangle(285, 480, 250, 40);

	private static final String WYJSCIE = "WYJŚCIE";
	private static final String GRAJ = "G R A J";
	private static final String WYJSCIEINFO1 = "Naprawde chcesz";
	private static final String WYJSCIEINFO2 = "wyjść? :'(";
	private static final String LATWY = "ŁATWY";
	private static final String SREDNI = "ŚREDNI";
	private static final String TRUDNY = "TRUDNY";

	public MenuGlowne() {

		try {
			imPrzed = ImageIO.read(getClass().getResource("/images/NaprawPrzed.png"));
			imMenu = ImageIO.read(getClass().getResource("/images/NaprawPrzed2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		meni.add(this);
	}

	public static void draw(Graphics2D g) {
		for (int i = 0; i < meni.size(); i++) {
			if (meni.size() != 0) {
				meni.get(i).drawMe(g);
			}
		}
	}

	private void drawMe(Graphics2D g) {
		g.setFont(new Font(null, 0, 30));
		if (hoverStart) {
			g.setColor(new Color(58, 58, 58));
			g.fillRect(0, 0, Window.size_x, 20);
			g.drawImage(imMenu, 0, 20, null);

			g.setColor(Color.BLUE);
			if (hoverLatwy) {
				g.setFont(new Font(null, Font.BOLD, 35));
				g.drawString(LATWY, latwy.x + latwy.width / 2 - g.getFontMetrics().stringWidth(LATWY) / 2,
						latwy.y + latwy.height - 3);
			} else {
				g.setFont(new Font(null, 0, 35));
				g.drawString(LATWY, latwy.x + latwy.width / 2 - g.getFontMetrics().stringWidth(LATWY) / 2,
						latwy.y + latwy.height - 3);
			}
			if (hoverSredni) {
				g.setFont(new Font(null, Font.BOLD, 35));
				g.drawString(SREDNI, sredni.x + sredni.width / 2 - g.getFontMetrics().stringWidth(SREDNI) / 2,
						sredni.y + sredni.height - 3);
			} else {
				g.setFont(new Font(null, 0, 35));
				g.drawString(SREDNI, sredni.x + sredni.width / 2 - g.getFontMetrics().stringWidth(SREDNI) / 2,
						sredni.y + sredni.height - 3);
			}
			if (hoverTrudny) {
				g.setFont(new Font(null, Font.BOLD, 35));
				g.drawString(TRUDNY, trudny.x + trudny.width / 2 - g.getFontMetrics().stringWidth(TRUDNY) / 2,
						trudny.y + trudny.height - 3);
			} else {
				g.setFont(new Font(null, 0, 35));
				g.drawString(TRUDNY, trudny.x + trudny.width / 2 - g.getFontMetrics().stringWidth(TRUDNY) / 2,
						trudny.y + trudny.height - 3);
			}
		} else if (hoverWyjscie) {
			g.setColor(new Color(58, 58, 58));
			g.fillRect(0, 0, Window.size_x, 20);
			g.drawImage(imMenu, 0, 20, null);
			g.setColor(Color.BLUE);
			g.drawString(WYJSCIEINFO1, start.x + start.width / 2 - g.getFontMetrics().stringWidth(WYJSCIEINFO1) / 2,
					start.y + start.height / 2);
			g.drawString(WYJSCIEINFO2, start.x + start.width / 2 - g.getFontMetrics().stringWidth(WYJSCIEINFO2) / 2,
					start.y + start.height / 2 + 35);
		} else if (!hoverStart) {
			g.setFont(new Font(null, 0, 50));
			g.setColor(new Color(58, 58, 58));
			g.fillRect(0, 0, Window.size_x, 20);
			g.drawImage(imPrzed, 0, 20, null);
			g.setColor(Color.BLUE);
			g.drawString(GRAJ, start.x + start.width / 2 - g.getFontMetrics().stringWidth(GRAJ) / 2,
					start.y + start.height / 2 + 50 / 2);
		}

		g.setColor(new Color(200, 0, 0));
		if (!hoverWyjscie)
			g.setFont(new Font(null, 0, 25));
		else
			g.setFont(new Font(null, Font.BOLD, 25));
		g.drawString(WYJSCIE, wyjscie.x + wyjscie.width / 2 - g.getFontMetrics().stringWidth(WYJSCIE) / 2,
				wyjscie.y + wyjscie.height - 7);

	}

	public static void wylancz() {
		meni.clear();
		
	}

	public static boolean isEmpty() {
		for (int i = 0; i < meni.size(); i++) {
			if (meni.get(i).getClass() == MenuGlowne.class)
				return false;
		}
		return true;
	}
}
