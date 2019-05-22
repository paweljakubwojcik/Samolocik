import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Konfiguracja Srutu oraz Lewy naboj
 * @author 7Adrian
 *
 */
public class BulletPelletLeft extends Bullet {
	
	protected int ruchBok = -3;
	protected int ruchPion = +3;
	
	static long delay = 1000;

	static int size = 12; // kazdy rodzaj kuli musi miec swoj statyczny rozmiars

	public BulletPelletLeft(int x, int y) {
		super(x, y);
		damage = 3;
	}

	synchronized void draw(Graphics2D g) {
		move();
		g.setColor(Color.GRAY);
		g.fillOval(x, y, size, size);
	}
	
	void move() {
		super.x+=ruchBok;
		super.y+=ruchPion;
	}
}
