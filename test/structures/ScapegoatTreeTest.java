package structures;

import static org.junit.Assert.*;
import java.util.Iterator;


import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.rules.Timeout;

public class ScapegoatTreeTest {

	private BSTInterface<Integer> tree;
	private ScapegoatTree<Integer> nineNodeTree;
	private ScapegoatTree<Integer> unbalancedNineNodeTree;
	
	@Rule
    public Timeout timeout = new Timeout(1L, TimeUnit.SECONDS);
	
	@Before
	public void before() {
		tree = new ScapegoatTree<Integer>();
		nineNodeTree = new ScapegoatTree<Integer>();
		nineNodeTree.add(5);
		nineNodeTree.add(6);
		nineNodeTree.add(8);
		nineNodeTree.add(7);
		nineNodeTree.add(9);
		nineNodeTree.add(3);
		nineNodeTree.add(4);
		nineNodeTree.add(2);
		nineNodeTree.add(1);
		unbalancedNineNodeTree = new ScapegoatTree<Integer>();
		unbalancedNineNodeTree.add(5);
		unbalancedNineNodeTree.add(6);
		unbalancedNineNodeTree.add(7);
		unbalancedNineNodeTree.add(8);
		unbalancedNineNodeTree.add(9);
		unbalancedNineNodeTree.add(3);
		unbalancedNineNodeTree.add(4);
		unbalancedNineNodeTree.add(2);
		unbalancedNineNodeTree.add(1);
	}
	
	@Test
	public void testAdd() {
		//System.out.println("Start testAdd");
		tree.add(1);
		tree.add(2);
		tree.add(3);
		tree.add(4);
		assertEquals(3, tree.height());
		tree.add(5);
		//System.out.println("testAdd before error");
		assertEquals(3, tree.height());
		//System.out.println("End testAdd");
		tree.contains(1);
		tree.contains(2);
		tree.contains(3);
		tree.contains(4);
		tree.contains(5);
		}
	
	@Test 
	public void testRemove() {
		for (int i: new int[] {3, 1, 5, 0, 4, 2, 6} ) {
			tree.add(i);
		}

		for (int i: new int[] {1, 2, 0, 4}) {
			tree.remove(i);
		}
		
		BSTInterface<Integer> smallTree = new BinarySearchTree<Integer>();
		smallTree.add(5);
		smallTree.add(3);
		smallTree.add(6);
		
		assertTrue(tree.equals(smallTree));
	}
	
	@Test
	public void testSubtreeInOrderIterator(){
		Iterator<Integer> iter = nineNodeTree.subtreeInOrderIterator(nineNodeTree.getRoot().getRight());
		int i = 6;
		while(iter.hasNext()){
			assertEquals((Integer)i, iter.next());
			i++;
		}
	}
	
	/*@Test
	public void testBalanceSubtree(){
		assertFalse(nineNodeTree.equals(unbalancedNineNodeTree));
		unbalancedNineNodeTree.balanceSubtree(unbalancedNineNodeTree.getRoot().getRight().getRight(), unbalancedNineNodeTree.getRoot().getRight());
		assertTrue(nineNodeTree.equals(unbalancedNineNodeTree));
		Iterator<Integer> iter = unbalancedNineNodeTree.preorderIterator();
		assertEquals(iter.next(), (Integer)5);
		assertEquals(iter.next(), (Integer)3);
		assertEquals(iter.next(), (Integer)2);
		assertEquals(iter.next(), (Integer)1);
		assertEquals(iter.next(), (Integer)4);
		assertEquals(iter.next(), (Integer)6);
		assertEquals(iter.next(), (Integer)8);
		assertEquals(iter.next(), (Integer)7);
		assertEquals(iter.next(), (Integer)9);
		//assertTrue(nineNodeTree.equals(unbalancedNineNodeTree));
	}*/
}
