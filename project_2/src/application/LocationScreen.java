package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Stack;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LocationScreen extends Application {
	private DistrictNode dnode;
	private LocationNode currentLocation;
	Stack<LocationNode> stack1 = new Stack<>();
	Stack<LocationNode> stack2 = new Stack<>();
	// LocationTree LT = new LocationTree();
	DistrictTree DT = new DistrictTree();
	DateTree dateT = new DateTree();
	Text current;
	ComboBox<LocationNode> cmb;
	Text eartext;
	Text lateText;
	Text maxText;
	// Queue<> q = new Queue<>();

	public DistrictNode getDnode() {
		return dnode;

	}

	public void setDnode(DistrictNode dnode) {
		this.dnode = dnode;
		levelOrder(dnode.getLocaionTree().getRoot());

	}

	public LocationNode getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(LocationNode currentLocation) {
		this.currentLocation = currentLocation;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button prev = new Button("Previous");
		Button next = new Button("Next");
		current = new Text();

		current.setStrokeWidth(30);
		HBox hbox = new HBox();
		hbox.getChildren().addAll(prev, current, next);
		hbox.setSpacing(80);
		Label earliest = new Label("a) The earliest date that has martyr is : ");
		eartext = new Text();
		eartext.setStrokeWidth(30);
		HBox hbox1 = new HBox();
		hbox1.getChildren().addAll(earliest, eartext);
		hbox1.setSpacing(30);
		Label latest = new Label("b) The latest date that has martyr is : ");
		lateText = new Text();
		lateText.setStrokeWidth(30);
		HBox hbox2 = new HBox();
		hbox2.getChildren().addAll(latest, lateText);
		hbox2.setSpacing(30);
		Label max = new Label("c) The date that has the max number of martyrs is : ");
		maxText = new Text();
		maxText.setStrokeWidth(30);
		HBox hbox3 = new HBox();
		hbox3.getChildren().addAll(max, maxText);
		hbox3.setSpacing(30);

		Text Lname = new Text(" Location Name ");
		TextField LN = new TextField();
		Button insert = new Button("Insert");
		VBox vbox4 = new VBox();
		vbox4.getChildren().addAll(Lname, LN, insert);
		Text UL = new Text("Updated Location");
		TextField LA = new TextField();
		Button update = new Button("Update");
		VBox vbox5 = new VBox();
		vbox5.getChildren().addAll(UL, LA, update);
		Button delete = new Button("Delete");
		HBox hbox4 = new HBox();
		hbox4.getChildren().addAll(vbox4, vbox5, delete);
		hbox4.setSpacing(50);
		VBox vbox6 = new VBox();
		TextField searchText = new TextField();
		Text founded = new Text();
		VBox vbox11 = new VBox();
		vbox11.getChildren().addAll(searchText, founded);
		vbox11.setSpacing(10);
		Button search = new Button("Search");
		Button save = new Button("save");
		HBox hbox66 = new HBox();

		cmb = new ComboBox<LocationNode>();
//		LocationNode selectedLocation = cmb.getValue();
//		current.setText(selectedLocation.locationName);
		hbox66.getChildren().addAll(cmb, vbox11, search, save);
		hbox66.setSpacing(50);
		Button gotomartyrscreen = new Button("GO To Martyr Screen ");
		vbox6.getChildren().addAll(hbox66, hbox, hbox1, hbox2, hbox3, hbox4, gotomartyrscreen);
		vbox6.setSpacing(70);
		next.setOnAction(event -> {
			if (stack1.size() > 1) {
				if (!current.getText().equals("No previous location available")) {
					stack2.push(stack1.pop());
				}
				currentLocation = stack1.peek();
				current.setText(currentLocation.locationName);
				DateNode Ddate = currentLocation.getDateTree()
						.findEarliestDate(currentLocation.getDateTree().getRoot());
				Date date = Ddate.date;

				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

				try {
					String dateString = dateFormat.format(date); // Convert Date object to string
					date = dateFormat.parse(dateString);
					eartext.setText(dateString);

				} catch (ParseException e) {
					System.out.println("Invalid date format");
				}

				DateNode Ddate2 = stack1.peek().getDateTree().findLatestDate(stack1.peek().getDateTree().getRoot());
				Date date2 = Ddate2.date;
				SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy");
				try {
					String dateString2 = dateFormat2.format(date2);
					date2 = dateFormat2.parse(dateString2);
					lateText.setText(dateString2);
				} catch (ParseException e) {
					System.out.println("Invalid date format");
				}
			} else {
				current.setText("No next Location available");
			}
			maxText.setText(stack1.peek().getDateTree().getMaxDate());

		});
		prev.setOnAction(e -> {
			if (!stack2.isEmpty()) {
				if (!current.getText().equals("No next Location available"))
					stack1.push(stack2.pop());
				currentLocation = stack1.peek();
				current.setText(currentLocation.locationName);
				DateNode Ddate = currentLocation.getDateTree()
						.findEarliestDate(currentLocation.getDateTree().getRoot());
				Date date = Ddate.date;

				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

				try {
					String dateString = dateFormat.format(date); // Convert Date object to string
					date = dateFormat.parse(dateString);
					eartext.setText(dateString);

				} catch (ParseException ex) {
					System.out.println("Invalid date format");
				}

				DateNode Ddate2 = stack1.peek().getDateTree().findLatestDate(stack1.peek().getDateTree().getRoot());
				Date date2 = Ddate2.date;
				SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy");
				try {
					String dateString2 = dateFormat2.format(date2);
					date2 = dateFormat2.parse(dateString2);
					lateText.setText(dateString2);
				} catch (ParseException ex) {
					System.out.println("Invalid date format");
				}
			} else {
				current.setText("No previous location available");
			}
			maxText.setText(stack1.peek().getDateTree().getMaxDate());
		});

		insert.setOnAction(event -> {
			String locationName = LN.getText();
			insertLocation(locationName);
		});
		update.setOnAction(event -> {

			String oldName = current.getText();
			dnode.getLocaionTree().updateLocationName(oldName, LA.getText());
			dnode.getLocaionTree().printLocationTree(dnode.getLocaionTree().getRoot());

			// dnode.getLocaionTree().printLocationTree(dnode.getLocaionTree().getRoot());
		});
		delete.setOnAction(event -> {
			String locationName = current.getText();
			deleteLocation(locationName);
			System.out.println("delete sucssfully");
			dnode.getLocaionTree().printLocationTree(dnode.getLocaionTree().getRoot());

		});
		cmb.setOnAction(event -> {
			LocationNode selectedLocation = cmb.getValue();
			if (selectedLocation != null) {
				current.setText(selectedLocation.locationName);
//				currentLocation = stack1.peek();
//				current.setText(currentLocation.locationName);
//				DateNode Ddate = currentLocation.getDateTree()
//						.findEarliestDate(currentLocation.getDateTree().getRoot());
//				Date date = Ddate.date;
//
//				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//
//				try {
//					String dateString = dateFormat.format(date); // Convert Date object to string
//					date = dateFormat.parse(dateString);
//					eartext.setText(dateString);
//
//				} catch (ParseException ex) {
//					System.out.println("Invalid date format");
//				}
//
//				DateNode Ddate2 = stack1.peek().getDateTree().findLatestDate(stack1.peek().getDateTree().getRoot());
//				Date date2 = Ddate2.date;
//				SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy");
//				try {
//					String dateString2 = dateFormat2.format(date2);
//					date2 = dateFormat2.parse(dateString2);
//					lateText.setText(dateString2);
//				} catch (ParseException ex) {
//					System.out.println("Invalid date format");
//				}
//				
//			}
//			maxText.setText(stack1.peek().getDateTree().getMaxDate());
			}
		});
		search.setOnAction(event -> {
			String locationName = searchText.getText(); // Get the district name from the text field

			// Search for the district in the district tree
			LocationNode foundLocation = searchLocation(locationName);

			// Check if the district was found
			if (foundLocation != null) {
				founded.setText("Founded"); // Update label to indicate district was found
				current.setText(foundLocation.locationName); // Update current district label
//				// Calculate total martyrs for the found district
//				int totalMartyrs = calculateTotalMartyrs(foundDistrict.DistrictName);
//				total1.setText(Integer.toString(totalMartyrs)); // Update total martyrs text

			} else {
				founded.setText("Not founded"); // Update label to indicate district was not found
				current.setText(""); // Clear current district label
//				total1.setText(""); // Clear total martyrs text
//			}
			}

		});

		// levelOrder(currentLocation);

		// cmb.getItems().add(new LocationNode("location",new DateTree(),null,null));
		BorderPane pane = new BorderPane();
		pane.setCenter(vbox6);
		// pane.setTop(cmb);
		Scene scene = new Scene(pane, 750, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Location Screen ");
		primaryStage.show();

		gotomartyrscreen.setOnAction(event -> {
			try {
				MartyrScreen martyrScreen = new MartyrScreen();
				martyrScreen.start(new Stage());
				martyrScreen.setLnode(stack1.peek());
				DateNode Ddate = stack1.peek().getDateTree().findEarliestDate(stack1.peek().getDateTree().getRoot());
				Date date = Ddate.date;

				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

				String dateString = dateFormat.format(date); // Convert Date object to string
				date = dateFormat.parse(dateString);
				martyrScreen.dateText.setText(dateString);// .setText(dateString);
				martyrScreen.fillTv();
				DateNode date1 = martyrScreen.stack1.peek();
				martyrScreen.avg1.setText(Double.toString(martyrScreen.calculateAvgMartyrAges(date1)));
				martyrScreen.youngest1.setText(martyrScreen.youngestMartyr(date1));
				martyrScreen.oldest1.setText(martyrScreen.oldestMartyr(date1));

			} catch (ParseException e) {
				System.out.println("Invalid date format");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	public void levelOrder(LocationNode root) {
		if (root == null)
			return;

		Queue q = new Queue();
		q.inQueue(root);
		while (!q.isEmpty()) {
			Node current = q.front;
			stack2.push(current.data);
			// System.out.println("dddddd");
			cmb.getItems().add(stack2.peek());
			if (current.data.left != null) {
				q.inQueue(current.data.left);

			}
			if (current.data.right != null) {
				q.inQueue(current.data.right);

			}
			q.deQueue();
			// cmb.getItems().add(stack2.peek());
		}

		while (!stack2.isEmpty()) {
			stack1.push(stack2.pop());
		}
		current.setText(stack1.peek().locationName);

	}

	public void insertLocation(String locationName) {

		if (!locationName.isEmpty()) {

			LocationNode newLocation = new LocationNode(locationName, new DateTree(), null, null);
			System.out.println("-----------------------------------------------------------------");
			dnode.getLocaionTree().insertLocation(newLocation, dnode.getLocaionTree().getRoot());

		}
		// System.out.println("dania");
		dnode.getLocaionTree().printLocationTree(dnode.getLocaionTree().getRoot());

		System.out.println("__________________________________");
	}

	public void deleteLocation(String locationName) {
		if (!locationName.isEmpty()) {
			dnode.getLocaionTree().deleteHelper(dnode.getLocaionTree().getRoot(), locationName);

		}
		dnode.getLocaionTree().printLocationTree(dnode.getLocaionTree().getRoot());

		System.out.println("__________________________________");
	}

	private int countMartyrsInLocationTree(LocationNode node) {
		if (node == null) {
			return 0;
		}

		// Recursively count martyrs in the left and right subtrees
		int leftMartyrs = countMartyrsInLocationTree(node.left);
		int rightMartyrs = countMartyrsInLocationTree(node.right);

		// Count martyrs in the linked list of this location
		int locationMartyrs = countMartyrsInDateTree(node.getDateTree().getRoot());

		// Add martyrs in the current node and those from the subtrees to the total
		// count
		return leftMartyrs + rightMartyrs + locationMartyrs;
	}

	// Helper method to count martyrs in a date tree
	private int countMartyrsInDateTree(DateNode node) {
		if (node == null) {
			return 0;
		}

		// Recursively count martyrs in the left and right subtrees
		int leftMartyrs = countMartyrsInDateTree(node.left);
		int rightMartyrs = countMartyrsInDateTree(node.right);

		// Count martyrs in the linked list of this date
		int dateMartyrs = countMartyrsInMartyrLinkedList(node.getMartyrLinkedList());

		// Add martyrs in the current node and those from the subtrees to the total
		// count
		return leftMartyrs + rightMartyrs + dateMartyrs;
	}

	// Helper method to count martyrs in a linked list of martyrs
	private int countMartyrsInMartyrLinkedList(MartyrLinkedList martyrLinkedList) {
		int totalMartyrs = 0;
		MartyrNode current = martyrLinkedList.first;
		// Iterate through the linked list and count martyrs
		while (current != null) {
			totalMartyrs++;
			current = current.next;
		}
		return totalMartyrs;
	}

	public LocationNode searchLocation(String locationName) {
		return dnode.getLocaionTree().searchLocation(locationName, dnode.getLocaionTree().getRoot());
	}
//	public String getMaxDate() {
//		Queue q = new Queue();
//		q.inQueue(currentLocation);
//	}

//	public int getMaxMartyrs(DateNode node) {
//		if (node == null)
//			return 0;
//		return Math.max(Math.max(getMaxMartyrs(node.left), getMaxMartyrs(node.right)), node.getMartyrLinkedList().size());
//		

	public static void main(String[] args) {
		launch(args);
	}
}
