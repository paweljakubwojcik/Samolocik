import java.awt.Color;
import java.awt.Graphics2D;

public class BulletExtra extends Bullet {

	public BulletExtra(int x, int y, int velocity) {
		super(x, y, velocity);
		this.size = 20;

	}

	synchronized void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillOval(x + win.statek1.statek.getWidth() / 2 - size / 2, y, size, size);
	}

}
