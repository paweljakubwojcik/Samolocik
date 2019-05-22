public class Pellet {

	public Pellet(int x, int y) {
		new BulletPelletLeft(x, y);
		new BulletPelletCenter(x, y);
		new BulletPelletRight(x, y);
	}
}
