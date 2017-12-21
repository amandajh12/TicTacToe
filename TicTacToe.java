package assignment6; 
import java.util.Scanner; 
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.HashMap; 
import java.util.Random;

enum PLAYER {PLAYER_X, PLAYER_O, EMPTY}; 

class TicTacToe
{
	final int ROWS = 3; 
	final int COLS = 3; 
	int[][] board_matrix = new int[ROWS][COLS]; 
	PLAYER currTurn = PLAYER.EMPTY; 
	
	/**
	 * Constructs TicTacToe object; initializes current turn randomly choosing player to go first; PLAYER_O is human, PLAYER_X is AI
	 */
	public TicTacToe()
	{
		Random rand = new Random();
		int  n = rand.nextInt(50) + 1;
		if(n%2 == 0)
		{
			currTurn = PLAYER.PLAYER_O; 
		}
		else {
			currTurn = PLAYER.PLAYER_X; 
		}
	}
	
	/**
	 * Sets the current turn to the next player
	 * @return
	 */
	public PLAYER updateTurn()
	{
		if(currTurn == PLAYER.PLAYER_X)
		{
			currTurn = PLAYER.PLAYER_O; 
		}
		else if(currTurn == PLAYER.PLAYER_O)
		{
			currTurn = PLAYER.PLAYER_X; 
		}
		else {
			System.out.println("updateTurn is returning empty");
			currTurn = PLAYER.EMPTY; 
		}
		return currTurn; 
	}
	
	/**
	 * Returns an array of 2 ints specifying the row and col to move to from the user
	 * @return
	 */
	public int[] getUserInput()
	{
		Scanner in = new Scanner(System.in); 
		int[] result = new int[2]; 
		try {
			System.out.print("Please enter row, 0-2: ");
			result[0] = in.nextInt(); 
			System.out.print("\nPlease enter col, 0-2: ");
			result[1] = in.nextInt(); 
		}
		catch (InputMismatchException e) {System.out.println("Please enter an integer."); getUserInput();}; 
		return result; 
	}
	
	/**
	 * Returns true if function was able to copy a 2D array of ints into this.board_matrix
	 * @param board int[][] 
	 * @return true, if copied values from board into this board
	 */
	public int[][] copyBoard(int[][] board)
	{
		int[][] cpy = new int[ROWS][COLS]; 
		for(int i  = 0; i < ROWS; i++)
		{
			for(int j = 0; j < COLS; j++)
			{
				cpy[i][j] = board[i][j]; 
			}
		}
		return cpy; 
	}
	
	/**
	 * Returns a array list of int coordinates i,j of all valid moves for specified player on this board_matrix
	 * @param p PLAYER type
	 * @return HashMap<int,int> 
	 */
	public ArrayList<int[]> findValidMoves(int[][] board)
	{
		final int TILES = 9; 
		final int XY = 2; 
		ArrayList allMoves = new ArrayList(); 
		for(int i = 0; i < ROWS; i++)
		{
			for(int j = 0; j < COLS; j++)
			{
				if(checkValidMove(board, i, j))
				{
					int[] result = new int[XY]; 
					result[0] = i; 
					result[1] = j; 
					allMoves.add(result); 
				}
			}
		}
		return allMoves; 
	}
	
	/**
	 * Prints all valid moves available to outstream
	 * @param board
	 */
	public void printValidMoves(int[][] board)
	{
		ArrayList<int[]> result = findValidMoves(board); 
		System.out.println("All available moves: ");
		for(int i = 0; i < result.size(); i++)
		{
			int[] r = result.get(i); 
			System.out.println(r[0] + ", " + r[1]);
		}
	}
	
	/**
	 * Returns the player that has won, returns empty player if no winner or if draw
	 * @param board
	 * @return
	 */
	public PLAYER winner(int[][] board)
	{
		PLAYER result1 = _winner_horizontal(board); 
		PLAYER result2 = _winner_vertical(board); 
		PLAYER result3 = _winner_diagonal(board); 
		if(result1 != PLAYER.EMPTY)
		{
			//System.out.println(result1 + " WON!");
			return result1; 
		}
		else if(result2 != PLAYER.EMPTY)
		{
			//System.out.println(result2 + " WON!");
			return result2; 
		}
		else if(result3 != PLAYER.EMPTY)
		{
			//System.out.println(result3 + " WON!");
			return result3; 
		}
		else {
			return PLAYER.EMPTY; 
		}
	}
	
