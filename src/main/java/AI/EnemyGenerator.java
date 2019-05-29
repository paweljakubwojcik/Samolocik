package AI;

import Program.Window;

public class EnemyGenerator {

	static int stageOfGame = 3; // 0 - brak alienow; 1,2,3 - kolejne fale
								// alienow; 4 - walka z bossem; 5 - odrodzenie
								// bossa; kolejne liczby to kolejne lvl moga byc

	static long time, time2;
	static long interval = 5000;
	static int maxInterval = 10000;
	Window win;
	static int numberOfAliens = 0;
	private static boolean generateAliens = true, generateAsteroids = true; // true;
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
		else if (stageOfGame > 0 && stageOfGame < 4) {

			generateAliens(5 * stageOfGame);
			checkStage();

		} else if (stageOfGame == 4) {

			if (!check(BossPaszko.class) && stworzonePaszki == 0) {
				win.audio.play(1);
				new BossPaszko(win, win.size_x / 2 - 50, -100);
				stworzonePaszki++;
				generateAliens=false;
			}
			checkStage();

		} else if (stageOfGame == 5) {

			if (!check(BossPaszko.class) && stworzonePaszki == 1) {
				new BossPaszko(win, 400, -100);
				stworzonePaszki++;
			}
			generateAliens(10);
			checkStage();

		}

		if (generateAsteroids)
			generateAsteroids();
	}

	private boolean checkStage() {
		///////////// sprawdzanie czy astepny etap gry ma
		///////////// wejsc/////////////////////////
		if (!check(BossPaszko.class) && !check(Alien.class) && !generateAliens) {
			stageOfGame++;
			generateAliens = true;
			System.out.println(stageOfGame);
			return true;
		} else
			return false;
	}

	private void generateAsteroids() {
		//////////// generator asteroid////////////////////
		if (System.currentTimeMillis() - time > interval) {
			time = System.currentTimeMillis();
			interval = (long) Enemy.generator.nextInt(maxInterval);
			new Asteroid(win);
		}

	}

	@SuppressWarnings("static-access")
	private void generateAliens(int numberOfAliens) {
		if (System.currentTimeMillis() - time2 > 500 && generateAliens) {
			new Alien(Enemy.generator.nextInt(numberOfAliens) * win.size_x / numberOfAliens + 20, -5, win);
			numerAliena++;
			time2 = System.currentTimeMillis();
		}

		if (numerAliena >= numberOfAliens) {
			generateAliens = false;
			numerAliena = 0;
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

	public static int getStageOfGame() {
		return stageOfGame;
	}

	public static void setAsteroids(boolean b) {
		generateAsteroids = b;
	}
}
