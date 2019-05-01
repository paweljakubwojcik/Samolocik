import java.awt.Color;
import java.awt.Graphics2D;

public class BulletEyes extends EnemyBullet {
	protected int sizeX, sizeY;
	private BossPaszko paszkow;

	BulletEyes(int pos_x, int pos_y, BossPaszko paszkow) {
		super(pos_x,pos_y);
		this.size = 13;
		this.paszkow=paszkow;
		sizeX = -(paszkow.paszko.getWidth() / 2 - size / 2)
				+ (x + paszkow.paszko.getWidth() / 2 + 5 * size / 2);
		sizeY = size * 13 / 3;

		bullets.add(this);
	}

	@Override
	synchronized void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(x + paszkow.paszko.getWidth() / 2 - size / 2, y, size / 3, size * 5);
		g.fillRect(x + paszkow.paszko.getWidth() / 2 + 5 * size / 2, y, size / 3, size * 5);
	}

	@Override
	public int[][] getPole() {
		int[][] tab = { { x + paszkow.paszko.getWidth() / 2 - size / 2, y, size / 3, size * 5 },
				{ x + paszkow.paszko.getWidth() / 2 + 5 * size / 2, y, size / 3, size * 5 } };
		return tab;
	}
}
