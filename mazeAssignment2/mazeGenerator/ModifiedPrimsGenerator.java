package mazeGenerator;
import maze.*;
import maze.Maze;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class ModifiedPrimsGenerator implements MazeGenerator {

	ArrayList<Cell> visitedCells = new ArrayList<Cell>();
	ArrayList<Cell> cellsToVisit = new ArrayList<Cell>();

	@Override
	public void generateMaze(Maze maze) {
		// TODO Auto-generated method stub

		int randomRow = (int)(Math.random() * maze.map.length);
		int randomCol = (int)(Math.random() * maze.map.length);
		System.out.println("");
		Cell startingCell = maze.map[randomRow][randomCol];
		this.randomMazeTraversal(startingCell);

	} // end of generateMaze()

	private void randomMazeTraversal(Cell startingCell) {
		this.visitedCells.add(startingCell);
		System.out.printf("Starting Cell R: %d, C: %d\n", startingCell.r, startingCell.c);

		for (Cell neighbourCell : startingCell.neigh) {
			if (neighbourCell != null && !cellIsVisited(neighbourCell) && !cellsToVisit.contains(neighbourCell)) {
				cellsToVisit.add(neighbourCell);
			}
		}

		Collections.shuffle(cellsToVisit);

		while (!cellsToVisit.isEmpty()) {
			Cell nextCell = cellsToVisit.get(0);
			cellsToVisit.remove(nextCell);
			//System.out.printf("Next Cell R: %d, C: %d\n", nextCell.r, nextCell.c);
			ArrayList<Cell> adjacentCells = adjacentVisitedCellsTo(nextCell);
			Cell aCell = adjacentCells.get(0);

			//System.out.printf("AAA Cell R: %d, C: %d\n", aCell.r, aCell.c);
			System.out.println();
			removeWallBetween(nextCell,aCell);
			//randomMazeTraversal(nextCell);
			startingCell = nextCell;
			this.visitedCells.add(startingCell);
			System.out.printf("Starting Cell R: %d, C: %d\n", startingCell.r, startingCell.c);

			for (Cell neighbourCell : startingCell.neigh) {
				if (neighbourCell != null && !cellIsVisited(neighbourCell) && !cellsToVisit.contains(neighbourCell)) {
					cellsToVisit.add(neighbourCell);
				}
			}

		}
	}

	private void removeWallBetween(Cell c1, Cell c2) {
		int direction = getDirection(c1,c2);
		c1.wall[direction].present = false;

	}

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

	private boolean cellsAreAdjacent(Cell c1, Cell c2) {
		for (Cell neighbour : c1.neigh) {
			if (neighbour != null && neighbour.equals(c2) && cellIsVisited(c2)) {
				return true;
			}
		}
		return false;
	}

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

	private boolean cellIsVisited(Cell cell) {
		for (Cell vCell : this.visitedCells) {
			if (cell.equals(vCell)) {
				return true;
			}
		}
		return false;
	}
} // end of class ModifiedPrimsGenerator

