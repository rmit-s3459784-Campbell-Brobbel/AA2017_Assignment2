package mazeSolver;

import maze.*;

/**
 * Implements WallFollowerSolver
 */


public class WallFollowerSolver implements MazeSolver {

	boolean isSolved = false;
	int mazeType;

	@Override
	public void solveMaze(Maze maze) {
		// TODO Auto-generated method stub

		this.mazeType = maze.type;
		Cell currentCell = maze.entrance;

		int currentDirection = Maze.SOUTH;

		while (!isSolved){
			maze.drawFtPrt(currentCell);

			// If a tunnel exists, draw the tunnels other side.
			if (currentCell.tunnelTo != null) {
				maze.drawFtPrt(currentCell.tunnelTo);

			}
			// If the current cell is the exit
			if(currentCell.equals(maze.exit)) {
				isSolved = true;
			}
			else {
				// If there is a wall to the left of the cell.
				int counterClockwiseDirection = getCounterClockwiseDirection(currentDirection);
				int doubleCounterClockwiseDir = getCounterClockwiseDirection(counterClockwiseDirection);
				if (currentCell.wall[counterClockwiseDirection].present) {

					// If there is a wall in front
					if(currentCell.wall[currentDirection].present) {

						// Rotate CW 90 Degrees
						currentDirection = getClockwiseDirection(currentDirection);
					}
					else {

						// Step Forward
						currentCell = currentCell.neigh[currentDirection];
					}
				}
				// If there is no wall to our left.
				else {

					// Rotate CCW.
					currentDirection = getCounterClockwiseDirection(currentDirection);
					// Step forward.
					currentCell = currentCell.neigh[currentDirection];

				}

			}
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

	// Gets the direction of the cell that is 1 rotation counter clockwise.
	private int getCounterClockwiseDirection(int currentDirection) {

		// Normal or tunnel maze.
		if (mazeType == Maze.NORMAL || mazeType == Maze.TUNNEL) {
			switch (currentDirection) {
				case Maze.NORTH:
					return Maze.WEST;
				case Maze.WEST:
					return Maze.SOUTH;
				case Maze.SOUTH:
					return Maze.EAST;
				case Maze.EAST:
					return Maze.NORTH;
			}
		}
		// Hex maze.
		else if (mazeType == Maze.HEX) {
			switch (currentDirection) {
				case Maze.NORTHWEST:
					return Maze.SOUTHWEST;
				case Maze.WEST:
					return Maze.SOUTHEAST;
				case Maze.SOUTHWEST:
					return Maze.EAST;
				case Maze.SOUTHEAST:
					return Maze.NORTHEAST;
				case Maze.EAST:
					return Maze.NORTHWEST;
				case Maze.NORTHEAST:
					return Maze.WEST;
			}
		}


		return 0;
	}

	// Gets the direction of the cell that is 1 rotation counter clockwise.
	private int getClockwiseDirection(int currentDirection) {
		// Normal Maze
		if (mazeType == Maze.NORMAL || mazeType == Maze.TUNNEL) {
			switch (currentDirection) {
				case Maze.NORTH:
					return Maze.EAST;
				case Maze.WEST:
					return Maze.NORTH;
				case Maze.SOUTH:
					return Maze.WEST;
				case Maze.EAST:
					return Maze.SOUTH;
			}
		}
		// Hex Maze
		else {
			switch (currentDirection) {
				case Maze.NORTHWEST:
					return Maze.NORTHEAST;
				case Maze.WEST:
					return Maze.NORTHWEST;
				case Maze.SOUTHWEST:
					return Maze.WEST;
				case Maze.SOUTHEAST:
					return Maze.SOUTHWEST;
				case Maze.EAST:
					return Maze.SOUTHEAST;
				case Maze.NORTHEAST:
					return Maze.EAST;
			}
		}

		return 0;
	}

} // end of class WallFollowerSolver
