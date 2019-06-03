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
	int miejsceWystrzalu;

	public static long delay = 400;

	public static int size = 15; // kazdy rodzaj kuli musi miec swoj statyczny
									// rozmiars
	int size2;
	int i = 1;

	int rk, gk, bk;

	private boolean fajerwerki = false;

	public Granade(int x, int y) {
		super(x, y);
		miejsceWystrzalu = y;
	}

	public Granade(int x, int y, boolean fajerwerki) {
		super(x, y);
		this.fajerwerki = fajerwerki;
		miejsceWystrzalu = y;
		Granade.size = generator.nextInt(10) + 1;
		rk = generator.nextInt(255);
		gk = generator.nextInt(255);
		bk = generator.nextInt(255);
	}

	synchronized void draw(Graphics2D g) {
		if (!fajerwerki) {
			g.setColor(new Color(255, 255, 0, opacity));
			g.fillOval(x, y, size, size);
		} else {
			g.setColor(new Color(rk, gk, bk, opacity));
			g.fillOval(x, y, size, size);
		}
	}

	@SuppressWarnings("static-access")
	@Override
	void MyMotion() {

		if (y > miejsceWystrzalu - generator.nextInt(200) - win.size_y / 3) {
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
			else if (System.currentTimeMillis() - time2 > 100 && !fajerwerki) {
				new GranadeExplosion(x + generator.nextInt(25 * i) - 25 * i / 2,
						y + generator.nextInt(25 * i) - 25 * i / 2);
				time2 = System.currentTimeMillis();
				i++;
			} else if (System.currentTimeMillis() - time2 > 100 && fajerwerki) {
				new GranadeExplosion(x + generator.nextInt(25 * i) - 25 * i / 2,
						y + generator.nextInt(25 * i) - 25 * i / 2, fajerwerki);
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

	public void aktualizujCzas(long t) {
		time += t;
	}

	public static void aktualizujCzasy(long t) {
		for (int i = 0; i < bullets.size(); i++)
			if (bullets.get(i).getClass() == Granade.class) {
				Granade o = (Granade) bullets.get(i);
				o.aktualizujCzas(t);
			}

	}
}
