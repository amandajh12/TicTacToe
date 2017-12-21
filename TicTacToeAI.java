package assignment6; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set; 
import java.util.Iterator;
import java.util.Map;

class TicTacToeAI extends TicTacToe
{
	//This class only needs final ROWS, COLS, and PLAYER currTurn; 
	public TicTacToeAI()
	{
		super();
		//System.out.println("Constructing ai...");
		 
	}

	/**
	 * Returns the other player
	 * @param p
	 * @return PLAYER
	 */
	public PLAYER switchTurn(PLAYER p)
	{
		PLAYER next = PLAYER.EMPTY; 
		if(p == PLAYER.PLAYER_X)
		{
			next = PLAYER.PLAYER_O; 
		}
		else if(p == PLAYER.PLAYER_O)
		{
			next = PLAYER.PLAYER_X; 
		}
		return next; 
	}
	
	/**
	 * Returns an int[] with either the best offensive or defensive move
	 * @param board
	 * @param curr
	 * @return int[], array size 2 with coordinates for best move to make
	 */
	public int[] bestMove(int[][] board, PLAYER curr)
	{
		int[] defensiveMove = defensive(board, curr); 
		if(defensiveMove[0] != -1 && defensiveMove[1] != -1)
		{
			return defensiveMove; 
		}
		else {
			int[] offensiveMove = offensive(board, curr); 
			return offensiveMove; 
		}
	}
	
	/**
	 * Returns an int[] with a move that blocks the other player, if necessary
	 * @param board
	 * @param curr
	 * @return
	 */
	private int[] defensive(int[][] board, PLAYER curr)
	{
		PLAYER opponent = switchTurn(curr); 
		ArrayList<int[]> allMoves = findValidMoves(board); 
		int[] moveMade = {-1, -1}; 
		for(int i =0; i < allMoves.size(); i++)
		{
			int[] nextMove = allMoves.get(i); 
			int[][] cpy = copyBoard(board); 
			move(cpy, opponent, nextMove[0], nextMove[1]); 
			if(winner(cpy) != PLAYER.EMPTY)
			{
				moveMade[0] = nextMove[0]; 
				moveMade[1] = nextMove[1]; 
				return moveMade; 
			}
		}
		return moveMade; 
	}
	
