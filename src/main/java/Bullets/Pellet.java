package Bullets;
/**
 * Shotgun
 * @category Zarządca Amunicji
 * 
 * @author adrian
 */
public class Pellet {

	private int liczbaWystrzalow = 3;
	private int velocity = 10;
	private float Vy;
	private float rozstrzal = (float) 7.0;
	private int x, y;

	/**
	 * Tworzy standardowa amunicje skladajaca sie z: 7 kul, predkosc 10, kat 7
	 * 
	 * @param x Punkt Startowy X
	 * @param y Punkt Startowy Y
	 */
	public Pellet(int x, int y) {
		this.x = x;
		this.y = y;
		tworzAmmo();
	}

	/**
	 * Tworzy standardowa amunicje: predkosc 10, kat 7
	 * 
	 * @param x                Punkt Startowy X
	 * @param y                Punkt Startowy Y
	 * @param liczbaWystrzalow Liczba naboi ktora ma zostac wystrzelona po jednej
	 *                         stronie, funckja automatycznie dodaje strzal prosto,
	 *                         oraz stosuje symetrie
	 */
	public Pellet(int x, int y, int liczbaWystrzalow) {
		this.x = x;
		this.y = y;
		this.liczbaWystrzalow = liczbaWystrzalow;
		tworzAmmo();
	}

	/**
	 * Tworzy amunicje: predkosc 10
	 * 
	 * @param x                Punkt Startowy X
	 * @param y                Punkt Startowy Y
	 * @param liczbaWystrzalow Liczba naboi ktora ma zostac wystrzelona po jednej
	 *                         stronie, funckja automatycznie dodaje strzal prosto,
	 *                         oraz stosuje symetrie
	 * @param kat              Kąt który różni kolejne naboje
	 */
	public Pellet(int x, int y, int liczbaWystrzalow, float kat) {
		this.x = x;
		this.y = y;
		this.liczbaWystrzalow = liczbaWystrzalow;
		this.rozstrzal = kat;
		tworzAmmo();
	}

	/**
	 * Tworzy amunicje. Konstruktor extremalny używaj jeśli wiesz co robisz
	 * 
	 * @param x                Punkt Startowy X
	 * @param y                Punkt Startowy Y
	 * @param liczbaWystrzalow Liczba naboi ktora ma zostac wystrzelona po jednej
	 *                         stronie, funckja automatycznie dodaje strzal prosto,
	 *                         oraz stosuje symetrie
	 * @param kat              Kąt który różni kolejne naboje
	 * @param velocity         Prędkość pocisku pionowego wzdłóż osi Y
	 */
	public Pellet(int x, int y, int liczbaWystrzalow, float kat, int velocity) {
		this.x = x;
		this.y = y;
		this.liczbaWystrzalow = liczbaWystrzalow;
		this.rozstrzal = kat;
		this.velocity = velocity;
		tworzAmmo();
	}

	private void tworzAmmo() {
		for (int i = -liczbaWystrzalow; i <= liczbaWystrzalow; i++) {
			Vy = obliczVY(Math.abs(i));
			if (i >= 0) {
				new BulletPellet(x, y, obliczX(), -Math.abs(Vy));
			} else {
				new BulletPellet(x, y, -obliczX(), -Math.abs(Vy));
			}
		}
	}

	private float obliczX() {

		return (float) Math.sqrt(Math.pow(velocity, 2) - Math.pow(Vy, 2));
	}

	private float obliczVY(int i) {
		float x2 = velocity * velocity;
		float tg2 = (float) (Math.pow(Math.tan(rozstrzal * i * Math.PI / 180), 2));

		return (float) Math.sqrt(x2 / (tg2 + 1));
	}

}
