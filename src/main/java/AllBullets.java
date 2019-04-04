import java.util.ArrayList;
import java.util.List;

public class AllBullets {

	static List<Bullet> bullets = new ArrayList<>();
	
	static void add(Bullet bullet)
	{
		bullets.add(bullet);
	}
	
	static void motion()
	{
		for(int i=0; i<bullets.size(); i++)
		{
			bullets.get(i).motion();
		}
	}
	
	static void drawBullets()
	{
		for(int i=0; i<bullets.size(); i++)
		{
			bullets.get(i).draw();
		}
	}
}
