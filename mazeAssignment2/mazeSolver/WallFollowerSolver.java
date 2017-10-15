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

		this.mazeType = mazeType(maze);

		Cell currentCell = maze.entrance;
		int currentDirection = Maze.SOUTH;

		while (!isSolved){
			maze.drawFtPrt(currentCell);
			// If the current cell is the exit
			if(currentCell.equals(maze.exit)) {
				isSolved = true;
			}
			else {
				// If there is a wall to the left of the cell.
				int counterClockwiseDirection = getCounterClockwiseDirection(currentDirection);
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


	private int getCounterClockwiseDirection(int currentDirection) {

		if (mazeType == 0 ) {
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
		else if (mazeType == 1) {
			switch (currentDirection) {
				case Maze.NORTHWEST:
					return Maze.WEST;
				case Maze.WEST:
					return Maze.SOUTHWEST;
				case Maze.SOUTHWEST:
					return Maze.SOUTHEAST;
				case Maze.SOUTHEAST:
					return Maze.EAST;
				case Maze.EAST:
					return Maze.NORTHEAST;
				case Maze.NORTHEAST:
					return Maze.NORTHWEST;
			}
		}


		return 0;
	}


	private int getClockwiseDirection(int currentDirection) {
		if (mazeType == 0) {
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
		else if (mazeType == 1) {
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

	private int mazeType(Maze maze) {

		Cell entrance = maze.entrance;
		// THe maze is hex if any of these are true.
		if (entrance.neigh[Maze.NORTHWEST] != null || entrance.neigh[Maze.NORTHEAST] != null
				|| entrance.neigh[Maze.SOUTHEAST] != null || entrance.neigh[Maze.SOUTHWEST] != null) {
			System.out.printf("Maze Type Is 1");
			return 1;
		}
		else {

			for (Cell[] cellArray : maze.map) {
				for (Cell cell : cellArray) {
					if (cell.tunnelTo != null) {
						System.out.printf("Maze Type Is 2");

						// Maze Is Tunnel Maze.
						return 2;
					}
				}
			}
		}

		// Maze Is Normal Maze
		System.out.printf("Maze Type Is 0");

		return 0;
	}
} // end of class WallFollowerSolver
