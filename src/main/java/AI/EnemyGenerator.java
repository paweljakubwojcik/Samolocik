package AI;

import Program.Window;

public class EnemyGenerator {

	static int stageOfGame = 5; // 0 - brak alienow; 1,2,3 - kolejne fale
								// alienow; 4 - walka z bossem; 5 - odrodzenie
								// bossa; kolejne liczby to kolejne lvl moga byc

	static long time, time2;
	static long interval = 5000;
	static int maxInterval = 10000;
	Window win;
	static int numberOfAliens = 0;
	static boolean generateAliens = true; //true;
	public static int stworzonePaszki = 0;

	/**
	 * 
	 * @param window
	 */
	public EnemyGenerator(Window win) {
		this.win = win;
		time = System.currentTimeMillis();
	}

	static int numerAliena = 0;

	@SuppressWarnings("static-access")
	public void generate() {

		if (stageOfGame == 0)
			;
		else if (stageOfGame > 0 && stageOfGame < 4 && generateAliens) {

			numberOfAliens = 5 * stageOfGame;

			if (System.currentTimeMillis() - time2 > 500) {
				new Alien(Enemy.generator.nextInt(numberOfAliens) * win.size_x / numberOfAliens + 20, -5, win);
				numerAliena++;
				time2 = System.currentTimeMillis();
			}

			if (numerAliena >= numberOfAliens) {
				generateAliens = false;
				numerAliena = 0;
			}

		} else if (stageOfGame == 4 && !check(BossPaszko.class)) {

			new BossPaszko(win, 400, 20);
			stworzonePaszki++;

		} else if (stageOfGame == 5) {

			if (!check(BossPaszko.class))
				new BossPaszko(win, 400, 20);

			if (System.currentTimeMillis() - time2 > 500&&generateAliens) {
				numberOfAliens=10;
				new Alien(Enemy.generator.nextInt(numberOfAliens) * win.size_x / numberOfAliens + 20, -5, win);
				numerAliena++;
				time2 = System.currentTimeMillis();
			}
			if (numerAliena >= numberOfAliens) {
				generateAliens = false;
				numerAliena = 0;
			}
		}

		/////////////sprawdzanie czy astepny etap gry ma wejsc/////////////////////////
		if (!check(BossPaszko.class) && !check(Alien.class)) {
			stageOfGame++;
			generateAliens = true;
		}



		//////////// generator asteroid////////////////////
		if (System.currentTimeMillis() - time > interval) {
			time = System.currentTimeMillis();
			interval = (long) Enemy.generator.nextInt(maxInterval);
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

	public void start() {
		stageOfGame++;
		generateAliens = true;
	}

}
