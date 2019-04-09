public class EnemyGenerator {

	static long time;
	static long interval = 5000;
	static long maxInterval = 10000;
	Window win;
	static int numberOfAliens=10;

	
	/**
	 * 
	 * @param window
	 */
	EnemyGenerator(Window win) {
		this.win = win;
		time = System.currentTimeMillis();
	}

	void generate() {

		if (!check(Alien.class)) {
			for (int i = 0; i < numberOfAliens; i++) {
				Enemy.enemies.add(new Alien(i * win.size_x / numberOfAliens + 20, 1, win));
			}
		}

		if (System.currentTimeMillis() - time > interval) {
			time = System.currentTimeMillis();
			interval = (long) Enemy.generator.nextInt(10000);
			Enemy.enemies.add(new Asteroid(win));

		}
	}

	/**
	 * @param Class c
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
