import java.util.ArrayList;
import java.util.List;

public class Player {

	List<Bullet> bullets = new ArrayList<>();
	int x, y;
	int velocity = 5; // predkosc samolotu

	Player(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	
	
	public void strzal() {
		bullets.add(new Bullet(x, y));
	}

	public void moveRight() {
		x += velocity;
	}

	public void moveLeft() {
		x += velocity;
	}

}
