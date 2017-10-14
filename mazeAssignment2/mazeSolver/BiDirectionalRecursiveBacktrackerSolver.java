package mazeSolver;
import java.util.*;
import maze.Maze;

/**
 * Implements the BiDirectional recursive backtracking maze solving algorithm.
 */
public class BiDirectionalRecursiveBacktrackerSolver implements MazeSolver {

    // A class to represent a (row,col) position in
// a Maze.
    class Position {
        // post: return Position to North of this
        private Position getNorth() {
            return null;
        }

        // post: return Position to South of this
        Position getSouth() {
            return null;
        }

        // post: return Position to East of this
        Position getEast() {
            return null;
        }

        // post: return Position to West of this
        Position getWest() {
            return null;
        }

        public boolean equals(Object o) {
            return false;
        }
    }

    // A class to represent a Maze
    class Maze {
        Position getStart() {
            return null;
        }

        Position getFinish() {
            return null;
        }

        // post: return true if p is not a wall
        boolean isWall() {
            return isWall();
        }

        // post: return true if p is not a wall
     //   boolean isWall(Position p);

        // post: p is marked as visited
        public void visit() {
            visit();
        }

        // post: p is marked as visited
        public void visit(Position p);

        // post: returns whether p has been visited
        boolean isVisited(Position p);
    }


    class Solver {

        // post: return a position adjacent to current that is reachable
        //       and that has not been visited before, or null if not possible.
        protected Position nextAdjacent(Maze m, Position current) {
            Position next = current.getNorth();
            if (!m.isWall(next) && !m.isVisited(next)) {
                return next;
            }

            next = current.getEast();
            if (!m.isWall(next) && !m.isVisited(next)) {
                return next;
            }

            next = current.getSouth();
            if (!m.isWall(next) && !m.isVisited(next)) {
                return next;
            }

            next = current.getWest();
            if (!m.isWall(next) && !m.isVisited(next)) {
                return next;
            }
            return null;
        }





        @Override
    public void solveMaze(Maze maze)  {
        Stack<Position> path = new StackList<Position>();

        Position current = maze.getStart();
        maze.visit(current);
        path.push(current);

        while (!path.empty() && !path.get().equals(maze.getEnd())) {
            Position next = nextAdjacent(maze, path.get());
            if (next != null) {
                maze.visit(next);
                path.push(next);
            } else {
                // No adjacent positions left to try.
                // Pop position and pick up path on previous.
                path.pop();
            }
        }
        if (!path.empty()) System.out.println(path);
    }




    @Override
	public boolean isSolved() {
		// TODO Auto-generated method stub
		return false;
	} // end if isSolved()


	@Override
	public int cellsExplored() {
		// TODO Auto-generated method stub
		return 0;
	} // end of cellsExplored()

} // end of class BiDirectionalRecursiveBackTrackerSolver
