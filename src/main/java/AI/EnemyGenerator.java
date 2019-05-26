package AI;

import Program.Window;

public class EnemyGenerator {

	static long time, time2;
	static long interval = 5000;
	static long maxInterval = 10000;
	Window win;
	static int numberOfAliens = 0;
	static boolean generateAliens = false, timeForBoss = false; // false false
	static int numerFali = 0;

	/**
	 * 
	 * @param window
	 */
	public EnemyGenerator(Window win) {
		this.win = win;
		time = System.currentTimeMillis();
	}

	static int i = 0;

	@SuppressWarnings("static-access")
	public void generate() {
		////////////// generator alienow//////////////////
		if (!check(Alien.class) && generateAliens == false && !check(BossPaszko.class)) {
			numberOfAliens += 5;
			numerFali++;
			generateAliens = true;
			time2 = System.currentTimeMillis();

			if (numerFali == 4) {
				timeForBoss = true;
				generateAliens = false;
			}

		} else if (generateAliens && System.currentTimeMillis() - time2 > 500) {
			new Alien(Enemy.generator.nextInt(numberOfAliens) * win.size_x / numberOfAliens + 20, -5, win);
			i++;
			time2 = System.currentTimeMillis();
		}

		if (i == numberOfAliens) {
			generateAliens = false;
			i = 0;
		}

		if (timeForBoss && !check(BossPaszko.class)) {
			new BossPaszko(win, 400, 20);

		}

		//////////// generator asteroid////////////////////
		if (System.currentTimeMillis() - time > interval) {
			time = System.currentTimeMillis();
			interval = (long) Enemy.generator.nextInt(10000);
			new Asteroid(win);
		}
	}

	/**
	 * @param Class
	 * @return true if there is at least 1 object of Class c
	 */
	@SuppressWarnings("rawtypes")
	private boolean check(Class c) {
		for (int i = 0; i < Enemy.enemies.size(); i++) {
			if (Enemy.enemies.get(i).getClass() == c)
				return true;
		}
		return false;
	}

}
