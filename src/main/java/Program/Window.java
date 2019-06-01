package Program;

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
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import AI.BossPaszko;
import AI.Enemy;
import AI.EnemyGenerator;
import Bullets.Bullet;
import Bullets.Drop;
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

	Window() {
		okno = new JFrame("Niewdzieczna przestrzen");
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

		audio.readIntro();

		losujtlo(im1);
		losujtlo(im2);
		scaltla(im1, im2);

		for (int i = 0; i < 10; i++) {
			skipy[i] = false;
		}

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

					if (!pause) {

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

		MessageTypingIn.draw(g);
		Restart.draw(g);
		Zaliczenie.draw(g);
		IntroBoss.draw(g);

		g.dispose();
		drawklatka();

		Point p = MouseInfo.getPointerInfo().getLocation();
		Rectangle r = okno.getBounds();
		if (p.x - r.x > Restart.restartx * ((float) size_xx / size_x)
				&& p.x - r.x < Restart.restartsizex * ((float) size_xx / size_x)
						+ Restart.restartx * ((float) size_xx / size_x)
				&& p.y - r.y > Restart.restarty * ((float) size_yy / size_y)
				&& p.y - r.y < Restart.restartsizey * ((float) size_yy / size_y)
						+ Restart.restarty * ((float) size_yy / size_y)
				&& !Restart.isEmpty()) {
			Restart.hover = true;
			cursor = new Cursor(Cursor.HAND_CURSOR);
			okno.setCursor(cursor);
		} else {
			Restart.hover = false;
			cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			okno.setCursor(cursor);
		}

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

	void Mute(boolean b) {
		// audio.setMusic(!b);
		if (b)
			audio.stop();
		if (!b && !audio.isRunning())
			audio.play(0);
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
		MessageBox.restart();
		audio.setDefault();
		audio.play(0);
		Sterowanie.InfoDrop();

		wyswietlWynik = false;
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
		} else if (klucz == KeyEvent.VK_P) {
			pause = !pause;

			mute = pause;
			Mute(mute);

		} else if (klucz == KeyEvent.VK_M) {
			mute = !mute;
			Mute(mute);
		} else if (klucz == KeyEvent.VK_S) {
			if (intro && !skipy[0]) {
				MessageTypingIn.skip();
				audio.readIntroStop();
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
		Point p = MouseInfo.getPointerInfo().getLocation();
		Rectangle r = okno.getBounds();
		if (p.x - r.x > Restart.restartx * ((float) size_xx / size_x)
				&& p.x - r.x < Restart.restartsizex * ((float) size_xx / size_x)
						+ Restart.restartx * ((float) size_xx / size_x)
				&& p.y - r.y > Restart.restarty * ((float) size_yy / size_y)
				&& p.y - r.y < Restart.restartsizey * ((float) size_yy / size_y)
						+ Restart.restarty * ((float) size_yy / size_y)
				&& !Restart.isEmpty())
			restart();

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void focusGained(FocusEvent f) {

	}

	@Override
	public void focusLost(FocusEvent f) {
		Object z = f.getSource();
		if (z == okno) {
			pause = true;
			strzal = false;
			ruch1L = false;
			ruch1P = false;
			ruch1U = false;
			ruch1D = false;
		}
	}

}
