package edu.coursera.ipetrushin.algorithms.partone.randomizedqueue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Iterator;

@RunWith(JUnit4.class)
public class TestDeque {

    Deque<Integer> deque;

    @Before
    public void init() {
        deque = new Deque<>();

        for (int i = 1; i <= 5; i++) {
            deque.addLast(i);
        }
    }

    @Test
    public void testAddFirst() {
        deque = new Deque<>();
        deque.addFirst(6);

        Assert.assertEquals((long) 6, (long) deque.iterator().next());

    }

    @Test
    public void testAddLast() {
        deque.addLast(6);
        Iterator<Integer> iterator = deque.iterator();
        for (int i = 1; i < deque.size(); i++) {
            iterator.next();
        }

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals((long) 6, (long) iterator.next());

    }

    @Test
    public void testRemoveLast() {
        int sizeBefore = deque.size();

        Integer last = deque.removeLast();
        Assert.assertEquals((long) 5, (long) last);

        int sizeAfter = deque.size();
        Assert.assertEquals(sizeBefore, sizeAfter + 1);
    }

    @Test
    public void testRemoveFirst() {
        int sizeBefore = deque.size();

        Integer first = deque.removeFirst();
        Assert.assertEquals((long) 1, (long) first);

        int sizeAfter = deque.size();
        Assert.assertEquals(sizeBefore, sizeAfter + 1);
    }
}
