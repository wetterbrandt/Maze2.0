package maze;

import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Maze maze = new Maze(219);
		maze.print();

		MazeRunner tl = new TurnLeft();
		for ( int i = 0; i < 100; i++){
			long t1 = System.nanoTime();
			maze = new Maze(219);
			long t2 = System.nanoTime();
			tl.solve(maze);
			System.out.println((t2 - t1) / 1000000 + "ms");
		}
		
	}
}