	/**
	 * Returns the player who has won diagonally, if none returns empty player
	 * @param board 2D array of ints
	 * @return PLAYER 
	 */
	private PLAYER _winner_diagonal(int[][] board)
	{
		PLAYER top_left = checkTile(board, 0,0); 
		PLAYER top_right = checkTile(board, 0,2); 
		if(top_left != PLAYER.EMPTY)
		{
			if(checkTile(board, 1,1) == top_left)
			{
				if(checkTile(board, 2,2) == top_left)
				{
					return top_left; 
				}
			}
		}
		if(top_right != PLAYER.EMPTY)
		{
			if(checkTile(board, 1,1) == top_right)
			{
				if(checkTile(board, 2,0) == top_right)
				{
					return top_right; 
				}
			}
		}
		return PLAYER.EMPTY; 
	}
	
	/**
	 * Returns true if a winner is found to get 3 in a row on the board_matrix
	 * @param board, 2D array of ints
	 * @return PLAYER
	 */
	private PLAYER _winner_horizontal(int[][] board)
	{	
		for(int i = 0; i < ROWS; i++)
		{
			for(int j = 0; j < COLS; j++)
			{
				PLAYER curr = checkTile(board, i,j); 
				if(j == 0)
				{
					if(curr != PLAYER.EMPTY)
					{
						if(checkTile(board, i,j+1) == curr)
						{
							if(checkTile(board, i,j+2) == curr)
							{
								return curr; 
							}
						}
					}
				}	
			}
		}
		return PLAYER.EMPTY; 
		
	}
	
	/**
	 * Returns player who has won vertically, returns empty player if none
	 * @param board, 2D array of ints
	 * @return PLAYER
	 */
	private PLAYER _winner_vertical(int[][] board)
	{
		for(int i = 0; i < ROWS; i++)
		{
			for(int j = 0; j < COLS; j++)
			{
				PLAYER curr = checkTile(board, i,j); 
				if(curr != PLAYER.EMPTY)
				{
					if(checkTile(board, i+1, j) == curr)
					{
						if(checkTile(board, i+2, j) == curr)
						{
							return curr; 
						}
					}
				}
			}
			break; 
		}
		return PLAYER.EMPTY; 
	}
	
	/**
	 * Returns the player that is occupying a particular tile on the board_matrix, if no player is, then it returns empty 
	 * @param board
	 * @param x, int representing row
	 * @param y, int representing col
	 * @return PLAYER, who is occupying space
	 */
	public PLAYER checkTile(int[][] board, int x, int y)
	{
		int result = board[x][y]; 
		switch(result)
		{
		case 1:
			return PLAYER.PLAYER_X; 
		case 2:
			return PLAYER.PLAYER_O; 
		default:
			return PLAYER.EMPTY; 
		}
	}
	
	/**
	 * Places a move in the x,y coordinates of the board_matrix for this given player. Returns true if move is valid, false otherwise. 
	 * @param board, 2D array of ints
	 * @param curr, current player's turn
	 * @param x, int row
	 * @param y, int col
	 * @return boolean, if move made returns true
	 */
	public boolean move(int[][] board, PLAYER curr, int x, int y)
	{
		boolean isValid = checkValidMove(board, x, y); 
		if(isValid)
		{
			switch(curr)
			{
			case PLAYER_X:
				board[x][y] = 1; 
				break; 
			case PLAYER_O:
				board[x][y] = 2; 
				break; 
			default: 
				System.out.println("move(): player returned empty."); 
			}
		}
		return isValid; 
	}
	
	/**
	 * Returns true if player can make a move in this x,y coordinates on the board_matrix
	 * @param board
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean checkValidMove(int[][] board, int x, int y)
	{
		if(y < COLS && y >= 0)
		{
			if(x < ROWS && x >= 0)
			{
				if(checkTile(board, x,y) == PLAYER.EMPTY)
				{
					return true; 
				}
			}
		}
		return false; 
	}
	
	/**
	 * Prints the board to the outstream
	 * @param board, 2D array of ints
	 */
	public void print(int[][] board)
	{
		System.out.println("+---+---+---+"); 
		for(int i = 0; i < ROWS; i++)
		{
			System.out.print("| ");
			for(int j = 0; j < COLS; j++)
			{
				int value = board[i][j]; 
				if(value == 1)
				{
					System.out.print("x |");
				}
				else if(value == 2)
				{
					System.out.print("O |");
				}
				else {
					System.out.print("  | "); 
				}
			}
			System.out.println("\n+---+---+---+"); 
		}
	}
	
}