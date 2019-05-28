package AI;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Bullets.Bullet;
import Bullets.BulletExtraPlayer;
import Bullets.BulletPellet;
import Bullets.BulletPlazma;
import Bullets.Drop;
import Bullets.Granade;
import Program.Window;
import Rozgrywka.Collisionable;

public abstract class Enemy extends Collisionable {

	public int x;
	public int y;
	float health;
	public int width;
	public int height;
	int velocity_x, velocity_y;
	Window win;
	static Random generator = new Random();
	public static List<Enemy> enemies = new ArrayList<>();

	public abstract void drawMe(Graphics2D g);

	public abstract void myMotion();

	/**
	 * 
	 * @param x
	 * @param y
	 */
	Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		enemies.add(this);
	}

	/**
	 * 
	 * @param g
	 */
	public static void draw(Graphics2D g) {
		for (int i = enemies.size() - 1; i >= 0; i--) {
			enemies.get(i).drawMe(g);
		}
	}

	public static void motion() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).myMotion();
		}
	}

	@Override
	public int[][] getPole() {
		int[][] tab = { { x, y, width, height } };
		return tab;
	}

	@Override
	public void collision(Object o) {

		if (o.getClass() == Bullet.class || o.getClass() == BulletExtraPlayer.class
				|| o.getClass() == BulletPellet.class || o.getClass() == Granade.class
				|| o.getClass() == BulletPlazma.class) {
			Bullet bullet = (Bullet) o;
			this.health -= bullet.damage;
		}

		if (this.health <= 0) {
			Drop.generateDrop(this);
			Enemy.enemies.remove(this);

			try {
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

	}

}
