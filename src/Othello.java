import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Othello {
	public static Board gameBoard;
	
	public static int playingNow = Color.Black;
	
	public static void main(String[] args)throws java.lang.InterruptedException{
		boolean rematch = true;
		do
		{
			@SuppressWarnings("resource")
			Scanner s = new Scanner(System.in);
			int mode;
			System.out.print("Insert game mode: (Type 0 for demo, 1 for player VS CPU or 2 for player VS player)");
			
			mode = s.nextInt();
			
			while (!(mode == 0 || mode == 1 || mode ==2)) {
				System.out.print("Ivalid Input!");
				System.out.print("Please type only 0,1 or 2! (Type 0 for demo, 1 for player VS CPU or 2 for player VS player)");
				mode = s.nextInt();
			}
			if (mode==0){
				
				int difficulty = 3;
				
				Demo(difficulty);
			}
			
			
			else if (mode == 1) {
				@SuppressWarnings("resource")
				Scanner s2 = new Scanner(System.in);
				int difficulty;
				System.out.println("Choose difficulty: 1 for easy - 2 for medium - 3 for hard ");
				int answer = s2.nextInt();
				
				while (!(answer == 1 || answer == 2 || answer == 3)) {
					System.out.print("Ivalid Input!");
					System.out.print("Please type only 1,2 or 3! (Type 1 for easy - 2 for medium - 3 for hard)");
					mode = s2.nextInt();
				}

				switch(answer) {
				
				case 1:
					difficulty = 2;
				case 2:
					difficulty = 4;
				case 3:
					difficulty = 6;
				default:
					difficulty = 3;
				}
				
				PvC(difficulty);
			}
			else if (mode == 2) {
				PvP();
			}
			
			@SuppressWarnings("resource")
			Scanner s3 = new Scanner(System.in);
			char ans;
			System.out.println("\n --------------------------------------\n\n Do you want to play again? type y or n \n\n -------------------------------------- \n\n");
			ans = s3.next().charAt(0);
			
			while (!(ans == 'y' || ans == 'Y' || ans == 'n' || ans == 'N')) {
				System.out.print("Ivalid Input!");
				System.out.print("Please type only y or n! (Type y for rematch or n for close the game)");
				ans = s3.next().charAt(0);
			}
			
			if (ans == 'Y' || ans == 'y')
			{
				rematch = true;
				System.out.println("OK! Let's play again!\n\n");
			}
			else
			{
				rematch = false;
			}
	
			
		}while(rematch==true);
		
		System.out.println("Bye Bye!!");
		TimeUnit.MILLISECONDS.sleep(1);
		System.out.println("Shutting down the game...");
		TimeUnit.MILLISECONDS.sleep(3);
		System.out.println("Press any key to continue...");
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		@SuppressWarnings("unused")
		String str = s.next();
		
		
	}
	
	
	public static void Demo(int maxDepth) throws java.lang.InterruptedException{
	
		Player AI1, AI2;
        

        System.out.print("AI-only play is to fast for human brain to keep up with, so insert the amount of SECONDS you want to pause " +
                "after every turn (0 for none)\n>>>");
        @SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
        float temp = s.nextFloat();
        int sec = (int)(1000 * temp);

        AI1 = new Player(maxDepth, "CPU1", Color.Black);
        AI2 = new Player(maxDepth, "CPU2", Color.White);

        gameBoard = new Board();
        gameBoard.printBoard();

        while(!gameBoard.endOfGame())
        {
            if (AI1.isPlayingNow(playingNow))
            {
            	gameBoard = AI1.MiniMax(gameBoard);
            }
            else
            {
            	gameBoard = AI2.MiniMax(gameBoard);
            }
            TimeUnit.MILLISECONDS.sleep(sec);
            gameBoard.printBoard();
            gameBoard.Score();
            playingNow = 3-playingNow;
        }
        
        int whites = 0;
        int blacks = 0;
        String winner = "";
        
        for (int i = 0; i<8; i++){
			for (int j = 0; j<8; j++){
				
				if (gameBoard.getSquare(i, j).getColor() == Color.White){
					whites++;
				}
				
				else if (gameBoard.getSquare(i, j).getColor() == Color.Black){
					blacks++;
				}
				
				if (blacks > whites) 
				{
					winner = "BLACK'S VICTORY!";
				}
				else if (blacks < whites)
				{
					winner = "WHITE'S VICTORY!";
				}
				else 
				{
					winner = "DRAW...";
				}
			}	
		}
        System.out.println(winner);
        
	}

	
		
	public static void PvC(int maxDepth) throws java.lang.InterruptedException{
	
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
        Player HumanPlayer, AIPlayer;
        
        System.out.print("What is your name? \n");
        String name = scanner.next();

        System.out.print("Black plays first\nPress 'F' to play first\nor any other button to play second\n>>> ");
        String ans = scanner.nextLine();

        

        gameBoard = new Board();
        gameBoard.printBoard();
        int infinityCheck = 0;

        if (ans.equals("F") || ans.equals("f"))
        {
            HumanPlayer = new Player(0, name ,Color.Black);
            AIPlayer = new Player(maxDepth,"CPU1", Color.White);
        }
        else
        {
            HumanPlayer = new Player(0,"Player1" ,Color.White);
            AIPlayer = new Player(maxDepth,"CPU1", Color.Black);
        }

        while(!gameBoard.endOfGame())
        {
            if (HumanPlayer.isPlayingNow(playingNow))
            {
                System.out.println("\nIt's " + HumanPlayer.getName() +  "'s turn: ");
                
                ArrayList<Move> possibleMoves = gameBoard.movesAllowedList(playingNow);
    			
    			if (possibleMoves.isEmpty()) {
    				System.out.println("\nSorry! No available move for you...");
    				
    				infinityCheck++;
    				if(playingNow == Color.Black) {
    					playingNow = Color.White;
    				}
    				else {
    					playingNow = Color.Black;
    				}
    				
    				if (infinityCheck == 2) {
    					gameBoard.finish();
    					System.out.println("\nEnd of game due to lack of moves for both of you...");
    					
    				}
    				continue;
    			}
    			else {
    				infinityCheck = 0;
    				boolean moved = false;
    				
    				while(moved == false) {
    								
    					System.out.println("\nPlease choose one of the following available moves, marked by a *: ");
    					gameBoard.putStarOnPossibleMoves(possibleMoves);
    					System.out.print("\nRow: ");
    					int r = scanner.nextInt();
    					System.out.print("\nColumn: ");
    					int c = scanner.nextInt();
    					
    					
    									
    					Move temp = new Move(r-1, c-1);
    				
    				
    				
    					for (Move m: possibleMoves) {
    						
    						
    						if (m.getX() == temp.getX() && m.getY() == temp.getY()) {
    							gameBoard.play(gameBoard, m, playingNow);
    							moved = true;
    						}
    					}
    					
    					if(moved == false) {
    						System.out.println("\nInvalid Input!\n");
    					}
    				}
    			}
    			
    			
    			gameBoard.printBoard();
    			
    			
    			gameBoard.Score();
    			
    			
    			if(playingNow == Color.Black) {
    				playingNow = Color.White;
    			}
    			else {
    				playingNow = Color.Black;
    			}
            }
            else if (AIPlayer.isPlayingNow(playingNow)){
            	System.out.println("It's " + AIPlayer.getName() +  "'s turn: ");
            	System.out.println("\n Waiting to decide it's move...\n\n");

                TimeUnit.SECONDS.sleep(2);
                gameBoard = AIPlayer.MiniMax(gameBoard);
                            
	            gameBoard.printBoard();
	           
	            gameBoard.Score();
	           
	            if(playingNow == Color.Black) {
					playingNow = Color.White;
				}
				else {
					playingNow = Color.Black;
				}
            }
        }
        
        System.out.println("\n\n\nAND");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("THE");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("WINNER");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("IIIIIIIIIIISSSSSS.........");
        TimeUnit.SECONDS.sleep(2);
        
        int whites = 0;
        int blacks = 0;
        String winner = "";
        
        for (int i = 0; i<8; i++){
			for (int j = 0; j<8; j++){
				
				if (gameBoard.getSquare(i, j).getColor() == Color.White){
					whites++;
				}
				
				else if (gameBoard.getSquare(i, j).getColor() == Color.Black){
					blacks++;
				}
				
				if (blacks > whites) 
				{
					if (HumanPlayer.getPlayerColor() == Color.Black) { winner = "HUMAN! CONGRATULATIONS!!!";}
					else {winner = "CPU! ROBOTS ARE COMING TO DESTROY YOU111";}
				}
				else if (blacks < whites)
				{
					if (HumanPlayer.getPlayerColor() == Color.White) { winner = "HUMAN! CONGRATULATIONS!!!";}
					else {winner = "CPU! ROBOTS ARE COMING TO DESTROY YOU111";}
				}
				else 
				{
					winner = "NO ONE!IT'S A DRAW...NICE TRY!";
				}
			}	
		}
        System.out.println(winner);
        
	}
	
	
	
	public static void PvP() throws java.lang.InterruptedException{ //player vs player mode
		
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		
		
		System.out.println("Enter your names below. Player 1 is black and plays first.");
		System.out.println("Player 1: ");
		String p1 = s.nextLine();
		System.out.println("Player 2: ");
		String p2 = s.nextLine();
		System.out.println(p1 + " is black and " + p2 + " is white.");
		
		Player Player1 = new Player(0, p1, 1);
		Player Player2 = new Player(0, p2, 2);
		
		
		gameBoard = new Board();
		gameBoard.printBoard();
		int infinityCheck = 0;
		
		while(!gameBoard.endOfGame()) {
			if (Player1.isPlayingNow(playingNow)) {
				System.out.println("It's " + p1 + "'s turn: ");
			}
			else {
				System.out.println("It's " + p2 + "'s turn: ");
			}
			
			ArrayList<Move> possibleMoves = gameBoard.movesAllowedList(playingNow);
			
			if (possibleMoves.isEmpty()) {
				System.out.println("Sorry! No available move for you...");
				infinityCheck++;
				if(playingNow == Color.Black) {
					playingNow = Color.White;
				}
				else {
					playingNow = Color.Black;
				}
				
				if (infinityCheck == 2) {
					gameBoard.finish();
					System.out.println("End of game due to lack of moves for both of you...");					
				}
				continue;
			}
			else {
				infinityCheck = 0;
				boolean moved = false;
				
				while(moved == false) {
								
					System.out.println("Please choose one of the following available moves, marked by a *: ");
					gameBoard.putStarOnPossibleMoves(possibleMoves);
					System.out.print("\nRow: ");
					int r = s.nextInt();
					System.out.print("\nColumn: ");
					int c = s.nextInt();
					
									
					Move temp = new Move(r-1, c-1);
				
				
				
					for (Move m: possibleMoves) {
						
						
						if (m.getX() == temp.getX() && m.getY() == temp.getY()) {
							gameBoard.play(gameBoard, m, playingNow);
							moved = true;
						}
					}
					
					if(moved == false) {
						System.out.println("Invalid Input!");
					}
				}
			}
			
			
			gameBoard.printBoard();
			
			gameBoard.Score();
			
			if(playingNow == Color.Black) {
				playingNow = Color.White;
			}
			else {
				playingNow = Color.Black;
			}
		
		}
		
		System.out.println("\n\n\nAND");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("THE");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("WINNER");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("IIIIIIIIIIISSSSSS.........");
        TimeUnit.SECONDS.sleep(2);
        
        int whites = 0;
        int blacks = 0;
        String winner = "";
        
        for (int i = 0; i<8; i++){
			for (int j = 0; j<8; j++){
				
				if (gameBoard.getSquare(i, j).getColor() == Color.White){
					whites++;
				}
				
				else if (gameBoard.getSquare(i, j).getColor() == Color.Black){
					blacks++;
				}
				
				if (blacks > whites) 
				{
					if (Player1.getPlayerColor() == Color.Black) { winner = Player1.getName() + "! CONGRATULATIONS!!!";}
					else {winner = Player2.getName() + "! CONGRATULATIONS!!!";}
				}
				else if (blacks < whites)
				{
					if (Player1.getPlayerColor() == Color.White) { winner = Player1.getName() + "! CONGRATULATIONS!!!";}
					else {winner = Player2.getName() + "! CONGRATULATIONS!!!";}
				}
				else 
				{
					winner = "NO ONE!IT'S A DRAW...NICE TRY!";
				}
			}	
		}
        System.out.println(winner);
		
		
	}
	
	 
}
