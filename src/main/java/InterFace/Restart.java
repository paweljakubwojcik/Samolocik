package InterFace;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
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
	public static boolean hover = false;
	public static int restartx = 215, restarty = 355, restartsizex = 380, restartsizey = 220;

	private BufferedImage imPrzed, imPo;

	private String opis;

	public Restart(String opis) {
		this.opis = opis;

		try {
			imPrzed = ImageIO.read(getClass().getResource("/images//NaprawPrzed.png"));
			imPo = ImageIO.read(getClass().getResource("/images/NaprawPo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		res.add(this);

	}

	public static void draw(Graphics2D g) {
		for (int i = 0; i < res.size(); i++)
			res.get(i).drawMe(g);
	}

	private void drawMe(Graphics2D g) {
		g.setFont(new Font(null, 0, 30));
		if (!hover) {
			g.setColor(new Color(58, 58, 58));
			g.fillRect(0, 0, Window.size_x, 20);
			g.drawImage(imPrzed, 0, 20, null);
			g.setColor(Color.BLUE);
			g.drawString(opis, restartx + restartsizex / 2 - g.getFontMetrics().stringWidth(opis) / 2,
					restarty + restartsizey / 2);
		} else if (hover) {
			g.setColor(new Color(58, 58, 58));
			g.fillRect(0, 0, Window.size_x, 20);
			g.drawImage(imPo, 0, 20, null);
		}

		g.setColor(Color.BLUE);
		g.setFont(new Font(null, Font.BOLD | Font.ITALIC, 15));
		g.drawString("R", 420 + 8, 543);

		g.setColor(Color.BLACK);
		g.setFont(new Font(null, 0, 12));
		g.drawString("estart", 435 + 8, 543);

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
