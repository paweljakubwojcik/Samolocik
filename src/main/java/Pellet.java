public class Pellet {
	
	private static final int liczbaWystrzalow = 3;

	public Pellet(int x, int y) {
		for(int i=-liczbaWystrzalow; i<=liczbaWystrzalow; i++) {
			new BulletPellet(x, y, obliczX(i), obliczY(i));
		}
//		new BulletPelletLeft(x, y);
//		new BulletPelletCenter(x, y);
//		new BulletPelletRight(x, y);
	}

	private float obliczY(int i) {
		
		return Math.abs(i);
	}

	private float obliczX(int i) {

		return (float) ((float) 10*Math.sin(10*i*Math.PI/180));
	}
}
