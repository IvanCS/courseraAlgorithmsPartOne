package edu.coursera.ipetrushin.algorithms.partone.patternrecognition;

import edu.princeton.cs.introcs.StdDraw;

import java.io.BufferedReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Write a program Fast.java that implements this algorithm.
 * The order of growth of the running time of your program should be N2 log N
 * in the worst case and it should use space proportional to N.
 * <p/>
 * Given a point p, the following method determines whether p participates in a set of 4 or more collinear points.
 * <p/>
 * Think of p as the origin.
 * For each other point q, determine the slope it makes with p.
 * Sort the points according to the slopes they makes with p.
 * Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p.
 * If so, these points, together with p, are collinear.
 * <p/>
 * Applying this method for each of the N points in turn yields an efficient algorithm to the problem.
 * The algorithm solves the problem because points that have equal slopes with respect to p are collinear,
 * and sorting brings such points together.
 * <p/>
 * The algorithm is fast because the bottleneck operation is sorting.
 */
public class Fast {

    private Point[] points;
    private int N;
    private Queue<Point> origins;


    public Fast(Path path) throws Exception {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        buildConvexHull(path);
    }

    public static void main(String[] args) throws Exception {
        if (args == null || args.length < 1) {
            throw new IllegalArgumentException("please specify name of input file as an argument");
        }

        String fileName = args[0];
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("specify file name!");
        }

        URL path = Brute.class.getClassLoader().getResource(fileName);
        Path p = Paths.get(path.toURI());
        if (!Files.exists(p)) {
            throw new IllegalArgumentException("file for name : " + fileName + " doesn't exists");
        }

        Fast fast = new Fast(p);
        fast.is4CollinearPointsPatternRecognized();

    }

    private void buildConvexHull(Path pathToFile) throws Exception {
        //TODO read form file
        try (BufferedReader reader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8);) {
            String line;
            String[] point;
            int i = 0;

            N = Integer.parseInt(reader.readLine());
            points = new Point[N];
            origins = new LinkedList<>();

            while (reader.ready()) {
                line = reader.readLine();
                line = line.trim();
                if (!line.isEmpty()) {
                    point = line.split("\\s+");
                    int x = Integer.parseInt(point[0]);
                    int y = Integer.parseInt(point[1]);

                    Point newPoint = new Point(x, y);

                    points[i++] = newPoint;
                    origins.add(newPoint);
                    newPoint.draw();
                }
            }

        }
    }

    private void shellSort(List<Point> l) {
        final int N = l.size();
        int h = 1;
        while (h < N / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < N; i++) {

                for (int j = i; j >= h && l.get(j).compareTo(l.get(j - h)) == -1; j -= h) {
                    Point swap = l.get(j);
                    l.set(j, (l.get(j - h)));
                    l.set(j - h, swap);
                }
            }
            h /= 3;
        }
    }

    private void shellSort(Comparable[] a, Comparator comparator) {
        final int N = a.length;
        int h = 1;

        while (h < N / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < N; i++) {

                for (int j = i; j >= h && isLess(a[j], a[j - h], comparator); j -= h) {

                    Comparable swap = a[j];
                    a[j] = a[j - h];
                    a[j - h] = swap;
                }
            }
            h /= 3;
        }
    }

    private boolean isLess(Comparable p, Comparable q, Comparator comparator) {
        if (comparator != null) {
            return comparator.compare(p, q) == -1;
        } else {
            return p.compareTo(q) == -1;
        }
    }


    private boolean is4CollinearPointsPatternRecognized() {
        Point p;
        List<Point> collinearSegment = new ArrayList<>(32);//TODO what is resizing complexity for array list impl?

        while (!origins.isEmpty()) {
            p = origins.poll();

            shellSort(points, p.SLOPE_ORDER);

            int i = 1;
            while (i + 1 < N - 1 && p.SLOPE_ORDER.compare(points[i], points[i + 1]) != 0) {
                i++;
            }

            if (i + 2 < N
                    && p.SLOPE_ORDER.compare(points[i], points[i + 2]) == 0) {

                collinearSegment.add(p);

                collinearSegment.add(points[i]);
                origins.remove(points[i]);

                //TODO find by binary search last index for optimization
                for (int k = i; k < N - 1 && p.SLOPE_ORDER.compare(points[k], points[k + 1]) == 0; k += 1) {
                    collinearSegment.add(points[k + 1]);
                    origins.remove(points[k + 1]);
                }
            }

            printoutSegment(collinearSegment);
        }
        return true;
    }

    private void printoutSegment(List<Point> collinearList) {

        if (collinearList != null && !collinearList.isEmpty()) {
            StringBuilder sb = new StringBuilder(collinearList.size() * 4);
            shellSort(collinearList);
            collinearList.get(0).drawTo(collinearList.get(collinearList.size() - 1));

            int index = 0;
            while (!collinearList.isEmpty() || collinearList.size() > 0) {
                sb.append(collinearList.remove(index));
                if (collinearList.size() > 0) {
                    sb.append(" -> ");
                }
            }

            if (!sb.toString().isEmpty()) {
                System.out.println(sb.toString());
            }
        }
    }

}
