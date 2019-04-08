import java.awt.Color;
import java.awt.Graphics2D;

public class Alien extends Enemy {

	
	static final int defaultHealth=4;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param win
	 */
	Alien(int x, int y, Window win) {
		super(x, y);
		this.win = win;
		this.health = 100;
		width = generator.nextInt(70) + 30;
		height = generator.nextInt(70) + 30;
		velocity_x = generator.nextInt(3) + 1;
		velocity_y = generator.nextInt(1) + 1;
		zakresRuchu = x + generator.nextInt(win.size_x - x);
	}

	@Override
	public void drawMe(Graphics2D g) {
		g.setColor(Color.orange);
		g.fillOval(x, y, width, height);
		
		g.setColor(Color.red);
		g.drawRect(x, y+win.size_y/400, win.size_x/15, win.size_y/300);
		g.fillRect(x, y+win.size_y/400, (win.size_x/15)*health/defaultHealth, win.size_y/300 );
	}

	@Override
	public void myMotion() {

		x += velocity_x;
		y += velocity_y;

		if (x > zakresRuchu && velocity_x > 0) {
			velocity_x = -velocity_x;
			zakresRuchu = win.size_x - generator.nextInt(x);
		}

		if (x < zakresRuchu && velocity_x < 0) {
			velocity_x = -velocity_x;
			zakresRuchu = x + generator.nextInt(win.size_x - x);
		}

	}
}
