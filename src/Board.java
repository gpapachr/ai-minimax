

import java.util.ArrayList;

public class Board{
	public Square[][] board = new Square[8][8];
	private int movesLeft;
	public int value;
	
	public Board()
	{
		value = 0;
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				board[i][j] = new Square(i, j);
			}
		}
		movesLeft = 60; //8*8 = 64 - 4 init squares = 60
		board[3][3].setColor(Color.White);
		board[3][4].setColor(Color.Black);
		board[4][3].setColor(Color.Black);
		board[4][4].setColor(Color.White);
		
	}
	
	public Board(int v)
	{
		value = v;
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				board[i][j] = new Square(i, j);
			}
		}
		movesLeft = 60; //8*8 = 64 - 4 init squares = 60
		board[3][3].setColor(Color.White);
		board[3][4].setColor(Color.Black);
		board[4][3].setColor(Color.Black);
		board[4][4].setColor(Color.White);
		
	}
	
	public Board(Board pBoard) {
		this.movesLeft = pBoard.movesLeft;
		this.value = pBoard.value;
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				this.board[i][j] = new Square(pBoard.board[i][j].getX(),pBoard.board[i][j].getY(), 
						pBoard.board[i][j].getColor());
			
			}
		}
	}
	
	public void finish() {
		this.movesLeft = 0;
	}
	
	public void printBoard(){
		System.out.println("\n");
		System.out.println(" |1|2|3|4|5|6|7|8| ");
				
		int[] rows = {1, 2, 3, 4, 5, 6, 7, 8};
		for (int i = 0; i < 8; i++){
			System.out.print(rows[i]+"|");
			for (int j = 0; j < 8; j++){
				System.out.print(board[i][j].getContent() + "|");
			}
			System.out.print("\n");
						
		}
		System.out.print("\n");
		System.out.print("------------------");
		System.out.print("\n");
	}

