
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Enemy extends Collisionable {

	int x, y;
	float health;
	int width, height;
	int velocity_x, velocity_y;
	Window win;
	static Random generator = new Random();
	static List<Enemy> enemies = new ArrayList<>();

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
	static void draw(Graphics2D g) {
		for (int i = enemies.size() - 1; i >= 0; i--) {
			enemies.get(i).drawMe(g);
		}
	}

	static void motion() {
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
				|| o.getClass() == BulletPelletLeft.class || o.getClass() == BulletPelletCenter.class
				|| o.getClass() == BulletPelletRight.class) {
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
