package maze;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class TreeCell {
	private int col;
	private int row;
	private boolean visited;
	private ArrayList<TreeCell> neighbours; //Non-weighted neighbours
	private PriorityQueue<TreeCell> closestNeighbours;
	private int distance;
	private int weightedDistance;

	public TreeCell(int col, int row){
		this.col = col;
		this.row = row;
		visited = false;
		neighbours = new ArrayList<TreeCell>();
		closestNeighbours = new PriorityQueue<TreeCell>((c1, c2) ->
			Integer.compare(c1.weightedDistance, c2.weightedDistance));
		distance = 0;
		weightedDistance = 0;
	}

	public TreeCell(Cell cell){
		this.row = cell.getRow();
		this.col = cell.getCol();
		visited = false;
		neighbours = new ArrayList<TreeCell>();
		distance = 0;
		weightedDistance = 0;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public int getRow(){
		return row;
	}

	public int getCol(){
		return col;
	}

	public ArrayList<TreeCell> getNeighbours(){
		return neighbours;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof TreeCell) {
			TreeCell treeCell = (TreeCell) o;
			return row == treeCell.row && col == treeCell.col;
		}
		return false;
	}

	@Override
	public int hashCode(){
		return ((Integer)(row + col)).hashCode();
	}

}