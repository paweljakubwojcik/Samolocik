import java.awt.Color;
import java.awt.Graphics2D;

public class BulletExtraPlayer extends Bullet {

	static long delay = 400;

	static int size = 20; // kazdy rodzaj kuli musi miec swoj statyczny rozmiars

	public BulletExtraPlayer(int x, int y) {
		super(x, y);

		damage = 2;

	}

	synchronized void draw(Graphics2D g) {
		g.setColor(Color.red);
		g.fillOval(x, y, size, size);
	}

}
