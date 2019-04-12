
public class Collisions {

	// tab = {x,y,width, height}

	static void checkCollision(Collisionable o1, Collisionable o2) {
		int[][] tab1 = o1.getPole(), tab2 = o2.getPole();

		if (tab1[0].length != tab2[0].length) {
			if (checkRectOval(tab1, tab2) || checkRectOval(tab2, tab1)) {
				o1.collision(o2);
				o2.collision(o1);
			}

		} else if (tab1[0].length == 4) {
			if (checkRectRect(tab1, tab2) || checkRectRect(tab2, tab1)) {
				o1.collision(o2);
				o2.collision(o1);
			}
		} else {
			if (checkOvalOval(tab1, tab2) || checkOvalOval(tab2, tab1)) {
				o1.collision(o2);
				o2.collision(o1);
			}
		}

	}

	static private boolean checkRectRect(int[][] tab1, int[][] tab2) {
		for (int i = 0; i < tab1.length; i++) {
			for(int j = 0; j < tab2.length; j++)
			if ((tab1[i][1] <= tab2[j][1]||tab1[i][1]<=tab2[j][1]+tab2[j][3]<=tab1[i][1]+tab1[i][3])) {
				return true;
			}
		}
		return false;
	}

	static private boolean checkRectOval(int[][] tab1, int[][] tab2) {
		
	}

	static private boolean checkOvalOval(int[][] tab1, int[][] tab2) {

	}
}
