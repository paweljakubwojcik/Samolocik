package Bullets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import Gracz.Player;
import Program.Window;

public class BulletPaszkoProstopadly extends EnemyBullet {

	private int velocity = 3;
	static int szerokosc = Window.size_x * 2 / 3;
	static int wysokosc = 5;
	public static int liczbaKresek = 0;
	boolean strona = false;

	public BulletPaszkoProstopadly(int x, int y) {
		super(x - 2 * Window.size_x, y);
		this.damage = 10000;
		super.velocity = this.velocity;
		liczbaKresek += 2;

		Random ruch = new Random();
		if (ruch.nextInt(2) % 2 == 1) {
			strona = true;
			super.x *= -1;
			super.x += szerokosc;
		}

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
		if (x > -2 * Window.size_x && x < Window.size_x && !strona)
			x += velocity;
		else if (x > -2 * Window.size_x && x < 4 * Window.size_x && strona)
			x -= velocity;
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
