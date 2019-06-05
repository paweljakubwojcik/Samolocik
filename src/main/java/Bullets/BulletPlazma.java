package Bullets;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Pojedyncze części do złożenia lasera
 * 
 * @category Amunicja
 * @author adrian
 */
public class BulletPlazma extends Bullet {

	public static long delay = 10; // czas ponownego strzalu

	public static int size = 12; // kazdy rodzaj kuli musi miec swoj statyczny rozmiars

	public static final int DefaultDamage = 20;
	private static int obrazenia = DefaultDamage;

	public BulletPlazma(int x, int y) {
		super(x, y);
		damage = (int) obrazenia;
		damage = (int) (damage + (generator.nextInt((int) (2 * damage / 10)) - damage / 10));
	}

	@Override
	synchronized void draw(Graphics2D g) {
		g.setColor(new Color(0, 255, 100));
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