/*    |A|B|C|D|E|F|G|H|       <-- 	init
     1| | | | | | | | |
	 2| | | | | | | | |
	 3| | | | | | | | |
	 4| | | |B|W| | | |
	 5| | | |W|B| | | |
	 6| | | | | | | | |
	 7| | | | | | | | |
	 8| | | | | | | | |

*/
	public void Score(){
		int whites = 0;
		int blacks = 0;
		float remainingEmpty = 0;
		
		
		for (int i = 0; i<8; i++){
			for (int j = 0; j<8; j++){
				
				if (board[i][j].getColor() == Color.White){
					whites++;
				}
				
				else if (board[i][j].getColor() == Color.Black){
					blacks++;
				}
				
				else{
					remainingEmpty++;
				}
			}	
		}
		System.out.println("    Score");
		System.out.println("-------------");
		System.out.println("Blacks = " + blacks);
		System.out.println("vs");
		System.out.println("Whites = " + whites+ "\n");
		System.out.println("Empty  = " + 100*(remainingEmpty/64) + "%");
		
	}
		/*
		      Score
		  -------------
		  Blacks = 2    
		  vs
		  Whites = 2       <- board.Score() output before first move
		  
		  Empty  = 93,75%
		 */
	
	
	public boolean endOfGame() {
		if (movesLeft <= 0) {
			return true;
		}
		return false;
	}
	
	public Square getSquare(int a, int b) {
        return board[a][b];
    }
	
	
	public ArrayList<Move> movesAllowedList(int colorPlayingNow)
	{
		ArrayList<Move> mAL = new ArrayList<>();
		
		for (int i = 0; i<8; i++) {
			
			for (int j = 0; j<8; j++) {
				
				Square cSquare = board[i][j];  //current Square that we check if it could be a possible move
				
				if(cSquare.getColor() == Color.Empty) { //if it's empty square, its a possible move and we check the neighbors
					
					for (int row = -1; row <= 1; row++) { // row up, current and down
						
						for(int column = -1; column<=1; column++) { // column left, current and right
							
							if (row != 0 || column != 0) { //row = 0 and column = 0 will return the current position
								
								try {
									
									Square neighbor = getSquare(cSquare.getX() + row, cSquare.getY() + column); //check the neighbors around cSquare
									
									if (neighbor.getColor() != colorPlayingNow && neighbor.getColor() != Color.Empty) {//if this neighbor has the opposite color
										
										int enemySquares = 0; // counts enemy squares in that direction till the first colorPlayingNow square
										
										while(neighbor.getColor() != Color.Empty) { // while we don't find an Empty cell
											
											neighbor = getSquare(neighbor.getX() + row, neighbor.getY() + column); //find a neighbor's neighbor in the same direction
											
											enemySquares++; //we count the first neighbor
											
											if (neighbor.getColor() == colorPlayingNow) {//if new neighbor has the colorPlayingNow color then we have a moveAllowed!
												
												boolean known = false;
												
												for (int a =0; a<mAL.size(); a++) {//first we check if the "new" move is already in the MA list
													
													Move m = mAL.get(a);
													
													if (m.getX() == cSquare.getX() && m.getY() == cSquare.getY()) {
														
														//is already in my list so i have to add on this move a new direction
														
														m.insertDirection(new int[] {row, column, enemySquares});
														
														known = true;
														
														break;
													}
												}
												//isn't already in my list so...
												if (!known) {
													
													Move new_move = new Move(cSquare.getX(), cSquare.getY());
													
													new_move.insertDirection(new int[] {row, column, enemySquares});
													
													mAL.add(new_move);
												}
											}
										}
									}
								}
								catch (ArrayIndexOutOfBoundsException e) {//in case of out of board searching
									continue;
								}
							}
						}
					}
				}
			}
		}
		return mAL;
	}
	
	
	public Board play(Board tBoard, Move m, int playingNow) {
		tBoard.getSquare(m.getX(), m.getY()).setColor(playingNow);
		
		tBoard.movesLeft--;
		
		
		
		for (int[] d: m.getDirections()) { //for i in possible directions of m, change the color of the enemy squares and evaluate the changes
					
			int tx = m.getX() + d[0];
			int ty = m.getY() + d[1];
			
			try {
				for (int j = 0; j < d[2]; j++) {
					if(tBoard.getSquare(tx, ty).getColor()!=playingNow) {
						tBoard.getSquare(tx, ty).changeColor();
						tx+=d[0];
						ty+=d[1];
					}
				}		
			}
			
			catch (ArrayIndexOutOfBoundsException e) {//in case of out of board searching
				continue;
			}	
		}
		tBoard.evaluate();
		
		return tBoard;
		
	}
	
	
	public ArrayList<Board> childBoard(int playingNow){
		
		ArrayList<Board> c = new ArrayList<Board>(); //construct a new list of children, one for every move in movesAllowedList
		
		ArrayList<Move> l = movesAllowedList(playingNow);
		
		for (int i = 0; i< l.size(); i++) {
			c.add(play(new Board(this), l.get(i), playingNow));
			
		}
		
		return c;
	}
		
	
	public void evaluate() {
		value = 0;
		int[][] squareValues =
			{
					{20, 1,  5,  5,  5,  5,  1, 20},
					{ 1, 1,  3,  3,  3,  3,  1,  1},
					{ 5, 3, 10, 10, 10, 10,  3,  5},				
					{ 5, 3, 10,  0,  0, 10,  3,  5},
					{ 5, 3, 10,  0,  0, 10,  3,  5},
					{ 5, 3, 10, 10, 10, 10,  3,  5},
					{ 1, 1,  3,  3,  3,  3,  1,  1},
					{20, 1,  5,  5,  5,  5,  1, 20}
			};
		int b=0, w=0;
		for(int x = 0; x < 8; x++)
        {
            for(int y = 0; y < 8; y++)
            {
                if(board[x][y].getColor() == Color.Black)
                {
                    b += squareValues[x][y];
                }
                else if(board[x][y].getColor() == Color.White)
                {
                    w += squareValues[x][y];
                }
            }
        }
        try
        {
            value = (b - w);
        }
        catch (Exception e)
        {
            value = 0;
        }

        b += movesAllowedList(Color.Black).size();
        w += movesAllowedList(Color.White).size();

        try
        {
            value += (b - w) ;
        }
        catch (Exception e)
        {
            value = 0;
        }
    }
		
	
	
	public void putStarOnPossibleMoves(ArrayList<Move> pM) {
		for (int i = 0; i < pM.size(); i++) {
			Move m = pM.get(i);
			getSquare(m.getX(), m.getY()).setColor(3);	//put a star on every possible move, there are already empty
		}
			printBoard();										//print board so player can see his possible moves with *
		
		for (int i = 0; i < pM.size(); i++) {
			Move m = pM.get(i);
			getSquare(m.getX(), m.getY()).setColor(0);	//restore the changes
		}
	}
}




	 