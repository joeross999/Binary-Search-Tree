package structures;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.rules.Timeout;

public class BinarySearchTreeTest {

	private BSTInterface<Integer> emptyTree;
	private BSTInterface<String> oneNodeTree;
	private BSTInterface<Integer> twoNodeTree;
	private BSTInterface<Integer> threeNodeTree;
	private BSTInterface<Integer> fourNodeTree;
	private BSTInterface<Integer> leftFourNodeTree;
	private BSTInterface<Integer> rightFourNodeTree;
	private BSTInterface<Integer> nineNodeTree;
	private static final String FOO = "foo";
	private static final Integer One = new Integer(1);

	@Rule
    public Timeout timeout = new Timeout(1L, TimeUnit.SECONDS);
	
	@Before
	public void before() {
		emptyTree = new BinarySearchTree<Integer>();
		oneNodeTree = new BinarySearchTree<String>();
		oneNodeTree.add(FOO);
		twoNodeTree = new BinarySearchTree<Integer>();
		twoNodeTree.add(1);
		twoNodeTree.add(2);
		threeNodeTree = new BinarySearchTree<Integer>();
		threeNodeTree.add(2);
		threeNodeTree.add(1);
		threeNodeTree.add(3);
		fourNodeTree = new BinarySearchTree<Integer>();
		fourNodeTree.add(3);
		fourNodeTree.add(1);
		fourNodeTree.add(2);
		fourNodeTree.add(4);
		leftFourNodeTree = new BinarySearchTree<Integer>();
		leftFourNodeTree.add(4);
		leftFourNodeTree.add(3);
		leftFourNodeTree.add(2);
		leftFourNodeTree.add(1);
		rightFourNodeTree = new BinarySearchTree<Integer>();
		rightFourNodeTree.add(1);
		rightFourNodeTree.add(2);
		rightFourNodeTree.add(3);
		rightFourNodeTree.add(4);
		nineNodeTree = new BinarySearchTree<Integer>();
		nineNodeTree.add(5);
		nineNodeTree.add(6);
		nineNodeTree.add(8);
		nineNodeTree.add(7);
		nineNodeTree.add(9);
		nineNodeTree.add(3);
		nineNodeTree.add(4);
		nineNodeTree.add(2);
		nineNodeTree.add(1);
		
	}
	
	@Test
	public void testEmpty() {
		assertTrue(emptyTree.isEmpty());
	}

	@Test
	public void testNotEmpty() {
		assertFalse(oneNodeTree.isEmpty());
	}

	@Test
	public void testSize() {
		assertEquals(0, emptyTree.size());
		assertEquals(1, oneNodeTree.size());
	}
	
	@Test
	public void testContains() {
		//check one node tree
		assertTrue(oneNodeTree.contains(FOO));
		//check empty tree
		assertFalse(emptyTree.contains(1));
		//check for elements not in the tree
		assertFalse(twoNodeTree.contains(3));
		assertFalse(threeNodeTree.contains(4));
		assertFalse(fourNodeTree.contains(5));
		//check for elements that are in the tree
		assertTrue(twoNodeTree.contains(1));
		assertTrue(twoNodeTree.contains(2));
		assertTrue(threeNodeTree.contains(1));
		assertTrue(threeNodeTree.contains(2));
		assertTrue(threeNodeTree.contains(3));
		assertTrue(fourNodeTree.contains(1));
		assertTrue(fourNodeTree.contains(2));
		assertTrue(fourNodeTree.contains(3));
		assertTrue(fourNodeTree.contains(4));
	}
	
	//test to make sure contains throws the proper exception
		@Test(expected=NullPointerException.class)
		public void testNPEOnContains(){
			fourNodeTree.contains(null);
		}
	
	@Test
	public void testRemove() {
		assertFalse(emptyTree.remove(0));
	}
	
	@Test
	public void testGet() {
		//check for null return on empty tree
		assertNull(emptyTree.get(One));
		//check for both item and null on one node tree
		assertEquals(FOO, oneNodeTree.get(FOO));
		assertNull(oneNodeTree.get("Not There"));
		//check for items that are in the tree
		assertEquals((Integer)1,twoNodeTree.get(1));
		assertEquals((Integer)2,twoNodeTree.get(2));
		assertEquals((Integer)2,threeNodeTree.get(2));
		assertEquals((Integer)1,threeNodeTree.get(1));
		assertEquals((Integer)3,threeNodeTree.get(3));
		assertEquals((Integer)3,fourNodeTree.get(3));
		assertEquals((Integer)1,fourNodeTree.get(1));
		assertEquals((Integer)2,fourNodeTree.get(2));
		assertEquals((Integer)4,fourNodeTree.get(4));
		//check for items that are not in the tree
		assertNull(twoNodeTree.get(3));
		assertNull(threeNodeTree.get(4));
		assertNull(fourNodeTree.get(5));
	}
	
	//test to make sure get throws the proper exception
	@Test(expected=NullPointerException.class)
	public void testNPEOnGet(){
		assertEquals((Integer)1,twoNodeTree.get(null));
	}
	
	@Test
	public void testAdd() {
		emptyTree.add(1);
		assertFalse(emptyTree.isEmpty());
		assertEquals(1, emptyTree.size());
	}
	
