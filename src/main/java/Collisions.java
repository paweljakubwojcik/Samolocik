
public class Collisions {

	// tab = {x,y,width, height}

	static void checkCollision(Collisionable o1, Collisionable o2) {
		/* long time1=System.currentTimeMillis(); */

		int[][] tab1 = o1.getPole(), tab2 = o2.getPole();

		if (tab1[0].length > tab2[0].length) {
			if (checkRectOval(tab1, tab2)) {
				o1.collision(o2);
				o2.collision(o1);
				// System.out.println("kolizja");
			}

		} else if (tab1[0].length < tab2[0].length) {
			if (checkRectOval(tab2, tab1)) {
				o1.collision(o2);
				o2.collision(o1);
				// System.out.println("kolizja");
			}

		} else if (tab1[0].length == 4) {

			if (checkRectRect(tab1, tab2) || checkRectRect(tab2, tab1)) {
				o1.collision(o2);
				o2.collision(o1);
				// System.out.println("kolizja");
			}
		} else {
			if (checkOvalOval(tab1, tab2) || checkOvalOval(tab2, tab1)) {
				o1.collision(o2);
				o2.collision(o1);
				// System.out.println("kolizja");
			}
		}

		/*
		 * long time2 =System.currentTimeMillis();
		 * System.out.print("Funkccja sprawdzenie kolizji wykonuje sie w : ");
		 * System.out.println(time2-time1);
		 */

	}

	/*
	 * ta funkcja jest okej
	 */
	static private boolean checkRectRect(int[][] tab1, int[][] tab2) {
		for (int i = 0; i < tab1.length; i++) {
			for (int j = 0; j < tab2.length; j++) {

				double x_0 = (double) tab1[i][0];
				double y_0 = (double) tab1[i][1];
				double x = (double) tab1[i][2];
				double y = (double) tab1[i][3];

				double x_1 = (double) tab2[j][0];
				double y_1 = (double) tab2[j][1];
				double x_2 = (double) tab2[j][2];
				double y_2 = (double) tab2[j][3];

				if (((y_0 <= y_1) && (y_1 <= y_0 + y) || (y_0 <= y_1 + y_2 && y_1 + y_2 <= y_0 + y))
						&& ((x_0 <= x_1) && (x_1 <= x_0 + x) || (x_0 <= x_1 + x_2 && x_1 + x_2 <= x_0 + x))) {
					return true;
				}
			}
		}
		return false;
	}

	static private boolean checkRectOval(int[][] tab1, int[][] tab2) {
		for (int i = 0; i < tab1.length; i++) {
			for (int j = 0; j < tab2.length; j++) {

				double x_1 = (double) tab1[i][0];
				double y_1 = (double) tab1[i][1];
				double x = (double) tab1[i][2];
				double y = (double) tab1[i][3];

				double x_0 = (double) tab2[j][0];
				double y_0 = (double) tab2[j][1];
				double r_0 = (double) tab2[j][2];

				if ((Math.sqrt((x_0 - x_1) * (x_0 - x_1) + (y_0 - y_1) * (y_0 - y_1)) <= r_0)
						|| (Math.sqrt((x_0 - x_1 - x) * (x_0 - x_1 - x) + (y_0 - y_1) * (y_0 - y_1)) <= r_0)
						|| (Math.sqrt((x_0 - x_1) * (x_0 - x_1) + (y_0 - y_1 - y) * (y_0 - y_1 - y)) <= r_0)
						|| (Math.sqrt((x_0 - x_1 - x) * (x_0 - x_1 - x) + (y_0 - y_1 - y) * (y_0 - y_1 - y)) <= r_0))

					return true;

				else if ((x_0 - r_0 - x <= x_1 && x_1 <= x_0 + r_0) && (y_0 - r_0 - y <= y_1 && y_1 <= y_0 + r_0))

					return true;
			}
		}
		return false;
	}

	// a to nie sprawdzone jest nawet
	static private boolean checkOvalOval(int[][] tab1, int[][] tab2) {
		for (int i = 0; i < tab1.length; i++) {
			for (int j = 0; j < tab2.length; j++) {
				double x_1 = (double) tab2[j][0];
				double x_0 = (double) tab1[i][0];
				double y_1 = (double) tab2[j][1];
				double y_0 = (double) tab1[i][1];
				double r_1 = (double) tab2[j][2];
				double r_0 = (double) tab1[i][2];
				if (Math.sqrt((x_1 - x_0) * (x_1 - x_0) + (y_1 - y_0) * (y_1 - y_0)) <= r_0 + r_1)
					return true;
			}
		}
		return false;
	}
}
