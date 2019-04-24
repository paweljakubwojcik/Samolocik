import java.awt.Color;
import java.awt.Graphics2D;

public class BulletEyes extends Bullet {
	protected int sizeX, sizeY;

	BulletEyes(int pos_x, int pos_y, int velocity) {
		this.x = pos_x;
		this.y = pos_y;
		this.velocity = velocity;
		this.size = 13;
		
		sizeX = -(win.paszkow.paszko.getWidth() / 2 - size / 2)
				+ (x + win.paszkow.paszko.getWidth() / 2 + 5 * size / 2);
		sizeY = size * 13 / 3;

		bullets.add(this);
	}

	@Override
	synchronized void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(x + win.paszkow.paszko.getWidth() / 2 - size / 2, y, size / 3, size * 5);
		g.fillRect(x + win.paszkow.paszko.getWidth() / 2 + 5 * size / 2, y, size / 3, size * 5);
	}

	@Override
	public int[][] getPole() {
		int[][] tab = { { x + win.paszkow.paszko.getWidth() / 2 - size / 2, y, size / 3, size * 5 },
				{ x + win.paszkow.paszko.getWidth() / 2 + 5 * size / 2, y, size / 3, size * 5 } };
		return tab;
	}
}
