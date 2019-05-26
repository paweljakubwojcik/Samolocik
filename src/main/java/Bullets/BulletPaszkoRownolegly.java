package Bullets;

import java.awt.Color;
import java.awt.Graphics2D;

import Gracz.Player;
import Program.Window;

public class BulletPaszkoRownolegly extends EnemyBullet {

	private int velocity = 3;
	static int szerokosc = 5;
	static int wysokosc = Window.size_y * 2 / 3;
	public static int liczbaKresek = 0;

	public BulletPaszkoRownolegly(int x, int y) {
		super(x, y - 2 * Window.size_y);
		this.damage = 10000;
		super.velocity = this.velocity;
		liczbaKresek += 2;

		bullets.add(this);
	}

	@Override
	synchronized void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(x, y, szerokosc, wysokosc);

	}

	@Override
	public int[][] getPole() {
		int[][] tab = { { x, y, szerokosc, wysokosc } };
		return tab;
	}

	@Override
	public void collision(Object o) {
		if (o.getClass() == Player.class) {
			Player player = (Player) o;
			if (!player.shield)
				player.health -= damage;
		}
	}

	@Override
	void MyMotion() {
		if (y > -2 * Window.size_y && y < Window.size_y)
			y += velocity;
		else
			try {
				liczbaKresek--;
				bullets.remove(this);
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
	}

}
