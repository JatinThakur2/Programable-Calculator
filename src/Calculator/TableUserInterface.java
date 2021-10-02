package Calculator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import myOwnApi.data;

/**
 * <p>
 * Title: TableUserInterface Class.
 * </p>
 * 
 * <p>
 * Description: A component of a JavaFX demonstration application that performs
 * CRUD operations
 * </p>
 * 
 * <p>
 * Copyright: Lynn Robert Carter © 2020
 * </p>
 * 
 * @author Jatin Thakur
 * 
 * @version 4.07 2020-03-12 Contains all the components of the Table Window
 * 
 */

public class TableUserInterface extends Application {

	private static int selectedRow = -1;
	private static data selectedData = null;

	// Attributes
	private File file;
	private Button button_Add = new Button("Add");
	private Button button_Delete = new Button("Delete");
	private Button button_Exit = new Button("Exit");
	private Button button_Search = new Button("Search");
	private Button button_Update = new Button("Update");
	private TextField text_Search = new TextField();
	@SuppressWarnings("rawtypes")
	private TableColumn name, measuredValue, errorvalue, unit, type;

	@SuppressWarnings("rawtypes")
	private final TableView table = new TableView<>();
	String preData, postData;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void start(Stage stage) {
		stage.setTitle("Table Window");
		setupButtonUI(button_Add, "Arial", 18, 100, Pos.CENTER, 10, 430, true);
		setupButtonUI(button_Delete, "Arial", 18, 100, Pos.CENTER, 125, 430, true);
		setupButtonUI(button_Exit, "Arial", 18, 100, Pos.CENTER, 360, 430, true);
		setupButtonUI(button_Search, "Arial", 18, 100, Pos.CENTER, 250, 470, true);
		setupButtonUI(button_Update, "Arial", 18, 100, Pos.CENTER, 250, 430, true);
		setupTextUI(text_Search, "Arial", 18, 100, Pos.CENTER, 10, 470, true);
		Scene scene = new Scene(new Group());
		stage.setWidth(480);
		stage.setHeight(560);

		table.setEditable(true);
		button_Update.setDisable(true);
		button_Delete.setDisable(true);

		button_Add.setOnAction(event -> {
			addDataIntoTable();
		});

		button_Exit.setOnAction(event -> {
			stage.close();
		});

		button_Update.setOnAction(event -> {
			update();
		});

		button_Delete.setOnAction(event -> {
			String deleteRow = selectedData.getName() + "," + selectedData.getMeasuredValue() + ","
					+ selectedData.getErrorValue() + "," + selectedData.getUnit() + "," + selectedData.getType();
			try {
				table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
				deleteData(deleteRow);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		button_Search.setOnAction(event -> {
			selectedRow = -1;
			button_Delete.setDisable(true);
			button_Update.setDisable(true);
			ArrayList<data> list = data.readDataFromFile();
			for (int i = 0; i <= list.size() - 1; i++) {
				if (list.get(i).getName().equals(text_Search.getText())) {
					table.getSelectionModel().select(i);
					selectedRow = i;
					selectedData = list.get(i);
					highlightTableRow();
					button_Delete.setDisable(false);
					button_Update.setDisable(false);
					return;
				}
			}
		});

		name = new TableColumn<>("Name");
		name.setMinWidth(100);

		type = new TableColumn<>("Type");
		type.setMinWidth(100);

		measuredValue = new TableColumn("MV");
		errorvalue = new TableColumn("ET");
		unit = new TableColumn("Unit");

		table.getColumns().addAll(name, measuredValue, errorvalue, unit, type);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(table);

		((Group) scene.getRoot()).getChildren().addAll(vbox, button_Add, button_Delete, button_Exit, button_Search,
				button_Update, text_Search);

		/**********
		 * Private local method to initialize the standard fields for a button
		 */

		name.setOnEditStart(event -> {
			getInitialRowData();
		});

		measuredValue.setOnEditStart(event -> {
			getInitialRowData();
		});

		errorvalue.setOnEditStart(event -> {
			getInitialRowData();
		});

		unit.setOnEditStart(event -> {
			getInitialRowData();
		});

		type.setOnEditStart(event -> {
			getInitialRowData();
		});

		name.setOnEditCommit(new EventHandler<CellEditEvent<data, String>>() {
			@Override
			public void handle(CellEditEvent<data, String> event) {
				((data) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setName(event.getNewValue());
				TablePosition<data, String> pos = event.getTablePosition();
				String newName = event.getNewValue();
				int row = pos.getRow();
				data data = event.getTableView().getItems().get(row);
				data.setName(newName);
			}
		});

		measuredValue.setOnEditCommit(new EventHandler<CellEditEvent<data, String>>() {
			@Override
			public void handle(CellEditEvent<data, String> event) {
				((data) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setName(event.getNewValue());
				TablePosition<data, String> pos = event.getTablePosition();
				String newName = event.getNewValue();
				int row = pos.getRow();
				data data = event.getTableView().getItems().get(row);
				data.setName(newName);
			}
		});
		errorvalue.setOnEditCommit(new EventHandler<CellEditEvent<data, String>>() {
			@Override
			public void handle(CellEditEvent<data, String> event) {
				((data) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setName(event.getNewValue());
				TablePosition<data, String> pos = event.getTablePosition();
				String newName = event.getNewValue();
				int row = pos.getRow();
				data data = event.getTableView().getItems().get(row);
				data.setName(newName);
			}
		});
		unit.setOnEditCommit(new EventHandler<CellEditEvent<data, String>>() {
			@Override
			public void handle(CellEditEvent<data, String> event) {
				((data) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setName(event.getNewValue());
				TablePosition<data, String> pos = event.getTablePosition();
				String newName = event.getNewValue();
				int row = pos.getRow();
				data data = event.getTableView().getItems().get(row);
				data.setName(newName);
			}
		});
		type.setOnEditCommit(new EventHandler<CellEditEvent<data, String>>() {
			@Override
			public void handle(CellEditEvent<data, String> event) {
				((data) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setName(event.getNewValue());
				TablePosition<data, String> pos = event.getTablePosition();
				String newName = event.getNewValue();
				int row = pos.getRow();
				data data = event.getTableView().getItems().get(row);
				data.setName(newName);
			}
		});

		file = new File("Repository/data.txt");
		stage.setScene(scene);
		stage.show();
		addValuesToTheTable();
	}

	private void setupTextUI(TextField b, String ff, double f, double w, Pos p, double x, double y, boolean c) {
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);

	}

	private void addDataIntoTable() {
		UserInterface obj = new UserInterface();
		obj.definition();

	}

	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y, boolean c) {
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);
	}

	@SuppressWarnings("unchecked")
	private void addValuesToTheTable() {

		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		measuredValue.setCellValueFactory(new PropertyValueFactory<>("measuredValue"));
		errorvalue.setCellValueFactory(new PropertyValueFactory<>("errorValue"));
		unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
		type.setCellValueFactory(new PropertyValueFactory<>("type"));

		ArrayList<data> dataItems = data.readDataFromFile();
		for (int i = 0; i <= dataItems.size() - 1; i++) {
			table.getItems().add(dataItems.get(i));
		}

	}

	//Method used to delete the data from the table
	private void deleteData(String deleteRow) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(file)); // Read the repository file
		String currentLine = reader.readLine(); // Get the first line from the repository
		String newContent = new String(); // String for storing all the lines apart from the line to be deleted

		// Comparing the line to be deleted with the current line read from the
		// Repository
		while (currentLine != null) {
			if (currentLine.equals(deleteRow)) {
			} else
				newContent += currentLine + "\n"; // Adding other lines to the string object
			currentLine = reader.readLine(); // Get next line from repository
		}
		// Create a writer for the same repository
		// but with no append to the previous version
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
		writer.write(newContent); // Add the left out entries
		writer.close(); // Close the writer
		reader.close();
	}

	@SuppressWarnings("unchecked")
	// Method used to highlight the cell of the table once it is searched
	private void highlightTableRow() {
		ObservableMap<Integer, Boolean> editable = FXCollections.observableHashMap();
		editable.put(selectedRow, Boolean.TRUE);

		name.setCellFactory(
				StateTextFieldTableCell.forTableColumn(j -> Bindings.valueAt(editable, j).isEqualTo(Boolean.TRUE)));
		measuredValue.setCellFactory(
				StateTextFieldTableCell.forTableColumn(j -> Bindings.valueAt(editable, j).isEqualTo(Boolean.TRUE)));
		errorvalue.setCellFactory(
				StateTextFieldTableCell.forTableColumn(j -> Bindings.valueAt(editable, j).isEqualTo(Boolean.TRUE)));
		unit.setCellFactory(
				StateTextFieldTableCell.forTableColumn(j -> Bindings.valueAt(editable, j).isEqualTo(Boolean.TRUE)));
		type.setCellFactory(
				StateTextFieldTableCell.forTableColumn(j -> Bindings.valueAt(editable, j).isEqualTo(Boolean.TRUE)));
		table.setRowFactory(t -> new TableRow<data>() {
			@Override
			public void updateItem(data item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setStyle("");
				} else if (item.getName().equals(selectedData.getName())) {
					setStyle("-fx-background-color: lightblue;");
				} else {
					setStyle("");
				}
			}
		});
	}
	
	//Getting the initial data of the row before the edit
	private void getInitialRowData() {
		// Get the row data from table
		data rowData = (data) table.getSelectionModel().getSelectedItem();
		preData = rowData.getName() + "," + rowData.getMeasuredValue() + "," + rowData.getErrorValue() + ","
				+ rowData.getUnit() + "," + rowData.getType();
	}

	/**
	 * Method to get the row data after the editing is done
	 */
	private void update() {
		data rowData = (data) table.getSelectionModel().getSelectedItem();
		postData = rowData.getName() + "," + rowData.getMeasuredValue() + "," + rowData.getErrorValue() + ","
				+ rowData.getUnit() + "," + rowData.getType();
		try {
			editRowData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void editRowData() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		update(preData, postData);
		writer.close();
	}

	// Method used to update the items in the file and Table
	@SuppressWarnings("resource")
	private void update(String pre, String post) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(file)); // Read the repository file
		String currentLine = reader.readLine(); // Get the first line from the repository
		String newContent = new String(); // String for storing all the lines apart from the line to be deleted

		// Comparing the line to be deleted with the current line read from the
		// Repository
		while (currentLine != null) {
			if (currentLine.equals(pre)) {
				currentLine = post;
				newContent += currentLine + "\n"; // Adding other lines to the string object
			} else
				newContent += currentLine + "\n"; // Adding other lines to the string object

			currentLine = reader.readLine(); // Get next line from repository
		}
		// Create a writer for the same repository
		// but with no append to the previous version
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
		writer.write(newContent); // Add the left out entries
		writer.close(); // Close the writer
	}

}
