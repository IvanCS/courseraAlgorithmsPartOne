package edu.coursera.ipetrushin.algorithms.partone.randomizedqueue;

import java.util.Iterator;

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

    }

    // unit testing
    public static void main(String[] args) {

    }

    public int incrementAndGetSize() {
        return ++size;
    }

    public int decrementAndGetSize() {
        return --size;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (head == null);
    }

    // return the number of items on the deque
    public int size() {
        return 0;
    }

    //Throw a java.lang.NullPointerException if the client attempts to add a null item;
    // add the item to the front
    public void addFirst(Item item) {
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

    //j java.util.NoSuchElementException if the client attempts to remove an item from an empty deque;
    // remove and return the item from the front
    public Item removeFirst() {
        Item removedValue = head.getNodeValue();
        head.getLast().setNext(null);
        head = head.getLast();

        decrementAndGetSize();
        return removedValue;
    }

    // remove and return the item from the end
    public Item removeLast() {
        Item removedValue = tail.getNodeValue();
        tail.getNext().setLast(null);
        tail = tail.getNext();

        decrementAndGetSize();
        return removedValue;
    }

    // throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
    // throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.
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
            currentNode = (Node<Item>) tail;
        }

        @Override
        public boolean hasNext() {
            if (currentNode == null) {
                return false; //TODO throw exc
            }
            return currentNode != null;
        }

        @Override
        public Item next() {
            if (tail == null) {
                return null; //TODO throw exc
            }
            currentNode = (Node<Item>) currentNode.getNext();
            return (Item) currentNode.getNodeValue();
        }

        @Override
        public void remove() {
            //TODO throw exc

            if (currentNode == head) {
                removeFirst();
            } else if (currentNode == tail) {
                removeLast();
            } else {
                Node<Item> last = currentNode.getLast();
                Node<Item> next = currentNode.getNext();
                last.setNext(next);
                next.setLast(last);
            }
        }
    }
}
