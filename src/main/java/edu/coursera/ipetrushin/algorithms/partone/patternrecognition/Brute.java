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

    private Point[] points;
    private int N;


    public Brute(Path p) throws Exception {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        buildConvexHull(p);
        shellSort(points);
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

        Brute brute = new Brute(p);
        brute.is4CollinearPointsPatternRecognized();

    }

    private void buildConvexHull(Path pathToFile) throws Exception {
        //TODO read form file

        try (BufferedReader reader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8);) {
            String line;
            String[] point;
            int i = 0;

            N = Integer.parseInt(reader.readLine());
            points = new Point[N];

            while (reader.ready()) {
                line = reader.readLine();
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

    private void shellSort(Comparable[] a) {
        final int N = a.length;
        int h = 1;
        while (h < N / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < N; i++) {

                for (int j = i; j >= h && a[j].compareTo(a[j - h]) == -1; j -= h) {
                    Comparable swap = a[j];
                    a[j] = a[j - h];
                    a[j - h] = swap;
                }
            }
            h /= 3;
        }
    }


    private boolean is4CollinearPointsPatternRecognized() {
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
                            sb = new StringBuilder(4*4);

                        }
                    }
                }
            }

        }

        return true;
    }

    private boolean isCollinear(Point p, Point q, Point s, Point r) {
        return (p.SLOPE_ORDER.compare(q, s) == 0
                && p.SLOPE_ORDER.compare(s, r) == 0);
    }


}
