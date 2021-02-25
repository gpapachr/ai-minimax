import java.util.ArrayList;


public class Move {
	private ArrayList<int[]> directions = new ArrayList<>();
	private int x,y;
	
	public Move(int x, int y) {
		setX(x);
		setY(y);
		directions.clear();
		
	}
	
	void insertDirection(int[] direction) {
		directions.add(direction);
	}
	
	public ArrayList<int[]> getDirections(){
		return directions;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
}
