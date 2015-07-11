package edu.coursera.ipetrushin.algorithms.partone.randomizedqueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Performance requirements.
 * Your deque implementation must support each deque operation in constant worst-case time
 * and use space proportional to the number of items currently in the deque.
 * <p/>
 * Additionally, your iterator implementation must support each operation
 * (including construction) in constant worst-case time.
 */
public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node<Item> head;
    private Node<Item> tail;

    /**
     * Constructs an empty deque.
     */
    public Deque() {
        size = 0;

    }

    // unit testing
    public static void main(String[] args) {

    }

    /**
     * Increments and gets size of the Deque instance.
     */
    private int incrementAndGetSize() {
        return ++size;
    }

    /**
     * Decrements and gets size of the Deque instance.
     */
    private int decrementAndGetSize() {
        return --size;
    }


    /**
     * Checks whether the Deque instance is empty.
     *
     * @return <t>true</t> if empty, <t>false</t> otherwise.
     */
    public boolean isEmpty() {
        return (size() <= 0);
    }


    /**
     * Counts the number of items on the Deque.
     *
     * @return size of the deque instance
     */
    public int size() {
        return size;
    }

    /**
     * Adds the item to the front.
     *
     * @param item to add
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Can't insert a null to the deque!");
        }

        Node<Item> newItem = new Node<>();
        newItem.setNodeValue(item);

        incrementAndGetSize();

        if (head == null) {
            head = newItem;
            tail = newItem;
            return;
        }

        newItem.setNext(head);
        head.setLast(newItem);
        head = newItem;
    }

    /**
     * Adds the item to the end.
     *
     * @param item to add
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("Can't insert a null to the deque!");
        }

        Node<Item> newItem = new Node<>();
        newItem.setNodeValue(item);

        incrementAndGetSize();

        if (tail == null) {
            tail = newItem;
            head = newItem;
            return;
        }

        tail.setNext(newItem);
        newItem.setLast(tail);
        tail = newItem;
    }


    /**
     * Removes and return the item from the front
     *
     * @return removed item
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can't remove an item from an empty deque!");
        }

        Item removedValue = head.getNodeValue();

        if (head == tail) {
            head = null;
            tail = head;
        } else {
            head.getNext().setLast(null);
            head = head.getNext();
        }

        decrementAndGetSize();
        return removedValue;
    }

    /**
     * Removes and return the item from the end
     *
     * @return removed item
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can't remove an item from an empty deque!");
        }

        Item removedValue = tail.getNodeValue();

        if (tail == head) {
            tail = null;
            head = tail;
        } else {
            tail.getLast().setNext(null);
            tail = tail.getLast();
        }

        decrementAndGetSize();
        return removedValue;
    }


    /**
     * Gets an iterator over items in order from front to end
     *
     * @return iterator implementation for the Deque
     */
    public Iterator<Item> iterator() {
        return new DequeIterator<>();
    }

    /**
     * The Node class is representation of internal DS for Deque implementation
     *
     * @param <Item> type of the Node value
     */
    private class Node<Item> {
        private Item nodeValue;
        private Node<Item> next;
        private Node<Item> last;

        public Node<Item> getNext() {
            return next;
        }

        public void setNext(Node<Item> next) {
            this.next = next;
        }

        public Item getNodeValue() {
            return nodeValue;
        }

        public void setNodeValue(Item nodeValue) {
            this.nodeValue = nodeValue;
        }

        public Node<Item> getLast() {
            return last;
        }

        public void setLast(Node<Item> last) {
            this.last = last;
        }

    }

    /**
     * Implementation of an Iterator for the Deque
     *
     * @param <Item> type of the items
     */
    private class DequeIterator<Item> implements Iterator<Item> {

        private Node<Item> currentNode;

        public DequeIterator() {
            currentNode = new Node<>();
            currentNode.setNext((Node<Item>) head);
        }

        @Override
        public boolean hasNext() {
            if (currentNode == null) {
                return false;
            }
            return currentNode.getNext() != null;
        }

        @Override
        public Item next() {
            if (currentNode == null || !hasNext()) {
                throw new NoSuchElementException("The end of the Deque has been already reached!");
            }
            currentNode = currentNode.getNext();
            return currentNode.getNodeValue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("#remove() is not supported for the Deque iterator!");
        }
    }
}
