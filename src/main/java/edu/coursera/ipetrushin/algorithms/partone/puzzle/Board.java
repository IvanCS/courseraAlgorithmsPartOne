package edu.coursera.ipetrushin.algorithms.partone.puzzle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Best-first search. Now, we describe a solution to the problem that illustrates a general artificial intelligence methodology
 * known as the A* search algorithm. We define a search node of the game to be a board, the number of moves made to reach the board,
 * and the previous search node. First, insert the initial search node (the initial board, 0 moves, and a null previous search node)
 * into a priority queue.
 * Then, delete from the priority queue the search node with the minimum priority, and insert onto the priority queue
 * all neighboring search nodes (those that can be reached in one move from the dequeued search node).
 * Repeat this procedure until the search node dequeued corresponds to a goal board.
 * The success of this approach hinges on the choice of priority function for a search node.
 * <p/>
 * We consider two priority functions:
 * <p/>
 * Hamming priority function.
 * The number of blocks in the wrong position, plus the number of moves made so far
 * to get to the search node. Intuitively, a search node with a small number of blocks
 * in the wrong position is close to the goal, and we prefer a search node that have been reached
 * using a small number of moves.
 * <p/>
 * Manhattan priority function.
 * The sum of the Manhattan distances (sum of the vertical and horizontal distance)
 * from the blocks to their goal positions, plus the number of moves made so far to get to the search node.
 * <p/>
 * We make a key observation:
 * To solve the puzzle from a given search node on the priority queue,
 * the total number of moves we need to make (including those already made) is at least its priority,
 * using either the Hamming or Manhattan priority function.
 * (For Hamming priority, this is true because each block that is out of place must move at least
 * once to reach its goal position. For Manhattan priority, this is true because each block must move
 * its Manhattan distance from its goal position. Note that we do not count the blank square when
 * computing the Hamming or Manhattan priorities.) Consequently, when the goal board is dequeued,
 * we have discovered not only a sequence of moves from the initial board to the goal board,
 * but one that makes the fewest number of moves.
 * (Challenge for the mathematically inclined: prove this fact.)
 * <p/>
 * A critical optimization.
 * Best-first search has one annoying feature:
 * search nodes corresponding to the same board are enqueued on the priority queue many times. To reduce unnecessary
 * exploration of useless search nodes, when considering the neighbors of a search node, don't enqueue a neighbor
 * if its board is the same as the board of the previous search node.
 */
public class Board {

