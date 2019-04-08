import java.awt.Color;
import java.awt.Graphics2D;

public class Alien extends Enemy {

	static final int defaultHealth = 4;
	int zakresRuchuX, zakresRuchuY;
	int velocity;

	/**
	 * 
	 * @param x
	 * @param y
	 * @param win
	 */
	Alien(int x, int y, Window win) {
		super(x, y);
		this.win = win;
		this.health = defaultHealth;
		width = 50;
		height = 50;

		velocity = 1;
		velocity_y = velocity;
		velocity_x = 0;
		zakresRuchuY = y + generator.nextInt(win.size_y / 2 - y);
		System.out.print("Alien");
	}

	@Override
	public void drawMe(Graphics2D g) {
		g.setColor(Color.orange);
		g.fillOval(x, y, width, height);

		g.setColor(Color.red);
		g.drawRect(x, y + win.size_y / 400, win.size_x / 20, win.size_y / 300);
		g.fillRect(x, y + win.size_y / 400, (win.size_x / 20) * health / defaultHealth, win.size_y / 300);
	}

	@Override
	public void myMotion() {

		if ((x >= zakresRuchuX && velocity_x > 0) || (x <= zakresRuchuX && velocity_x < 0)
				|| (y >= zakresRuchuY && velocity_y > 0) || (y <= zakresRuchuY && velocity_y < 0) ) {
			losujKierunek();
			if (velocity_y > 0)
				zakresRuchuY = y + generator.nextInt(win.size_y/2 - y +1);
			if (velocity_x > 0)
				zakresRuchuX = x + generator.nextInt(win.size_x - x +1);
			if (velocity_y < 0)
				zakresRuchuY = generator.nextInt(y);
			if (velocity_x < 0)
				zakresRuchuX = generator.nextInt(x);
		}
		x += velocity_x;
		y += velocity_y;
	}

	private void losujKierunek() {
		if (generator.nextBoolean()) // true - vertical, false - horizontal
		{
			if (velocity_y == 0) {
				if (generator.nextBoolean())// true -up, false - down
				{
					velocity_y = -velocity_x;
				} else {
					velocity_y = velocity_x;
				}
				velocity_x = 0;
			} else {
				velocity_y = -velocity_y;
			}
		} else {
			if (velocity_x == 0) {
				if (generator.nextBoolean())// true left, false right
				{
					velocity_x = -velocity_y;
				} else {
					velocity_x = velocity_y;
				}
				velocity_y = 0;
			} else {
				velocity_x = -velocity_x;
			}
		}
	}
}
