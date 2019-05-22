import java.awt.Color;
import java.awt.Graphics2D;

public class BulletPellet extends Bullet {

	private float ruchBok = -3;
	private float ruchPion = +3;
	private int polx;
	private int klatka=0;
	
	static long delay = 1000;

	static int size = 12; // kazdy rodzaj kuli musi miec swoj statyczny rozmiars

	public BulletPellet(int x, int y) {
		super(x, y);
		damage = 3;
	}
	
	/**
	 * Tworzenie naboi srotów
	 * @param x Położenie X Start
	 * @param y Położenie Y Start
	 * @param Vx Ruch w X
	 * @param Vy Spowolnienie Ruchu w Y
	 */
	public BulletPellet(int x, int y, float Vx, float Vy) {
		super(x, y);
		damage = 2;
		this.ruchBok = Vx;
		this.ruchPion = Vy;
		this.polx=x;
	}

	synchronized void draw(Graphics2D g) {
		move();
		g.setColor(Color.GRAY);
		g.fillOval(x, y, size, size);
	}
	
	void move() {
		klatka++;
		super.x=(int) ruchBok*klatka+polx;
		super.y+=ruchPion;
	}
	
}
