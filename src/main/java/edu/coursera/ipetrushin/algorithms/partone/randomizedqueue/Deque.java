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
//TODO implement by Node DS
public class Deque<Item> implements Iterable<Item> {

    int size;
    private Node<Item> head;
    private Node<Item> tail;

    // construct an empty deque
    public Deque() {
        size = 0;
    }

    // unit testing
    public static void main(String[] args) {

    }


    private int incrementAndGetSize() {
        return ++size;
    }

    private int decrementAndGetSize() {
        return --size;
    }


    // is the deque empty?
    public boolean isEmpty() {
        return (size() == 0);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    //Throw a java.lang.NullPointerException if the client attempts to add a null item;
    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Can't insert a null to deque!");
        }

        Node<Item> newItem = new Node<>();
        newItem.setNodeValue(item);

        incrementAndGetSize();

        if (head == null) {
            head = newItem;
            tail = newItem;
            return;
        }

        newItem.setLast(head);
        head.setNext(newItem);
        head = newItem;

    }

    // add the item to the end
    public void addLast(Item item) {
        Node<Item> newItem = new Node<>();
        newItem.setNodeValue(item);

        incrementAndGetSize();

        if (tail == null) {
            tail = newItem;
            head = newItem;
            return;
        }

        tail.setLast(newItem);
        newItem.setNext(tail);
        tail = newItem;


    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            new NoSuchElementException("Can't remove an item from empty deque!");
        }

        Item removedValue = head.getNodeValue();
        head.getLast().setNext(null);
        head = head.getLast();

        decrementAndGetSize();
        return removedValue;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            new NoSuchElementException("Can't remove an item from empty deque!");
        }

        Item removedValue = tail.getNodeValue();
        tail.getNext().setLast(null);
        tail = tail.getNext();

        decrementAndGetSize();
        return removedValue;
    }


    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator<>();
    }

    private class Node<Item> {
        Item nodeValue;
        Node<Item> next;
        Node<Item> last;

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

    private class DequeIterator<Item> implements Iterator<Item> {

        private Node<Item> currentNode;

        public DequeIterator() {
            currentNode = (Node<Item>) head;
        }

        @Override
        public boolean hasNext() {
            if (currentNode == null) {
                return false;
            }
            return currentNode != null;
        }

        @Override
        public Item next() {
            if (currentNode == null) {
                throw new NoSuchElementException("The end of the Deque has been already reached!");
            }

            currentNode = (Node<Item>) currentNode.getLast();
            return (Item) currentNode.getNodeValue();
        }

        @Override
        public void remove() {
            //TODO throw exc
            throw new UnsupportedOperationException("Remove is not supported for the Deque iterator!");

          /*  if (currentNode == head) {
                removeFirst();
            } else if (currentNode == tail) {
                removeLast();
            } else {
                Node<Item> last = currentNode.getLast();
                Node<Item> next = currentNode.getNext();
                last.setNext(next);
                next.setLast(last);
            }*/
        }
    }
}
