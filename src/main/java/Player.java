import java.util.ArrayList;
import java.util.List;

public class Player {

	List<Bullet> bullets = new ArrayList<>();
	Window win = Samolotoszczalec.win;

	int velocity = 5; // predkosc samolotu
	int x, y;

	Player(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void strzal() {
		bullets.add(new Bullet(x, y));
	}

	public void moveRight() {
		if (x + win.statek.getWidth() < win.size_x) {
			x += velocity;
		}
	}

	public void moveLeft() {
		if (x > 0) {
			x -= velocity;
		}
	}

}
