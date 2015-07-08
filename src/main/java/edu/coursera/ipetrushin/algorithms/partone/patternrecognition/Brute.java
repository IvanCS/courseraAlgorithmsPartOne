package edu.coursera.ipetrushin.algorithms.partone.patternrecognition;

import edu.princeton.cs.introcs.StdDraw;

import java.io.BufferedReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Brute force  examines 4 points at a time
 * and checks whether they all lie on the same line segment,
 * printing out any such line segments to standard output
 * and drawing them using standard drawing.
 * <p/>
 * To check whether the 4 points p, q, r, and s are collinear,
 * check whether the slopes between p and q, between p and r,
 * and between p and s are all equal.
 */
public class Brute {

    /**
     * Points grid
     */
    private Point[] points;

    /**
     * Points grid's size
     */
    private int N;

   /* public Brute(Path p) throws Exception {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        initializePoints(p);
        shellSort(points);
    }*/

    public static void main(String[] args) throws Exception {
        if (args == null || args.length < 1) {
            throw new IllegalArgumentException("please specify name of input file as an argument");
        }
        String fileName = args[0];

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);


        Brute brute = buildBruteFromFile(fileName);
        brute.recognizePattern();
    }

    /**
     * Builds a {@link Brute} object from specified file
     *
     * @param fileName name of file containing points information
     * @return {@link Brute}
     * @throws Exception
     */
    private static Brute buildBruteFromFile(String fileName) throws Exception {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("specify file name!");
        }

        Path p = Paths.get(fileName);
        if (!p.isAbsolute()) {
            URL path = Brute.class.getClassLoader().getResource(fileName);
            if (path != null) {
                p = Paths.get(path.toURI());
            }
        }

        if (!Files.exists(p)) {
            throw new IllegalArgumentException("file for name : " + fileName + " doesn't exists");
        }

        Brute brute = new Brute();
        brute.initializePoints(p);
        brute.shellSort(brute.points);
        return brute;
    }

    /**
     * Initializes points grid from a file
     *
     * @param pathToFile
     * @throws Exception
     */
    private void initializePoints(Path pathToFile) throws Exception {
        try (BufferedReader reader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8);) {
            String line;
            String[] point;
            int i = 0;

            N = Integer.parseInt(reader.readLine());
            points = new Point[N];

            while (reader.ready()) {
                line = reader.readLine();
                if (line == null) {
                    throw new IllegalArgumentException("no points information provided in the input file : " + pathToFile.getFileName().toString());
                }

                line = line.trim();
                if (!line.isEmpty()) {
                    point = line.split("\\s+");
                    int x = Integer.parseInt(point[0]);
                    int y = Integer.parseInt(point[1]);

                    Point newPoint = new Point(x, y);
                    points[i++] = newPoint;
                    newPoint.draw();
                }
            }
        }
    }

    /**
     * Sorts  array of {@link Comparable} items according to Shell algorithm
     *
     * @param a array for sorting
     */
    private void shellSort(Comparable[] a) {
        final int N = a.length;
        int h = 1;
        while (h < N / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < N; i++) {

                for (int j = i; j >= h && a[j].compareTo(a[j - h]) < 0; j -= h) {
                    Comparable swap = a[j];
                    a[j] = a[j - h];
                    a[j - h] = swap;
                }
            }
            h /= 3;
        }
    }

    /**
     * Recognized 4-Collinear-Points pattern
     *
     * @return
     */
    private void recognizePattern() {
        Point p, q, s, r;
        StringBuilder sb = new StringBuilder(4 * 4);

        for (int i = 0; i < N - 3; i++) {
            p = points[i];
            for (int j = i + 1; j < N - 2; j++) {
                q = points[j];

                for (int k = j + 1; k < N - 1; k++) {
                    s = points[k];

                    for (int m = k + 1; m < N; m++) {
                        r = points[m];

                        if (isCollinear(p, q, s, r)) {
                            Point[] segment = new Point[]{p, q, s, r};
                            shellSort(segment);
                            segment[0].drawTo(segment[3]);

                            for (int index = 0; index < 3; index++) {
                                sb.append(segment[index] + " -> ");
                            }
                            sb.append(segment[3]);
                            System.out.println(sb.toString());
                            sb = new StringBuilder(4 * 4);

                        }
                    }
                }
            }

        }
    }

    /**
     * Checks whether specified 4 points are collinear.
     *
     * @param p
     * @param q
     * @param s
     * @param r
     * @return <tt>true</tt> if the points are collinear, <tt>false</tt> otherwise
     */
    private boolean isCollinear(Point p, Point q, Point s, Point r) {
        return (p.SLOPE_ORDER.compare(q, s) == 0
                && p.SLOPE_ORDER.compare(s, r) == 0);
    }
}
