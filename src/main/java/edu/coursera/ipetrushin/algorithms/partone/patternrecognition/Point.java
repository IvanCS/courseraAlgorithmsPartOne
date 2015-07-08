package edu.coursera.ipetrushin.algorithms.partone.patternrecognition;

/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import edu.princeton.cs.introcs.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point>  , Cloneable{

    // compare points by slope to this point
    // compare points by slope
    /**
     * The SLOPE_ORDER comparator should compare points by the slopes they make with the invoking point (x0, y0).
     * Formally, the point (x1, y1) is less than the point (x2, y2) if and only if the slope (y1 − y0) / (x1 − x0) is less
     * than the slope (y2 − y0) / (x2 − x0).
     * Treat horizontal, vertical, and degenerate line segments as in the slopeTo() method.
     */
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            double s1 = slopeTo(o1);
            double s2 = slopeTo(o2);

          /*  if(s1 == s2 && s1 == 0) return +0;//horizontal line segment
            if(s1 == s2 && s1 == Double.POSITIVE_INFINITY) return +2;//vertical line segment;
            if(s1 == s2 && s1 == Double.NEGATIVE_INFINITY) return -2;*/

            if (s1 == s2) return +0;
            if (s1 < s2) return -1;
            return +1;
        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // slope between this point and that point

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates

    /**
     * The slopeTo() method should return the slope between the invoking point (x0, y0)
     * and the argument point (x1, y1), which is given by the formula (y1 − y0) / (x1 − x0).
     * Treat the slope of a horizontal line segment as positive zero;
     * treat the slope of a vertical line segment as positive infinity;
     * treat the slope of a degenerate line segment (between a point and itself) as negative infinity.
     *
     * @param that
     * @return
     */
    public double slopeTo(Point that) {
        if (this.y == that.y && this.x == that.x) return Double.NEGATIVE_INFINITY;
        if (this.y == that.y && this.x != that.x) return +0; //horizontal line segment
        if (this.x == that.x && this.y != that.y) return Double.POSITIVE_INFINITY;//vertical line segment
        //degenerate line segment (between a point and itself)


        double tmp = that.y - this.y;
        return tmp /= (that.x - this.x);
    }

    /**
     * The compareTo() method should compare points by their y-coordinates,
     * breaking ties by their x-coordinates. Formally, the invoking point (x0, y0)
     * is less than the argument point (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that
     * @return
     */
    public int compareTo(Point that) {
        if (this.y == that.y && this.x == that.x) return 0;
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) return -1;

        return +1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
}