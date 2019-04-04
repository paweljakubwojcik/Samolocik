public class Player {

	int velocity = 5; // predkosc samolotu
	int x, y;

	Player(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void strzal() {
		AllBullets.add(new Bullet(x, y));
	}

	public void moveRight() {
		x += velocity;
	}

	public void moveLeft() {
		x -= velocity;
	}

}
