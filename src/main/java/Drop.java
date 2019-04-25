import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Drop extends Collisionable {

static List<Drop> drops = new ArrayList<>();	
static Random generator = new Random();
	
int x,y,width, height,velocity;
static Window win = Samolotoszczalec.win;


Drop(int x, int y, Window win)
{
	this.x=x;
	this.y=y;
	this.velocity=generator.nextInt(2);
	//this.win=win;
	drops.add(this);
	}

@SuppressWarnings("static-access")
void myMotion()
{
	y+=velocity;
	if(y>win.size_y) drops.remove(this);
}

abstract void drawMe(Graphics2D g);

}
