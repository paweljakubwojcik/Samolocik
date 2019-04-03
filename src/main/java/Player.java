import java.util.ArrayList;
import java.util.List;

public class Player {
	List<Bullet> bullets = new ArrayList<>();
	int x = 400, y = 500;

	public void strzal() {
		bullets.add(new Bullet(x, y));
	}

}
