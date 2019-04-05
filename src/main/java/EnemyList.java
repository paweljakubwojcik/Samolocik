import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class EnemyList {
	//tu bêdzie lista dla ka¿dego typu przeszkody
	static List<Asteroid> asteroids = new ArrayList<>();

	static void motion() {
		for (int i = 0; i < asteroids.size(); i++) {
			asteroids.get(i).motion();
		}
	}

	static void draw(Graphics2D g) {
		for (int i = 0; i < asteroids.size(); i++) {
			asteroids.get(i).draw(g);
		}
	}

	static void add(Object o) {
		asteroids.add((Asteroid) o);
	}
}
