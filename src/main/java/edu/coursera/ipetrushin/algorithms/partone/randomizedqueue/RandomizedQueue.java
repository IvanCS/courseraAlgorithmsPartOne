package edu.coursera.ipetrushin.algorithms.partone.randomizedqueue;

import java.util.Iterator;
import java.util.NoSuchElementException;
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
    private int[] randomizedOrder;
    private int headIndex;
    private int tailIndex;
    private int size;


    /**
     * Constructs an empty randomized queue.
     */
    public RandomizedQueue() {
        size = 0;
    }

    // unit testing
    public static void main(String[] args) {

    }

    /**
     * Checks whether the RandomizedQueue instance is empty.
     *
     * @return <t>true</t> if empty, <t>false</t> otherwise.
     */
    public boolean isEmpty() {
        return nodes == null || size <= 0;
    }

    /**
     * Counts the number of items on the RandomizedQueue.
     *
     * @return size of the RandomizedQueue instance
     */
    public int size() {
        return size;
    }

    /**
     * Adds the item to the tail of the queue
     *
     * @param item to add
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("An item to add can't be null!");
        }

        int newSize = size + 1;

        if (isEmpty()) {
            nodes = (Item[]) new Object[newSize];
            headIndex = newSize - 1;
            tailIndex = headIndex;
            nodes[headIndex] = item;
            size = newSize;
            return;
        } else if (isQuoterFull()) {
            //resize
            Item[] resizedNodes = (Item[]) new Object[nodes.length * 2];


            for (int i = 0; i < nodes.length; i++) {
                    resizedNodes[i] = nodes[i];
            }
            nodes = resizedNodes;

            //rebuild random order
            randomizedOrder = null;
        }

        size = newSize;
        nodes[++tailIndex] = item;

    }


    /**
     * Removes and return a random item
     *
     * @return removed item
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue doesn't have any items!");
        }

        if (randomizedOrder == null) {
            randomizedOrder = buildRandomOrder(size);
        }

        int randomIndex = randomizedOrder[headIndex];
        Item itemAtHead = nodes[randomIndex];
        int newSize = size - 1;

        headIndex++;

        if (isQuoterEmpty() && newSize != 0) {
            //resize

            Item[] resizedNodes = (Item[]) new Object[nodes.length / 2];

            for (int i = 0; i < resizedNodes.length; i++) {
                if (i > headIndex || i == randomizedOrder[headIndex]) {
                    resizedNodes[i] = nodes[i];
                }
            }

            nodes = resizedNodes;
            headIndex = 0;

            //rebuild random order
            randomizedOrder = null;
        }

        tailIndex = newSize - 1;
        size = newSize;
        return itemAtHead;
    }

    private boolean isQuoterFull() {
        double percent = (double) nodes.length / 100;
        double currentCapacity = (double) size / percent / 100;

        return currentCapacity >= 0.75;
    }

    private boolean isQuoterEmpty() {

        double percent = (double) nodes.length / 100;
        double currentCapacity = (double) size / percent / 100;

        return currentCapacity <= 0.25;
    }


    /**
     * Returns (but do not removes) a random item
     *
     * @return sample item
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue doesn't have any items!");
        }

        if (randomizedOrder == null) {
            randomizedOrder = buildRandomOrder(size);
        }

        int randomIndex = randomizedOrder[headIndex];
        Item itemAtHead = nodes[randomIndex];
        return itemAtHead;
    }

    /**
     * Builds random order.
     */
    private int[] buildRandomOrder(int size) {
        int[] randomizedOrder = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int randomPosition = random.nextInt(i + 1);
            randomizedOrder[i] = randomizedOrder[randomPosition];
            randomizedOrder[randomPosition] = i;
        }

        return randomizedOrder;
    }


    /**
     * Gets an independent iterator over items in random order
     *
     * @return iterator implementation
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>(size);
    }

    /**
     * Random iterator implementation for the {@link RandomizedQueue}
     *
     * @param <Item> type of items
     */
    private class RandomizedQueueIterator<Item> implements Iterator<Item> {

        private final int[] iteratorRandomizedOrder;
        private int currentNodeIndex;

        private RandomizedQueueIterator(int size) {
            currentNodeIndex = 0;
            iteratorRandomizedOrder = buildRandomOrder(size);

        }

        @Override
        public boolean hasNext() {
            return !isEmpty() && currentNodeIndex < iteratorRandomizedOrder.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("The end of the Deque has been already reached!");
            }

            int randomIndex = iteratorRandomizedOrder[currentNodeIndex++];
            return (Item) nodes[randomIndex];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("#remove() is not supported for the RandomizedQueue iterator!");
        }
    }


}
