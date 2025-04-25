package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Main extends Application {
	MartyrLinkedList ml = new MartyrLinkedList();
//	DistrictNode districtNode = new DistrictNode("AL_Quds", null, null, null);
//	DistrictTree DT = new DistrictTree(districtNode);
	File f;
	DistrictTree DT = new DistrictTree();
	// LocationTree LT = new LocationTree();
	Stack<DistrictNode> stack1 = new Stack<>();
	Stack<DistrictNode> stack2 = new Stack<>();
	DistrictNode currentDistrict;

	@Override
	public void start(Stage primaryStage) {
		Button fileButton = new Button(" Upload data ");
		TextField sDistrict = new TextField();
		Text founded = new Text();
		Button search = new Button("Search");
		Button save = new Button(" Save ");
		HBox hbox1 = new HBox();
		VBox vbox1 = new VBox();
		vbox1.getChildren().addAll(sDistrict, founded);
		hbox1.getChildren().addAll(fileButton, vbox1, search, save);
		vbox1.setSpacing(10);
		hbox1.setSpacing(60);
		Text line = new Text(
				"_________________________________________________________________________________________________________________________________________________________");
		VBox vbox2 = new VBox();
		vbox2.getChildren().addAll(hbox1, line);
		Button prev = new Button("Previous");
		Button next = new Button("Next");
		Text current = new Text();

		current.setStrokeWidth(30);
		HBox hbox2 = new HBox();
		hbox2.getChildren().addAll(prev, current, next);
		hbox2.setSpacing(80);
		Text total = new Text("The total number of martyrs is :");
		Text total1 = new Text();
		HBox hbox3 = new HBox();
		hbox3.getChildren().addAll(total, total1);
		hbox3.setSpacing(30);

		Text Dname = new Text(" District Name ");
		TextField DN = new TextField();
		Button insert = new Button("Insert");
		VBox vbox4 = new VBox();
		vbox4.getChildren().addAll(Dname, DN, insert);
		Text UD = new Text("Updated district");
		TextField DA = new TextField();
		Button update = new Button("Update");
		VBox vbox5 = new VBox();
		vbox5.getChildren().addAll(UD, DA, update);
		Button delete = new Button("Delete");
		HBox hbox4 = new HBox();
		hbox4.getChildren().addAll(vbox4, vbox5, delete);
		hbox4.setSpacing(50);
		Button gotolocation = new Button(" GO to location screen ");
		VBox vbox3 = new VBox(hbox2, hbox3, hbox4, gotolocation);
		vbox3.setSpacing(60);

		BorderPane pane = new BorderPane();
		pane.setTop(vbox2);
		pane.setCenter(vbox3);
		BorderPane.setAlignment(vbox3, Pos.CENTER);
		fileButton.setOnAction(event -> {

			FileChooser fileChooser = new FileChooser();
			f = fileChooser.showOpenDialog(primaryStage);
			readFile();
			fillStack(current.getText());
			DistrictNode smallest = stack1.peek();
			if (smallest != null) {
				current.setText(smallest.DistrictName);
				currentDistrict = smallest;
				int totalMartyrs = calculateTotalMartyrs(currentDistrict.DistrictName);
				total1.setText(Integer.toString(totalMartyrs)); // Update total martyrs text
			} else {
				current.setText("No districts available");
			}
		});

		next.setOnAction(event -> {
			if (stack1.isEmpty() && stack2.isEmpty()) {
				fillStack(current.getText());
			}
			if (stack1.size() > 1) {
				if (!current.getText().equals("No previous district available")) {
					stack2.push(stack1.pop());
				}
				currentDistrict = stack1.peek();
				current.setText(currentDistrict.DistrictName);
				// Calculate total martyrs for the new current district
				int totalMartyrs = calculateTotalMartyrs(currentDistrict.DistrictName);
				total1.setText(Integer.toString(totalMartyrs)); // Update total martyrs text
			} else {
				current.setText("No next district available");
				total1.setText(""); // Clear total martyrs text when no district is available
			}
		});

		prev.setOnAction(event -> {
			if (stack2.isEmpty()) {
				fillStack(current.getText());
			}
			if (!stack2.isEmpty()) {
				if (!current.getText().equals("No next district available")) {
					// if (!stack2.isEmpty()) {
					currentDistrict = stack2.pop();

					stack1.push(currentDistrict);
				}
				if (!stack1.isEmpty()) {
					current.setText(stack1.peek().DistrictName);
					// Calculate total martyrs for the new current district
					int totalMartyrs = calculateTotalMartyrs(stack1.peek().DistrictName);
					total1.setText(Integer.toString(totalMartyrs)); // Update total martyrs text
				} else {
					current.setText("");
					total1.setText(""); // Clear total martyrs text when no district is available
				}
			} else {
				current.setText("No previous district available");
				total1.setText(""); // Clear total martyrs text when no district is available
			}
		});

		search.setOnAction(event -> {
			String districtName = sDistrict.getText(); // Get the district name from the text field

			// Search for the district in the district tree
			DistrictNode foundDistrict = searchDistrict(districtName);

			// Check if the district was found
			if (foundDistrict != null) {
				founded.setText("Founded"); // Update label to indicate district was found
				current.setText(foundDistrict.DistrictName); // Update current district label
				// Calculate total martyrs for the found district
				int totalMartyrs = calculateTotalMartyrs(foundDistrict.DistrictName);
				total1.setText(Integer.toString(totalMartyrs)); // Update total martyrs text
			} else {
				founded.setText("Not founded"); // Update label to indicate district was not found
				current.setText(""); // Clear current district label
				total1.setText(""); // Clear total martyrs text
			}
		});
		insert.setOnAction(event -> {
			String districtName = DN.getText(); // Get the district name from the text field

			// Insert the new district into the district tree
			insertDistrict(districtName);
		});
		delete.setOnAction(event -> {
			String districtName = current.getText();
			deleteDistrict(districtName);
		});
		update.setOnAction(event -> {
			String oldName = current.getText();
			DT.updateDistrictName(oldName, DA.getText());
			DT.printDistrictTree(DT.getRoot());

			System.out.println("__________________________________");
		});
		gotolocation.setOnAction(event -> {

			try {
				LocationScreen locationScreen = new LocationScreen();
				locationScreen.start(new Stage());
				locationScreen.setDnode(currentDistrict);
				
               DateNode date =locationScreen.stack1.peek().getDateTree().findEarliestDate(locationScreen.stack1.peek().getDateTree().getRoot());
             locationScreen.eartext.setText(date.toString());
             DateNode date1 =locationScreen.stack1.peek().getDateTree().findLatestDate(locationScreen.stack1.peek().getDateTree().getRoot());
             locationScreen.lateText.setText(date1.toString());
           //  DateNode date2 =locationScreen.stack1.peek().getDateTree().getMaxDate();
             locationScreen.maxText.setText(locationScreen.stack1.peek().getDateTree().getMaxDate());
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			

		});
		Scene scene = new Scene(pane, 600, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("District Screen");
		primaryStage.show();

	}

	public void readFile() {
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String[] firstLine = br.readLine().split(",");

			String line;
			while ((line = br.readLine()) != null) {
				String[] tokens = line.split(",");
				
				
				  // Check if any cell in the row is empty
                boolean isEmptyCell = false;
                for (String token : tokens) {
                    if (token.isEmpty()) {
                        isEmptyCell = true;
                        break;
                    }
                }

                // If any cell is empty, skip this row
                if (isEmptyCell) {
                    continue;
                }
				
				
				String name = tokens[0];
				String dateString = tokens[1]; // Example string representing a date

				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Date date = null;
				try {
					date = dateFormat.parse(dateString);
					// System.out.println("Parsed date: " + date);
				} catch (ParseException e) {
					System.out.println("Invalid date format");
				}
				int age = Integer.parseInt(tokens[2]);
				String location = tokens[3];
				String district = tokens[4];
				String gender = tokens[5];
				Martyr m = new Martyr(name, age, gender);
				// ml.addMartyr(m);
				// ml.print();
				MartyrNode martyrNode = new MartyrNode(m);
				MartyrLinkedList martyrList = new MartyrLinkedList(martyrNode);
				DateNode dateNode = new DateNode(date, martyrList, null, null);
				DateTree DateT = new DateTree(dateNode);

				LocationNode locationNode = new LocationNode(location, DateT, null, null);
				LocationTree LT = new LocationTree(locationNode);
				DistrictNode districtNode = new DistrictNode(district, LT, null, null);

				// DistrictTree DT = new DistrictTree(districtNode);
				DT.insertDistrict(districtNode, DT.getRoot());

				// DT.printDistrictTree(DT.getRoot());
				districtNode = DT.searchDistrict(district, DT.getRoot());
				districtNode.getLocaionTree().insertLocation(locationNode, districtNode.getLocaionTree().getRoot());

				locationNode = districtNode.getLocaionTree().searchLocation(location,
						districtNode.getLocaionTree().getRoot());
				locationNode.getDateTree().insertDate(dateNode, locationNode.getDateTree().getRoot());
				dateNode = locationNode.getDateTree().searchDate(date, locationNode.getDateTree().getRoot());
				dateNode.getMartyrLinkedList().addMartyr(m);
				// DT.searchDistrict(district,
				// DT.getRoot()).getLocaionTree().insertLocation(locationNode, LT.getRoot());
				// LT.printLocationTree(LT.getRoot());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		DT.printDistrictTree(DT.getRoot());

		// System.out.println("__________________________________");
		// System.out.println(DT.searchDistrict("al-Quds", DT.getRoot()).DistrictName);

	}

	public DistrictNode findSmallest(DistrictNode node) {
		if (node == null) {
			return null;
		}
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	private void fillStackInOrder(DistrictNode node) {
		if (node == null) {
			return;
		}

		fillStackInOrder(node.right);
		stack1.push(node);
		fillStackInOrder(node.left);
	}

	public void fillStack(String startingDistrict) {
		stack1.clear();
		fillStackInOrder(DT.getRoot());
		if (startingDistrict != null && !startingDistrict.isEmpty()) {
			while (!stack1.isEmpty() && !stack1.peek().DistrictName.equals(startingDistrict)) {
				stack2.push(stack1.pop());
			}
			if (!stack1.isEmpty()) {
				currentDistrict = stack1.pop();
				stack2.push(currentDistrict);
			}
		}
		stack1.addAll(stack2);
		stack2.clear();
	}

	public int calculateTotalMartyrs(String districtName) {
		DistrictNode districtNode = DT.searchDistrict(districtName, DT.getRoot());
		if (districtNode == null) {
			return 0; // District not found, return 0 martyrs
		}

		int totalMartyrs = 0;

		// Traverse through the location tree of the district
		totalMartyrs = countMartyrsInLocationTree(districtNode.getLocaionTree().getRoot());

		return totalMartyrs;
	}

	// Helper method to count martyrs in a location tree
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

	public DistrictNode searchDistrict(String districtName) {
		return DT.searchDistrict(districtName, DT.getRoot());
	}

	public void insertDistrict(String districtName) {
		// Check if the district name is not empty
		if (!districtName.isEmpty()) {
			// Create a new DistrictNode with the provided district name
			DistrictNode newDistrict = new DistrictNode(districtName, new LocationTree(), null, null);

			// Insert the new district into the binary search tree
			DT.insertDistrict(newDistrict, DT.getRoot());

		}
		// DT.printDistrictTree(DT.getRoot());

		System.out.println("__________________________________");
	}

	public void deleteDistrict(String districtName) {
		if (!districtName.isEmpty()) {
			DT.deleteHelper(DT.getRoot(), districtName);

		}
		DT.printDistrictTree(DT.getRoot());

		System.out.println("__________________________________");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
