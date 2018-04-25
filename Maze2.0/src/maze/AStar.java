// Frågor? Hör med Jacob. hejhej

package maze;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

public class AStar {
	private boolean complete;
	private int count;
	private Set<TreeCell> closedSet;					// Alla celler som redan är besökta.
	private Set<TreeCell> openSet;						// Mängden "aktiva" celler.
	private Map<TreeCell, TreeCell> cameFrom;	// Cellens kortaste väg ett steg bakåt.
	private Map<TreeCell, Integer> gScore;		// Kostnaden från start till cellen.

	public AStar(){
		complete = false;
		count = 0;
		closedSet = new HashSet<TreeCell>();
		openSet = new HashSet<TreeCell>();
		cameFrom = new HashMap<TreeCell, TreeCell>();
		gScore = new HashMap<TreeCell, Integer>();
	}

	public void solve(TreeMaze tMaze) {
		TreeCell startCell = tMaze.getStartCell();
		TreeCell endCell = tMaze.getEndCell();

		// Totala kostnaden från start till end genom denna cell. Komparatorn jämför avståndet från startcellen och avständet till slutcellen. Innehåller endast "aktiva" celler.
		TreeMap<TreeCell, Double> fScore = new TreeMap<TreeCell, Double>((c1, c2) ->
			((int)getHeuristicValue(c1, endCell) + gScore.get(c1)) -
			((int)getHeuristicValue(c2, endCell) + gScore.get(c2)));

		// Lägg till startCellen i mängden "aktiva" celler.
		openSet.add(startCell);

		// Kostnaden från start till start är 0.
		gScore.put(startCell, 0);

		// Kostnaden från start till end är pyth.
		fScore.put(startCell, getHeuristicValue(startCell , endCell));

		while (!openSet.isEmpty()) {

			// Flytta runt den nuvarande cellen i alla mängder, heapar och sånt.
			TreeCell current = fScore.pollFirstEntry().getKey();
			openSet.remove(current);
			closedSet.add(current);

			if (current == endCell){
				complete = true;
			}

			for (TreeCell neighbour : current.getNeighbours()) {

				// LÄGGA TILL GRANNEN I GSCORE!?

			  if (closedSet.contains(neighbour)){
					continue;
				}

				// Hey! Vi hittade en hittills obesökt cell!
				if (!openSet.contains(current)) {
					openSet.add(neighbour);
				}

				// Avståndet från start till denna grannen.
				Integer tentativeGScore = gScore.get(current) + distBetween(current, neighbour);

				// Om denna grannen inte ligger i gScore måste den läggas till
				if (gScore.get(neighbour) == null) {
					gScore.put(neighbour, Integer.MAX_VALUE);
				}

				if (tentativeGScore >= gScore.get(neighbour)) {
					// Då är detta in en längre väg.
					continue;
				}

				// Detta är den hittills bästa vägen till denna cellen!
				cameFrom.put(neighbour, current);
				gScore.put(neighbour, tentativeGScore);
				fScore.put(neighbour, gScore.get(neighbour) + getHeuristicValue(neighbour, endCell));
			}
		}
	}

	public double getHeuristicValue(TreeCell current, TreeCell endCell){
		// Pyth mellan current och endCell.
		return Math.sqrt(
						Math.pow(current.getRow() - endCell.getRow(), 2) +
						Math.pow(current.getCol() - endCell.getCol(), 2));
	}

	public Integer distBetween(TreeCell c1, TreeCell c2){
		return Math.abs(c1.getCol() - c2.getCol() + c1.getRow() - c2.getRow());
	}

	public void PrintPath(){
		// printa pathen!
	}


	public int nrOfVisitedNodes() {
		return closedSet.size();
	}

}