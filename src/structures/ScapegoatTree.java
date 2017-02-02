package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class ScapegoatTree<T extends Comparable<T>> extends
		BinarySearchTree<T> {

	private double upperBound = 0.0;
	//Queue<T> path = new LinkedList<T>();
	BSTNode<T> path[]; // (BSTNode<T>[])  new Object[height()];

	int counter = 0;
	
	/**
	 * Adds an element to the tree.
	 * 
	 * The modified tree must still obey the BST rule, though it might not be
	 * balanced.
	 * 
	 * In addition to obeying the BST rule, the resulting tree must also obey
	 * the scapegoat rule. 
	 * 
	 * This method must only perform rebalancing of subtrees when indicated
	 * by the scapegoat rule; do not unconditionally call balance() 
	 * after adding, or you will receive no credit. 
	 * See the project writeup for details.
	 * 
	 * @param element
	 * @throws NullPointerException if element is null
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void add(T element) {
		// TODO		
		//System.out.println("Start: " + element);
		root = addToSubtree(element, root);
		upperBound++;
		int height = height();
		double upLog = Math.log(upperBound);
		double log = Math.log(1.5);
		double result = upLog/log;
		double answer = height - result;
		//System.out.println("Result: " + result);
		//System.out.println("UpperBound: " + upperBound);
		//System.out.println("Answer: " + answer);
		if(answer > 0){
			//Tree needs to be balanced
			//System.out.println("Gets in on: " + element);
			path = (BSTNode<T>[])  new BSTNode<?>[height()+1];
			counter = 0;
			//System.out.println("pathFinder starts");
			pathFinder(root);
			//System.out.println("pathFinder ends");
			for(int i = 0; i < path.length; i++){
			//System.out.println("path: " + path[i].getData());
			}
			//System.out.println("height: " + height);
			int i = height - 1;
			while(i >= 0){
				double size = subtreeSize(path[i]);
				double subSize = subtreeSize(path[i+1]);
				double b = subSize/size;
				double a = b-0.666;
				/*System.out.println("loop in: " + i);
				System.out.println("subtree size: " + size);
				System.out.println("Child subtree size: " + subSize);
				System.out.println("result: " + b);
				System.out.println();*/
				if(a>0.001){
					//System.out.println("break at: " + i);
					break;
				}
				i--;
			}
			//now currentNode is the scapegoat Node
			//System.out.println("balanceSubtree starts");
			//System.out.println("index: " + i);
			//System.out.println("Scapegoat node is: " + path[i].getData());
			//System.out.println("Parent node is: " + path[i-1].getData());
			balanceSubtree(path[i], path[i-1]);
			
		}
		//System.out.println("Done With: " + element);
		//System.out.println();
	}
	
	public void pathFinder(BSTNode<T> curNode) {
		path[counter] = curNode;
		counter++;
		if(curNode.getRight() == null && curNode.getLeft() == null){//heightHelper(curNode) == 0){
			return;
		}
		if(curNode.getRight() == null){
			pathFinder(curNode.getLeft());
		}
		else if(curNode.getLeft() == null){
			pathFinder(curNode.getRight());
		}
		else if(heightHelper(curNode.getLeft())> heightHelper(curNode.getRight())){
			pathFinder(curNode.getLeft());
		}
		else if(heightHelper(curNode.getLeft()) < heightHelper(curNode.getRight())){
			pathFinder(curNode.getRight());
		}
		return;
	}
	
	private BSTNode<T> addToSubtree(T t, BSTNode<T> node) {
		if (node == null) {
			return new BSTNode<T>(t, null, null);
		}
		if (t.compareTo(node.getData()) <= 0) {
			node.setLeft(addToSubtree(t, node.getLeft()));
		} else {
			node.setRight(addToSubtree(t, node.getRight()));
		}
		return node;
	}
	
	public Iterator<T> subtreeInOrderIterator(BSTNode<T> node){
		Queue<T> queue = new LinkedList<T>();
		inorderTraverse(queue, node);
		return queue.iterator();
	}
	
	public void balanceSubtree(BSTNode<T> node, BSTNode<T> parent){
		//get values of subtree
		Iterator<T> Iter = subtreeInOrderIterator(node);
		/*@SuppressWarnings("unchecked")
		 T values[] = (T[]) new Comparable[size()];
		int i = 0;
		while(Iter.hasNext()){
			values[i] = Iter.next();
			i++;
		}*/
		if(node.getData().compareTo(parent.getData()) < 0) parent.setLeft(null);
		else parent.setRight(null);
		//Create tree for subtree
		BinarySearchTree<T> subtree = new BinarySearchTree<T>();
		while(Iter.hasNext()){
			subtree.add(Iter.next());
		}
		subtree.balance();
		Iterator<T> subtreeIter = subtree.preorderIterator();
		while(subtreeIter.hasNext()){
			//System.out.print(subtreeIter.next());
			addToSubtree(subtreeIter.next(), parent);
		}
		upperBound = subtreeSize(root);
	}
	
	/**
	 * Attempts to remove one copy of an element from the tree, returning true
	 * if and only if such a copy was found and removed.
	 * 
	 * The modified tree must still obey the BST rule, though it might not be
	 * balanced.
	 * 
	 * In addition to obeying the BST rule, the resulting tree must also obey
	 * the scapegoat rule.
	 * 
	 * This method must only perform rebalancing of subtrees when indicated
	 * by the scapegoat rule; do not unconditionally call balance() 
	 * after removing, or you will receive no credit. 
	 * See the project writeup for details.

	 * @param element
	 * @return true if and only if an element removed
	 * @throws NullPointerException if element is null
	 */
	@Override
	public boolean remove(T element) {
		// TODO
		boolean result = contains(element);
		if (result) {
			root = removeFromSubtree(root, element);
		}
		int size = subtreeSize(root);
		if(upperBound > 2 * size){
			balance();
		}
		return result;
	}

	private BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
		// node must not be null
		int result = t.compareTo(node.getData());
		if (result < 0) {
			node.setLeft(removeFromSubtree(node.getLeft(), t));
			return node;
		} else if (result > 0) {
			node.setRight(removeFromSubtree(node.getRight(), t));
			return node;
		} else { // result == 0
			if (node.getLeft() == null) {
				return node.getRight();
			} else if (node.getRight() == null) {
				return node.getLeft();
			} else { // neither child is null
				T predecessorValue = getHighestValue(node.getLeft());
				node.setLeft(removeRightmost(node.getLeft()));
				node.setData(predecessorValue);
				return node;
			}
		}
	}
}
