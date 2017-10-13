package mazeGenerator;

import java.lang.reflect.Array;
import java.util.*;

import maze.Maze;
import maze.Cell;

public class GrowingTreeGenerator implements MazeGenerator {
	// Growing tree maze generator. As it is very general, here we implement as "usually pick the most recent cell, but occasionally pick a random cell"
	
	double threshold = 0.1;
	ArrayList<Cell> visitedCells = new ArrayList<Cell>();
	boolean isVisited[][];
	@Override
	public void generateMaze(Maze maze) {

		int startingRow = (int)(Math.random() * maze.map.length);
		int startingCol = (int)(Math.random() * maze.map.length);

		System.out.printf("Maze R: %d, C: %d\n", maze.map.length, maze.map.length);
		isVisited = new boolean[maze.map.length][maze.map.length];

		Cell startingCell = maze.map[startingRow][startingCol];

		for (int x = 0; x < maze.sizeR; x++) {
			System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_");
			for (int y = 0; y < maze.sizeC; y++) {
				if(maze.map[x][y] != null) {
					System.out.printf("Maze Cell R: %d, C:%d\n", maze.map[x][y].r, maze.map[x][y].c);

				}
				else {
					System.out.println("Cell is null");
				}
			}
		}
		System.out.println("----------------------------------");
		visitedCells.add(startingCell);
		isVisited[startingRow][startingCol] = true;
		traverseMaze();

	}

	public void traverseMaze() {

		while(this.visitedCells.size() > 0) {

			int randomIndex = (int) (Math.random() * visitedCells.size());
			Cell cell = this.visitedCells.get(randomIndex);

			Cell unvisitedNeighCell = null;
			for (Cell neighbour : cell.neigh) {
				if (neighbour != null && !isVisited[neighbour.r][neighbour.c]) {
					unvisitedNeighCell = neighbour;
					break;
				}
			}
			if (unvisitedNeighCell != null) {
				removeWallBetween(cell, unvisitedNeighCell);
				isVisited[unvisitedNeighCell.r][unvisitedNeighCell.c] = true;
				visitedCells.add(unvisitedNeighCell);
			}
			else {
				this.visitedCells.remove(cell);
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
