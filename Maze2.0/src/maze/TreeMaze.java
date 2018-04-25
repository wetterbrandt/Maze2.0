package maze;

public class TreeMaze {
	private TreeCell startCell;
	private TreeCell endCell;

	public TreeMaze(Maze maze){
		startCell = new TreeCell(maze.getStartCell());
		endCell = new TreeCell(maze.getEndCell());
	}

	private void generateNeighbours(){

	}


	// TODO
	public TreeCell getStartCell(){
		return null;
	}

	// TODO
	public TreeCell getEndCell(){
		return null;
	}



}