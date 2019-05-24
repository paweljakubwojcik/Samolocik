package Bullets;
import java.awt.Color;
import java.awt.Graphics2D;

public class Granade extends Bullet {

	boolean bang = false;
	int opacity=255;

	public static long delay = 400;

	public static int size = 15; // kazdy rodzaj kuli musi miec swoj statyczny rozmiars
	int size2;
	
	public Granade(int x, int y) {
		super(x, y);
		this.size2=size;
		damage = 5;

	}

	synchronized void draw(Graphics2D g) {
		g.setColor(new Color(255, 255, 0, opacity));
		if (!bang)
			g.fillOval(x, y, size, size);
		else 
			g.drawOval(x, y, size2, size2);
	}

	@SuppressWarnings("static-access")
	@Override
	void MyMotion() {

		if (!bang) {
			if (y > 0 && y < win.size_y)
				y -= velocity;
			else
				try {
					bullets.remove(this);
					this.finalize();

				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} else {
			size2++;
			opacity-=10;
			
		}
		if(opacity<=10) Bullet.bullets.remove(this);
	}
	
	public void detonate()
	{
		bang=true;
	}
}
