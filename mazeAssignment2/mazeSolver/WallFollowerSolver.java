package mazeSolver;

import maze.*;

/**
 * Implements WallFollowerSolver
 */


public class WallFollowerSolver implements MazeSolver {

	boolean isSolved = false;

	@Override
	public void solveMaze(Maze maze) {
		// TODO Auto-generated method stub

		Cell currentCell = maze.entrance;
		int direction = Maze.SOUTH;

		while (!isSolved){


		}


        
	} // end of solveMaze()
    
    
	@Override
	public boolean isSolved() {
		// TODO Auto-generated method stub
		return this.isSolved;
	} // end if isSolved()
    
    
	@Override
	public int cellsExplored() {
		// TODO Auto-generated method stub
		return 0;
	} // end of cellsExplored()

} // end of class WallFollowerSolver
