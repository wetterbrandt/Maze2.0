package maze;


import java.util.ArrayList;
import java.util.Random;

public class Cell {
  private int row;								// starts at 0
  private int col;								// starts at 0
  private boolean visited;
  private boolean isWall;					// true if this cell i a wall.
  private Cell[] twoStepNeighbours;			// North, East, South, West. Som en klocka!
  private Cell[] oneStepNeighbours;

  public Cell(int row, int col, boolean isWall, boolean isVisited){
    this.row = row;
    this.col = col;
    visited = isVisited;
    this.isWall = isWall;
    twoStepNeighbours = new Cell[4];
    oneStepNeighbours = new Cell[4];
  }

  public boolean isVisited() {
    return visited;
  }

  public void setVisited(boolean isVisited) {
	    visited = isVisited;
	}

  public boolean isWall(){
    return isWall;
  }

  public void setWall(boolean isWall){
    this.isWall = isWall;
  }

  public int getRow(){
    return row;
  }

  public int getCol(){
    return col;
  }

  public int getRowBetween(Cell cell){
    return (row + cell.getRow()) / 2;
  }

  public int getColBetween(Cell cell){
    return (col + cell.getCol()) / 2;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Cell) {
      Cell cell = (Cell) o;
      return row == cell.row && col == cell.col;
    }
    return false;
  }

  public Cell[] getOneStepNeighboursArray() {
    return oneStepNeighbours;
  }

  public void setOneStepNeighbour(int x, Cell c) {
    if (x >= 0 && x < 4) {
      oneStepNeighbours[x] = c;
    } else {
      throw new IllegalArgumentException();
    }
  }

  public ArrayList<Cell> getTwoStepNeighbours() {
    ArrayList<Cell> neighbourList = new ArrayList<Cell>();
    for (int i = 0; i < twoStepNeighbours.length; i++) {
      Cell current = twoStepNeighbours[i];
      if (current != null){
        neighbourList.add(current);
      }
    }
    return neighbourList;
  }

  public void setTwoStepNeighbour(int x, Cell c) {
    if (x >= 0 && x < 4) {
      twoStepNeighbours[x] = c;
    } else {
      throw new IllegalArgumentException();
    }
  }

  public ArrayList<Cell> getRandomNeighbours() {
    ArrayList<Cell> randomList = new ArrayList<Cell>();
    ArrayList<Cell> neighbourList = getTwoStepNeighbours();
    Random rnd = new Random();

    while (neighbourList.size() > 0) {
      randomList.add(neighbourList.remove(rnd.nextInt(neighbourList.size())));
    }
    return randomList;
  }

  // returns null if no such neighbour exists.
  public Cell getRandomUnvisitedNeighbour(){
    ArrayList<Cell> unvisitedNeighbours = new ArrayList<Cell>();
    Random rnd = new Random();

    for (Cell cell : twoStepNeighbours) {
      if (!cell.isVisited()) {
        unvisitedNeighbours.add(cell);
      }
    }

    if (unvisitedNeighbours.size() == 0) {
      return null;
    }

    return unvisitedNeighbours.get(rnd.nextInt(unvisitedNeighbours.size()));
  }

  public String toString() {
	  StringBuilder sb = new StringBuilder();

	  sb.append(row + ", " + col + '\n');

    sb.append("State: ");
    if (isWall) {
      sb.append("# ");
    } else {
      sb.append("  ");
    }

	  sb.append("Visited: ");
	  if (visited) {
      sb.append("True ");
    } else {
      sb.append("False ");
    }

    sb.append("Neighbours(N,E,S,W): ");
    for (Cell cell  : oneStepNeighbours) {
      int count = 0;
      if (cell != null) {
    	  if (!cell.isWall()) {
    		  count++;
    	  }
      }
      sb.append(count);
    }

    return sb.toString();
  }
}