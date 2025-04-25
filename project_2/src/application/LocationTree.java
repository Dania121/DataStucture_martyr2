package application;

public class LocationTree {
	private LocationNode root;

	public LocationTree() {
		super();
	}

	public LocationTree(LocationNode root) {
		super();
		this.root = root;
	}

	public LocationNode getRoot() {
		return root;
	}

	public void setRoot(LocationNode root) {
		this.root = root;
	}

	public boolean containsLocation(String locationName, LocationNode root) {
		if (root == null) {
			return false; // location tree is empty
		}

		int comparisonResult = locationName.compareToIgnoreCase(root.locationName);
		if (comparisonResult == 0) {
			return true; // District found
		} else if (comparisonResult < 0) {
			return containsLocation(locationName, root.left);
		} else {
			return containsLocation(locationName, root.right);
		}
	}

	public void insertLocation(LocationNode newNode, LocationNode rootExplore) {

		if (rootExplore == null) {
			root = newNode;
			return;
		}

		if (containsLocation(newNode.locationName, rootExplore)) {
			return;
		}

		int comparisonResult = newNode.locationName.compareToIgnoreCase(rootExplore.locationName);

		if (comparisonResult > 0) {
			if (rootExplore.right == null)
				rootExplore.right = newNode;
			else
				insertLocation(newNode, rootExplore.right);
		} else if (comparisonResult < 0) {
			if (rootExplore.left == null)
				rootExplore.left = newNode;
			else
				insertLocation(newNode, rootExplore.left);
		}

	}

	public LocationNode searchLocation(String locationName, LocationNode root) {
		try {
			if (root == null) {
				throw new NullPointerException("Location tree is empty");
			}

			int comparisonResult = locationName.compareToIgnoreCase(root.locationName);
			if (comparisonResult == 0) {
				return root; // location found
			} else if (comparisonResult < 0) {
				return searchLocation(locationName, root.left);
			} else {
				return searchLocation(locationName, root.right);
			}
		} catch (NullPointerException e) {
			System.out.println("Not found");
			return null;
		}
	}

	public String minValue(LocationNode node) {
		String min = root.locationName;
		while (root.left != null) {
			min = root.left.locationName;
			root = root.left;
		}
		return min;
	}

	public LocationNode deleteHelper(LocationNode root, String locationname) {
		if (root == null)
			return null;

		int comparisonResult = locationname.compareToIgnoreCase(root.locationName);

		if (comparisonResult < 0) {
			root.left = deleteHelper(root.left, locationname);
		} else if (comparisonResult > 0) {
			root.right = deleteHelper(root.right, locationname);
		} else {
			// Node to be deleted found

			// Case 1: Node to be deleted has no children or only one child
			if (root.left == null)
				return root.right;
			else if (root.right == null)
				return root.left;

			// Case 2: Node to be deleted has two children
			root.locationName = minValue(root.right);
			root.right = deleteHelper(root.right, root.locationName);
		}

		return root;
	}

	public void updateLocationName(String oldName, String newName) {

		LocationNode nodeToUpdate = searchLocation(oldName, root);

		// If district with old name found, update the name
		if (nodeToUpdate != null) {
			// Check if the new name already exists in the tree
			if (containsLocation(newName, root)) {
				System.out.println("location with name " + newName + " already exists.");
				return;
			}

			// Update the district name
			nodeToUpdate.locationName = newName;
			System.out.println("Location name updated successfully.");
		} else {
			System.out.println("Location with name " + oldName + " not found.");
		}
	}

	public void printLocationTree(LocationNode root) {
		if (root != null) {
			printLocationTree(root.left);
			System.out.println(root.locationName);
			// root.getLocaionTree().printLocationTree(root.getLocaionTree().getRoot());
			root.getDateTree().printDateTree(root.getDateTree().getRoot());
			printLocationTree(root.right);

		}
	}

}
