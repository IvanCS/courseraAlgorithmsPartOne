package edu.coursera.ipetrushin.algorithms.partone.percolation;

import java.util.Random;

/**
 * Percolation Statistics
 */
public class PercolationStats {

    private Percolation percolation;
    private final int N;
    private final int T;
    private int[] experimentsResults;
    private double meanValue;
    private double stddevValue;

    /**
     * perform T independent experiments on an N-by-N grid
     *
     * @param N grid dimension
     * @param T number of experiments
     */
    public PercolationStats(int N, int T) {
        this.T = T;
        this.N = N;
        percolation = new Percolation(N);
        experimentsResults = new int[T];

        calculateMean();
        calculateStdDev();
    }

    private int percolateRandomlyAndGetThreshold(Percolation percolation) {
        if (percolation == null) {
            throw new NullPointerException("percolation object is not initialized");
        }

        int thresholdCounter = 0;
        Random randomI = new Random();
        Random randomJ = new Random();
        int i;
        int j;

        while (!percolation.percolates()) {
            i = randomI.nextInt(N) + 1;
            j = randomJ.nextInt(N) + 1;

            if (!percolation.isOpen(i, j)) {
                percolation.open(i, j);
                thresholdCounter++;
            }
        }

        return thresholdCounter;
    }

    /**
     * calculates sample mean of percolation threshold
     *
     * @return percents of mean value
     */
    public double mean() {
        double percent = (double) N * N / 100;
        return meanValue / percent / 100;
    }

    /**
     * sample standard deviation of percolation threshold
     *
     * @return percents of stddev value
     */
    public double stddev() {
        double percent = (N * N - mean()) / (T - 1);
        return stddevValue / percent / 100;
    }


    /**
     * Calculates mean value
     */
    private void calculateMean() {
        int experimentsResult = 0,
                experimentThreshold;

        for (int t = 0; t < T; t++) {
            experimentThreshold = percolateRandomlyAndGetThreshold(percolation);
            experimentsResults[t] = experimentThreshold;
            experimentsResult += experimentThreshold;

            percolation.resetGrid();
            percolation.resetSiteWeights();
        }

        meanValue = (((double) experimentsResult) / T);
    }


    /**
     * Calculates standard deviation value
     */
    private void calculateStdDev() {
        for (int threshold : experimentsResults) {
            stddevValue += threshold * threshold - 2 * threshold * meanValue + meanValue * meanValue;
        }

        stddevValue /= T - 1;
        stddevValue = Math.sqrt(stddevValue);
    }

    /**
     * calculates low endpoint of 95% confidence interval
     *
     * @return low endpoint
     */
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    /**
     * calculates high endpoint of 95% confidence interval
     *
     * @return high endpoint
     */
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    /**
     * test client
     * <p/>
     * the method that takes two command-line arguments N and T,
     * performs T independent computational experiments (discussed above) on an N-by-N grid,
     * and prints out the mean, standard deviation, and the 95% confidence interval for the percolation threshold.
     * Use standard random from our standard libraries to generate random numbers;
     * use standard statistics to compute the sample mean and standard deviation.
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {

        if (args == null || args.length == 0 ||
                args[0] == null || args[1] == null ||
                args[0].isEmpty() || args[1].isEmpty()) {

            throw new NullPointerException("please provide arguments values for N and T");
        }

        int n = Integer.valueOf(args[0]);
        int t = Integer.valueOf(args[0]);

        PercolationStats percolationStats = new PercolationStats(n, t);

        System.out.println("mean\t\t\t\t\t= " + percolationStats.mean());
        System.out.println("stddev\t\t\t\t\t= " + percolationStats.stddev());
        System.out.println("95% confidence interval = " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
    }
}
