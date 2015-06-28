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
    public void doesNotPercolates(){
        Percolation percolation = new Percolation(4);
        percolation.open(1,1);
        percolation.open(2,2);
        percolation.open(3,2);
        percolation.open(3,3);
        percolation.open(4,3);

        Assert.assertFalse(percolation.percolates());
    }

    @Test
    public void doesPercolates(){
        Percolation percolation = new Percolation(4);
        percolation.open(1,1);
        percolation.open(2,2);
        percolation.open(3,2);
        percolation.open(3,3);
        percolation.open(4,3);

        percolation.open(2,1); //boom!!! :)

        Assert.assertTrue(percolation.percolates());
    }
}
