package application;

public class BSTNode {

	Object data;
	BSTNode left;
	BSTNode right;

	public BSTNode() {
		super();
	}

	public BSTNode(Object data, BSTNode left, BSTNode right) {
		super();
		this.data = data;
		this.left = left;
		this.right = right;
	}

	
	
	

//	public boolean contains(Object data, BSTNode root) {
//		if (root == null) {
//			return false; // District tree is empty
//		}
//
//		int comparisonResult = ((Object) data).compareToIgnoreCase(root.data);
//		if (comparisonResult == 0) {
//			return true; // District found
//		} else if (comparisonResult < 0) {
//			return contains(data, root.left);
//		} else {
//			return contains(data, root.right);
//		}
//	}

}
