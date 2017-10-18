package mazeGenerator;

import maze.Cell;
import maze.Maze;
import maze.Wall;

import java.util.ArrayList;
import java.util.Collections;

public class RecursiveBacktrackerGenerator implements MazeGenerator {

	// List of visited cells.
	ArrayList<Cell> visitedCells;
	// A reference to the maze object being worked with.
	Maze maze;

	@Override
	public void generateMaze(Maze maze) {
		this.maze = maze;
		visitedCells = new ArrayList<Cell>();

		Cell cell = null;
		while (cell == null) {
			int randomRow = (int)(Math.random() * maze.sizeR-1);
			int randomCol = (int)(Math.random() * maze.sizeC-1);
			cell = maze.map[randomCol][randomRow];
		}

		traverseMaze(cell);

	} // end of generateMaze()

	// A recursive function that traverses the maze
	private void traverseMaze(Cell startingCell) {

		if (startingCell == null) {
			return;
		}

		// A list of integers representing indexs.
		// Used for selecting a neighbour.
		ArrayList<Integer> neighbourIndexes = new ArrayList<Integer>(6);
		for (int index = 0; index < startingCell.neigh.length; index++) {
			neighbourIndexes.add(index);
		}

		// Neighbour Indices are shuffled to generate a random neighbour.
		Collections.shuffle(neighbourIndexes);
		this.visitedCells.add(startingCell);

		// For each neighbouring cell of the current cell.
		for (Integer index: neighbourIndexes) {
			Cell neighbour = startingCell.neigh[index];
				if (neighbour != null) {

					// if the cell is not already visited
					if (!this.isCellVisited(neighbour)) {
						// remove the wall between the current cell and that neighbour.
						this.removeWallBetween(startingCell, neighbour);

						// If the cell has a tunnel.
						if (neighbour.tunnelTo != null)	 {
							visitedCells.add(neighbour);
							visitedCells.add(neighbour.tunnelTo);

							break;
						}
						// If the cell has no tunnel
						else {
							// run the same function on the neighbour cell before moving to the
							// next neighbour cell for the current cell.
							this.traverseMaze(neighbour);
						}
					}

				}
		}
	}

	// Checks if the given cell has already been visited.
	private boolean isCellVisited(Cell cell) {

		return visitedCells.contains(cell);
	}

	// Removes the wall between 2 cells
	private void removeWallBetween(Cell startingCell, Cell destinationCell) {
		int direction = getDirection(startingCell, destinationCell);
		startingCell.wall[direction].present = false;

	}

	// Returns an integer representing a direction between 2 cells.
	private int getDirection(Cell startingCell, Cell destinationCell) {
		// Direction is South
		if (destinationCell.r < startingCell.r && destinationCell.c == startingCell.c) {
			return Maze.SOUTH;
		}
		// Direction is North
		else if(destinationCell.r > startingCell.r && destinationCell.c == startingCell.c) {
			return Maze.NORTH;
		}
		// Direction is West
		else if(destinationCell.c < startingCell.c && destinationCell.r == startingCell.r) {
			startingCell.wall[Maze.WEST].present = false;
			return Maze.WEST;

		}
		// Direction is East
		else if (destinationCell.c > startingCell.c && destinationCell.r == startingCell.r){
			startingCell.wall[Maze.EAST].present = false;
			return Maze.EAST;
		}
		// Direction is North West
		else if(destinationCell.r > startingCell.r && destinationCell.c < startingCell.c) {
			startingCell.wall[Maze.NORTHWEST].present = false;
			return Maze.NORTHWEST;

		}
		// Direction is North East
		else if (destinationCell.r > startingCell.r && destinationCell.c > startingCell.c){
			startingCell.wall[Maze.NORTHEAST].present = false;
			return Maze.NORTHEAST;

		}
		// Direction is South East
		if (destinationCell.r < startingCell.r && destinationCell.c > startingCell.c) {
			startingCell.wall[Maze.SOUTHEAST].present = false;
			return Maze.SOUTHEAST;
		}
		// Direction is South West
		else if(destinationCell.r < startingCell.r && destinationCell.c < startingCell.c) {
			startingCell.wall[Maze.SOUTHWEST].present = false;
			return Maze.SOUTHWEST;
		}
		return 0;
	}



} // end of class RecursiveBacktrackerGenerator
