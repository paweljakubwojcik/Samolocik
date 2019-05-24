package Bullets;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import Program.Samolotoszczalec;
import Program.Window;
import Rozgrywka.Collisionable;

public class Bullet extends Collisionable {

	protected int x, y;
	public static int size = 10; // to jest statyczne poniewaz jest wykorzystywane w klasie player
	protected int velocity = 10;
	public float damage = 1;
	public static long delay = 200;
	protected static Window win = Samolotoszczalec.win;
	public static List<Bullet> bullets = new ArrayList<>();

	/**
	 * 
	 * @param       pos_x: pozycja rakiety w momencie SZCZAUU
	 * @param pos_y pozycja rakiety w momencie SZCZAUU
	 */
	public Bullet(int pos_x, int pos_y) {
		this.x = pos_x;
		this.y = pos_y;
		bullets.add(this);
	}

	Bullet() {

	}

	/**
	 * opisuje ruch pocisku
	 * 
	 * @author pafeu
	 */
	@SuppressWarnings("static-access")
	void MyMotion() {
		if (y > 0 && y < win.size_y)
			y -= velocity;
		else
			try {
				bullets.remove(this);
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}

	}

	void draw(Graphics2D g) {
		g.setColor(Color.red);
		g.fillOval(x, y, size, size);
	}

	public static void motion() {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).MyMotion();
		}
	}

	public static void drawBullets(Graphics2D g) {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g);
		}
	}

	@Override
	public int[][] getPole() {

		int[][] tab = { { x + size / 2, y + size / 2, size / 2 } };
		return tab;
	}

	@Override
	public void collision(Object o) {

		bullets.remove(this);

	}
	
	/**
	 * @param Class
	 * @return true if there is at least 1 object of Class c
	 */
	@SuppressWarnings("rawtypes")
	public
	static boolean check(Class c) {
		for (int i = 0; i < Bullet.bullets.size(); i++) {
			if(i>=Bullet.bullets.size()) break;
			
			if (Bullet.bullets.get(i).getClass() == c)
				return true;
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public
	static int find(Class c) {
		for (int i = 0; i < Bullet.bullets.size(); i++) {
			if (Bullet.bullets.get(i).getClass() == c) {
				return i;
			}
				
		}
		return 0;
	}
}
