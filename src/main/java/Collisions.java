
public class Collisions {

	// tab = {x,y,width, height}

	static void checkCollision(Collisionable o1, Collisionable o2) {
		int[][] tab1 = o1.getPole(), tab2 = o2.getPole();

		if (tab1[0].length != tab2[0].length) {
			if(checkRectOval(o1,o2)||checkRectOval(o2,o1))
			{
				o1.collision(o2);
				o2.collision(o1);
			}
			
		} else if (tab1[0].length == 4) {
			checkRectRect(o1,o2);
		} else {\
			checkOvalOval(o1,o2);
		}

	}

	static private boolean checkRectRect(Collisionable o1, Collisionable o2) {
		int[][] tab1 = o1.getPole(), tab2 = o2.getPole();
		for (int i = 0; i < tab1.length; i++) {
			for(int j = 0; j < tab2.length; j++)
			if ((tab1[i][1] <= tab2[j][1]||tab1[i][1]<=tab2[j][1]+tab2[j][3]<=tab1[i][1]+tab1[i][3])&&()) {

			}
		}
	}

	static private boolean checkRectOval(Collisionable o1, Collisionable o2) {

	}

	static private void checkOvalOval(Collisionable o1, Collisionable o2) {

	}
}
