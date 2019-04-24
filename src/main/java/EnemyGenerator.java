public class EnemyGenerator {

	static long time, time2;
	static long interval = 5000;
	static long maxInterval = 10000;
	Window win;
	static int numberOfAliens = 0;
	static boolean generateAliens = false;

	/**
	 * 
	 * @param window
	 */
	EnemyGenerator(Window win) {
		this.win = win;
		time = System.currentTimeMillis();
	}

	static int i = 0;

	@SuppressWarnings("static-access")
	void generate() {
		////////////// generator alienow//////////////////

		if (!check(Alien.class) && generateAliens == false) {
			numberOfAliens += 5;
			generateAliens = true;
			time2 = System.currentTimeMillis();
		}
		if (generateAliens && numberOfAliens > 15)
			new BossPaszko(win, 400, 20);
		else if (generateAliens && System.currentTimeMillis() - time2 > 500) {
			new Alien(Enemy.generator.nextInt(numberOfAliens) * win.size_x / numberOfAliens + 20, -5, win);
			i++;
			time2 = System.currentTimeMillis();
		}
		if (i == numberOfAliens) {
			generateAliens = false;
			i = 0;
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
