package Bullets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import Gracz.Player;
import InterFace.MessageBox;
import Program.Window;

public class BulletPaszkoProstopadly extends EnemyBullet {

	private int velocity = 3;
	static int szerokosc = Window.size_x * 2 / 3;
	static int wysokosc = 5;
	public static int liczbaKresek = 0;
	boolean strona = false;
	private static final int DomyslnyDamage = 100;
	private static int obrazenia = 100;

	public BulletPaszkoProstopadly(int x, int y) {
		super(x - 2 * Window.size_x, y);
		this.damage = obrazenia; // 10000*100
		this.damage = (int) (damage + (generator.nextInt((int) (2 * damage / 10)) - damage / 10));
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
		// int[][] tab = { { x, y, szerokosc, wysokosc } };
		int[][] tab = new int[10][4];
		for (int i = 0; i < 10; i++) {
			tab[i][0] = x + i * szerokosc / 10;
			tab[i][1] = y;
			tab[i][2] = szerokosc / 10;
			tab[i][3] = wysokosc;
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
				if (!strona)
					new MessageBox(Integer.toString((int) -damage), 1000, player.getX() - 35, y - 10, "RED");
				else
					new MessageBox(Integer.toString((int) -damage), 1000, player.getX() + player.getWidth(), y - 10,
							"RED");
			}
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

	public static void setEasy() {
		obrazenia = DomyslnyDamage / 4;
	}

	public static void setMedium() {
		obrazenia = DomyslnyDamage * 2 / 3;
	}

	public static void setHard() {
		obrazenia = DomyslnyDamage;
	}

}
