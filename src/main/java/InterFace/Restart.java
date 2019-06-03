package InterFace;

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
 * Okienko Restartu
 * 
 * @author adrian
 *
 */
public class Restart {
	static ArrayList<Restart> res = new ArrayList<Restart>();
	public static boolean hoverRestart = false, hoverRestart2 = false, hoverMenu = false;

	public static Rectangle restart = new Rectangle(233, 365, 353, 160);
	public static Rectangle restart2 = new Rectangle(418, 524, 78, 23);
	public static Rectangle Menu = new Rectangle(295, 355 + 172, 120, 35);

	private BufferedImage imPrzed, imPo, imMenu;

	private String opis;

	public Restart(String opis) {
		this.opis = opis;

		try {
			imPrzed = ImageIO.read(getClass().getResource("/images/NaprawPrzed.png"));
			imPo = ImageIO.read(getClass().getResource("/images/NaprawPo2.png"));
			imMenu = ImageIO.read(getClass().getResource("/images/NaprawPrzed2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		res.add(this);

	}

	public static void draw(Graphics2D g) {
		for (int i = 0; i < res.size(); i++) {
			if (res.size() != 0) {
				res.get(i).drawMe(g);
			}
		}
	}

	private void drawMe(Graphics2D g) {
		g.setFont(new Font(null, 0, 30));
		if (hoverRestart) {
			g.setColor(new Color(58, 58, 58));
			g.fillRect(0, 0, Window.size_x, 20);
			g.drawImage(imPo, 0, 20, null);
		} else if (hoverMenu) {
			g.setColor(new Color(58, 58, 58));
			g.fillRect(0, 0, Window.size_x, 20);
			g.drawImage(imMenu, 0, 20, null);
			g.setColor(Color.BLUE);
			g.drawString("EKRAN", restart.x + restart.width / 2 - g.getFontMetrics().stringWidth("EKRAN") / 2,
					restart.y + restart.height / 2);
			g.drawString("STARTOWY?", restart.x + restart.width / 2 - g.getFontMetrics().stringWidth("STARTOWY?") / 2,
					restart.y + restart.height / 2 + 35);
		} else if (!hoverRestart) {
			g.setColor(new Color(58, 58, 58));
			g.fillRect(0, 0, Window.size_x, 20);
			g.drawImage(imPrzed, 0, 20, null);
			g.setColor(Color.BLUE);
			g.drawString(opis, restart.x + restart.width / 2 - g.getFontMetrics().stringWidth(opis) / 2,
					restart.y + restart.height / 2);
		}

		g.setColor(Color.BLUE);
		if (!hoverRestart2)
			g.setFont(new Font(null, Font.BOLD | Font.ITALIC, 15));
		else
			g.setFont(new Font(null, Font.BOLD | Font.ITALIC, 16));
		g.drawString("R", 420 + 8, 543);

		g.setColor(Color.BLACK);
		if (!hoverRestart2)
			g.setFont(new Font(null, 0, 12));
		else
			g.setFont(new Font(null, Font.BOLD | Font.ITALIC, 13));
		g.drawString("estart", 435 + 8, 543);

		g.setColor(Color.blue);
		if (!hoverMenu)
			g.setFont(new Font(null, 0, 35));
		else
			g.setFont(new Font(null, Font.BOLD, 35));
		g.drawString("MENU", Menu.x + Menu.width / 2 - g.getFontMetrics().stringWidth("MENU") / 2,
				Menu.y + Menu.height - 3);

//		g.setColor(Color.RED);
//		g.draw(restart);
//		g.draw(Menu);
//		g.draw(restart2);

	}

	public static void wylancz() {
		res.clear();
	}

	public static boolean isEmpty() {
		for (int i = 0; i < res.size(); i++) {
			if (res.get(i).getClass() == Restart.class)
				return false;
		}
		return true;
	}

}
