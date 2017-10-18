package mazeGenerator;

import java.lang.reflect.Array;
import java.util.*;

import maze.Maze;
import maze.Cell;

public class GrowingTreeGenerator implements MazeGenerator {
	// Growing tree maze generator. As it is very general, here we implement as "usually pick the most recent cell, but occasionally pick a random cell"
	
	ArrayList<Cell> visitedCells = new ArrayList<Cell>();
	ArrayList<Cell> cellsWithUnvisitedNeigh = new ArrayList<Cell>();

	@Override
	public void generateMaze(Maze maze) {

		Cell startingCell = null;

		while (startingCell == null) {
			int startingRow = (int)(Math.random() * maze.map.length);
			int startingCol = (int)(Math.random() * maze.map.length);

			startingCell = maze.map[startingRow][startingCol];
		}

		visitedCells.add(startingCell);
		cellsWithUnvisitedNeigh.add(startingCell);
		traverseMaze();

	}

	/*
	 * Traverses the maze and removes walls between random cells and their adjacent visited cells.
	 */
	public void traverseMaze() {

		// Run this loop until the list of cells with unvisited neighbours is empty.
		while(this.cellsWithUnvisitedNeigh.size() > 0) {

			// Get a random cell who has neighbours left to visit.
			int randomIndex = (int) (Math.random() * this.cellsWithUnvisitedNeigh.size());
			Cell cell = this.cellsWithUnvisitedNeigh.get(randomIndex);

			// Get a random neighbour of the current cell who has not been visited.
			Cell unvisitedNeighCell = null;
			for (Cell neighbour : cell.neigh) {
				if (neighbour != null && !cellIsVisited(neighbour)) {
					unvisitedNeighCell = neighbour;
					break;

				}

			}
			if (unvisitedNeighCell != null) {
				// Remove the wall between the current cell and the neighbour cell
				removeWallBetween(cell, unvisitedNeighCell);
				visitedCells.add(unvisitedNeighCell);
				cellsWithUnvisitedNeigh.add(unvisitedNeighCell);
			}
			else {
				// If there is no random neighbour who hasnt been visited,
				// remove the current cell from cellsWithUnvisitedNeigh.
				this.cellsWithUnvisitedNeigh.remove(cell);
			}


		}
	}

	// Checks if the given cell has already been visited.
	public boolean cellIsVisited(Cell cell) {

		return visitedCells.contains(cell);
	}

	// Removes the wall between 2 cells
	private void removeWallBetween(Cell c1, Cell c2) {
		int direction = getDirection(c1,c2);
		c1.wall[direction].present = false;

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

}
