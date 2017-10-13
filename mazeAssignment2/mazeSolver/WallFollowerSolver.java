package mazeSolver;

import maze.*;

/**
 * Implements WallFollowerSolver
 */

public class WallFollowerSolver implements MazeSolver {
	
	@Override
	public void solveMaze(Maze maze) {
		// TODO Auto-generated method stub

		Cell startingCell = maze.entrance;
		Cell nextCell = startingCell.neigh[Maze.EAST];


	} // end of solveMaze()
    
    
	@Override
	public boolean isSolved() {
		// TODO Auto-generated method stub
		return false;
	} // end if isSolved()
    
    
	@Override
	public int cellsExplored() {
		// TODO Auto-generated method stub
		return 0;
	} // end of cellsExplored()

} // end of class WallFollowerSolver
