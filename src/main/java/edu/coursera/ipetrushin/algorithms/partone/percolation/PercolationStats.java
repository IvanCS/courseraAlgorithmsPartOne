package edu.coursera.ipetrushin.algorithms.partone.percolation;

import java.util.Random;

/**
 * Created by ipetrush on 6/30/2015.
 */
public class PercolationStats {

    private Percolation percolation;
    private final int N;
    private final int T;
    private int[] experimentsResults;
    private double meanValue;
    private double stddevValue;

    /**
     * // perform T independent experiments on an N-by-N grid
     *
     * @param N
     * @param T
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
     * sample mean of percolation threshold
     *
     * @return
     */
    public double mean() {
        double percent = (double) N * N / 100;
        return meanValue / percent / 100;
    }

    /**
     * sample standard deviation of percolation threshold
     *
     * @return
     */
    public double stddev() {
        double percent = (N*N - mean())/(T-1);
        return stddevValue / percent / 100;
    }


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


    private void calculateStdDev() {
        for (int threshold : experimentsResults) {
            stddevValue += threshold*threshold - 2*threshold*meanValue + meanValue*meanValue;
        }

        stddevValue /=  T - 1;
        stddevValue = Math.sqrt(stddevValue);
    }

    /**
     * low  endpoint of 95% confidence interval
     *
     * @return
     */
    public double confidenceLo() {
        return mean() - 1.96*stddev()/Math.sqrt(T);
    }

    /**
     * high endpoint of 95% confidence interval
     *
     * @return
     */
    public double confidenceHi() {
        return mean() + 1.96*stddev()/Math.sqrt(T);
    }

    /**
     * test client (described below)
     *
     * @param args
     */
    public static void main(String[] args) {

    }
}