    private int[][] blocks;
    private Board previousBoard;
    private int movesCount;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new NullPointerException("Board initialization error!");
        }
        this.blocks = blocks;
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        throw new UnsupportedOperationException("");
    }

    // board dimension N
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        int outOfPlaceCounter = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                //taking blocks[i].length for not proportional board size cases
                int goalBlockId = i * blocks[i].length + j;
                goalBlockId++;


                if (blocks[i][j] != 0
                        && blocks[i][j] != goalBlockId) outOfPlaceCounter++;
            }
        }

        return outOfPlaceCounter + movesCount;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int sumOfGoalDistances = 0;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                //taking blocks[i].length for not proportional board size cases

                int expectedBlockId = i * blocks[i].length + j;
                expectedBlockId++;

                int actualBlockId = blocks[i][j];

                if (actualBlockId != expectedBlockId && actualBlockId != 0) { //calculate shift

                    int expectedRowId = i;
                    int expectedColumnId = j + 1;

                    int actualRowId = actualBlockId / dimension();
                    int actualColumnId = actualBlockId - actualRowId * dimension();

                    int rowDelta = Math.abs(actualRowId - expectedRowId);
                    int columnDelta = Math.abs(actualColumnId - expectedColumnId);

                    int shift = 0;

                    if (rowDelta != 0 && columnDelta == 0) {
                        shift = rowDelta;
                    } else if (rowDelta == 0 && columnDelta != 0) {
                        shift = columnDelta;
                    } else {
                        shift = rowDelta + columnDelta;
                    }

                    sumOfGoalDistances += shift;
                }
            }
        }

        return sumOfGoalDistances + movesCount;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                //taking blocks[i].length for not proportional board size cases
                int goalBlockId = i * blocks[i].length + j;
                goalBlockId++;

                if (goalBlockId == dimension() * dimension()) {
                    goalBlockId = 0;
                }

                if (blocks[i][j] != goalBlockId) return false;
            }
        }

        return true;
    }

    // a board that is obtained by exchanging two adjacent blocks in the same row
    public Board twin() {

        int[][] twinBlocks = cloneBlocks();

        //TODO random
        int neighbourBlockId = 0;
        int randomBlockId = 0;
        Random r = new Random();
        while (neighbourBlockId == 0) {
            randomBlockId = r.nextInt(dimension() * dimension());
            randomBlockId++;

            int randomBlockRowId = randomBlockId / dimension();
            int randomBlockColumnId = randomBlockId - randomBlockRowId * dimension();
            if (randomBlockColumnId + 1 < dimension()) {
                if (twinBlocks[randomBlockRowId][randomBlockColumnId + 1] != 0) {
                    neighbourBlockId = randomBlockColumnId + 1;
                    int buffer = twinBlocks[randomBlockRowId][randomBlockColumnId];
                    twinBlocks[randomBlockRowId][randomBlockColumnId] = twinBlocks[randomBlockRowId][randomBlockColumnId + 1];
                    twinBlocks[randomBlockRowId][randomBlockColumnId + 1] = buffer;
                }

            } else if (randomBlockColumnId - 1 > 0) {
                if (twinBlocks[randomBlockRowId][randomBlockColumnId - 1] != 0) {
                    neighbourBlockId = randomBlockColumnId - 1;

                    int buffer = twinBlocks[randomBlockRowId][randomBlockColumnId];
                    twinBlocks[randomBlockRowId][randomBlockColumnId] = twinBlocks[randomBlockRowId][randomBlockColumnId - 1];
                    twinBlocks[randomBlockRowId][randomBlockColumnId - 1] = buffer;
                }
            }

        }

        return new Board(twinBlocks);
    }

    private int[][] cloneBlocks() {
        int[][] clone = new int[dimension()][];
        for (int i = 0; i < dimension(); i++) {
            clone[i] = Arrays.copyOf(this.blocks[i], dimension());
        }
        return clone;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || this.getClass() != y.getClass()) return false;
        if (this == y) return true;

        Board that = (Board) y;
        if (this.blocks.length != that.blocks.length) return false;

        for (int i = 0; i < this.blocks.length; i++) {

            if (this.blocks[i].length != that.blocks[i].length) return false;

            for (int j = 0; j < blocks[i].length; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new LinkedList<>();
        int[][] clone;
        int buffer;

        boolean hasLeftNeighbour,
                hasRightNeighbour,
                hasBottomNeighbour,
                hasUpNeighbour;

        //find empty block index
        for (int i = 0; i < blocks.length; i++) {

            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] == 0) {
                    hasLeftNeighbour = (j - 1 >= 0);
                    hasRightNeighbour = (j + 1 < dimension());
                    hasUpNeighbour = (i - 1 >= 0);
                    hasBottomNeighbour = (i + 1 < dimension());

                    if (hasLeftNeighbour) {
                        clone = cloneBlocks();

                        buffer = clone[i][j];
                        clone[i][j] = clone[i][j - 1];
                        clone[i][j - 1] = buffer;

                        neighbors.add(new Board(clone));
                    }

                    if (hasRightNeighbour) {
                        clone = cloneBlocks();

                        buffer = clone[i][j];
                        clone[i][j] = clone[i][j + 1];
                        clone[i][j + 1] = buffer;

                        neighbors.add(new Board(clone));
                    }
                    if (hasUpNeighbour) {
                        clone = cloneBlocks();

                        buffer = clone[i][j];
                        clone[i][j] = clone[i - 1][j];
                        clone[i - 1][j] = buffer;

                        neighbors.add(new Board(clone));
                    }
                    if (hasBottomNeighbour) {
                        clone = cloneBlocks();

                        buffer = clone[i][j];
                        clone[i][j] = clone[i + 1][j];
                        clone[i + 1][j] = buffer;

                        neighbors.add(new Board(clone));
                    }

                    break;
                }
            }

            if (neighbors.size() > 0) break;
        }

        return neighbors;
    }

    private int getBlockId(int i, int j) {
        int blockId = i * dimension() + j;
        return ++blockId;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension() + "\n");
        for (int i = 0; i < blocks.length; i++) {
            sb.append("\t");
            for (int j = 0; j < blocks[i].length; j++) {
                sb.append((j + 1 < blocks[i].length) ? blocks[i][j] + "\t" : blocks[i][j] + "\n");
            }

        }
        return sb.toString();
    }
}
