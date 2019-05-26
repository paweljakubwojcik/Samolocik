package Bullets;

import java.awt.Color;
import java.awt.Graphics2D;

import Program.Window;

/**
 * Pojedyncze kule amunicji do shotguna
 * 
 * @category Amunicja
 * @author adrian
 */

public class BulletPellet extends Bullet {

	private float ruchBok = -3;
	private float ruchPion = +3;
	private int polx, poly;
	private int klatka = 0;

	public static long delay = 1000; // czas ponownego strzalu

	public static int size = 12; // kazdy rodzaj kuli musi miec swoj statyczny rozmiars

	/**
	 * Tworzenie naboi srotów
	 * 
	 * @param x  Położenie X Start
	 * @param y  Położenie Y Start
	 * @param Vx Ruch w X
	 * @param Vy Spowolnienie Ruchu w Y
	 */
	public BulletPellet(int x, int y, float Vx, float Vy) {
		super(x, y);
		damage = 1;
		this.ruchBok = Vx;
		this.ruchPion = Vy;
		this.polx = x;
		this.poly = y;
	}

	@Override
	synchronized void draw(Graphics2D g) {
		g.setColor(new Color(0, 200, 200));
		g.fillOval(x, y, size, size);
		// rysuje okrąg po którym poruszają się kule
		// g.drawOval(polx - 10 * klatka, (poly - 10 * klatka) * 1, 2 * (klatka * 10), 2
		// * (klatka * 10));
	}

	@Override
	void MyMotion() {
		klatka++;

		if (y > 0 && y < Window.size_y && x > 0 && x < Window.size_x) {
			super.x = (int) (ruchBok * klatka + polx);
			super.y = (int) (ruchPion * klatka + poly);
		} else
			try {
				bullets.remove(this);
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
	}

}
