package edu.coursera.ipetrushin.algorithms.partone.randomizedqueue;

import java.util.Iterator;
import java.util.Random;

/*

Corner cases.
    The order of two or more iterators to the same randomized queue must be mutually independent;
    each iterator must maintain its own random order.
    Throw a java.lang.NullPointerException if the client attempts to add a null item;
    throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty randomized queue;
    throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
    throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.

Performance requirements.
    Your randomized queue implementation must support each randomized queue operation (besides creating an iterator)
    in constant amortized time and use space proportional to the number of items currently in the queue.

    That is, any sequence of M randomized queue operations (starting from an empty queue) should take at most cM steps in the worst case,
     for some constant c. Additionally, your iterator implementation must support
     operations next() and hasNext() in constant worst-case time;
     and construction in linear time; you may use a linear amount of extra memory per iterator.

 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] nodes;
    private int headIndex;
    private int tailIndex;
    private int size;

    // private Iterator<Item> iterator;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        //iterator = new RandomizedQueueIterator<Item>();
    }

    // unit testing
    public static void main(String[] args) {

    }

    // is the queue empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            //TODO
        }

        size++;

        if (nodes == null) {
            nodes = (Item[]) new Object[size];
            headIndex = size - 1;
            tailIndex = headIndex;
            nodes[headIndex] = item;
            return;
        } else if (size > nodes.length) {
            //resize
            Item[] resizedNodes = (Item[]) new Object[nodes.length * 2];
            for (int i = 0; i < nodes.length; i++) {
                resizedNodes[i] = nodes[i];
            }
            nodes = resizedNodes;
        }

        nodes[++tailIndex] = item;

    }

    // remove and return a random item
    public Item dequeue() {

        if (size <= 0) {
            //TODO
        }

        Item itemAtHead = nodes[headIndex];
        --size;
        ++headIndex;

        if (size <= nodes.length / 2) {
            //resize
            Item[] resizedNodes = (Item[]) new Object[nodes.length / 2];
            for (int i = 0; i < resizedNodes.length; i++) {
                resizedNodes[i] = nodes[i + headIndex];
            }
            nodes = resizedNodes;
        }

        return itemAtHead;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (size <= 0) {
            //TODO
        }

        return new RandomizedQueueIterator<Item>(size).next();
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>(size);
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {

        private final int[] randomizedOrder;
        private int currentNodeIndex;

        private RandomizedQueueIterator(int size) {
            currentNodeIndex = 0;
            randomizedOrder = new int[size];

            buildRandomOrder();
        }


        private void buildRandomOrder() {
            Random random = new Random();
            for (int i = 0; i < randomizedOrder.length; i++) {
                int randomPosition = random.nextInt(i + 1);
                randomizedOrder[i] = randomPosition;
                randomizedOrder[randomPosition] = i;
            }
        }

        @Override
        public boolean hasNext() {
            return currentNodeIndex < randomizedOrder.length - 1;
        }

        @Override
        public Item next() {
            int randomIndex = randomizedOrder[currentNodeIndex++];
            return (Item) nodes[randomIndex];
        }

        @Override
        public void remove() {
            // TODO throw new  OperationNotSupportedException("");
        }
    }


}
