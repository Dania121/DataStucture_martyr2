package application;

public class LocationNode {
	String locationName;
	 private DateTree dateTree;
	LocationNode left;
	LocationNode right;

	public LocationNode() {
		super();
	}

	public LocationNode(String locationName, DateTree dateTree, LocationNode left, LocationNode right) {
		super();
		this.locationName = locationName;
		this.dateTree = dateTree;
		this.left = left;
		this.right = right;
	}

	public DateTree getDateTree() {
		return dateTree;
	}

	public void setDateTree(DateTree dateTree) {
		this.dateTree = dateTree;
	}

	@Override
	public String toString() {
		return "LocationNode [locationName=" + locationName + "]";
	}
	

}
