
/**
 * Team members:
 * @author Lauren Arner
 * @author Richard Smith
 * @author Justin Sung
 *
 * RBTree class, maintains operations on RBTree.
 */

import java.lang.Math;

public class RBTree {

	/**
	 * RB Tree constructor. It initializes nil node as well.
	 */
	Node root;
	Node NIL;
	int nodeCount;
	int treeHeight;

	public RBTree() {
		//TODO: Add code as needed.
		this.NIL = new Node	();
		this.NIL.color = 1;
		this.root = this.NIL;
		NIL.parent = NIL;
		NIL.left = NIL;
		NIL.right = NIL;
	}


	/**
	 * Returns the root of the tree.
	 * @return
	 */
	public Node getRoot() {
		return this.root;
	}


	/**
	 * Returns reference for the nil node, for the rbTree.
	 * @return
	 */
	public Node getNILNode() {
		return this.NIL;
	}


	/**
	 * Returns the number of internal nodes in the tree.
	 * @return
	 */
	public int getSize() {
		return this.nodeCount;
	}


	/**
	 * Returns the height of the tree.
	 * @return
	 */
	public int getHeight() {
		return this.treeHeight;
	}

	//Add more functions as  you see fit.

	/**
	 * Computes value variable for nodes recursively
	 * @param node
	 * @return
	 */
	public int computeVal(Node node) { //DONE

		if(node == NIL) {
			return 0;
		}
		return computeVal(node.left) + node.p + computeVal(node.right);
	}


	/**
	 * Computes max value variable for nodes
	 * @param node
	 */
	public void computeMaxVal(Node node) { //DONE

		node.maxval = Math.max(Math.max(node.left.getMaxVal(), node.left.getVal() + node.getP()), node.left.getVal() + node.getP() + node.right.getMaxVal());

		if (node.maxval == node.left.getMaxVal()){
			node.emax = node.left.getEmax();
		}
		else if (node.maxval == node.right.getMaxVal() + node.getP()) {
			node.emax = node.getEndpoint();
		}
		else {
			node.emax = node.right.getEmax();
		}
	}


	/**
	 * calculate height for tree
	 * @param node
	 */
	private void calculateHeight(Node node) {
		int nodeHeight = 1;
		Node tempNode = searchTree (node, node.getKey());

		if(tempNode.equals(NIL)) {
			nodeHeight = 0;
		}

		while((!tempNode.equals(NIL))) {
			tempNode = tempNode.parent;
			nodeHeight++;
		}

		if(nodeHeight > treeHeight) {
			treeHeight = nodeHeight;
		}
	}


	/**
	 * private search for node in order to calculate height
	 * @param node
	 * @param key
	 * @return
	 */
	private Node searchTree(Node node, int key) {
		if(node.equals(NIL)) {
			return NIL;
		}

		if(key < node.getKey()) {
			return searchTree (node.left, key);
		}

		else {
			return searchTree(node.right, key);
		}
	}


	/**
	 * performs left rotation on given node
	 * @param node
	 */
	public void leftRotate (Node node) { //DONE
		Node y = node.right;
		node.right = y.left;

		if(y.left != NIL) {
			y.left.parent = node;
		}y.parent = node.parent;

		if(node.parent == NIL) {
			root = y;
		}else if(node == node.parent.left) {
			node.parent.left = y;
		}else {
			node.parent.right = y;
		}

		y.left = node;
		node.parent = y;
	}


	/**
	 * performs right rotation on given node
	 * @param node
	 */
	public void rightRotate (Node node) { //DONE

		Node y = node.parent;
		y.left = node.right;
		if(node.right != null) {
			node.right.parent = y;
		}node.parent = y.parent;
		if(y.parent == null) {
			root = node;
		}else if(y == y.parent.right) {
			y.parent.right = node;
		}else {
			y.parent.left = y;
		}
	}


	/**
	 * Helper method for RBInsert to "fixup" the freshly inserted node
	 * @param node
	 */
	public void RBInsertFixup(Node z){ //DONE
		Node y = new Node();
		while (z.parent.color == 0){
		if (z.parent == z.parent.parent.left){
			y = z.parent.parent.right;
			if (y.color == 0){
				z.parent.color = 1;
				y.color = 1;
				z.parent.parent.color = 0;
				z = z.parent.parent;
			}
			else {
					if (z == z.parent.right){
						z = z.parent;
						leftRotate(z);
					}
					z.parent.color = 1;
					z.parent.parent.color = 0;
					rightRotate(z.parent.parent);
			}
		}
		else {
			if (z.parent == z.parent.parent.right){
				y = z.parent.parent.left;
					if (y.color == 0){
							z.parent.color = 1;
							y.color = 1;
							z.parent.parent.color = 0;
							z = z.parent.parent;
		}
			else {
				if (z == z.parent.left){
					z = z.parent;
					rightRotate(z);
				}
				z.parent.color = 1;
				z.parent.parent.color = 0;
				leftRotate(z.parent.parent);
					}
				}
			}
		}
		root.color = 1;
	}



	/**
	 * Correctly inserts given node into RB tree and then calls RBInsertFixup method
	 * @param node
	 */
	public void RBInsert(Node z) { //DONE
		Node y = NIL;
		Node x = root;
		while (x != NIL) {
			y = x;
			if (z.key < x.key) {
				x = x.left;
			}
			else {
				x = x.right;
			}
		}
		z.parent = y;
		if (y == NIL) {
			root = z;
		}
		else if (z.key < y.key) {
			y.left = z;
		}
		else {
			y.right = z;
		}
		z.left = NIL;
		z.right = NIL;
		z.color = 0;
		RBInsertFixup(z);
	}


	public void update(Node z, RBTree tree) {

		if (z == tree.getRoot()) {
			return;
		}
		computeVal(z);
		computeMaxVal(z);
		update(z.getParent(), tree);

	}






















}
