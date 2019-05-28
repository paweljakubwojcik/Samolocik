package Bullets;

import java.awt.Color;
import java.awt.Graphics2D;

import AI.BossPaszko;

public class BulletEyes extends EnemyBullet {
	protected int sizeX, sizeY;
	private BossPaszko paszkow;
	static int size = 13;

	
	public BulletEyes(int pos_x, int pos_y, BossPaszko paszkow) {
		super(pos_x, pos_y);
		this.paszkow = paszkow;
		sizeX = -(paszkow.paszko.getWidth() / 2 - size / 2) + (x + paszkow.paszko.getWidth() / 2 + 5 * size / 2);
		sizeY = size * 13 / 3;

		super.velocity = 6;

		this.damage = 10*100;

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
