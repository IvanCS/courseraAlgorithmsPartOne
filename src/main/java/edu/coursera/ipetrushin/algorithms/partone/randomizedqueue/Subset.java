package edu.coursera.ipetrushin.algorithms.partone.randomizedqueue;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

import java.util.Iterator;

/**
 * Write a client program Subset.java that takes a command-line integer k;
 * reads in a sequence of N strings from standard input using StdIn.readString(); and prints out exactly k of them, uniformly at random.
 * Each item from the sequence can be printed out at most once. You may assume that 0 ? k ? N,
 * where N is the number of string on standard input.
 * <p/>
 * <p/>
 * The running time of Subset must be linear in the size of the input.
 * You may use only a constant amount of memory plus either one Deque or RandomizedQueue object of maximum size at most N,
 * where N is the number of strings on standard input.
 * <p/>
 * (For an extra challenge, use only one Deque or RandomizedQueue object of maximum size at most k.)
 */
public class Subset {

    public static void main(String[] args) {
        if (args == null || args.length < 1 || Integer.parseInt(args[0]) <= 0) {
            throw new IllegalArgumentException("Number of random items 'k' must be specified!");
        }

        int k = Integer.parseInt(args[0]);

        String inputString = StdIn.readLine();
        String[] input = inputString.trim().split("\\s+");

        if (k > input.length) {
            throw new IllegalArgumentException("Number of random items 'k' can't be bigger than number of items!");
        }

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        for (String item : input) {
            randomizedQueue.enqueue(item);
        }

        Iterator<String> randomizedIterator = randomizedQueue.iterator();
        StringBuilder stringBuilder = new StringBuilder(randomizedQueue.size() * 2);
        int i = 1;

        while (k >= i++) {
            stringBuilder.append(randomizedIterator.next()).append(" ");
        }

        StdOut.println(stringBuilder.toString());


    }
}