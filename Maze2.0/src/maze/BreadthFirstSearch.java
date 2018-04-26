package maze;

import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;


public class BreadthFirstSearch extends MazeRunner {
	private Set<Cell> closedSet; // Mängden besökta celler.
	private Queue<Cell> queue;
	Cell startCell;
	Cell endCell;

	public BreadthFirstSearch(){
		super();
		closedSet = new HashSet<Cell>();
		queue = new LinkedList<Cell>();
	}

	@Override
	public void solve(Maze maze) {
		this.startCell = maze.getStartCell();
		this.endCell = maze.getEndCell();

		queue.offer(startCell);
		closedSet.add(startCell);

		while(!queue.isEmpty()) {
			count++;
			Cell current = queue.poll();

			if (current.equals(endCell)) {
				complete = true;
			} else {
				for (Cell cell : current.getOneStepNeighbours()) {
					if (cell.isWall() == false) {
						queue.offer(cell);
					}
				}
			}
			current.setVisited(true);
			closedSet.add(current);
			System.out.println(current);
		}
		complete = true;
	}

	@Override
	public int nrOfVisitedNodes() {
		return closedSet.size();
	}

  @Override
  public String toString(){
	  StringBuilder sb = new StringBuilder();
	  sb.append("Solved: " + complete + ". Count: " + count);
	  return sb.toString();
  }

}
