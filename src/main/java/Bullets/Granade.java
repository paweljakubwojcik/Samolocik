package Bullets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Granade extends Bullet {

	boolean bang = false;
	int opacity = 255;
	long time = System.currentTimeMillis();
	long time2 = System.currentTimeMillis();
	Random generator = new Random();

	public static long delay = 400;

	public static int size = 15; // kazdy rodzaj kuli musi miec swoj statyczny
									// rozmiars
	int size2;
	int i = 1;

	public Granade(int x, int y) {
		super(x, y);

	}

	synchronized void draw(Graphics2D g) {
		g.setColor(new Color(255, 255, 0, opacity));
		g.fillOval(x, y, size, size);

	}

	@SuppressWarnings("static-access")
	@Override
	void MyMotion() {

		if (System.currentTimeMillis() - time < 600) {
			if (y > 0 && y < win.size_y)
				y -= velocity;
			else
				try {
					bullets.remove(this);
					this.finalize();

				} catch (Throwable e) {
					e.printStackTrace();
				}
		} else {

			opacity = 0;
			if (System.currentTimeMillis() - time > 1000)
				Bullet.bullets.remove(this);
			else if (System.currentTimeMillis() - time2 > 100) {
				new GranadeExplosion(x + generator.nextInt(25 * i) - 25 * i / 2,
						y + generator.nextInt(25 * i) - 25 * i / 2);
				time2 = System.currentTimeMillis();
				i++;
			}

		}
	}

	@Override
	public int[][] getPole() {

		int[][] tab = { { 0, 0, 0 } };
		return tab;
	}

	public void detonate() {
		bang = true;
	}
}
