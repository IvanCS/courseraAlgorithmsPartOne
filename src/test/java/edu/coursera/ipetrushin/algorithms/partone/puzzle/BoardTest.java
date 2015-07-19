package edu.coursera.ipetrushin.algorithms.partone.puzzle;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class BoardTest {


    @Test
    public void testHamming() {
        Board board = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        System.out.print(board.toString());

        int h = board.hamming();

        Assert.assertEquals(5, h);
    }


    @Test
    public void testManhattan() {
        Board board = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        System.out.print(board.toString());

        Assert.assertTrue(board.equals(board));

        int m = board.manhattan();

        Assert.assertEquals(10, m);


    }

    @Test
    public void testGoal() {
        Board board = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        System.out.print(board.toString());

        Assert.assertTrue(board.isGoal());

    }


    @Test
    public void testTwin() {
        Board board = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        System.out.print("original : \n");
        System.out.print(board.toString());
        Board twin = board.twin();

        System.out.print(board.toString());
        System.out.print("\n twin: \n");
        System.out.print(twin.toString());

        Assert.assertFalse(twin.equals(board));

    }

    @Test
    public void testNeighboursWhenAllPresent() {
        Board board = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        System.out.print("original : \n");
        System.out.print(board.toString());

        Iterable<Board> neighbours = board.neighbors();
        for (Board neighbourBoard : neighbours) {
            System.out.print("\nn: \n");
            System.out.print(neighbourBoard.toString());
            System.out.print("\n==========\n");
            Assert.assertTrue(neighbourBoard.equals(new Board(new int[][]{{8, 1, 3}, {0, 4, 2}, {7, 6, 5}}))
                    || neighbourBoard.equals(new Board(new int[][]{{8, 1, 3}, {4, 2, 0}, {7, 6, 5}}))
                    || neighbourBoard.equals(new Board(new int[][]{{8, 0, 3}, {4, 1, 2}, {7, 6, 5}}))
                    || neighbourBoard.equals(new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {7, 0, 5}})))
            ;
        }
    }

    @Test
    public void testNeighboursWhenOnlyUpAndRightPresent() {
        Board board = new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {0, 7, 5}});
        System.out.print("original : \n");
        System.out.print(board.toString());

        Iterable<Board> neighbours = board.neighbors();

        for (Board neighbourBoard : neighbours) {
            System.out.print("\nn: \n");
            System.out.print(neighbourBoard.toString());
            System.out.print("\n==========\n");
            Assert.assertTrue(neighbourBoard.equals(new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {7, 0, 5}}))
                    || neighbourBoard.equals(new Board(new int[][]{{8, 1, 3}, {0, 6, 2}, {4, 7, 5}}))
                   )
            ;
        }
    }

    @Test
    public void testNeighboursWhenOnlyBottomAndLeftPresent() {
        Board board = new Board(new int[][]{{8, 1, 0}, {4, 3, 2}, {6, 7, 5}});
        System.out.print("original : \n");
        System.out.print(board.toString());

        Iterable<Board> neighbours = board.neighbors();

        for (Board neighbourBoard : neighbours) {
            System.out.print("\nn: \n");
            System.out.print(neighbourBoard.toString());
            System.out.print("\n==========\n");
            Assert.assertTrue(neighbourBoard.equals(new Board(new int[][]{{8, 1, 2}, {4, 3, 0}, {6, 7, 5}}))
                            || neighbourBoard.equals(new Board(new int[][]{{8, 0, 1}, {4, 3, 2}, {6, 7, 5}}))
            )
            ;
        }
    }
}
