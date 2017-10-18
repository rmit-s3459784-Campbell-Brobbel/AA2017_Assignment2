package mazeSolver;
import java.util.*;
import maze.*;

/**
 * Implements the BiDirectional recursive backtracking maze solving algorithm.
 */
public class BiDirectionalRecursiveBacktrackerSolver implements MazeSolver {

	boolean mazeSolved = false;

    @Override
    public void solveMaze(Maze maze)  {

    	// Entrance search
    	Search entSearch = new Search(maze.entrance);

    	// Exit Search
    	Search exitSearch = new Search(maze.exit);

		while (!mazeSolved) {

			// If the current cell of either of the searches appears in the
			// other search, the maze is solved.
			if (entSearch.allVisitedCells.contains(exitSearch.currentCell)
					|| exitSearch.allVisitedCells.contains(entSearch.currentCell)) {
				this.mazeSolved = true;
			}
			else {
				// Draw the current cells
				maze.drawFtPrt(entSearch.currentCell);
				maze.drawFtPrt(exitSearch.currentCell);

				// Move each search to the next cell.
				entSearch.nextCell();
				exitSearch.nextCell();
			}
		}

    }

    @Override
	public boolean isSolved() {
		// TODO Auto-generated method stub
		return mazeSolved;
	} // end if isSolved()


	@Override
	public int cellsExplored() {
		// TODO Auto-generated method stub
		return 0;
	} // end of cellsExplored()



} // end of class BiDirectionalRecursiveBackTrackerSolver

// A Search object will be used to track multiple DFS Searchs from a given starting cell.
class Search {

	ArrayList<Cell> allVisitedCells = new ArrayList<Cell>();
	Stack<Cell> visitedCellStack;
	Cell currentCell;

	public Search(Cell startingCell) {
		this.currentCell = startingCell;
		this.visitedCellStack = new Stack<Cell>();

	}

	// moves the search to a random unvisited neighbour of the current cell. If there are no unvisited
	// neighbour cells, it pops back to the previous cell in the stack.
	void nextCell() {

		ArrayList<Cell> unvisitedNeighbours = new ArrayList<Cell>(100);

		for (Cell neighbourCell : this.currentCell.neigh) {
			if (neighbourCell != null && !allVisitedCells.contains(neighbourCell) && !wallExistsBetween(this.currentCell, neighbourCell)) {
				unvisitedNeighbours.add(neighbourCell);
			}
		}

		allVisitedCells.add(this.currentCell);

		if (unvisitedNeighbours.size() > 0) {
			Collections.shuffle(unvisitedNeighbours);
			Cell neighbourCell = unvisitedNeighbours.get(0);
			this.visitedCellStack.push(currentCell);
			currentCell = neighbourCell;
			return;
		}

		if (this.visitedCellStack.size() > 0) {
			currentCell = this.visitedCellStack.pop();

		}

	}

	// Returns true if there is an active wall between c1 and c2.
	boolean wallExistsBetween(Cell c1, Cell c2) {

		int direction = getDirection(c1,c2);

		return c1.wall[direction].present;
	}

	// Returns an integer representing a direction from startingCell to destinationCell.
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
			//startingCell.wall[Maze.WEST].present = false;
			return Maze.WEST;

		}
		// Direction is East
		else if (destinationCell.c > startingCell.c && destinationCell.r == startingCell.r){
			//startingCell.wall[Maze.EAST].present = false;
			return Maze.EAST;
		}
		// Direction is North West
		else if(destinationCell.r > startingCell.r && destinationCell.c < startingCell.c) {
			//startingCell.wall[Maze.NORTHWEST].present = false;
			return Maze.NORTHWEST;

		}
		// Direction is North East
		else if (destinationCell.r > startingCell.r && destinationCell.c > startingCell.c){
			//startingCell.wall[Maze.NORTHEAST].present = false;
			return Maze.NORTHEAST;

		}
		// Direction is South East
		if (destinationCell.r < startingCell.r && destinationCell.c > startingCell.c) {
			//startingCell.wall[Maze.SOUTHEAST].present = false;
			return Maze.SOUTHEAST;
		}
		// Direction is South West
		else if(destinationCell.r < startingCell.r && destinationCell.c < startingCell.c) {
			//startingCell.wall[Maze.SOUTHWEST].present = false;
			return Maze.SOUTHWEST;
		}
		return 0;
	}

}