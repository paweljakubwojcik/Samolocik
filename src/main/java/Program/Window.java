package Program;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import AI.BossPaszko;
import AI.Enemy;
import AI.EnemyGenerator;
import Bullets.Bullet;
import Bullets.BulletExtraPlayer;
import Bullets.BulletEyes;
import Bullets.BulletPaszkoProstopadly;
import Bullets.BulletPaszkoRownolegly;
import Bullets.BulletPellet;
import Bullets.BulletPlazma;
import Bullets.Drop;
import Bullets.Granade;
import Gracz.HealthPack;
import Gracz.Player;
import InterFace.AudioMeneger;
import InterFace.Intro;
import InterFace.IntroBoss;
import InterFace.MessageBox;
import InterFace.MessageTypingIn;
import InterFace.Restart;
import InterFace.Sterowanie;
import InterFace.Zaliczenie;
import Rozgrywka.Collisions;
import achievement.Achievement;
import menu.MenuESC;
import menu.MenuGlowne;
import napisyKoncowe.Credits;

public class Window implements KeyListener, MouseListener, FocusListener {

	public JFrame okno;
	public static final int size_x = 800, size_y = 600;
	public static int size_xx = 800, size_yy = 600; // (800 * 1.75) (600 * 1.75)

	private int tloY = -size_y, pozycjatla = 0;
	BufferedImage klatka;
	BufferedImage imc = new BufferedImage(size_x, size_y * 2, BufferedImage.TYPE_INT_ARGB);
	BufferedImage im1 = new BufferedImage(size_x, size_y, BufferedImage.TYPE_INT_ARGB); // obrazek
																						// tla1
	BufferedImage im2 = new BufferedImage(size_x, size_y, BufferedImage.TYPE_INT_ARGB); // obrazek
																						// tla2
	public AudioMeneger audio = new AudioMeneger();

	Player statek1;
	Thread Game;
	EnemyGenerator generator = new EnemyGenerator(this);
	boolean ruch1L = false;
	boolean ruch1P = false;
	boolean ruch1D = false;
	boolean ruch1U = false;
	boolean strzal = false;
	boolean pause = false;
	boolean mute = false;

	boolean intro = true;
	boolean sterowanie = true;
	boolean instrukcja = false;

	boolean spanie = true; // kombinuje jak si� tego pozby�

	Achievement ach = new Achievement();

	public static boolean wyswietlWynik = false;

	private boolean[] skipy = new boolean[10];

	private boolean YouWon = false;

	private Cursor cursor;

	private static boolean pelnyekran = false;

	private boolean zmienekran = false;

	public static long PauzaStart, PauzaStop, czasPauzy;

	private static int gierek = 0;

	BufferedImage zdjKursora, kurso, kursorLapka;
	Toolkit toolkit;

