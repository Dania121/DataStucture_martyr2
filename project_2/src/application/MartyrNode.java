package application;

public class MartyrNode {
	private Martyr martyr;
	MartyrNode next;

	public MartyrNode() {
		super();
	}

	public MartyrNode(Martyr martyr) {
		super();
		this.martyr = martyr;
		
	}

	public Martyr getMartyr() {
		return martyr;
	}

	public void setMartyr(Martyr martyr) {
		this.martyr = martyr;
	}

}
