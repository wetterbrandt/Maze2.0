package maze;

public class TurnLeft extends MazeRunner{

	public TurnLeft() {
		super();
	}

	@Override
  public void solve(Maze maze){
    int heading = 2;
    int turn = 1;

    Cell current = maze.getStartCell().getOneStepNeighboursArray()[2];

    if (current == null) {
      return;
    }

    while (!complete) {

      current.setVisited(true);

      if (current.equals(maze.getEndCell())) {
        complete = true;
        return;
      }

      count++;

      Cell[] neighbours = current.getOneStepNeighboursArray();

			// Framåt
      if (neighbours[Math.floorMod(heading - turn, 4)].isWall() == false) {
        heading = Math.floorMod(heading - turn, 4);
        current = neighbours[Math.floorMod(heading, 4)];
    	} else if (neighbours[Math.floorMod(heading, 4)].isWall() == false) {
        current = neighbours[Math.floorMod(heading, 4)];
      } else if (neighbours[Math.floorMod(heading + turn, 4)].isWall() == false) {
        heading = Math.floorMod(heading + turn, 4);
      } else if (neighbours[Math.floorMod(heading + 2, 4)].isWall() == false) {
        heading = Math.floorMod(heading + 2, 4);
        current = neighbours[heading];
      }
    }
  }

  @Override
  public int nrOfVisitedNodes() {
  	return count;
  }

  @Override
  public String toString(){
	  StringBuilder sb = new StringBuilder();
	  sb.append("Solved: " + complete + ". Count: " + count);
	  return sb.toString();
  }
}