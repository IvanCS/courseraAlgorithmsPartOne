import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by ipetrush on 6/28/2015.
 */
@RunWith(JUnit4.class)
public class PercolationTesting {

    @Test
    public void doesNotPercolates() {
        Percolation percolation = new Percolation(4);
        percolation.open(1, 1);
        percolation.open(2, 2);
        percolation.open(3, 2);
        percolation.open(3, 3);
        percolation.open(4, 3);

        Assert.assertFalse(percolation.percolates());
    }

    @Test
    public void doesPercolates() {
        Percolation percolation = new Percolation(4);
        percolation.open(1, 1);
        percolation.open(2, 2);
        percolation.open(3, 2);
        percolation.open(3, 3);
        percolation.open(4, 3);

        percolation.open(2, 1); //boom!!! :)

        Assert.assertTrue(percolation.percolates());
    }

    @Test
    public void doesPercolates2() {
        Percolation percolation = new Percolation(4);
        percolation.open(1, 1);
        percolation.open(2, 1);

        percolation.open(3, 3);
        percolation.open(3, 2);
        percolation.open(4, 3);

        percolation.open(2, 2); //boom!!! :)

        Assert.assertTrue(percolation.percolates());
    }

    @Test
    public void isFullWorks() {
        Percolation percolation = new Percolation(4);
        percolation.open(1, 1);
        percolation.open(1, 2);
        percolation.open(1, 3);
        percolation.open(1, 4);

        percolation.open(2, 1);
        percolation.open(2, 2);
        percolation.open(2, 3);
        percolation.open(2, 4);

        percolation.open(3, 1);
        percolation.open(3, 3);


        percolation.open(4, 2);
        percolation.open(4, 3);
        percolation.open(4, 4);

        Assert.assertTrue(percolation.isFull(1, 1));
        Assert.assertTrue(percolation.isFull(1, 2));
        Assert.assertTrue(percolation.isFull(1, 4));
        Assert.assertTrue(percolation.isFull(2, 1));
        Assert.assertTrue(percolation.isFull(2, 3));
        Assert.assertTrue(percolation.isFull(4, 3));

        Assert.assertFalse((percolation.isFull(4, 2)));
        Assert.assertFalse((percolation.isFull(3, 1)));
        Assert.assertFalse((percolation.isFull(2, 4)));

        Assert.assertFalse((percolation.isFull(3, 4)));
        Assert.assertFalse((percolation.isFull(4, 1)));
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void checkArrayOitOfBoutException1() {

        Percolation percolation = new Percolation(4);
        percolation.open(5, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkArrayOitOfBoutException2() {
        Percolation percolation = new Percolation(4);
        percolation.isFull(0, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkArrayOitOfBoutException3() {
        Percolation percolation = new Percolation(4);
        percolation.open(1, 5);
    }

}
