package application;

public class DistrictNode {
	String DistrictName;
	private LocationTree locaionTree;
	DistrictNode left;
	DistrictNode right;

	public DistrictNode() {
		super();
	}

	public DistrictNode(String districtName, LocationTree locaionTree, DistrictNode left, DistrictNode right) {
		super();
		DistrictName = districtName;
		this.locaionTree = locaionTree;
		this.left = left;
		this.right = right;
	}

	public LocationTree getLocaionTree() {
		return locaionTree;
	}

	public void setLocaionTree(LocationTree locaionTree) {
		this.locaionTree = locaionTree;
	}

}
