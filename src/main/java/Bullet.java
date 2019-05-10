import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Bullet extends Collisionable {

	protected int x, y;
	protected int size = 10;
	protected int velocity = 10;
	float damage = 1;
	static long delay = 200;
	protected static Window win = Samolotoszczalec.win;
	static List<Bullet> bullets = new ArrayList<>();

	/**
	 * 
	 * @param pos_x:
	 *            pozycja rakiety w momencie SZCZAUU
	 * @param pos_y
	 *            pozycja rakiety w momencie SZCZAUU
	 */
	Bullet(int pos_x, int pos_y ) {
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	void draw(Graphics2D g) {
		g.setColor(Color.red);
		g.fillOval(x, y, size, size);
	}

	static void motion() {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).MyMotion();
		}
	}

	static void drawBullets(Graphics2D g) {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g);
		}
	}

	@Override
	public int[][] getPole() {

		int[][] tab = { { x + size / 2, y + size / 2, size } };
		return tab;
	}

	@Override
	public void collision(Object o) {

		
		bullets.remove(this);
		
		
	}
}
