import java.util.Random;

public class Enemy {

	int x,y;
	int health;
	int width, height;
	int velocity_x, velocity_y;
	protected int zakresRuchu;
	Window win;
	static Random generator = new Random();
	
	Enemy(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
}
