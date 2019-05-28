package Rozgrywka;


public abstract class Collisionable {

	
	/**
	 * function have to return if(square){x,y,width,height} 
	 * if(oval) {x,y,radius}
	 * first dimension is number of shapes
	 * @return int[][]
	 */
	public abstract int[][] getPole();


	/**
	 * @param o 
	 */
	public abstract void collision(Object o);
}
