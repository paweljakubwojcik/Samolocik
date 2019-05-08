import java.awt.Color;
import java.awt.Graphics2D;

public class BulletExtraPlayer extends Bullet {

	public BulletExtraPlayer(int x, int y) {
		super(x, y);
		this.size = 20;
		damage=2;

	}

	synchronized void draw(Graphics2D g) {
		g.setColor(Color.red);
		g.fillOval(x, y, size, size);
	}

}
