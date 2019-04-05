import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Player {

	Window win;
	BufferedImage statek;

	int velocity = 5; // predkosc samolotu
	int x, y;

	Player(Window win, int x, int y) {
		this.win = win;
		this.x = x;
		this.y = y;

		URL url = getClass().getResource("samolot.png");
		try {
			statek = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g) {
		g.drawImage(statek, x, y, null);
	}

	public void strzal() {
		AllBullets.add(new Bullet(x, y));
	}

	public void moveRight() {
		if (x + statek.getWidth() < win.size_x) {
			x += velocity;
		}
	}

	public void moveLeft() {
		if (x > 0) {
			x -= velocity;
		}
	}

}
