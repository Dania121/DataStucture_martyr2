package application;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Stack;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MartyrScreen extends Application {
	Stack<DateNode> stack1 = new Stack<>();
	Stack<DateNode> stack2 = new Stack<>();
	private LocationNode Lnode;
	Text dateText;
	DateNode currentdate;
	TableView<Martyr> tv;
	ObservableList<Martyr> data;
	Text avg1;
	Text youngest1;
	Text oldest1;
	TextField name1;
	TextField age1;
	TextField gender1;
	Alert alert;
	Alert alert2 = new Alert(AlertType.ERROR);

	public LocationNode getLnode() {
		return Lnode;
	}

	public void setLnode(LocationNode lnode) {
		this.Lnode = lnode;
		fillStack(Lnode.getDateTree().getRoot().date);
	}

	public DateNode getCurrentdate() {
		return currentdate;
	}

	public void setCurrentdate(DateNode currentdate) {
		this.currentdate = currentdate;
		fillTv();
	}

	@Override
	public void start(Stage primaStage) throws Exception {
		// TODO Auto-generated method stub

		Button next = new Button("Next");
		dateText = new Text();
		Button prev = new Button("Previous");
		HBox hbox1 = new HBox();
		hbox1.getChildren().addAll(prev, dateText, next);
		hbox1.setSpacing(70);
		Label avg = new Label("The average martyr ages in this date is : ");
		avg1 = new Text();
		HBox hbox2 = new HBox();
		hbox2.getChildren().addAll(avg, avg1);
		hbox2.setSpacing(70);
		Label youngest = new Label("The youngest martyr in this date is : ");
		youngest1 = new Text();
		HBox hbox3 = new HBox();
		hbox3.getChildren().addAll(youngest, youngest1);
		hbox3.setSpacing(70);
		Label oldest = new Label("The oldest martyr in this date is : ");
		oldest1 = new Text();
		HBox hbox4 = new HBox();
		hbox4.getChildren().addAll(oldest, oldest1);
		hbox4.setSpacing(70);

		Label name = new Label("Martyr Name");
		name1 = new TextField();
		VBox vbox1 = new VBox();
		vbox1.getChildren().addAll(name, name1);
		vbox1.setSpacing(20);

		Label age = new Label("Martyr age");
		age1 = new TextField();
		VBox vbox2 = new VBox();
		vbox2.getChildren().addAll(age, age1);
		vbox2.setSpacing(20);

		Label gender = new Label("Martyr gender");
		gender1 = new TextField();
		VBox vbox3 = new VBox();
		vbox3.getChildren().addAll(gender, gender1);
		vbox3.setSpacing(20);
		DatePicker d = new DatePicker();
		Label d1 = new Label("Date of martyrdom");
		VBox vbox4 = new VBox();
		vbox4.getChildren().addAll(d1, d);
		vbox4.setSpacing(20);
		HBox hbox5 = new HBox();
		hbox5.getChildren().addAll(vbox1, vbox2, vbox3, vbox4);
		hbox5.setSpacing(50);
		Button insert = new Button("insert");
		Button update = new Button("update");
		Button delete = new Button("delete");
		HBox hbox6 = new HBox();
		hbox6.getChildren().addAll(insert, update, delete);
		hbox6.setSpacing(100);
//		VBox vbox = new VBox();
//		vbox.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox5);
//		vbox.setSpacing(50);

		next.setOnAction(e -> {
			if (stack1.size() >= 1) {
				if (!dateText.getText().equals("No previous date available")) {
					stack2.push(stack1.pop());
				}
				if (stack1.isEmpty())
					stack1.push(stack2.pop());
				DateNode nextDate = currentdate = stack1.peek();
				dateText.setText(new SimpleDateFormat("MM/dd/yyyy").format(nextDate.date));
				avg1.setText(Double.toString(calculateAvgMartyrAges(nextDate)));
				youngest1.setText(youngestMartyr(nextDate));
				oldest1.setText(oldestMartyr(nextDate));
				fillTv();
			} else {
				dateText.setText("No next date available");
			}
		});

		prev.setOnAction(e -> {
			if (!stack2.isEmpty()) {
				if (!dateText.getText().equals("No next date available"))
					stack1.push(stack2.pop());
				DateNode prevDate = currentdate = stack1.peek();
				dateText.setText(new SimpleDateFormat("MM/dd/yyyy").format(prevDate.date));
				avg1.setText(Double.toString(calculateAvgMartyrAges(prevDate)));
				youngest1.setText(youngestMartyr(prevDate));
				oldest1.setText(oldestMartyr(prevDate));
				fillTv();
			} else {
				dateText.setText("No previous date available");
			}
		});
//		insert.setOnAction(event -> {
//			String name2 = name1.getText();
//			String ageStr = age1.getText();
//			String gender2 = gender1.getText();
//
//			LocalDate selectedDate = d.getValue();
//
//			// Check if ageStr is a valid integer
//			int age2 = 0;
//			try {
//				age2 = Integer.parseInt(ageStr);
//			} catch (NumberFormatException e) {
//				// Handle the case where ageStr is not a valid integer
//				// You can show an error message to the user or handle it as per your
//				// requirement
//				System.out.println("Invalid age!");
//				return;
//			}
//
//			Martyr martyr = new Martyr(name2, age2, gender2);
//
//			Date date = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//			DateNode existingDateNode = Lnode.getDateTree().searchDate(date, Lnode.getDateTree().getRoot());
//
//			if (existingDateNode != null) {
//				// Date node already exists, add martyr to the existing date node
//				existingDateNode.getMartyrLinkedList().addMartyr(martyr);
//			} else {
//				// Date node doesn't exist, create a new one and add martyr to it
//				MartyrLinkedList newMartyrLinkedList = new MartyrLinkedList();
//				newMartyrLinkedList.addMartyr(martyr);
//				DateNode newDateNode = new DateNode(date, newMartyrLinkedList, null, null);
//				Lnode.getDateTree().insertDate(newDateNode, Lnode.getDateTree().getRoot());
//			}
//
//			System.out.println("-----------------------------------------------------------------------------");
//			Lnode.getDateTree().printDateTree(Lnode.getDateTree().getRoot());
//		});

//		insert.setOnAction(event -> {
//			String name2 = name1.getText();
//			String ageStr = age1.getText();
//			String gender2 = gender1.getText();
//
//			LocalDate selectedDate = d.getValue();
//
//			// Check if ageStr is a valid integer
//			int age2 = 0;
//			try {
//				age2 = Integer.parseInt(ageStr);
//			} catch (NumberFormatException e) {
//				// Handle the case where ageStr is not a valid integer
//				// You can show an error message to the user or handle it as per your
//				// requirement
//				System.out.println("Invalid age!");
//				return;
//			}
//
//			Martyr martyr = new Martyr(name2, age2, gender2);
//
//			Date date = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//			DateNode existingDateNode = Lnode.getDateTree().searchDate(date, Lnode.getDateTree().getRoot());
//
//			if (existingDateNode != null) {
//				// Date node already exists, add martyr to the existing date node
//				existingDateNode.getMartyrLinkedList().addMartyr(martyr);
//			} else {
//				// Date node doesn't exist, create a new one and add martyr to it
//				DateNode newDateNode = new DateNode(date, new MartyrLinkedList(), null, null);
//				newDateNode.getMartyrLinkedList().addMartyr(martyr);
//				Lnode.getDateTree().insertDate(newDateNode, Lnode.getDateTree().getRoot());
//			}
//
//			System.out.println("-----------------------------------------------------------------------------");
//			Lnode.getDateTree().printDateTree(Lnode.getDateTree().getRoot());
//		});

		insert.setOnAction(event -> {
			String name2 = name1.getText();
			String ageStr = age1.getText();
			String gender2 = gender1.getText();

			LocalDate selectedDate = d.getValue();

			// Check if ageStr is a valid integer and within the valid range
			int age2 = 0;
			try {
				age2 = Integer.parseInt(ageStr);
				if (age2 <= 0 || age2 >= 130) {
					// Age is not within the valid range, show an alert
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Invalid Age");
					alert.setHeaderText(null);
					alert.setContentText("Age must be between 1 and 129.");
					alert.showAndWait();
					return; // Exit the method if age is not valid
				}
			} catch (NumberFormatException e) {
				// Handle the case where ageStr is not a valid integer
				// Show an error message to the user or handle it as per your requirement
				System.out.println("Invalid age!");
				return;
			}

			Martyr martyr = new Martyr(name2, age2, gender2);
			data.add(martyr);
			Date date = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			DateNode existingDateNode = Lnode.getDateTree().searchDate(date, Lnode.getDateTree().getRoot());

			if (existingDateNode != null) {
				// Date node already exists, add martyr to the existing date node
				existingDateNode.getMartyrLinkedList().addMartyr(martyr);
			} else {
				// Date node doesn't exist, create a new one and add martyr to it
				DateNode newDateNode = new DateNode(date, new MartyrLinkedList(), null, null);
				newDateNode.getMartyrLinkedList().addMartyr(martyr);
				Lnode.getDateTree().insertDate(newDateNode, Lnode.getDateTree().getRoot());
			}
			// data.add(martyr);
			System.out.println("-----------------------------------------------------------------------------");
			Lnode.getDateTree().printDateTree(Lnode.getDateTree().getRoot());
		});

		update.setOnAction(event -> {
			Martyr martyr = tv.getSelectionModel().getSelectedItem();
			// if (name1.getText().isBlank() || age1.getText().isBlank()) {
			// alert2.setContentText("Fill in all the fields.");
			// alert2.showAndWait();
			// } else {
			// if (name1.getText().equals(martyr.getName())) {
			try {
				int newAge = Integer.parseInt(age1.getText());
				if (newAge <= 0 || newAge > 130)
					throw new NumberFormatException();
				martyr.setAge(newAge);
				martyr.setName(name1.getText());
				martyr.setGender(gender1.getText());
				data.clear();
				DateNode node = stack1.peek();
				avg1.setText(Double.toString(calculateAvgMartyrAges(node)));
				youngest1.setText(youngestMartyr(node));
				oldest1.setText(oldestMartyr(node));
				fillTv();
				System.out.println("------------------------------------------------------");
				Lnode.getDateTree().printDateTree(Lnode.getDateTree().getRoot());
			} catch (NumberFormatException e) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setContentText("Please enter a valid age (1-130).");
				errorAlert.showAndWait();
			}
			// }
			// }
		});

		delete.setOnAction(event -> {
			Martyr martyr = tv.getSelectionModel().getSelectedItem();
			data.remove(martyr);
			stack1.peek().getMartyrLinkedList().delete(martyr);
			System.out.println("------------------------------------------------------");
			Lnode.getDateTree().printDateTree(Lnode.getDateTree().getRoot());
		});

		tv = new TableView<Martyr>();
		data = FXCollections.observableArrayList();
		TableColumn<Martyr, String> nameColumn = new TableColumn("name");
		TableColumn<Martyr, Integer> ageColumn = new TableColumn("age");
		TableColumn<Martyr, String> ganderColumn = new TableColumn("gender");
		tv.getColumns().addAll(nameColumn, ageColumn, ganderColumn);
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
		ganderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
		nameColumn.setStyle("-fx-alignment: CENTER;");
		ageColumn.setStyle("-fx-alignment: CENTER;");
		ganderColumn.setStyle("-fx-alignment: CENTER;");
		tv.setItems(data);
		tv.setMaxWidth(400);
		tv.setMaxHeight(400);
		// fillTv();
		VBox vbox = new VBox();
		vbox.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox5, tv, hbox6);
		vbox.setSpacing(50);
		BorderPane pane = new BorderPane();
		// pane.setBottom(tv);
