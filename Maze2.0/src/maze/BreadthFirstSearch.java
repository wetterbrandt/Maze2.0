package maze;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;


public class BreadthFirstSearch extends MazeRunner {
	private Set<Cell> closedSet;
	private Set<Cell> openSet;
	private List<Cell> graph;

	public BreadthFirstSearch(){
		super();
		closedSet = new HashSet<Cell>();
		openSet = new HashSet<Cell>();
		graph = new LinkedList<Cell>();
	}

	@Override
	public void solve(Maze maze) {
		// TODO Auto-generated method stub

	}

	@Override
	public int nrOfVisitedNodes() {
		// TODO Auto-generated method stub
		return 0;
	}

}