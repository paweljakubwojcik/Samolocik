import java.awt.Color;
import java.awt.Graphics2D;

public class BulletExtra extends Bullet {

	public BulletExtra(int x, int y, int velocity) {
		super(x, y, velocity);
		this.size = 20;
		damage=10;

	}

	synchronized void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillOval(x, y, size, size);
	}

}
