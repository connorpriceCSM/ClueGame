package experiment;
//Authors: Amelia Atiles and Connor Price
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {

	private Map<BoardCell, Set<BoardCell>> adjMtx;
	 private HashSet<BoardCell> visited;
	 private HashSet<BoardCell> targets;
	 private BoardCell[][] grid;
	 private int rows,cols;
	 public IntBoard(int rows, int cols)
	 {
		 this.rows = rows;
		 this.cols = cols;
		 // initialize grid and lists
		 // visited isn't needed
		 // set each spot on the grid with a BoardCell
		 // Easy double for loop
		 grid = new BoardCell[rows][cols];
		 for( int i = 0; i < rows; i++)
		 {
			 for(int j = 0; j < cols; j++)
			 {
				 grid[i][j] = new BoardCell(i,j);
			 }
		 }
		 adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		 targets = new HashSet<BoardCell>();
		 
		 
	 }
	 // get the adjacent cell list for each board cell and put it in the instantiated map!
	public void calcAdjacencies() 
	{
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++ )
			{
				BoardCell tempCell = new BoardCell(rows,cols);
				Set<BoardCell> adjList = getAdjList(tempCell);
				adjMtx.put(tempCell, adjList);
			}
		}
		
	}
	public void calcTargets(BoardCell startCell, int steps)
	{
		visited =  new HashSet<BoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, steps);
		
	}
	public void findAllTargets(BoardCell startCell, int steps)
	{
		targets = new HashSet<BoardCell>();
		Set<BoardCell> adjCells = getAdjList(startCell);
		HashSet<BoardCell> tempVisit = new HashSet<BoardCell>();
		for (BoardCell adjCell: adjCells) {
			if (tempVisit.contains(adjCell)) {
				
			} else {
				tempVisit.add(adjCell);
				if (steps ==1) {
					targets.add(adjCell);
				} else {
					findAllTargets(adjCell, steps-1);
				}
				tempVisit.remove(adjCell);
			}

		}
	}
	public HashSet<BoardCell> getTargets()
	{
		return targets;
	}
	// First check to see if the parameter cell is at the edge or corner of the grid
	// add adjacent cells as needed in a list and return it
	public Set<BoardCell> getAdjList(BoardCell cell)
	{
		Set<BoardCell> adjList = new HashSet<BoardCell>();
		//Up Adjacent
		// make sure the program doesn't try to look at a negative row
		if(cell.getRow() > 0)
		{
			adjList.add(getCell(cell.getRow() -1, cell.getColumn()));
			
		}
		// Down Adjacent
		// make sure the program doesn't try to look at a nonexistent row
		if(cell.getRow() < 3)
		{
			adjList.add(this.getCell(cell.getRow() + 1, cell.getColumn()));
		}
		
		//Right Adjacent
		// make sure the program doesn't try to look at a nonexistent column
		if(cell.getColumn() < 3)
		{
			adjList.add(getCell(cell.getRow(), cell.getColumn() +1));
		}
		//Left Adjacent
		// make sure the program doesn't try to look at a  negative column
		if(cell.getColumn() > 0)
		{
			adjList.add(getCell(cell.getRow(), cell.getColumn() -1));
		}
		
		return adjList;
	}
	
	public BoardCell getCell(int x, int y)
	{
		return grid[x][y];
	}
}
