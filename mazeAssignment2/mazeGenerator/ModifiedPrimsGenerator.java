package mazeGenerator;
import maze.*;
import maze.Maze;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class ModifiedPrimsGenerator implements MazeGenerator {

	// List of visited cells
	ArrayList<Cell> visitedCells = new ArrayList<Cell>();
	// List of neighbours of the visited cells that still need to be visited.
	ArrayList<Cell> cellsToVisit = new ArrayList<Cell>();

	@Override
	public void generateMaze(Maze maze) {
		// TODO Auto-generated method stub

		Cell startingCell = null;

		// Generate a random cell to start with.
		while (startingCell == null) {
			int randomRow = (int)(Math.random() * maze.map.length);
			int randomCol = (int)(Math.random() * maze.map.length);
			startingCell =  maze.map[randomRow][randomCol];
		}

		// Traverse the maze grid to generate a perfect maze.
		this.randomMazeTraversal(startingCell);

	} // end of generateMaze()

	// Traverses the maze according to a modified version of Prim's Algorithm.
	private void randomMazeTraversal(Cell startingCell) {

		// Add the starting cell to the list of visited cells.
		this.visitedCells.add(startingCell);

		// Add each neighbouring cell of the starting cell to a list of cells
		// that need to be visited if they are not already in there.
		for (Cell neighbourCell : startingCell.neigh) {
			if (neighbourCell != null && !cellIsVisited(neighbourCell) && !cellsToVisit.contains(neighbourCell)) {
				cellsToVisit.add(neighbourCell);
			}
		}

		// Shuffle the cells to visit so that a random one is chosen.
		Collections.shuffle(cellsToVisit);

		// Run this loop until there are no more cells to visit.
		while (!cellsToVisit.isEmpty()) {

			// Get the first cell in the shuffled list of cells to visit.
			Cell nextCell = cellsToVisit.get(0);
			// Remove it from that list.
			cellsToVisit.remove(nextCell);

			// A list of cells that are already visited and are adjacent to the
			// cell we are currently in.
			ArrayList<Cell> adjacentCells = adjacentVisitedCellsTo(nextCell);
			Cell aCell = adjacentCells.get(0);

			// Remove the wall between those 2 cells.
			removeWallBetween(nextCell,aCell);

			// Update the starting cell to be the cell we just visited.
			startingCell = nextCell;
			// Add the starting cell to the list of visited cells.
			this.visitedCells.add(startingCell);

			// Add each neighbouring cell of the starting cell to a list of cells
			// that need to be visited if they are not already in there.
			for (Cell neighbourCell : startingCell.neigh) {
				if (neighbourCell != null && !cellIsVisited(neighbourCell) && !cellsToVisit.contains(neighbourCell)) {
					cellsToVisit.add(neighbourCell);
				}
			}

		}
	}

	// Removes the wall between c1 and c2/
	private void removeWallBetween(Cell c1, Cell c2) {
		int direction = getDirection(c1,c2);
		c1.wall[direction].present = false;

	}

	// Returns a shuffled list of cells that are adjacent to the input cell.
	private ArrayList<Cell> adjacentVisitedCellsTo(Cell cell) {
		ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
		for (Cell nextCell : cell.neigh) {
			if (nextCell != null && this.visitedCells.contains(nextCell)) {
				adjacentCells.add(nextCell);
			}
		}
		Collections.shuffle(adjacentCells);
		return adjacentCells;
	}

	// Returns true if Cell c1 is adjacent to Cell c2.
	private boolean cellsAreAdjacent(Cell c1, Cell c2) {
		for (Cell neighbour : c1.neigh) {
			if (neighbour != null && neighbour.equals(c2) && cellIsVisited(c2)) {
				return true;
			}
		}
		return false;
	}

	// Returns an integer representing the direction going from the startingCell
	// to the destinationCell.
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

	// Checks if the input cell is in the list of visited cells.
	private boolean cellIsVisited(Cell cell) {
		for (Cell vCell : this.visitedCells) {
			if (cell.equals(vCell)) {
				return true;
			}
		}
		return false;
	}
} // end of class ModifiedPrimsGenerator