	/**
	 * Returns best offensive move by putting greatest weight on center piece, medium weight on corners, and weaker weight on mid pieces
	 * @param board, int[][] 
	 * @param curr, PLAYER object 
	 * @return
	 */
	private int[] offensive(int[][] board, PLAYER curr)
	{
		ArrayList<int[]> allMoves = findValidMoves(board);
		PLAYER opponent = switchTurn(curr); 
		int[] move = {-1, -1}; 
		int MAX = 0; 
		for(int i = 0; i < allMoves.size(); i++)
		{
			int[] result = allMoves.get(i); 
			int[] temp = new int[2]; 
			int weight = 0; 
			if(result[0] == 0 && result[1] == 0)
			{
				temp[0] = 0; 
				temp[1] = 0; 
				if(checkTile(board, 0, 1) != opponent)
				{
					if(checkTile(board, 0, 2) != opponent)
					{
						weight++; 
					}
				}
				if(checkTile(board, 1, 0) != opponent)
				{
					if(checkTile(board, 2, 0) != opponent)
					{
						weight++; 
					}
				}
				if(checkTile(board, 1, 1) != opponent)
				{
					if(checkTile(board, 2, 2) != opponent)
					{
						weight++; 
					}
				}
			}
			else if(result[0] == 0 && result[1] == 1)
			{
				temp[0] = 0; 
				temp[1] = 1; 
				if(checkTile(board, 0, 0) != opponent)
				{
					if(checkTile(board, 0, 2) != opponent)
					{
						weight++; 
					}
				}
				if(checkTile(board, 1, 1) != opponent)
				{
					if(checkTile(board, 2, 1) != opponent)
					{
						weight++; 
					}
				}
			}
			else if(result[0] == 0 && result[1] == 2)
			{
				temp[0] = 0; 
				temp[1] = 2; 
				if(checkTile(board, 0, 1) != opponent)
				{
					if(checkTile(board, 0, 0) != opponent)
					{
						weight++; 
					}
				}
				if(checkTile(board, 1, 1) != opponent)
				{
					if(checkTile(board, 2, 0) != opponent)
					{
						weight++; 
					}
				}
				if(checkTile(board, 1, 2) != opponent)
				{
					if(checkTile(board, 2, 2) != opponent)
					{
						weight++; 
					}
				}
			}
			else if(result[0] == 1 && result[1] == 0)
			{
				temp[0] = 1; 
				temp[1] = 0; 
				if(checkTile(board, 0, 0) != opponent)
				{
					if(checkTile(board, 2, 0) != opponent)
					{
						weight++; 
					}
				}
				if(checkTile(board, 1, 1) != opponent)
				{
					if(checkTile(board, 1, 2) != opponent)
					{
						weight++; 
					}
				}
				
			}
			else if(result[0] == 1 && result[1] == 1)
			{
				temp[0] = 1; 
				temp[1] = 1; 
				if(checkTile(board, 0, 1) != opponent)
				{
					if(checkTile(board, 2, 1) != opponent)
					{
						weight++; 
					}
				}
				if(checkTile(board, 1, 0) != opponent)
				{
					if(checkTile(board, 1, 2) != opponent)
					{
						weight++; 
					}
				}
				if(checkTile(board, 0, 2) != opponent)
				{
					if(checkTile(board, 2, 0) != opponent)
					{
						weight++; 
					}
				}
				if(checkTile(board, 0, 0) != opponent)
				{
					if(checkTile(board, 2, 2) != opponent)
					{
						weight++; 
					}
				}
			}
			else if(result[0] == 1 && result[1] == 2)
			{
				temp[0] = 1; 
				temp[1] = 2; 
				if(checkTile(board, 0, 2) != opponent)
				{
					if(checkTile(board, 2, 2) != opponent)
					{
						weight++; 
					}
				}
				if(checkTile(board, 1, 0) != opponent)
				{
					if(checkTile(board, 1, 1) != opponent)
					{
						weight++; 
					}
				}
			}
			else if(result[0] == 2 && result[1] == 0)
			{
				temp[0] = 2;  
				temp[1] = 2; 
				if(checkTile(board, 0, 0) != opponent)
				{
					if(checkTile(board, 1, 0) != opponent)
					{
						weight++; 
					}
				}
				if(checkTile(board, 1, 1) != opponent)
				{
					if(checkTile(board, 0, 2) != opponent)
					{
						weight++; 
					}
				}
				if(checkTile(board, 2, 1) != opponent)
				{
					if(checkTile(board, 2, 2) != opponent)
					{
						weight++; 
					}
				}
			}
			else if(result[0] == 2 && result[1] == 1)
			{
				temp[0] = 2; 
				temp[1] = 1; 
				
				if(checkTile(board, 2, 0) != opponent)
				{
					if(checkTile(board, 2, 2) != opponent)
					{
						weight++; 
					}
				}
				if(checkTile(board, 1, 1) != opponent)
				{
					if(checkTile(board, 0, 1) != opponent)
					{
						weight++; 
					}
				}
				
			}
			else if(result[0] == 2 && result[1] == 2)
			{
				temp[0] = 2; 
				temp[1] = 2; 
				if(checkTile(board, 0, 0) != opponent)
				{
					if(checkTile(board, 1, 1) != opponent)
					{
						weight++; 
					}
				}
				if(checkTile(board, 2, 1) != opponent)
				{
					if(checkTile(board, 2, 0) != opponent)
					{
						weight++; 
					}
				}
				if(checkTile(board, 1, 2) != opponent)
				{
					if(checkTile(board, 0, 2) != opponent)
					{
						weight++; 
					}
				}
			}
			if(weight > MAX)
			{
				MAX = weight; 
				move = temp; 
			}
		}
		return move; 
	}
	
}
