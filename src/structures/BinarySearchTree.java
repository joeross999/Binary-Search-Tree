package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements
		BSTInterface<T> {
	protected BSTNode<T> root;
	protected T elements[];

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return subtreeSize(root);
	}

	protected int subtreeSize(BSTNode<T> node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + subtreeSize(node.getLeft())
					+ subtreeSize(node.getRight());
		}
	}

	public boolean contains(T t) throws NullPointerException{
		//Finished and tested
		if(get(t) == null){
			return false;
		}
		return true;
	}

	public boolean remove(T t) {
		boolean result = contains(t);
		if (result) {
			root = removeFromSubtree(root, t);
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

	protected T getHighestValue(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getData();
		} else {
			return getHighestValue(node.getRight());
		}
	}

	protected BSTNode<T> removeRightmost(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getLeft();
		} else {
			node.setRight(removeRightmost(node.getRight()));
			return node;
		}
	}

	public T get(T t) throws NullPointerException{
		//Finished and Working
		if(t == null) throw new NullPointerException();
		if(isEmpty()) return null;
		BSTNode<T> curNode = root;
		BSTNode<T> nextNode;
		if(t.equals(curNode.getData())){
			return curNode.getData();
		}
		if(t.compareTo(curNode.getData())>0){
			nextNode = curNode.getRight();
		}else{
			nextNode = curNode.getLeft();
		}
		while(nextNode != null){
			curNode = nextNode;
			if(t.equals(curNode.getData())){
				return curNode.getData();
			}
			if(t.compareTo(curNode.getData())>0){
				nextNode = curNode.getRight();
			}else{
				nextNode = curNode.getLeft();
			}
		}
		
		return null;
	}

	public void add(T t) {
		root = addToSubtree(t, root);
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

	@Override
	public T getMinimum() {
		// Finished and Tested
		if(isEmpty()){
			return null;
		}
		BSTNode<T> curNode = root;
		while(curNode.getLeft() != null){
			curNode = curNode.getLeft();
		}
		return curNode.getData();
	}

	@Override
	public T getMaximum() {
		// Finished and Tested
		if(isEmpty()){
			return null;
		}
		BSTNode<T> curNode = root;
		while(curNode.getRight() != null){
			curNode = curNode.getRight();
		}
		return curNode.getData();
	}


	@Override
	public int height() {
		// Finished and Tested
		if(isEmpty()){
			return -1;
		}
		return heightHelper(root);
	}
	
	public int heightHelper(BSTNode<T> curNode){
		//Recursive Helper method for height
		if(curNode == null){
			return -1;
		}
		return Math.max(heightHelper(curNode.getLeft()), heightHelper(curNode.getRight())) + 1;
	}

	@Override
	public Iterator<T> preorderIterator() {
		// Finished and Tested
		Queue<T> queue = new LinkedList<T>();
		preorderTraverse(queue, root);
		return queue.iterator();
	}
	
	//Helper Method for preorderIterator
	private void preorderTraverse(Queue<T> queue, BSTNode<T> node){
		if(node != null) {
			queue.add(node.getData());
			preorderTraverse(queue, node.getLeft());
			preorderTraverse(queue, node.getRight());
		}
	}

	@Override
	public Iterator<T> inorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		inorderTraverse(queue, root);
		return queue.iterator();
	}
	
	protected void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			inorderTraverse(queue, node.getLeft());
			queue.add(node.getData());
			inorderTraverse(queue, node.getRight());
		}
	}

	@Override
	public Iterator<T> postorderIterator() {
		// Finished and Tested
		Queue<T> queue = new LinkedList<T>();
		postorderTraverse(queue, root);
		return queue.iterator();
	}
	
	//Helper method for postorderIterator
	private void postorderTraverse(Queue<T> queue, BSTNode<T> node){
		if(node != null) {
			postorderTraverse(queue, node.getLeft());
			postorderTraverse(queue, node.getRight());
			queue.add(node.getData());
		}
	}

	@Override
	public boolean equals(BSTInterface<T> other) {
		// TODO
		Iterator<T> thisIter = preorderIterator();
		Iterator<T> thatIter = other.preorderIterator();
		T thisCur;
		T thatCur;
		while(thisIter.hasNext()){
			thisCur = thisIter.next();
			if(thatIter.hasNext()){
				thatCur = thatIter.next();
			}else return false;
			if(!thisCur.equals(thatCur)) return false;
		}
		if(thatIter.hasNext())return false;
		return true;
	}

	@Override
	public boolean sameValues(BSTInterface<T> other) {
		// TODO
		Iterator<T> thatIter = other.inorderIterator();
		int i = 0;
		T thatCur;
		while(thatIter.hasNext()){
			thatCur = thatIter.next();
			i++;
			if(!contains(thatCur))return false;
		}
		if(i != size())return false;
		return true;
	}

	@Override
	public boolean isBalanced() {
		// finished and Tested
		if(isEmpty())return true;
		int n = size();
		int h = height();
		if(Math.pow(2, h) <= n && n < Math.pow(2, h+1)){
			return true;
		}
		return false;
	}

	@Override
	public void balance() {
		// TODO	
		if(isEmpty())return;
		Iterator<T> Iter = inorderIterator();
		@SuppressWarnings("unchecked")
		 T values[] = (T[]) new Comparable[size()];
		int i = 0;
		while(Iter.hasNext()){
			values[i] = Iter.next();
			i++;
		}
		root = null;
		elements = values;
		balanceHelper(0, i-1);
	}
	
	public void balanceHelper(int low, int high){
		if(low == high) add(elements[low]);
		else if(low + 1 == high){
			add(elements[low]);
			add(elements[high]);
		}
		else{
			int mid = (low + high)/2;
			add(elements[mid]);
			balanceHelper(low, mid - 1);
			balanceHelper(mid + 1, high);
		}
	}
	
	
	
	@Override
	public BSTNode<T> getRoot() {
		// DO NOT MODIFY
		return root;
	}

	public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
		// DO NOT MODIFY
		// see project description for explanation

		// header
		int count = 0;
		String dot = "digraph G { \n";
		dot += "graph [ordering=\"out\"]; \n";
		// iterative traversal
		Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
		queue.add(root);
		BSTNode<T> cursor;
		while (!queue.isEmpty()) {
			cursor = queue.remove();
			if (cursor.getLeft() != null) {
				// add edge from cursor to left child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getLeft().getData().toString() + ";\n";
				queue.add(cursor.getLeft());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}
			if (cursor.getRight() != null) {
				// add edge from cursor to right child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getRight().getData().toString() + ";\n";
				queue.add(cursor.getRight());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}

		}
		dot += "};";
		return dot;
	}
}