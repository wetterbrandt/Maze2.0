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
			Cell current = queue.poll();
			
			if (current.equals(endCell)) {				
				complete = true;			
			} else {
				Cell[] neighbours = current.getOneStepNeighboursArray();
				for (int i = 0; i < neighbours.length - 1; i++) {
					if (neighbours[i].isWall() == false) { // Ger NullPointerException i nuläget. Arrayet måste fyllas med currents grannar. Hur kommer man åt dem?
						queue.offer(neighbours[i]);
		
					}
				}
			}
			current.setVisited(true);
			closedSet.add(current);
		}
	}

	@Override
	public int nrOfVisitedNodes() {
		return closedSet.size();
	}

}