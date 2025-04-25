package application;

import java.util.Stack;

public class DistrictTree {
	private DistrictNode root;

	public DistrictTree() {
		super();
	}

	public DistrictTree(DistrictNode root) {
		super();
		this.root = root;
	}

	public DistrictNode getRoot() {
		return root;
	}

	public void setRoot(DistrictNode root) {
		this.root = root;
	}

	public boolean containsDistrict(String districtName, DistrictNode root) {
		if (root == null) {
			return false; // District tree is empty
		}

		int comparisonResult = districtName.compareToIgnoreCase(root.DistrictName);
		if (comparisonResult == 0) {
			return true; // District found
		} else if (comparisonResult < 0) {
			return containsDistrict(districtName, root.left);
		} else {
			return containsDistrict(districtName, root.right);
		}
	}

	public void insertDistrict(DistrictNode newNode, DistrictNode rootExplore) {
		if (rootExplore == null) {
			root = newNode;
			return;
		}

		if (containsDistrict(newNode.DistrictName, rootExplore)) {

			return;
		}

		int comparisonResult = newNode.DistrictName.compareToIgnoreCase(rootExplore.DistrictName);

		if (comparisonResult > 0) {
			if (rootExplore.right == null)
				rootExplore.right = newNode;
			else
				insertDistrict(newNode, rootExplore.right);
		} else if (comparisonResult < 0) {
			if (rootExplore.left == null)
				rootExplore.left = newNode;
			else
				insertDistrict(newNode, rootExplore.left);
		}
	}

	public void printDistrictTree(DistrictNode root) {
		if (root != null) {
			printDistrictTree(root.left);

			System.out.println(root.DistrictName);
			root.getLocaionTree().printLocationTree(root.getLocaionTree().getRoot());

			// System.out.println("___________________________________________________");
			printDistrictTree(root.right);

		}
	}

	public DistrictNode searchDistrict(String districtName, DistrictNode root) {
		try {
			if (root == null) {
				throw new NullPointerException("District tree is empty");
			}

			int comparisonResult = districtName.compareToIgnoreCase(root.DistrictName);
			if (comparisonResult == 0) {
				return root; // District found
			} else if (comparisonResult < 0) {
				return searchDistrict(districtName, root.left);
			} else {
				return searchDistrict(districtName, root.right);
			}
		} catch (NullPointerException e) {
			System.out.println("Not found");
			return null;
		}
	}

	public String minValue(DistrictNode node) {
		String min = root.DistrictName;
		while (root.left != null) {
			min = root.left.DistrictName;
			root = root.left;
		}
		return min;
	}

	public DistrictNode deleteHelper(DistrictNode root, String districtname) {
		if (root == null)
			return null;

		int comparisonResult = districtname.compareToIgnoreCase(root.DistrictName);

		if (comparisonResult < 0) {
			root.left = deleteHelper(root.left, districtname);
		} else if (comparisonResult > 0) {
			root.right = deleteHelper(root.right, districtname);
		} else {
			// Node to be deleted found

			// Case 1: Node to be deleted has no children or only one child
			if (root.left == null)
				return root.right;
			else if (root.right == null)
				return root.left;

			// Case 2: Node to be deleted has two children
			root.DistrictName = minValue(root.right);
			root.right = deleteHelper(root.right, root.DistrictName);
		}

		return root;
	}

	public void updateDistrictName(String oldName, String newName) {
		
		DistrictNode nodeToUpdate = searchDistrict(oldName, root);

		// If district with old name found, update the name
		if (nodeToUpdate != null) {
			// Check if the new name already exists in the tree
			if (containsDistrict(newName, root)) {
				System.out.println("District with name " + newName + " already exists.");
				return;
			}

			// Update the district name
			nodeToUpdate.DistrictName = newName;
			System.out.println("District name updated successfully.");
		} else {
			System.out.println("District with name " + oldName + " not found.");
		}
	}

}
