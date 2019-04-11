//unused
public interface IColision {

/**
 * function have to return if(square){x,y,width,height} 
 * if(oval) {x,y,radius}
 * first dimension is number of shapes
 * @return int[][]
 */
public int[][] getPole();


/**
 * @param o 
 */
void collision(Object o);

}
