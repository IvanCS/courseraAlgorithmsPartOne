package edu.coursera.ipetrushin.algorithms.partone.randomizedqueue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Iterator;

@RunWith(JUnit4.class)
public class TestRandomizeQueue {

    private RandomizedQueue<Integer> rq;

    @Before
    public void init(){
        rq = new RandomizedQueue<>();

        for(int i = 1 ; i <= 5; i++){
            rq.enqueue(i);
        }
    }

    @Test
    public void testEnqueue(){
        int sizeBefore = rq.size();

        rq.enqueue(6);

        int sizeAfter = rq.size();
        Assert.assertEquals(sizeBefore, sizeAfter - 1);

        Iterator<Integer> iterator = rq.iterator();
        while (iterator.hasNext()){
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
        for(Integer item: rq){
           System.out.print(item + " ");
        }
    }


    @Test
    public void testEnqueue2(){
        rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);

        int item;
        while(true){
            item = rq.dequeue();
            if(item== 2){
                break;
            }
        }

        Assert.assertEquals(item,2);
    }

    @Test
    public void testEnqueueDeque(){
        rq = new RandomizedQueue<>();

        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
        rq.enqueue(7);
        rq.enqueue(8);
        Assert.assertNotEquals((Integer) 1, rq.dequeue());
        rq.enqueue(9);
    }

    @Test
    public void testEnqueueDeque2(){
        rq = new RandomizedQueue<>();
        rq.enqueue(1);
        Assert.assertEquals((Integer) 1, rq.dequeue());
        rq.enqueue(2);

        Assert.assertEquals(1, rq.size());
    }

    @Test
    public void testRandomDeque(){
        rq = new RandomizedQueue<>();
        rq.enqueue(341);
        rq.enqueue(485);
        rq.dequeue();    // ==> 341
        rq.enqueue(64);
        rq.isEmpty(); //    ==> false
        rq.enqueue(16);
        rq.isEmpty(); //    ==> false
        rq.isEmpty();  //   ==> false
        rq.enqueue(129);
        rq.dequeue();


        Assert.assertEquals(3, rq.size());
    }

    @Test
    public void test4(){
        rq = new RandomizedQueue<>();

        Assert.assertTrue(rq.isEmpty());
        rq.isEmpty();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.dequeue(); //1
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
        rq.enqueue(7);
        rq.enqueue(8);
        rq.enqueue(9);
        rq.enqueue(10);
        rq.enqueue(11);
        rq.enqueue(12);
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(2);
        rq.enqueue(2);
        rq.enqueue(2);
        rq.enqueue(2);
        rq.dequeue();rq.dequeue();
        rq.dequeue();rq.dequeue();
        rq.dequeue();rq.dequeue();
        rq.dequeue();rq.dequeue();

        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();

    }


    @Test
    public void testSample(){
        rq = new RandomizedQueue<>();

        rq.isEmpty();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        int s =  rq.sample(); //1
        Assert.assertEquals(s,(int) rq.sample());
        Assert.assertEquals(s,(int) rq.sample());
        Assert.assertEquals(s,(int) rq.sample());
        Assert.assertEquals(s,(int) rq.sample());
        Assert.assertEquals(s,(int) rq.sample());
        Assert.assertEquals(s,(int) rq.sample());


    }
}
