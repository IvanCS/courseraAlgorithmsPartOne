package edu.coursera.ipetrushin.algorithms.partone.puzzle;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

/**
 *  immutable data type Solver
 *  To implement the A* algorithm,
 *  you must use the MinPQ data type from algs4.jar for the priority queue(s).
 */
public class Solver {
    public Solver(Board initial){

    }           // find a solution to the initial board (using the A* algorithm)
    public boolean isSolvable()  {
        throw new UnsupportedOperationException("");
    }          // is the initial board solvable?
    public int moves()      {
        throw new UnsupportedOperationException("");
    }               // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution() {
        throw new UnsupportedOperationException("");
    }     // sequence of boards in a shortest solution; null if unsolvable

   // solve a slider puzzle (given below)
   public static void main(String[] args) {

       // create initial board from file
       In in = new In(args[0]);
       int N = in.readInt();
       int[][] blocks = new int[N][N];
       for (int i = 0; i < N; i++)
           for (int j = 0; j < N; j++)
               blocks[i][j] = in.readInt();
       Board initial = new Board(blocks);

       // solve the puzzle
       Solver solver = new Solver(initial);

       // print solution to standard output
       if (!solver.isSolvable())
           StdOut.println("No solution possible");
       else {
           StdOut.println("Minimum number of moves = " + solver.moves());
           for (Board board : solver.solution())
               StdOut.println(board);
       }
   }
}
