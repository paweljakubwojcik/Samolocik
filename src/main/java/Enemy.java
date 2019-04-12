
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends Collisionable implements IEnemy {

	int x, y;
	int health;
	int width, height;
	int velocity_x, velocity_y;
	Window win;
	static Random generator = new Random();
	static List<Enemy> enemies = new ArrayList<>();

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

	/**
	 * 
	 * @return {x,y,width,height}
	 */
	int[] getObszar() {
		int[] ret = { x, y, width, height };
		return ret;
	}

	@Override
	public void drawMe(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void myMotion() {
		// TODO Auto-generated method stub

	}

	@Override
	public int[][] getPole() {
		int[][] tab= {{x,y,width,height}};
		return tab;
	}

	@Override
	public void collision(Object o) {
		System.out.println("dziaua");
		
	}

}
