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

	public BulletPlazma(int x, int y) {
		super(x, y);
		damage = (float) 1; // mniej niż 1.0 musi być
	}

	@Override
	synchronized void draw(Graphics2D g) {
		g.setColor(new Color(0, 255, 100));
		g.fillOval(x, y, size, size);
	}

}
