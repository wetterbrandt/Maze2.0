/*
 * Observera att konstruktorn antar att "starten" är i första raden och "slutet" i sista raden.
 * Funkar endast för kvadratiska labyrinter.
 */

package maze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;

public class Maze {
	private Cell[][] maze;
	private int startCol;
	private int endCol;
	private int size;
	Stack<Cell> unvisitedCells;

	// Konstruktorn som tar från file
	public Maze(String file) throws IOException {
		String line = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException fe) {
			System.out.println("File not found: " + file);
		}

		line = bufferedReader.readLine();
		maze = new Cell[line.length()][line.length()];
		size = maze.length;

		// Fill the Cell[][] with correkt wall-info.
		int row = 0;
		int col = 0;
		while (line != null) {
			for (char c : line.toCharArray()) {
				switch (c) {
					case '#': maze[row][col] = new Cell(row, col, true, false);
										break;
					case ' ': maze[row][col] = new Cell(row, col, false, false);
										break;
				}
				col++;
			}
			col = 0;
			row++;
			line = bufferedReader.readLine();
		}
		bufferedReader.close();

		// Hitta startkolumnen!
		for (col = 0; col < maze[0].length;	col++) {
			if (maze[0][col].isWall() == false) {
				startCol = col;
			}
		}

		// Hitta slutkolumnen!
		for (col = 0; col < maze[0].length;	col++) {
			if (maze[size - 1][col].isWall() == false) {
				endCol = col;
			}
		}

		BuildTwoStepNeighbourRelations();

	}

	// Genererar en kvadratisk labyrint av storlek size
	public Maze(int size) {

		this.size = size;
		maze = new Cell[size][size];

		initialize();
		generate();
	}

	public void	print() {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze.length; j++) {
				if (maze[i][j].isWall()) {
					System.out.print("X");
				} else {
					System.out.print("-");
				}

			}
			System.out.println();
		}
	}

	public int getStartCol() {
		return startCol;
	}

	public int getEndCol() {
		return endCol;
	}

	public int getSize() {
		return size;
	}

	public Cell	getStartCell() {
		return maze[0][startCol];
	}

	public Cell getEndCell() {
		return maze[size - 1][endCol];
	}

	private void initialize(){
		unvisitedCells = new Stack<Cell>();

		// Initialize all edge cells as visited walls and all others as unvisited walls.
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (row == 0 || col == 0) {
					maze[row][col] = new Cell(row, col, true, true);
				}
				if (row == (size - 1) || col == size - 1) {
					maze[row][col] = new Cell(row, col, true, true);
				}
				if (row != 0 && row != size - 1 && col != 0 && col != size - 1) {
					maze[row][col] = new Cell(row, col, true, false);
					unvisitedCells.add(maze[row][col]);
				}
			}
		}
		Random rnd = new Random();
		this.startCol = rnd.nextInt((size - 1) / 2) * 2 + 1; // för att få ojämna tal.
		this.endCol = rnd.nextInt((size - 1) / 2) * 2 + 1; // för att få ojämna tal.

		maze[0][startCol].setWall(false);
		maze[size - 1][endCol].setWall(false);

		BuildTwoStepNeighbourRelations();
		BuildOneStepNeighbourRelations();

	}

	private void generate(){

		Cell startCell = maze[0][startCol];

		maze[size - 1][endCol].setVisited(false); // sätt målet som obesökt

		startCell.setVisited(true);

		DFS(startCell.getTwoStepNeighbours().get(0), startCell);

		removeRandomWalls(size/5); // Öpnna upp labyrinten för att möjligen ge flera lösningar

	}

	private void removeRandomWalls(int nbr){
		for(int i = 1; i <= nbr; i++) {
			Random rnd = new Random();
			Cell rndCell = maze[rnd.nextInt(size - 2) + 1][rnd.nextInt(size - 2) + 1];
			while(!rndCell.isWall()) {
				rndCell = maze[rnd.nextInt(size - 2) + 1][rnd.nextInt(size - 2) + 1];
			}
			rndCell.setWall(false);
		}
	}

	private void DFS(Cell current, Cell prev){

		current.setVisited(true);
		current.setWall(false);

		for (Cell cell: current.getRandomNeighbours()) {
			if (!cell.isVisited()) {
				int row = current.getRowBetween(cell);
				int col = current.getColBetween(cell);
				if(row > 0 && row < size &&
				 	 col > 0 && col < size){
						maze[row][col].setWall(false);
						DFS(cell, current);
				}
			}
		}
	}

	private void BuildTwoStepNeighbourRelations() {
		// Construct all inner neighbour relationships (of length 2).
		for (int row = 1; row < size - 1; row ++) {
			for (int col = 1; col < size - 1; col++) {

				// North
				if( row - 2 > 0) {
					maze[row][col].setTwoStepNeighbour(0, maze[row - 2][col]);
				}
				// West
				if ( col + 2 < size - 1){
					maze[row][col].setTwoStepNeighbour(1, maze[row][col + 2]);
				}
				//South
				if ( row + 2 < size - 1){
					maze[row][col].setTwoStepNeighbour(2, maze[row + 2][col]);
				}
				// East
				if (col - 2 > 0){
					maze[row][col].setTwoStepNeighbour(3, maze[row][col - 2]);
				}
			}
		}

		// Construct the neighbour relations for start and end cells
		getStartCell().setTwoStepNeighbour(2, maze[1][startCol]);
		getEndCell().setTwoStepNeighbour(0, maze[size - 2][endCol]);
	}

	private void BuildOneStepNeighbourRelations() {
		// Construct all inner neighbour relationships (of length 1).
		for (int row = 1; row < size - 1; row ++) {
			for (int col = 1; col < size - 1; col++) {

				// North
				if( row - 1 >= 0) {
					maze[row][col].setOneStepNeighbour(0, maze[row - 1][col]);
				}
				// West
				if ( col + 1 <= size - 1){
					maze[row][col].setOneStepNeighbour(1, maze[row][col + 1]);
				}
				//South
				if ( row + 1 <= size - 1){
					maze[row][col].setOneStepNeighbour(2, maze[row + 1][col]);
				}
				// East
				if (col - 1 >= 0){
					maze[row][col].setOneStepNeighbour(3, maze[row][col - 1]);
				}
			}
		}

		// Construct the neighbour relations for start and end cells
		getStartCell().setOneStepNeighbour(2, maze[1][startCol]);
		getEndCell().setOneStepNeighbour(0, maze[size - 2][endCol]);
	}

	public static void main(String[] args) throws IOException {
		//Maze myMaze = new Maze("resources/Maze2.txt");
		Maze myMaze = new Maze(7);
		BreadthFirstSearch BFS = new BreadthFirstSearch();

		myMaze.print();

		BFS.solve(myMaze);

		System.out.println(BFS);
	}

}