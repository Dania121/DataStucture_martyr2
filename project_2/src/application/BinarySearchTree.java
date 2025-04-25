package application;

import java.util.Date;

public class BinarySearchTree {
	private BSTNode root;

	public BinarySearchTree() {
		super();
	}

	public BinarySearchTree(BSTNode root) {
		super();
		this.root = root;
	}

	public BSTNode getRoot() {
		return root;
	}

	public void setRoot(BSTNode root) {
		this.root = root;
	}

	public boolean contains(Object data, BSTNode root) {
		if (root == null) {
			return false; // Entire tree is empty
		}

		int comparisonResult = ((String) data).compareToIgnoreCase((String) root.data);

		if (comparisonResult == 0) {
			return true; // Data found
		} else if (comparisonResult < 0) {

			return contains(data, root.left);
		} else {

			return contains(data, root.right);
		}
	}

	public void insert(Object data) {

//		if (data != null && data instanceof String && !((String) data).isEmpty()) {
//
//			BSTNode newNode = new BSTNode(data, null, null);
//
//			if (root == null) {
//				root = newNode;
//				return;
//			}
//
//			insertNode(newNode, root);
//		}
		//else {
			BSTNode newNode = new BSTNode(data, null, null);

			if (root == null) {
				root = newNode;
				return;
			}

			insertMartyr(newNode, root);
		//}
	}

//	private void insertNode(BSTNode newNode, BSTNode rootExplore) {
//
//		int comparisonResult = ((String) newNode.data).compareToIgnoreCase((String) rootExplore.data);
//
//		if (comparisonResult > 0) {
//
//			if (rootExplore.right == null) {
//
//				rootExplore.right = newNode;
//			} else {
//
//				insertNode(newNode, rootExplore.right);
//			}
//		} else if (comparisonResult < 0) {
//
//			if (rootExplore.left == null) {
//
//				rootExplore.left = newNode;
//			} else {
//
//				insertNode(newNode, rootExplore.left);
//			}
//		}
//	}
	private void insertMartyr(BSTNode newNode, BSTNode rootExplore) {

		int comparisonResult = (((Martyr) newNode.data).getName()).compareToIgnoreCase(((Martyr) rootExplore.data).getName());

		if (comparisonResult > 0) {

			if (rootExplore.right == null) {

				rootExplore.right = newNode;
			} else {

				insertMartyr(newNode, rootExplore.right);
			}
		} else if (comparisonResult < 0) {

			if (rootExplore.left == null) {

				rootExplore.left = newNode;
			} else {

				insertMartyr(newNode, rootExplore.left);
			}
		}
	}
}
