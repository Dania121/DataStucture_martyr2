package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateNode {
	Date date;
	private MartyrLinkedList martyrLinkedList;
	DateNode left;
	DateNode right;

	public DateNode() {
		super();
	}

	public DateNode(Date date, MartyrLinkedList martyrLinkedList, DateNode left, DateNode right) {
		super();
		this.date = date;
		this.martyrLinkedList = martyrLinkedList;
		this.left = left;
		this.right = right;
	}

	public MartyrLinkedList getMartyrLinkedList() {
		return martyrLinkedList;
	}

	public void setMartyrLinkedList(MartyrLinkedList martyrLinkedList) {
		this.martyrLinkedList = martyrLinkedList;
	}

	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String formattedDate = dateFormat.format(date);
		return formattedDate;
	}
}
