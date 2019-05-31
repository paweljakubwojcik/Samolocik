package Bullets;

import java.awt.Color;
import java.awt.Graphics2D;

import Gracz.Player;
import InterFace.MessageBox;
import Program.Window;

public class BulletPaszkoRownolegly extends EnemyBullet {

	private int velocity = 3;
	static int szerokosc = 5;
	static int wysokosc = Window.size_y * 2 / 3;
	public static int liczbaKresek = 0;

	public BulletPaszkoRownolegly(int x, int y) {
		super(x, y - 2 * Window.size_y);
		this.damage = 100;
		this.damage = (int) (damage + (generator.nextInt((int) (2 * damage / 10)) - damage / 10));
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
		// int[][] tab = { { x, y, szerokosc, wysokosc } };
		int[][] tab = new int[10][4];
		for (int i = 0; i < 10; i++) {
			tab[i][0] = x;
			tab[i][1] = y + i * wysokosc / 10;
			tab[i][2] = szerokosc;
			tab[i][3] = wysokosc / 10;
		}
		return tab;
	}

	@Override
	public void collision(Object o) {
		if (o.getClass() == Player.class) {
			Player player = (Player) o;

			if (!player.shield) {
				player.health -= damage;
				player.obrazenia = true;
				player.klatkiObrazenia = 0;
				new MessageBox(Integer.toString((int) -damage), 1000, x - 10, player.getY(), "RED");
			}
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
