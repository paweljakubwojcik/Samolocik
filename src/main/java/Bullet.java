import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet {

	int x, y;
	static int velocity = 1;
	Window win = Samolotoszczalec.win;

	/**
	 * 
	 * @param       pos_x: pozycja rakiety w momencie SZCZAUU
	 * @param pos_y pozycja rakiety w momencie SZCZAUU
	 */
	Bullet(int pos_x, int pos_y) {
		this.x = pos_x;
		this.y = pos_y;

		Thread ruch = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					motion();
					try {
						Thread.sleep(100/60);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		ruch.start();
	}

	/**
	 * opisuje ruch pocisku
	 * 
	 * @author pafeu
	 */
	private void motion() {
		if (y > 0)
			y -= velocity;
		else
			try {
				this.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	void draw()
	{
		Graphics2D g = (Graphics2D) win.klatka.getGraphics();
		g.setColor(Color.red);
		g.fillOval(x+win.statek.getWidth()/2-2, y, 5, 5);
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

}
