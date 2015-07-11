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
        return (size == 0);
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

    /**
     * Removes and return a random item
     *
     * @return removed item
     */
    public Item dequeue() {
        if (size <= 0) {
            throw new NoSuchElementException("The queue doesn't have any items!");
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


    /**
     * Returns (but do not removes) a random item
     *
     * @return sample item
     */
    public Item sample() {
        if (size <= 0) {
            throw new NoSuchElementException("The queue doesn't have any items!");
        }

        return new RandomizedQueueIterator<Item>(size).next();
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

        private final int[] randomizedOrder;
        private int currentNodeIndex;

        private RandomizedQueueIterator(int size) {
            currentNodeIndex = 0;
            randomizedOrder = new int[size];
            buildRandomOrder();
        }

        /**
         * Builds random order.
         */
        private void buildRandomOrder() {
            Random random = new Random();
            for (int i = 0; i < randomizedOrder.length; i++) {
                int randomPosition = random.nextInt(i + 1);
                randomizedOrder[i] = randomizedOrder[randomPosition];
                randomizedOrder[randomPosition] = i;
            }
        }

        @Override
        public boolean hasNext() {
            return currentNodeIndex < randomizedOrder.length;
        }

        @Override
        public Item next() {
            int randomIndex = randomizedOrder[currentNodeIndex++];
            return (Item) nodes[randomIndex];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("#remove() is not supported for the RandomizedQueue iterator!");
        }
    }


}
