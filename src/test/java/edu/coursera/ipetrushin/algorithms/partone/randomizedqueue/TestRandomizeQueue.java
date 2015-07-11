package edu.coursera.ipetrushin.algorithms.partone.randomizedqueue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Iterator;

@RunWith(JUnit4.class)
public class TestRandomizeQueue {

    private RandomizedQueue<Integer> randomizedQueue;

    @Before
    public void init(){
        randomizedQueue = new RandomizedQueue<>();

        for(int i = 1 ; i <= 5; i++){
            randomizedQueue.enqueue(i);
        }
    }

    @Test
    public void testEnqueue(){
        int sizeBefore = randomizedQueue.size();

        randomizedQueue.enqueue(6);

        int sizeAfter = randomizedQueue.size();
        Assert.assertEquals(sizeBefore,sizeAfter-1);

        Iterator<Integer> iterator = randomizedQueue.iterator();
        while (iterator.hasNext()){
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
        for(Integer item: randomizedQueue){
           System.out.print(item + " ");
        }
    }

}