//	    BorderPane.setAlignment(tv, Pos.CENTER);
//	    BorderPane.setMargin(tv, new Insets(20));

		pane.setCenter(vbox);
		Scene scene = new Scene(pane, 600, 600);
		primaStage.setScene(scene);
		primaStage.show();
	}

	private void fillStackInOrder(DateNode node) {
		if (node == null) {
			return;
		}

		fillStackInOrder(node.left);
		stack1.push(node);
		fillStackInOrder(node.right);
	}

	public void fillStack(Date startingDate) {
		stack1.clear();
		fillStackInOrder(Lnode.getDateTree().getRoot());

		while (!stack1.isEmpty()) {
			stack2.push(stack1.pop());
		}

		boolean foundStartingDate = false;
		while (!stack2.isEmpty()) {
			DateNode node = stack2.pop();
			stack1.push(node);
			if (!foundStartingDate && node.date.equals(startingDate)) {
				currentdate = node;
				foundStartingDate = true;
			}
		}
		fillTv();
	}

	public double calculateAvgMartyrAges(DateNode date) {
		double avg = 0;
		int sum = 0;
		int count = 0;

		MartyrNode node = date.getMartyrLinkedList().first;
		while (node != null) {
			int age = node.getMartyr().getAge();
			sum += age;
			count++;
			node = node.next;
		}

		if (count != 0) {
			avg = (double) sum / count;
		}

		return avg;
	}

	public String youngestMartyr(DateNode date) {
		MartyrNode node = date.getMartyrLinkedList().first;
		if (node == null) {
			return "No martyrs found";
		}
		String name = node.getMartyr().getName();
		return name;
	}

	public String oldestMartyr(DateNode date) {
		MartyrNode node = date.getMartyrLinkedList().last;
		if (node == null) {
			return "No martyrs found";
		}
		String name = node.getMartyr().getName();
		return name;
	}

	public void fillTv() {
		MartyrNode node = currentdate.getMartyrLinkedList().first;
		BinarySearchTree martyrBST = new BinarySearchTree();
		data.clear();
		tv.getItems().clear();
		while (node != null) {
			System.out.println((Martyr) node.getMartyr());
			martyrBST.insert((Martyr) node.getMartyr());
			node = node.next;
		}
		fillData(martyrBST.getRoot());
	}

	public void fillData(BSTNode node) {
		if (node == null)
			return;
		fillData(node.right);
		data.add((Martyr) node.data);
		fillData(node.left);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
