package assignment6; 
import assignment6.TicTacToe;
import assignment6.TicTacToeAI;

class main
{
	
	public static void changeTurn(TicTacToe game, PLAYER curr)
	{
		if(curr == PLAYER.PLAYER_O)
		{
			game.currTurn = PLAYER.PLAYER_X; 
		}
		else if(curr == PLAYER.PLAYER_X)
		{
			game.currTurn = PLAYER.PLAYER_O; 
		}
	}
	
	public static void playerTurn(TicTacToe game, PLAYER p1)
	{
		int[] result = game.getUserInput(); 
		boolean moveMade = game.move(game.board_matrix, p1, result[0], result[1]); 
		System.out.println(moveMade); 
		game.print(game.board_matrix); 
		changeTurn(game, game.currTurn); 
	}
	
	public static void main(String[] args)
	{
		TicTacToe game = new TicTacToe(); 
		TicTacToeAI ai = new TicTacToeAI(); 
		game.print(game.board_matrix);
		System.out.println("Welcome to Tic Tac Toe!");
		System.out.println("It's " + game.currTurn + "'s turn first!");
		while(game.winner(game.board_matrix) == PLAYER.EMPTY)
		{
			boolean turnFinished = false; 
			if(game.findValidMoves(game.board_matrix).isEmpty()){
				System.out.println("DRAW!");
				break; 
			}
			if(game.currTurn == PLAYER.PLAYER_X)
			{
				int[] userInput = game.getUserInput(); 
				game.move(game.board_matrix, PLAYER.PLAYER_X, userInput[0], userInput[1]); 
				game.print(game.board_matrix);
				turnFinished = true; 
			}
			else if(game.currTurn == PLAYER.PLAYER_O)
			{
				System.out.println("Computer making move...");
				int[] result = ai.bestMove(game.board_matrix, PLAYER.PLAYER_O); 
				game.move(game.board_matrix, PLAYER.PLAYER_O, result[0], result[1]); 
				System.out.println("Computer move to Row: " + result[0] + " Col: " + result[1]);
				game.print(game.board_matrix);
				turnFinished = true; 
			}
			if(turnFinished)
			{
				game.updateTurn(); 
				ai.switchTurn(game.currTurn); 
			}
		}
		PLAYER winner = game.winner(game.board_matrix); 
		System.out.println("WINNER IS " + winner + "!");
	}
}