import java.awt.Color;
import java.awt.Graphics2D;

public class Asteroid extends Enemy implements IEnemy {

	/**
	 * @param window
	 * @param x
	 * @param y
	 * 
	 */
	Asteroid(Window win, int x, int y) {
		super(x, y);
		this.win = win;
		this.health = 100;
		width = generator.nextInt(70) + 30;
		height = generator.nextInt(70) + 30;
		velocity_x = generator.nextInt(5) + 1;
		velocity_y = generator.nextInt(2) + 1;
		zakresRuchu = x + generator.nextInt(win.size_x - x);
	}

	@Override
	public void drawMe(Graphics2D g) {
		g.setColor(Color.orange);
		g.fillOval(x, y, width, height);
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
