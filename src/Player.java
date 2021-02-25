import java.util.ArrayList;

public class Player {
	private int maxDepth;
	private String name;
	private int playerColor;
	
	public Player(int maxDepth, String name, int playerColor) {
		this.maxDepth = maxDepth;
		this.name = name;
		this.playerColor = playerColor;
	}
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getPlayerColor() {
		return playerColor;
	}


	public void setPlayerColor(int playerColor) {
		this.playerColor = playerColor;
	}


	public boolean isPlayingNow(int color) {
		return playerColor == color;
	}
	
	
	public Board MiniMax(Board board) {
		if(playerColor == Color.Black) {
			return max(new Board(board), Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		}
		else {
			return min(new Board(board), Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		}
	}
	
	public Board max(Board board, int a, int b, int depth) {
		if(board.endOfGame() || depth == maxDepth) {
			return board;
		}
		ArrayList<Board> children = board.childBoard(Color.Black);
		if (children.isEmpty()) {
			System.out.println("Turn Skipped...No Available moves....");
			return board;
		}
		Board maxBoard = new Board(Integer.MIN_VALUE);
		for (Board child: children)
		{
			Board opponent = min(child, a, b, depth + 1);
			if(opponent.value >= maxBoard.value)
			{
				maxBoard = child;
			}
			
			a = Integer.max(a, opponent.value);
			
			if(b<=a)
			{
				break;
			}
		}
		return maxBoard;
	}
	
	public Board min(Board board, int a, int b, int depth)
	{
		if(board.endOfGame() || depth == maxDepth) {
			return board;
		}
		ArrayList<Board> children = board.childBoard(Color.White);
		
		if (children.isEmpty()) {
			System.out.println("Turn Skipped...No Available moves....");
			
			return board;
		}
		Board minBoard = new Board(Integer.MAX_VALUE);
		for (Board child: children)
		{
			Board opponent = max(child, a, b, depth + 1);
			if(opponent.value <= minBoard.value)
			{
				minBoard = child;
			}
			b = Integer.min(b, opponent.value);
			if (b<=a)
			{
				break;
			}
		}
		return minBoard;
		
	}
}
