
public class Square {
	private int x, y, color;
	
	
	public Square(int x, int y) 						//constructor without color initialization
	{
		this.x = x;
		this.y = y;
		color = Color.Empty;		
	}
	
	
	
	public Square(int x, int y, int color)				//constructor with color initialization
	{
		this.x = x;
		this.y = y;
		this.color = color;		
	}
	
		
	
	public void changeColor()							//after player's move, we change the color of squares between the 2 player's squares 
	{
		if (color == Color.White)
		{
			color = Color.Black;
		}
		else if (color == Color.Black)
		{
			color = Color.White;
		}
	}
	
	

	public int getColor() {
		return color;
	}
	

	public void setColor(int color) {
		this.color = color;
	}
	
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public char getContent()				//converts color data into UI symbols
	{
		if (color == Color.White)
		{
			return 'W';
		}
		else if (color == Color.Black)
		{
			return 'B';
		}
		else if(color == Color.Star)
		{
			return '*';
		}
		else 
		{
			return ' ';
		}
		
	}
}
