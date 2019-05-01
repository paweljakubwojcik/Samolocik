import java.awt.Color;
import java.awt.Graphics2D;

public class BulletExtra extends EnemyBullet {

	public BulletExtra(int x, int y) {
		super(x, y);
		this.size = 20;
		damage=10;

	}

	synchronized void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillOval(x, y, size, size);
	}

}
