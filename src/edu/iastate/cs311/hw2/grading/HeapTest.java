 package edu.iastate.cs311.hw2.grading;

// Author: Xiaoqiu Huang

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Random;

import edu.iastate.cs311.hw2.Heap;
import edu.iastate.cs311.hw2.solution.HeapSolution;

@Total(value = 90)
public class HeapTest {
        private Heap<Integer>  one;
        private Heap<Integer>  heap, heaptwo, heapthree;
        private HeapSolution<Integer>  answerheap, answerheaptwo, answerheapthree;
        private Heap<String>  strheap, strheapone, strheaptwo;
        private HeapSolution<String>  answerstrheap, answerstrheapone, answerstrheaptwo;
        private int heapsize;
        private Random rand;
        private int upperbound = 1000000;

	@Before
	public void setUp() {

                one = new Heap<Integer>();
                one.add(10);
                
                rand = new Random();
                heapsize = 10000;
                answerheap = new HeapSolution<Integer>();
                heap = new Heap<Integer>();
                for ( int j = 0; j < heapsize; j++ )
                  { int num = rand.nextInt(upperbound);
                    heap.add(num);
                    answerheap.add(num);
                  }

                answerheaptwo = new HeapSolution<Integer>();
                heaptwo = new Heap<Integer>();
                for ( int j = 0; j < heapsize; j++ )
                  { int num = rand.nextInt(upperbound);
                    heaptwo.add(num);
                    answerheaptwo.add(num);
                  }

                answerheapthree = new HeapSolution<Integer>();
                heapthree = new Heap<Integer>();
                for ( int j = 0; j < heapsize; j++ )
                  { int num = rand.nextInt(upperbound);
                    heapthree.add(num);
                    answerheapthree.add(num);
                  }
                
                answerstrheap = new HeapSolution<String>();
                strheap = new Heap<String>();
	        fillStrHeap(strheap, answerstrheap, heapsize);

                answerstrheapone = new HeapSolution<String>();
                strheapone = new Heap<String>();
	        fillStrHeap(strheapone, answerstrheapone, heapsize);

                answerstrheaptwo = new HeapSolution<String>();
                strheaptwo = new Heap<String>();
	        fillStrHeap(strheaptwo, answerstrheaptwo, heapsize);
	}

	@After
	public void tearDown() {
	//	this.clone = null;
	}

	@Test(expected = NoSuchElementException.class)
	@Points(value = 5)
	public void nullgetLastInternalTest1() {
		one.getLastInternal();
	}

	@Test(expected = NoSuchElementException.class)
	@Points(value = 5)
	public void nulltrimEveryLeafTest1() {
		one.trimEveryLeaf();
	}

	@Test(timeout = 100)
	@Points(value = 15)
	public void getMinTestInt() {
		assertThat("Failed getMin() on large integer heap", heap.getMin(),
				equalTo(answerheap.getMin() ));
	}

    @Test(timeout = 100)
    @Points(value = 15)
    public void removeMinTest1() {
	    heap.removeMin();
	    answerheap.removeMin();
        assertThat("removeMin() does not correctly update the minimum value", heap.getMin(),
                equalTo(answerheap.getMin() ));
    }

    @Test(timeout = 100)
    @Points(value = 15)
    public void removeMinTest2() {
	    int NUMBER_OF_REMOVALS = answerheap.size()/ 20;
        for (int i = 0; i < NUMBER_OF_REMOVALS; i++) {
            heap.removeMin();
            answerheap.removeMin();
            assertThat("removeMin() does not correctly update the minimum value", heap.getMin(),
                    equalTo(answerheap.getMin() ));
        }
    }

	@Test(timeout = 100)
	@Points(value = 5)
	public void getMinTestStr() {
		assertThat("Failed getMin() on large string heap", strheap.getMin(),
				equalTo(answerstrheap.getMin() ));
	}

	@Test(timeout = 100)
	@Points(value = 15)
	public void getLastInternalTestInt() {
		assertThat("Failed getLastInternal() on large integer heap", heaptwo.getLastInternal(),
				equalTo(answerheaptwo.getLastInternal() ));
	}

	@Test(timeout = 100)
	@Points(value = 5)
	public void getLastInternalTestStr() {
		assertThat("Failed getLastInternal() on large string heap", strheapone.getLastInternal(),
	  			equalTo(answerstrheapone.getLastInternal() ));
	}

	@Test(timeout = 10000)
	@Points(value = 15)
	public void trimEveryLeafTestInt() {
                heapthree.trimEveryLeaf();
                answerheapthree.trimEveryLeaf();
		assertThat("Failed trimEveryLeaf() on large integer heap", heapthree.getLastInternal(),
				equalTo(answerheapthree.getLastInternal() ));
	}

	@Test(timeout = 10000)
	@Points(value = 5)
	public void trimEveryLeafTestStr() {
                strheaptwo.trimEveryLeaf();
                answerstrheaptwo.trimEveryLeaf();
		assertThat("Failed trimEveryLeaf() on larg string heap", strheaptwo.getLastInternal(),
				equalTo(answerstrheaptwo.getLastInternal() ));
	}

	private void fillStrHeap(Heap<String> strheap, HeapSolution<String> answerstrheap, int heapsize) {
                Random rand = new Random();
                int clen = 10;
                char[] base = { 'A', 'C', 'G', 'T' };
                char[] carr = new char[clen];
                for ( int j = 0; j < heapsize; j++ )
                 { for ( int k = 0; k < clen; k++ )
                     { int num = rand.nextInt(4);
                       carr[k] = base[num];
                     }
                    String str = new String(carr);
                    answerstrheap.add(str);
                    strheap.add(str);
                 }
        }
}