	@Test
	public void testGetMinimum() {
		assertEquals(null, emptyTree.getMinimum());
		assertEquals(FOO, oneNodeTree.getMinimum());
		assertEquals((Integer)1, twoNodeTree.getMinimum());
		assertEquals((Integer)1, threeNodeTree.getMinimum());
		assertEquals((Integer)1, fourNodeTree.getMinimum());
		assertEquals((Integer)1, leftFourNodeTree.getMinimum());
		assertEquals((Integer)1, rightFourNodeTree.getMinimum());
	}

	@Test
	public void testGetMaximum() {
		assertEquals(null, emptyTree.getMaximum());
		assertEquals(FOO, oneNodeTree.getMaximum());
		assertEquals((Integer)2, twoNodeTree.getMaximum());
		assertEquals((Integer)3, threeNodeTree.getMaximum());
		assertEquals((Integer)4, fourNodeTree.getMaximum());
		assertEquals((Integer)4, leftFourNodeTree.getMaximum());
		assertEquals((Integer)4, rightFourNodeTree.getMaximum());
		}

	@Test
	public void testHeight() {
		assertEquals(-1, emptyTree.height());
		assertEquals(0, oneNodeTree.height());
		assertEquals(1, twoNodeTree.height());
		assertEquals(1, threeNodeTree.height());
		assertEquals(2, fourNodeTree.height());
		assertEquals(3, leftFourNodeTree.height());
		assertEquals(3, rightFourNodeTree.height());
	}
	
	@Test
	public void testPreorderIterator() {
		Iterator<String> i = oneNodeTree.preorderIterator();
		while (i.hasNext()) {
			assertEquals(FOO, i.next());			
		}
		Iterator<Integer> iter = nineNodeTree.preorderIterator();
		assertEquals((Integer)5, iter.next());
		assertEquals((Integer)3, iter.next());
		assertEquals((Integer)2, iter.next());
		assertEquals((Integer)1, iter.next());
		assertEquals((Integer)4, iter.next());
		assertEquals((Integer)6, iter.next());
		assertEquals((Integer)8, iter.next());
		assertEquals((Integer)7, iter.next());
		assertEquals((Integer)9, iter.next());
	}

	@Test
	public void testInorderIterator() {
		Iterator<String> i = oneNodeTree.inorderIterator();
		while (i.hasNext()) {
			assertEquals(FOO, i.next());			
		}
		Iterator<Integer> iter = nineNodeTree.inorderIterator();
		assertEquals((Integer)1, iter.next());
		assertEquals((Integer)2, iter.next());
		assertEquals((Integer)3, iter.next());
		assertEquals((Integer)4, iter.next());
		assertEquals((Integer)5, iter.next());
		assertEquals((Integer)6, iter.next());
		assertEquals((Integer)7, iter.next());
		assertEquals((Integer)8, iter.next());
		assertEquals((Integer)9, iter.next());
	}

	@Test
	public void testPostorderIterator() {
		Iterator<Integer> i = emptyTree.postorderIterator();
		assertFalse(i.hasNext());
		Iterator<Integer> iter = nineNodeTree.postorderIterator();
		assertEquals((Integer)1, iter.next());
		assertEquals((Integer)2, iter.next());
		assertEquals((Integer)4, iter.next());
		assertEquals((Integer)3, iter.next());
		assertEquals((Integer)7, iter.next());
		assertEquals((Integer)9, iter.next());
		assertEquals((Integer)8, iter.next());
		assertEquals((Integer)6, iter.next());
		assertEquals((Integer)5, iter.next());
	}
	
	@Test
	public void testEquals() {
		BSTInterface<String> tree = new BinarySearchTree<String>();
		assertFalse(oneNodeTree.equals(tree));
		tree.add(new String("foo"));
		assertTrue(oneNodeTree.equals(tree));
		assertFalse(fourNodeTree.equals(leftFourNodeTree));
	}
	
	@Test
	public void testSameValues() {
		BSTInterface<Integer> tree = new BinarySearchTree<Integer>();
		assertTrue(emptyTree.sameValues(tree));
		
		emptyTree.add(1);
		emptyTree.add(2);
		
		tree.add(2);
		tree.add(1);
		
		assertTrue(emptyTree.sameValues(tree));
		assertTrue(leftFourNodeTree.sameValues(rightFourNodeTree));
	}
	
	@Test 
	public void testIsBalanced() {
		assertTrue(emptyTree.isBalanced());
		emptyTree.add(1);
		assertTrue(emptyTree.isBalanced());
		emptyTree.add(2);
		assertTrue(emptyTree.isBalanced());
		emptyTree.add(3);
		assertFalse(emptyTree.isBalanced());
		assertTrue(twoNodeTree.isBalanced());
		assertTrue(threeNodeTree.isBalanced());
		assertTrue(fourNodeTree.isBalanced());
		assertFalse(leftFourNodeTree.isBalanced());
		assertFalse(rightFourNodeTree.isBalanced());
	}
	
	@Test 
	public void testBalance() {
		emptyTree.add(1);
		emptyTree.add(2);
		emptyTree.add(3);
		assertFalse(emptyTree.isBalanced());
		assertEquals(3, emptyTree.size());
		emptyTree.balance();
		assertTrue(emptyTree.isBalanced());
		assertEquals(3, emptyTree.size());
		assertTrue(emptyTree.contains(1));
		assertTrue(emptyTree.contains(2));
		assertTrue(emptyTree.contains(3));
		
		}
	}

