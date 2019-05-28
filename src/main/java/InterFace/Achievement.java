package InterFace;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import Program.Window;

public class Achievement {

	public BufferedImage achievement;
	int x = Window.size_x, y = Window.size_y - 50;
	long time;
	boolean powrot = false;
	public boolean usun = false;

	/**
	 * Daje osiągnięcie za zabicie Paszka. Konstruktor wczytuje zdjęcie do pamięci,
	 * funkcja drawMe realizuje niezbędne rysunek oraz ruch
	 */
	public Achievement() {
		URL url = getClass().getResource("/images/killPaszkoAchievment.png");
		try {
			achievement = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void drawMe(Graphics2D g) {
		g.drawImage(achievement, x, y, null);
		motion();
	}

	private void motion() {
		if (x + achievement.getWidth() > Window.size_x && !powrot) {
			x--;
		} else if (!powrot) {
			time = System.currentTimeMillis();
			powrot = true;
		}

		if (System.currentTimeMillis() - time > 10000 && powrot) {
			x++;
			if (x > Window.size_x) {
				try {
					this.finalize();
					usun = true;
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}

	}

}
