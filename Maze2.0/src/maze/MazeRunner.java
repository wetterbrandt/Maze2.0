package maze;

public abstract class MazeRunner {
	protected int count;
	protected boolean complete;
	
	public MazeRunner(){
		count = 0;
		complete = false;
	}

  public abstract void solve(Maze maze);

  public abstract int nrOfVisitedNodes();

}