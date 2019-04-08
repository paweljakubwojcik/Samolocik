import java.awt.Color;
import java.awt.Graphics2D;

public class Alien extends Enemy {

	
	static final int defaultHealth=4;
	int zakresRuchuX,zakresRuchuY;
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
		health = 50;
		velocity_x = 0;
		velocity_y = generator.nextInt(1) + 1;
		zakresRuchuX = x + generator.nextInt(win.size_x - x);
		zakresRuchuY = y + generator.nextInt(win.size_y/2 - y);
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

		/*if (x > zakresRuchuX && velocity_x > 0) {
			velocity_x = -velocity_x;
			zakresRuchuX = win.size_x - generator.nextInt(x);
		}
		if (x < zakresRuchuX && velocity_x < 0) {
			velocity_x = -velocity_x;
			zakresRuchuX = generator.nextInt(x);
		}
		if (y > zakresRuchuY && velocity_y > 0) {
			velocity_y = -velocity_y;
			zakresRuchuX = win.size_y - generator.nextInt(y);
		}
		if (y < zakresRuchuY && velocity_y < 0) {
			velocity_y = -velocity_y;
			zakresRuchuX = generator.nextInt(y);
		}*/


	}
	
	
}