	Window() {
		okno = new JFrame("Niewdzieczna przestrzen    F11 aby przejść na pełny ekran");
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setResizable(false);
		okno.setLocationRelativeTo(null);
		okno.setLocation(0, 0);
		okno.setSize(size_xx, size_yy);
		okno.setLayout(null);
		okno.setVisible(true);
		okno.addKeyListener(this);
		okno.addMouseListener(this);
		okno.setFocusable(true);
		okno.addFocusListener(this);
		klatka = new BufferedImage(size_x, size_y, BufferedImage.TYPE_INT_ARGB);

		statek1 = new Player(this, 400, 500);

		audio.play(0); // to jest intro

		losujtlo(im1);
		losujtlo(im2);
		scaltla(im1, im2);

		for (int i = 0; i < 10; i++) {
			skipy[i] = false;
		}

		toolkit = Toolkit.getDefaultToolkit();
		zdjKursora = new BufferedImage(42, 44, BufferedImage.TYPE_INT_ARGB);
		try {
			zdjKursora = ImageIO.read(this.getClass().getResource("/menu/kursor.png"));
			kurso = ImageIO.read(this.getClass().getResource("/menu/dot.png"));
			kursorLapka = ImageIO.read(this.getClass().getResource("/menu/kursorLapka.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		cursor = toolkit.createCustomCursor(kurso, new Point(okno.getX(), okno.getY()), "Default Kursor");
		okno.setCursor(cursor);

		new MenuGlowne();

		Game = new Thread(new Runnable() {

			@Override
			public void run() {
				long startTime;
				@SuppressWarnings("unused")
				long lacznyCzas = 0;
				int odliczanie = 0;

				while (true) {
					if (zmienekran) {
						fullScreen();
						zmienekran = !zmienekran;
					}
					startTime = System.nanoTime();

					if (!pause && MenuGlowne.isEmpty() && MenuESC.isEmpty()) {

						if (!statek1.isDead() && EnemyGenerator.getStageOfGame() != 6) {
							draw();
							Bullet.motion();
							Enemy.motion();
							Drop.motion();
							sprawdzKolizje();
							ach.sprawdzOsiagniecia(statek1);

							if (!intro && !sterowanie)
								generator.generate();

							if (ruch1L)
								statek1.moveLeft();
							if (ruch1P)
								statek1.moveRight();
							if (ruch1U)
								statek1.moveUp();
							if (ruch1D)
								statek1.moveDown();
							if (strzal && !IntroBoss.czyMoznaPominac && !BossPaszko.czyMoznaPominac
									|| strzal && EnemyGenerator.stworzonePaszki == 2)
								statek1.strzal();
						} else {
							ach.sprawdzOsiagniecia(statek1);
							endOfGame(statek1.isDead());
						}

						if (EnemyGenerator.getStageOfGame() == 6 && !YouWon) {
							new MessageBox("YOU WON");
							YouWon = !YouWon;
							audio.play(4);
						}

						// wyświetla max potencjał PC w klatkach na sekunde
						if (odliczanie == 60) {
							lacznyCzas += (1000.0 / ((System.nanoTime() - startTime) / 1000.0 / 1000.0));
							// System.out.println(lacznyCzas / 60);
							lacznyCzas = 0;
							odliczanie = 0;
						} else {
							lacznyCzas += (1000.0 / ((System.nanoTime() - startTime) / 1000.0 / 1000.0));
							odliczanie++;
						}

						while (System.nanoTime() - startTime < 1000000000 / 60) // Około
																				// 60
																				// FPSa
						{
							try {
								Thread.sleep(0, 100 * 1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						////////////////////////////
						ruchTla();
						///////////////////////////////

					} else if (!MenuGlowne.isEmpty() || !MenuESC.isEmpty()) {
						draw();
						try {
							Thread.sleep(16);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						////////// rysowanie znaku zatrzymania//////////////
						spanie = !spanie;
						if (spanie)
							drawklatka();
						else {
							Graphics2D g3 = (Graphics2D) okno.getGraphics();
							BufferedImage pauzen = new BufferedImage(size_x, size_y, BufferedImage.TYPE_INT_ARGB);
							Graphics2D g2d = (Graphics2D) pauzen.getGraphics();
							g2d.setColor(Color.white);
							g2d.fillRect(size_x / 2 - 30, size_y / 2 - 30, 20, 50);
							g2d.fillRect(size_x / 2 + 20, size_y / 2 - 30, 20, 50);
							g2d.drawString("Naciśnij P aby wznowić", 10, size_y - 10);
							g3.drawImage(pauzen, 0, 0, size_xx, size_yy, null);
						}

						///////////////////////////////////////////////////////
						try {
							Thread.sleep(700);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, "GameWhile");

	}

	void draw() {
		Graphics2D g = (Graphics2D) klatka.getGraphics();
		if (MenuGlowne.isEmpty() && MenuESC.isEmpty()) {
			if (!Credits.isActive()) {
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, size_x, size_y);
				g.drawImage(imc, 0, tloY, null);
				ach.draw(g);
				Bullet.drawBullets(g);
				Enemy.draw(g);
				Drop.draw(g);
				statek1.draw(g);
				MessageBox.draw(g);
				if (intro)
					Intro.draw(g);
				if (instrukcja) {
					Sterowanie.draw(g);
				}
				IntroBoss.draw(g);

				MessageTypingIn.draw(g);
			}
			Restart.draw(g);
			Zaliczenie.draw(g);

			if (Credits.isActive()) {
				AlphaComposite acc = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Credits.fade);
				g.setComposite(acc);
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, size_x, size_y);
				g.drawImage(imc, 0, tloY, null);
				Credits.draw(g);
				acc = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
				g.setComposite(acc);
			}
		}

		MenuGlowne.draw(g);
		MenuESC.draw(g);
		Point p = MouseInfo.getPointerInfo().getLocation();
		Rectangle r = okno.getBounds();
		if (!Restart.isEmpty()) {
			if ((Myszka(p, r, Restart.restart) || Myszka(p, r, Restart.restart2))) {
				Restart.hoverRestart = true;
				Restart.hoverRestart2 = true;
				Restart.hoverMenu = false;

				drawLapka(p, r, g);
			} else if (Myszka(p, r, Restart.Menu)) {
				Restart.hoverMenu = true;
				Restart.hoverRestart = false;
				Restart.hoverRestart2 = false;

				drawLapka(p, r, g);
			} else {
				Restart.hoverRestart = false;
				Restart.hoverRestart2 = false;
				Restart.hoverMenu = false;

				drawKursor(p, r, g);
			}
		}
		if (!MenuGlowne.isEmpty()) {
			if (Myszka(p, r, MenuGlowne.start)) {
				MenuGlowne.hoverStart = true;
				MenuGlowne.hoverWyjscie = false;
				MenuGlowne.hoverLatwy = false;
				MenuGlowne.hoverSredni = false;
				MenuGlowne.hoverTrudny = false;

				if (Myszka(p, r, MenuGlowne.latwy)) {
					MenuGlowne.hoverLatwy = true;
					MenuGlowne.hoverSredni = false;
					MenuGlowne.hoverTrudny = false;
				} else if (Myszka(p, r, MenuGlowne.sredni)) {
					MenuGlowne.hoverLatwy = false;
					MenuGlowne.hoverSredni = true;
					MenuGlowne.hoverTrudny = false;
				} else if (Myszka(p, r, MenuGlowne.trudny)) {
					MenuGlowne.hoverLatwy = false;
					MenuGlowne.hoverSredni = false;
					MenuGlowne.hoverTrudny = true;
				}

				drawLapka(p, r, g);
			} else if (Myszka(p, r, MenuGlowne.wyjscie)) {
				MenuGlowne.hoverWyjscie = true;
				MenuGlowne.hoverStart = false;

				drawLapka(p, r, g);
			} else {
				MenuGlowne.hoverStart = false;
				MenuGlowne.hoverWyjscie = false;

				drawKursor(p, r, g);
			}
		}
		if (!MenuESC.isEmpty()) {
			if (Myszka(p, r, MenuESC.menu)) {
				MenuESC.hovermenu = true;
				MenuESC.hoverWroc = false;
				drawLapka(p, r, g);
			} else if (Myszka(p, r, MenuESC.wroc)) {
				MenuESC.hovermenu = false;
				MenuESC.hoverWroc = true;
				drawLapka(p, r, g);
			} else {
				MenuESC.hovermenu = false;
				MenuESC.hoverWroc = false;
				drawKursor(p, r, g);
			}
		}

		g.dispose();
		drawklatka();

	}

	private void drawLapka(Point p, Rectangle r, Graphics2D g) {
		g.drawImage(kursorLapka, (int) ((p.x - r.x) * ((float) size_x / size_xx)),
				(int) ((p.y - r.y - 38 * ((float) size_yy / size_y)) * ((float) size_y / size_yy)), null);
	}

	private void drawKursor(Point p, Rectangle r, Graphics2D g) {
		g.drawImage(zdjKursora, (int) ((p.x - r.x) * ((float) size_x / size_xx)),
				(int) ((p.y - r.y) * ((float) size_y / size_yy)), null);
	}

	boolean Myszka(Point p, Rectangle r, Rectangle przycisk) {

		return p.x - r.x > przycisk.x * ((float) size_xx / size_x)
				&& p.x - r.x < przycisk.width * ((float) size_xx / size_x) + przycisk.x * ((float) size_xx / size_x)
				&& p.y - r.y > przycisk.y * ((float) size_yy / size_y)
				&& p.y - r.y < przycisk.height * ((float) size_yy / size_y) + przycisk.y * ((float) size_yy / size_y);
	}

	void drawklatka() {
		Graphics2D g2d = (Graphics2D) okno.getGraphics();
		g2d.drawImage(klatka, 0, 0, size_xx, size_yy, null);
		g2d.dispose();
	}

	void losujtlo(BufferedImage im) {
		Random los = new Random();
		Graphics2D g2d = (Graphics2D) im.getGraphics();
		g2d.setColor(new Color(0, 0, 15, 255));
		g2d.fillRect(0, 0, size_x, size_y);
		g2d.setColor(Color.WHITE);
		int wielkosc = 0, xxx = 0, yyy = 0;
		for (int i = 0; i < 20; i++) { // 20
			wielkosc = los.nextInt(10); // 10
			xxx = los.nextInt(size_x);
			yyy = los.nextInt(size_y);

			g2d.fillOval(xxx, yyy, wielkosc, wielkosc);
		}
	}

	void scaltla(BufferedImage i1, BufferedImage i2) {
		Graphics2D g2d = (Graphics2D) imc.getGraphics();
		g2d.drawImage(i1, 0, 0, null);
		g2d.drawImage(i2, 0, size_y, null);
		g2d.dispose();
	}

	void ruchTla() {
		tloY += 5;
		pozycjatla += 5;

		if (tloY >= 0) {
			if (pozycjatla == size_y) {
				losujtlo(im2);
				scaltla(im2, im1);
			} else if (pozycjatla >= size_y * 2) {
				losujtlo(im1);
				scaltla(im1, im2);
				pozycjatla = 0;
			}
			tloY = -size_y;
		}
	}

	void endOfGame(boolean win) {
		if (!wyswietlWynik) {
			wyswietlWynik = true;
			new Zaliczenie(7000, 3, 2, statek1.punkty, !win, statek1.isDead()); // 100000000
		}
		draw();
		Bullet.motion();
	}

	private void sprawdzKolizje() {

		for (int j = 0; j < Enemy.enemies.size(); j++) {
			for (int i = 0; i < Bullet.bullets.size(); i++) {

				Collisions.checkCollision(Enemy.enemies.get(j), Bullet.bullets.get(i)); // enemy
																						// z
																						// nabojami
				if (Enemy.enemies.size() <= j)
					break;
			}
		}
		for (int j = 0; j < Enemy.enemies.size(); j++) {
			Collisions.checkCollision(statek1, Enemy.enemies.get(j)); // enemy z
																		// playerem
		}
		for (int i = 0; i < Bullet.bullets.size(); i++) {
			Collisions.checkCollision(Bullet.bullets.get(i), statek1); // player
																		// z
																		// nabojami
		}

		for (int i = 0; i < Drop.drops.size(); i++) {
			Collisions.checkCollision(statek1, Drop.drops.get(i)); // player
																	// z
																	// dropami
		}
	}

	private void restart() {
		statek1.setDefault();
		generator.setDefault();
		ach.setDefault(statek1);
		System.out.println("restart");
		skipy[1] = false;
		skipy[2] = false;
		skipy[3] = false;
		Zaliczenie.wylancz();
		Restart.wylancz();
		Drop.wylancz();
		MessageBox.restart();
		audio.setDefault();
		audio.play(1);
		if (gierek != 0) {
			Sterowanie.InfoDrop();
		}
		gierek++;
		Sterowanie.setDefault();
		IntroBoss.czyMoznaPominac = false;
		BossPaszko.czyMoznaPominac = false;

		wyswietlWynik = false;
	}

	private void easy() {
		restart();
		statek1.setEasy();
		BulletPaszkoProstopadly.setEasy();
		BulletPaszkoRownolegly.setEasy();
		HealthPack.setEasy();
		BossPaszko.setEasy();
		BulletExtraPlayer.setEasy();
		BulletPellet.setEasy();
		BulletPlazma.setEasy();
		BulletEyes.setEasy();
	}

	private void medium() {
		restart();
		statek1.setMedium();
		BulletPaszkoProstopadly.setMedium();
		BulletPaszkoRownolegly.setMedium();
		HealthPack.setMedium();
		BossPaszko.setMedium();
		BulletExtraPlayer.setMedium();
		BulletPellet.setMedium();
		BulletPlazma.setMedium();
		BulletEyes.setMedium();
	}

	private void hard() {
		restart();
		statek1.setHard();
		BulletPaszkoProstopadly.setHard();
		BulletPaszkoRownolegly.setHard();
		HealthPack.setHard();
		BossPaszko.setHard();
		BulletExtraPlayer.setHard();
		BulletPellet.setHard();
		BulletPlazma.setHard();
		BulletEyes.setHard();
	}

	public void fullScreen() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		if (!device.isFullScreenSupported() && pelnyekran == false) {
			JOptionPane.showMessageDialog(okno, "Twój komputer nie wspiera pełnego ekranu ;/");
			okno.requestFocus();
		} else if (device.isFullScreenSupported() && pelnyekran == false) {
			Dimension rozmiarekranu = Toolkit.getDefaultToolkit().getScreenSize();
			okno.setSize(rozmiarekranu.width, rozmiarekranu.height);
			okno.dispose();
			okno.setUndecorated(true);
			okno.setResizable(true);
			device.setFullScreenWindow(okno);
			okno.requestFocus();

			pelnyekran = true;
			aktualizujWymiary();
		} else {
			okno.dispose();
			okno.setUndecorated(false);
			okno.setSize(size_x, size_y);
			okno.setVisible(true);
			okno.requestFocus();
			okno.setResizable(false);
			pelnyekran = false;
			aktualizujWymiary();
		}

	}

	private void aktualizujWymiary() {
		size_xx = okno.getWidth();
		size_yy = okno.getHeight();
	}

	private void aktualizujCzasy() {
		Credits.aktualizujCzas(czasPauzy);
		Zaliczenie.aktualizujCzas(czasPauzy);
		statek1.aktualizujCzas(czasPauzy);
		MessageBox.aktualizujeCzasy(czasPauzy);
		Granade.aktualizujCzasy(czasPauzy);
	}

	@Override
	public void keyPressed(KeyEvent key) {
		int klucz = key.getKeyCode();
		if (klucz == KeyEvent.VK_Z) {
			strzal = true;
		} else if (klucz == KeyEvent.VK_LEFT) {
			ruch1L = true;
		} else if (klucz == KeyEvent.VK_RIGHT) {
			ruch1P = true;
		} else if (klucz == KeyEvent.VK_UP) {
			ruch1U = true;
		} else if (klucz == KeyEvent.VK_DOWN) {
			ruch1D = true;
		} else if (klucz == KeyEvent.VK_1) {
			statek1.changeAmunition(0);
		} else if (klucz == KeyEvent.VK_2) {
			statek1.changeAmunition(1);
		} else if (klucz == KeyEvent.VK_3) {
			statek1.changeAmunition(2);
		} else if (klucz == KeyEvent.VK_4) {
			statek1.changeAmunition(3);
		} else if (klucz == KeyEvent.VK_5) {
			statek1.changeAmunition(4);
		} else if (klucz == KeyEvent.VK_P && !Credits.isActive() && MenuGlowne.isEmpty() && MenuESC.isEmpty()) {
			if (!pause) {
				PauzaStart = System.currentTimeMillis();
			} else {
				PauzaStop = System.currentTimeMillis();
				czasPauzy = PauzaStop - PauzaStart;
				aktualizujCzasy();
			}
			pause = !pause;
			if (pause) {
				audio.Mute(true);
			} else if (!mute) {
				audio.Mute(false);
			}

		} else if (klucz == KeyEvent.VK_M) {
			mute = !mute;
			if (!pause)
				audio.Mute(mute);
		} else if (klucz == KeyEvent.VK_S) {
			if (intro && !skipy[0]) {
				MessageTypingIn.skip();
				// audio.play(1);
				skipy[0] = true;
			} else if (instrukcja && !skipy[1]) {
				instrukcja = false;
				sterowanie = false;
				skipy[1] = true;
				if (!Sterowanie.dropAmmo)
					Sterowanie.InfoDrop();
			} else if (!skipy[2] && BossPaszko.czyMoznaPominac) {
				BossPaszko.wylacz();
				skipy[2] = true;
			}
			if (!skipy[3] && IntroBoss.czyMoznaPominac) {
				IntroBoss.wylacz();
				skipy[3] = true;
			}
		} else if (klucz == KeyEvent.VK_R && statek1.isDead()) {
			restart();
		} else if (klucz == KeyEvent.VK_F11) {
			if (!zmienekran)
				zmienekran = !zmienekran;
		} else if (klucz == KeyEvent.VK_ESCAPE && !Credits.isActive() && MenuGlowne.isEmpty() && !pause) {
			if (MenuESC.isEmpty()) {
				new MenuESC();
			} else {
				MenuESC.wylancz();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent key) {
		int klucz = key.getKeyCode();
		if (klucz == KeyEvent.VK_Z) {
			strzal = false;
		} else if (klucz == KeyEvent.VK_LEFT) {
			ruch1L = false;
		} else if (klucz == KeyEvent.VK_RIGHT) {
			ruch1P = false;
		} else if (klucz == KeyEvent.VK_UP) {
			ruch1U = false;
		} else if (klucz == KeyEvent.VK_DOWN) {
			ruch1D = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent key) {

	}

	public void setIntro(boolean b) {
		intro = b;
	}

	public boolean isIntro() {
		return intro;
	}

	public void setSterowanie(boolean sterowanie) {
		this.sterowanie = sterowanie;
	}

	public void setInstrukcja(boolean instrukcja) {
		this.instrukcja = instrukcja;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		Point p = MouseInfo.getPointerInfo().getLocation();
		Rectangle r = okno.getBounds();
		if ((Myszka(p, r, Restart.restart) || Myszka(p, r, Restart.restart2)) && !Restart.isEmpty()) {
			restart();
			Restart.wylancz();
		}
		if (Myszka(p, r, MenuGlowne.wyjscie) && !MenuGlowne.isEmpty()) {
			try {
				System.exit(0);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		if (Myszka(p, r, Restart.Menu) && !Restart.isEmpty()) {
			new MenuGlowne();
			Restart.wylancz();
		}
		if (Myszka(p, r, MenuGlowne.latwy) && !MenuGlowne.isEmpty()) {
			Restart.wylancz();
			easy();
			MenuGlowne.wylancz();
		}
		if (Myszka(p, r, MenuGlowne.sredni) && !MenuGlowne.isEmpty()) {
			Restart.wylancz();
			medium();
			MenuGlowne.wylancz();
		}
		if (Myszka(p, r, MenuGlowne.trudny) && !MenuGlowne.isEmpty()) {
			Restart.wylancz();
			hard();
			MenuGlowne.wylancz();
		}
		if (Myszka(p, r, MenuESC.menu) && !MenuESC.isEmpty()) {
			new MenuGlowne();
			MenuESC.wylancz();
		}
		if (Myszka(p, r, MenuESC.wroc) && !MenuESC.isEmpty()) {
			MenuESC.wylancz();
		}

	}

	@Override
	public void focusGained(FocusEvent f) {
		Object z = f.getSource();
		if (z == okno && pause) {
			if (!pause) {
				PauzaStart = System.currentTimeMillis();
			} else {
				PauzaStop = System.currentTimeMillis();
				czasPauzy = PauzaStop - PauzaStart;
				aktualizujCzasy();
			}
			pause = !pause;
			if (pause) {
				audio.Mute(true);
			} else if (!mute) {
				audio.Mute(false);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent f) {
		Object z = f.getSource();
		if (z == okno && !pause) {
			PauzaStart = System.currentTimeMillis();
			pause = true;
			audio.Mute(true);
			strzal = false;
			ruch1L = false;
			ruch1P = false;
			ruch1U = false;
			ruch1D = false;
		}
	}

}
