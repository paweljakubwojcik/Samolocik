package Bullets;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import AI.Enemy;
import Gracz.HealthPack;
import Gracz.Shield;
import Gracz.Shrink;
import Program.Samolotoszczalec;
import Program.Window;
import Rozgrywka.Collisionable;

public abstract class Drop extends Collisionable {

	public static List<Drop> drops = new ArrayList<>();
	static Random generator = new Random();

	protected int x;
	protected int y;
	protected int width;
	protected int height;
	int velocity;
	static Window win = Samolotoszczalec.win;

	protected Drop(int x, int y) {
		this.x = x;
		this.y = y;
		this.velocity = generator.nextInt(2) + 1;
		// this.win=win;
		drops.add(this);
	}

	public static void draw(Graphics2D g) {
		for (int i = drops.size() - 1; i >= 0; i--) {
			drops.get(i).drawMe(g);
		}
	}

	public static void motion() {
		for (int i = drops.size() - 1; i >= 0; i--) {
			drops.get(i).myMotion();
		}
	}

	public static void generateDrop(Enemy e) {
		int chance = generator.nextInt(100);
		if (chance < 20)
			new HealthPack(e.x, e.y);
		else if (chance < 30)
			new DropExtraBullet(e.x, e.y);
		else if (chance < 40)
			new Shield(e.x, e.y);
		else if (chance < 50)
			new DropPelletBullet(e.x, e.y);
		else if (chance < 60)
			new DropPlazmaBullet(e.x, e.y);
		else if (chance < 70)
			new DropGranade(e.x, e.y);
		else if (chance < 80)
			new Shrink(e.x, e.y);

	}

	public int[][] getPole() {
		int[][] tab = { { x, y, width, height } };
		return tab;
	}

	@SuppressWarnings("static-access")
	void myMotion() {
		this.y += velocity;
		if (this.y > win.size_y)
			drops.remove(this);
	}

	public static void wylancz() {
		drops.clear();
	}

	protected abstract void drawMe(Graphics2D g);

}
