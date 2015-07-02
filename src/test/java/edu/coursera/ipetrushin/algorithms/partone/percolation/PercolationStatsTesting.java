package edu.coursera.ipetrushin.algorithms.partone.percolation;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PercolationStatsTesting {

    @Test
    public void testMeanPositive() {
        PercolationStats percolationStats = new PercolationStats(4, 10);
        double meanThreshold = percolationStats.mean();

        Assert.assertTrue(meanThreshold > 0.5);
    }

    @Test
    public void testStdDevPositive() {
        PercolationStats percolationStats = new PercolationStats(100, 50);
        double stddev = percolationStats.stddev();

        System.out.println(percolationStats.mean());
        System.out.println(stddev);
        System.out.println(percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());

        Assert.assertTrue(stddev < 0.1);
    }


    @Test
    public void testLoHiConfidenceIntervalPositive() {
        PercolationStats percolationStats = new PercolationStats(4, 10);
        double lo = percolationStats.confidenceLo();
        double hi = percolationStats.confidenceHi();

        Assert.assertTrue(lo < percolationStats.mean());
        Assert.assertTrue(hi > percolationStats.mean());
    }
}
