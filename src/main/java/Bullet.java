import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Bullet {

	protected int x, y;
	protected int size = 10;
	protected int velocity = 10;
	protected static Window win = Samolotoszczalec.win;
	static List<Bullet> bullets = new ArrayList<>();

	/**
	 * 
	 * @param       pos_x: pozycja rakiety w momencie SZCZAUU
	 * @param pos_y pozycja rakiety w momencie SZCZAUU
	 */
	Bullet(int pos_x, int pos_y) {
		this.x = pos_x;
		this.y = pos_y;
		bullets.add(this);
	}
	
	Bullet(){
		
	}

	Bullet(int pos_x, int pos_y, int velocity) {
		this.x = pos_x;
		this.y = pos_y;
		this.velocity = velocity;
		bullets.add(this);
	}

	/**
	 * opisuje ruch pocisku
	 * 
	 * @author pafeu
	 */
	void MyMotion() {
		if (y > 0)
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

	synchronized void draw(Graphics2D g) {
		g.setColor(Color.red);
		g.fillOval(x + win.statek1.statek.getWidth() / 2 - size / 2, y, size, size);
	}

	/**
	 * @author pafeu
	 * @return true jesli pocisk jest w oknie, false jesli poza
	 */
	public boolean doesExist() {
		if (y <= 0)
			return true;
		else
			return false;
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
}
