import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Drop extends Collisionable {

	static List<Drop> drops = new ArrayList<>();
	static Random generator = new Random();

	int x, y, width, height, velocity;
	static Window win = Samolotoszczalec.win;

	Drop(int x, int y) {
		this.x = x;
		this.y = y;
		this.velocity = generator.nextInt(2)+1;
		// this.win=win;
		drops.add(this);
	}

	static void draw(Graphics2D g) {
		for (int i = drops.size() - 1; i >= 0; i--) {
			drops.get(i).drawMe(g);
		}
	}

	static void motion() {
		for (int i = drops.size() - 1; i >= 0; i--) {
			drops.get(i).myMotion();
		}
	}

	static void generateDrop(Enemy e)
	{
		int chance = generator.nextInt(100);
		if(chance<20) new HealthPack(e.x, e.y);
		
	}
	
	@SuppressWarnings("static-access")
	void myMotion() {
		this.y += velocity;
		if (this.y > win.size_y)
			drops.remove(this);
	}

	abstract void drawMe(Graphics2D g);

}
