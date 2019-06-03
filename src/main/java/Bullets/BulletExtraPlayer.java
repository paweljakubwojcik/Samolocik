package Bullets;

import java.awt.Color;
import java.awt.Graphics2D;

public class BulletExtraPlayer extends Bullet {

	public static long delay = 200;

	public static int size = 20; // kazdy rodzaj kuli musi miec swoj statyczny rozmiars

	public static final int DefaultDamage = (int) (1.6 * 100);
	private static int obrazenia = DefaultDamage;

	public BulletExtraPlayer(int x, int y) {
		super(x, y);

		damage = obrazenia;
		damage = (int) (damage + (generator.nextInt((int) (2 * damage / 10)) - damage / 10));

	}

	synchronized void draw(Graphics2D g) {
		g.setColor(Color.red);
		g.fillOval(x, y, size, size);
	}

	public static void setEasy() {
		obrazenia = 2 * DefaultDamage;
	}

	public static void setMedium() {
		obrazenia = 3 * DefaultDamage / 2;
	}

	public static void setHard() {
		obrazenia = DefaultDamage;
	}

}
