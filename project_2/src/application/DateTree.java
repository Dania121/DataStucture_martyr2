package application;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTree {
	private DateNode root;

	public DateTree() {
		super();
	}

	public DateTree(DateNode root) {
		super();
		this.root = root;
	}

	public DateNode getRoot() {
		return root;
	}

	public void setRoot(DateNode root) {
		this.root = root;
	}

	public boolean containsDate(Date date, DateNode root) {
		if (root == null) {
			return false; // date tree is empty
		}

		if (date.equals(root.date)) {
			return true; // date found
		} else if (date.before(root.date)) {
			return containsDate(date, root.left);
		} else {
			return containsDate(date, root.right);
		}
	}

//	public boolean containsDate(Date date, DateNode root) {
//		if (root == null) {
//			return false; // date tree is empty
//		}
//
//		int comparisonResult = date.compareTo(root.date);
//		if (comparisonResult == 0) {
//			return true; // date found
//		} else if (comparisonResult < 0) {
//			return containsDate(date, root.left);
//		} else {
//			return containsDate(date, root.right);
//		}
//	}

	public void insertDate(DateNode newNode, DateNode rootExplore) {
		if (rootExplore == null) {
			root = newNode;
			return;
		}

		if (containsDate(newNode.date, rootExplore)) {
			return;
		}

		if (newNode.date.after(rootExplore.date)) {
			if (rootExplore.right == null)
				rootExplore.right = newNode;
			else
				insertDate(newNode, rootExplore.right);
		} else if (newNode.date.before(rootExplore.date)) {
			if (rootExplore.left == null)
				rootExplore.left = newNode;
			else
				insertDate(newNode, rootExplore.left);
		}
	}

//	public void insertDate(DateNode newNode, DateNode rootExplore) {
//
//		if (rootExplore == null) {
//			root = newNode;
//			return;
//		}
//
//		if (containsDate(newNode.date, rootExplore)) {
//			return;
//		}
//
//		int comparisonResult = newNode.date.compareTo(rootExplore.date);
//
//		if (comparisonResult > 0) {
//			if (rootExplore.right == null)
//				rootExplore.right = newNode;
//			else
//				insertDate(newNode, rootExplore.right);
//		} else if (comparisonResult < 0) {
//			if (rootExplore.left == null)
//				rootExplore.left = newNode;
//			else
//				insertDate(newNode, rootExplore.left);
//		}
//
//	}

//	public DateNode searchDate(Date date, DateNode root) {
//		try {
//			if (root == null) {
//				throw new NullPointerException("Date tree is empty");
//			}
//
//			int comparisonResult = date.compareTo(root.date);
//			if (comparisonResult == 0) {
//				return root; // location found
//			} else if (comparisonResult < 0) {
//				return searchDate(date, root.left);
//			} else {
//				return searchDate(date, root.right);
//			}
//		} catch (NullPointerException e) {
//			System.out.println("Not found");
//			return null;
//		}
//	}
	public DateNode searchDate(Date date, DateNode root) {
		try {
			if (root == null) {
				throw new NullPointerException("Date tree is empty");
			}

			if (date.equals(root.date)) {
				return root; // date found
			} else if (date.before(root.date)) {
				return searchDate(date, root.left);
			} else {
				return searchDate(date, root.right);
			}
		} catch (NullPointerException e) {
			System.out.println("Not found");
			return null;
		}
	}

	public DateNode findEarliestDate(DateNode node) {
		if (node == null) {
			return null;
		}
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	public DateNode findLatestDate(DateNode node) {
		if (node == null) {
			return null;
		}
		while (node.right != null) {
			node = node.right;
		}
		return node;
	}
	public String getMaxDate() {
		if (this.root == null)
			return "None";
		Queue2 q = new Queue2();
		q.inQueue(this.root);
		int max = getMaxMartyrs(this.root);
		while(!q.isEmpty()) {
			DateNode d = q.deQueue().data;
			if (d.getMartyrLinkedList().size()==max)
				return d.toString();
				//return d.getMartyrLinkedList().first.getMartyr().getName();
			if (d.left!=null)
				q.inQueue(d.left);
			if (d.right!=null)
				q.inQueue(d.right);
			
		}
		return "none";
		
	}
	
	
	public int getMaxMartyrs(DateNode node) {
		if (node == null)
			return 0;
		return Math.max(Math.max(getMaxMartyrs(node.left), getMaxMartyrs(node.right)), node.getMartyrLinkedList().size());
	}

	public void printDateTree(DateNode root) {
		if (root != null) {
			printDateTree(root.left);
			System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(root.date));
			// root.getDateTree().printDateTree(root.getDateTree().getRoot());
			root.getMartyrLinkedList().print();
			printDateTree(root.right);
		}
	}
}
