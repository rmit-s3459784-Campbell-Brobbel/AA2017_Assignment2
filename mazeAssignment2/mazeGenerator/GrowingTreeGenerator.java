package mazeGenerator;

import java.lang.reflect.Array;
import java.util.*;

import maze.Maze;
import maze.Cell;

public class GrowingTreeGenerator implements MazeGenerator {
	// Growing tree maze generator. As it is very general, here we implement as "usually pick the most recent cell, but occasionally pick a random cell"
	
	double threshold = 0.1;
	ArrayList<Cell> visitedCells = new ArrayList<Cell>();
	ArrayList<Cell> cellsWithUnvisitedNeigh = new ArrayList<Cell>();

	boolean isVisited[][];
	@Override
	public void generateMaze(Maze maze) {

		isVisited = new boolean[maze.map.length][maze.map.length];

		Cell startingCell = null;

		while (startingCell == null) {
			int startingRow = (int)(Math.random() * maze.map.length);
			int startingCol = (int)(Math.random() * maze.map.length);

			startingCell = maze.map[startingRow][startingCol];
		}


		visitedCells.add(startingCell);
		cellsWithUnvisitedNeigh.add(startingCell);
		//isVisited[startingRow][startingCol] = true;
		traverseMaze();

	}

	public void traverseMaze() {

		while(this.cellsWithUnvisitedNeigh.size() > 0) {

			int randomIndex = (int) (Math.random() * this.cellsWithUnvisitedNeigh.size());
			Cell cell = this.cellsWithUnvisitedNeigh.get(randomIndex);

			Cell unvisitedNeighCell = null;
			for (Cell neighbour : cell.neigh) {
				if (neighbour != null && !cellIsVisited(neighbour)) {
					System.out.printf("Neighbour Cell R: %d, C: %d\n", neighbour.r, neighbour.c);
					unvisitedNeighCell = neighbour;
					break;

				}
				else {
					System.out.println("Neighbour is Null");
				}
				System.out.println("_______________________________-");
			}
			if (unvisitedNeighCell != null) {
				removeWallBetween(cell, unvisitedNeighCell);
				visitedCells.add(unvisitedNeighCell);
				cellsWithUnvisitedNeigh.add(unvisitedNeighCell);
			}
			else {
				this.cellsWithUnvisitedNeigh.remove(cell);
			}


		}
	}

	public boolean cellIsVisited(Cell cell) {

		for(Cell c : this.visitedCells) {
			if (c.equals(cell)) {
				return true;
			}
		}
		return false;
	}

	private void removeWallBetween(Cell c1, Cell c2) {
		int direction = getDirection(c1,c2);
		c1.wall[direction].present = false;

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

}
