import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy implements IEnemy {

	int x, y;
	int health;
	int width, height;
	int velocity_x, velocity_y;
	protected int zakresRuchu;
	Window win;
	static Random generator = new Random();
	static List<Enemy> enemies = new ArrayList<>();

	Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		enemies.add(this);
	}

	static void draw(Graphics2D g) {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).drawMe(g);
		}
	}
	
	static void motion() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).myMotion();
		}
	}
	
}
