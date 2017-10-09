package mazeGenerator;

import maze.Cell;
import maze.Maze;
import maze.Wall;

import java.util.ArrayList;
import java.util.Collections;

public class RecursiveBacktrackerGenerator implements MazeGenerator {

	ArrayList<Cell> visitedCells;
	Maze maze;
	@Override
	public void generateMaze(Maze maze) {
		this.maze = maze;
		visitedCells = new ArrayList<Cell>();
		int randomRow = (int)(Math.random() * maze.sizeR-1);
		int randomCol = (int)(Math.random() * maze.sizeC-1);
		System.out.printf("Random Row: %d, Random Col: %d\n", randomRow, randomCol);
		// TODO Auto-generated method stub
		Cell cell = maze.map[randomCol][randomRow];

		traverseMaze(cell);

	} // end of generateMaze()

	private void traverseMaze(Cell startingCell) {

		if (startingCell == null) {
			return;
		}

		ArrayList<Integer> neighbourIndexes = new ArrayList<Integer>();
		for (int index = 0; index < startingCell.neigh.length; index++) {
			neighbourIndexes.add(index);
		}
		Collections.shuffle(neighbourIndexes);
		//System.out.printf("Cell Row: %d, Cell Column: %d\n", startingCell.r, startingCell.c);
		this.visitedCells.add(startingCell);
		for (Integer index: neighbourIndexes) {
			Cell neighbour = startingCell.neigh[index];
				if (neighbour != null) {
					if (!this.isCellVisited(neighbour)) {
						this.removeWallBetween(startingCell, neighbour);
						if (neighbour.tunnelTo != null)	 {
							visitedCells.add(neighbour);
							visitedCells.add(neighbour.tunnelTo);
							break;
						}
						else {
							this.traverseMaze(neighbour);
						}
					}

				}
		}
	}

	private boolean isCellVisited(Cell cell) {
		for (Cell visitedCell: visitedCells) {
			if (cell.equals(visitedCell)) {
				return true;
			}
		}
		return false;
	}

	private void removeWallBetween(Cell startingCell, Cell destinationCell) {
		int direction = getDirection(startingCell, destinationCell);
		startingCell.wall[direction].present = false;

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



} // end of class RecursiveBacktrackerGenerator
