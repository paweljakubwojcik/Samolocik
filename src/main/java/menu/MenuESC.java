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

public class MenuESC {
	static ArrayList<MenuESC> meniESC = new ArrayList<MenuESC>();
	private static BufferedImage imPrzed, imMenu;
	public static boolean hovermenu, hoverWroc;

	public static Rectangle wroc = new Rectangle(233, 365, 353, 160);
	public static Rectangle menu = new Rectangle(233, 355 + 175, 185, 35);

	public static Rectangle menuinfo1 = new Rectangle(285, 390, 250, 30);
	public static Rectangle menuinfo2 = new Rectangle(285, 430, 250, 30);
	public static Rectangle menuinfo3 = new Rectangle(285, 470, 250, 30);

	private static final String MENU = "MENU";
	private static final String MENUINFO1 = "UTRACISZ";
	private static final String MENUINFO2 = "OBECNY";
	private static final String MENUINFO3 = "STAN GRY";
	private static final String WROC = "POWRÓT";

	public MenuESC() {
		try {
			imPrzed = ImageIO.read(getClass().getResource("/images/NaprawPrzed.png"));
			imMenu = ImageIO.read(getClass().getResource("/images/NaprawPrzed2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		meniESC.add(this);
	}

	public static void draw(Graphics2D g) {
		for (int i = 0; i < meniESC.size(); i++) {
			if (meniESC.size() != 0) {
				meniESC.get(i).drawMe(g);
			}
		}
	}

	private void drawMe(Graphics2D g) {
		g.setFont(new Font(null, 0, 30));
		if (hoverWroc) {
			g.setColor(new Color(58, 58, 58));
			g.fillRect(0, 0, Window.size_x, 20);
			g.drawImage(imMenu, 0, 20, null);
			g.setColor(Color.BLUE);
			g.setFont(new Font(null, Font.BOLD, 50));
			g.drawString(WROC, wroc.x + wroc.width / 2 - g.getFontMetrics().stringWidth(WROC) / 2,
					wroc.y + wroc.height / 2 + 50 / 2);
		} else if (hovermenu) {
			g.setColor(new Color(58, 58, 58));
			g.fillRect(0, 0, Window.size_x, 20);
			g.drawImage(imMenu, 0, 20, null);
			g.setColor(Color.RED);
			g.drawString(MENUINFO1, menuinfo1.x + menuinfo1.width / 2 - g.getFontMetrics().stringWidth(MENUINFO1) / 2,
					menuinfo1.y + menuinfo1.height / 2);
			g.drawString(MENUINFO2, menuinfo2.x + menuinfo2.width / 2 - g.getFontMetrics().stringWidth(MENUINFO2) / 2,
					menuinfo2.y + menuinfo2.height / 2);
			g.drawString(MENUINFO3, menuinfo3.x + menuinfo3.width / 2 - g.getFontMetrics().stringWidth(MENUINFO3) / 2,
					menuinfo3.y + menuinfo3.height / 2);
		} else if (!hoverWroc) {
			g.setFont(new Font(null, 0, 50));
			g.setColor(new Color(58, 58, 58));
			g.fillRect(0, 0, Window.size_x, 20);
			g.drawImage(imPrzed, 0, 20, null);
			g.setColor(Color.BLUE);
			g.drawString(WROC, wroc.x + wroc.width / 2 - g.getFontMetrics().stringWidth(WROC) / 2,
					wroc.y + wroc.height / 2 + 50 / 2);
		}

		g.setColor(new Color(200, 0, 0));
		if (!hovermenu)
			g.setFont(new Font(null, 0, 25));
		else
			g.setFont(new Font(null, Font.BOLD, 25));
		g.drawString(MENU, menu.x + menu.width / 2 - g.getFontMetrics().stringWidth(MENU) / 2,
				menu.y + menu.height - 7);
	}

	public static void wylancz() {
		meniESC.clear();
	}

	public static boolean isEmpty() {
		for (int i = 0; i < meniESC.size(); i++) {
			if (meniESC.get(i).getClass() == MenuESC.class)
				return false;
		}
		return true;
	}

}
