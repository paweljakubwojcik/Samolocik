import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

public class Window implements KeyListener {

	JFrame okno;
	public static final int size_x = 800, size_y = 600;

	private int tloY = -size_y, pozycjatla = 0;
	BufferedImage klatka;
	BufferedImage imc = new BufferedImage(size_x, size_y * 2, BufferedImage.TYPE_INT_ARGB);
	BufferedImage im1 = new BufferedImage(size_x, size_y, BufferedImage.TYPE_INT_ARGB); // obrazek
																						// tla1
	BufferedImage im2 = new BufferedImage(size_x, size_y, BufferedImage.TYPE_INT_ARGB); // obrazek
																						// tla2

	Player statek1;
	Thread Game;
	boolean ruch1L = false;
	boolean ruch1P = false;
	boolean ruch1D = false;
	boolean ruch1U = false;
	boolean strzal = false;
	boolean pause = false;
<<<<<<< HEAD
	boolean playerdeath = false;
=======
>>>>>>> branch 'master' of https://github.com/7Adrian/Samolocik.git

	boolean spanie = true; // kombinuje jak siê tego pozbyæ

	Window() {
		okno = new JFrame("Niewdzieczna przestrzen");
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setResizable(false);
		okno.setLocationRelativeTo(null);
		okno.setSize(size_x, size_y);
		okno.setLayout(null);
		okno.setVisible(true);
		okno.addKeyListener(this);
		klatka = new BufferedImage(size_x, size_y, BufferedImage.TYPE_INT_ARGB);
		statek1 = new Player(this, 400, 500);
		Graphics2D g3 = (Graphics2D) okno.getGraphics();

		EnemyGenerator generator = new EnemyGenerator(this);

		losujtlo(im1);
		losujtlo(im2);
		scaltla(im1, im2);

		Game = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					if (!pause) {

<<<<<<< HEAD
						draw();
						Bullet.motion();
						Enemy.motion();
						Drop.motion();
						generator.generate();
						sprawdzKolizje();
=======
						if (!statek1.isDead()) {
							draw();
							Bullet.motion();
							Enemy.motion();
							Drop.motion();
							generator.generate();
							sprawdzKolizje();
>>>>>>> branch 'master' of https://github.com/7Adrian/Samolocik.git

							if (ruch1L)
								statek1.moveLeft();
							if (ruch1P)
								statek1.moveRight();
							if (ruch1U)
								statek1.moveUp();
							if (ruch1D)
								statek1.moveDown();
							if (strzal)
								statek1.strzal();
						} else
							endOfGame();
						try {
							Thread.sleep(1000 / 60);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						////////////////////////////
						ruchTla();
						///////////////////////////////
						while (playerdeath) {
							g3.setColor(Color.RED);
							g3.fillRect(0, 0, size_x, size_y);
							g3.setColor(Color.BLACK);
							g3.setFont(new Font(null, 100, 100));
							g3.drawString("UMARLEM", size_x / 5, size_y / 2);
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

					} else {
						////////// rysowanie znaku zatrzymania//////////////
						spanie = !spanie;
						if (spanie)
							drawklatka();
						else  {
							g3.setColor(Color.white);
							g3.fillRect(size_x / 2 - 30, size_y / 2 - 30, 20, 50);
							g3.fillRect(size_x / 2 + 20, size_y / 2 - 30, 20, 50);
						}

						///////////////////////////////////////////////////////
						try {
							Thread.sleep(700);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
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
		Bullet.drawBullets(g);
		Enemy.draw(g);
		Drop.draw(g);
		statek1.draw(g);
		MessageBox.draw(g);
		g.dispose();
		drawklatka();
	}

	void drawklatka() {
		Graphics2D g2d = (Graphics2D) okno.getGraphics();
		g2d.drawImage(klatka, 0, 0, null);
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
		// g2d.setColor(Color.WHITE);
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

	void endOfGame() {
		draw();
		Bullet.motion();
//		Graphics2D g = (Graphics2D) okno.getGraphics();
//		g.setColor(new Color(255,0,0,100));
//		g.fillRect(0, 0, size_x, size_y);
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
			statek1.whichAmunition = 0;
		} else if (klucz == KeyEvent.VK_2) {
			statek1.whichAmunition = 1;
		} else if (klucz == KeyEvent.VK_3) {
			statek1.whichAmunition = 2;
		} else if (klucz == KeyEvent.VK_4) {
			statek1.whichAmunition = 3;
		} else if (klucz == KeyEvent.VK_5) {
			statek1.whichAmunition = 4;
		} else if (klucz == KeyEvent.VK_P) {
			pause = !pause;
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
			Collisions.checkCollision( Bullet.bullets.get(i),statek1); // player
																		// z
																		// nabojami
		}

		for (int i = 0; i < Drop.drops.size(); i++) {
			Collisions.checkCollision(statek1, Drop.drops.get(i)); // player
																	// z
																	// dropami
		}
	}

}
