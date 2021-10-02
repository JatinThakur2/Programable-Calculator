package Calculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ProgramUserInterface {
	private String str_FileName;
	private Scanner scanner_Input = null;
	private Label message_ErrorDetails = new Label("");
	private String errorMessage_FileContents = "";

	Label assignment1 = new Label("How to write print statement -> print " + "Enter the Value of a");
	Label assignment2 = new Label("How to write input statement -> input a=");
	Label lbl_FileFound = new Label("");
	Label lbl_FileNotFound = new Label("");

	Label label_detail = new Label("Enter the program's file name here:");
	TextField enter = new TextField();
	Button button_load = new Button("Load the programs");
	TextArea data = new TextArea();
	Button button_Add = new Button("Add");
	Button button_Search = new Button("Search");
	Button button_Edit = new Button("Edit");
	Button button_Delete = new Button("Delete");
	Button button_RunExpression = new Button("Run Expression");
	Button button_RunProgram = new Button("Run Program");

	final Stage editDialog = new Stage();
	
	private Label lbl_New = new Label("Add New Entry");
	private Label lbl_Name = new Label("The Name of the Program");
	private TextField fld_Name = new TextField();
	private Label lbl_Content = new Label("Content of Program");
	private TextArea txt_Content = new TextArea();
	private Label lbl_AddProgram = new Label("Add the Program");
	private Button btn_SaveAddedChanges = new Button("Add");
	
	
	private ComboBox<String> editComboBox = new ComboBox<String>();
	private Label lbl_1SelectProgram = new Label("1. Select a Program");
	private Label lbl_2EditProgram = new Label("2. Edit a Program");
	private Label lbl_Save = new Label("3. Save the Program");
	private Button btn_SaveEditChanges = new Button("Save");
	
	
	private Label lbl_Delete = new Label("2. Delete the Program");
	private Button btn_DeletePopup = new Button("Delete");
	private File file = new File("program.txt");
	
	private Label message = new Label("");
	
	public ProgramUserInterface(Pane theRoot) {
		setupLabelUI(assignment1, "Arial", 16, 200, Pos.CENTER, 20, 20);
		assignment1.setTextFill(Color.web("#2ECC71"));

		setupLabelUI(assignment2, "Arial", 16, 200, Pos.CENTER, 20, 60);
		assignment2.setTextFill(Color.web("#2ECC71"));

		setupLabelUI(label_detail, "Arial", 20, 200, Pos.CENTER, 20, 100);

		setupLabelUI(lbl_FileFound, "Arial", 14, 200, Pos.BASELINE_LEFT, 370, 115);

		setupLabelUI(lbl_FileNotFound, "Arial", 14, 200, Pos.BASELINE_LEFT, 370, 115);

		setupButtonUI(button_load, "Symbol", 18, 10, Pos.BASELINE_LEFT, 370, 135);
		setupButtonUI(button_Search, "Symbol", 18, 10, Pos.BASELINE_LEFT, 370, 135);
		setupButtonUI(button_Add, "Symbol", 18, 10, Pos.BASELINE_LEFT, 470, 135);
		setupButtonUI(button_Edit, "Symbol", 14, 10, Pos.BASELINE_LEFT, 380, 55);
		setupButtonUI(button_Delete, "Symbol", 14, 10, Pos.BASELINE_LEFT, 450, 55);
		setupButtonUI(button_RunExpression, "Symbol", 14, 10, Pos.BASELINE_LEFT, 530, 55);
		setupButtonUI(button_RunProgram, "Symbol", 14, 10, Pos.BASELINE_LEFT, 530, 95);

		setupTextUI(enter, "Arial", 18, 300, Pos.BASELINE_LEFT, 20, 140, true);

		setupTextAreaUI(data, "Arial", 14, 20, 220, 650, 520, false);

		enter.textProperty().addListener((observable, oldValue, newValue) -> {
			checkFileValidation();
		});

		button_load.setDisable(true);
		button_Search.setVisible(false);
		button_Add.setVisible(false);
		button_Edit.setVisible(false);
		button_Delete.setVisible(false);
		button_RunExpression.setVisible(false);
		button_RunProgram.setVisible(false);
		
		
		button_load.setOnAction(event -> {
			fileOpener();
		});
		
		button_Add.setOnAction(event -> {
			additionPopUp();
		});
		
		button_Edit.setOnAction(event -> {
			editPopUp();
		});
		
		button_Delete.setOnAction(event -> {
			deletePopUp();
		});

		theRoot.getChildren().addAll(label_detail, enter, button_load, data, assignment1, assignment2, lbl_FileFound,
				lbl_FileNotFound, button_Search,button_Add,button_Edit,button_Delete,button_RunExpression,button_RunProgram);

	}

	/**********
	 * Private local method to initialize the standard fields for a label
	 */
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y) {
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);
	}

	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e) {
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);
		t.setEditable(e);
	}

	/**********
	 * Private local method to initialize the standard fields for a button
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y) {
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);
	}

	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextAreaUI(TextArea t, String ff, double f, double x, double y, double w, double h, boolean e) {
		t.setFont(Font.font(ff, f));
		t.setPrefWidth(w);
		t.setPrefHeight(h);
		t.setLayoutX(x);
		t.setLayoutY(y);
		t.setEditable(e);
		t.setWrapText(true);
	}

	private void checkFileValidation() {
		str_FileName = enter.getText().trim(); // Whenever the text area for the file name is changed
		if (str_FileName.length() <= 0) { // this routine is called to see if it is a valid filename.
			lbl_FileFound.setText(""); // Reset the messages
			lbl_FileNotFound.setText(""); // to empty
			scanner_Input = null;
		} else // If there is something in the file name text area
			try { // this routine tries to open it and establish a scanner.
				scanner_Input = new Scanner(new File(str_FileName));

				// There is a readable file there... this code checks the data to see if it is
				// valid
				// for this application (Basic user input errors are GUI issues, not analysis
				// issues.)
				if (fileContentsAreValid()) {
					lbl_FileFound.setText("File found and the contents are valid!");
					lbl_FileFound.setTextFill(Color.web("#2ECC71"));
					message_ErrorDetails.setText("");
					lbl_FileNotFound.setText("");
					button_load.setDisable(false);
				}
				// If the methods returns false, it means there is a problem with input file
				else { // and the method has set up a String to explain what the issue is
					lbl_FileFound.setText("");
					lbl_FileNotFound.setText("File found, but the contents are not valid!");
					lbl_FileNotFound.setTextFill(Color.RED);
					message_ErrorDetails.setText(errorMessage_FileContents);
					button_load.setDisable(true);
				}
			} catch (FileNotFoundException e) { // If an exception is thrown, the file name
				lbl_FileFound.setText(""); // that the button to run the analysis is
				lbl_FileNotFound.setText("File not found!"); // not enabled.
				lbl_FileNotFound.setTextFill(Color.RED);
				message_ErrorDetails.setText("");
				scanner_Input = null;
				button_load.setDisable(true);
			}
	}

	private boolean fileContentsAreValid() {

		// Declare and initialize data variables used to control the method
		@SuppressWarnings("unused")
		int numberOfLinesInTheInputFile = 0; // This attribute sets the number of lines set during the first read
		String firstLine = "";

		// Read in the first line and verify that it has the proper header
		if (scanner_Input.hasNextLine()) {
			firstLine = scanner_Input.nextLine().trim(); // Fetch the first line from the file
			if (firstLine.equalsIgnoreCase("Program")) // See if it is what is expected
				numberOfLinesInTheInputFile = 1; // If so, count it as one line
			else { // If not, issue an error message
				return false; // and return false
			}
		} else {
			// If the execution comes here, there was no first line in the file
			return false;
		}

		// Process each and every subsequent line in the input to make sure that none
		// are too long
		while (scanner_Input.hasNextLine()) {
			numberOfLinesInTheInputFile++; // Count the number of input lines

			// Read in the line
			String inputLine = scanner_Input.nextLine();

			// Verify that the input line is not larger than 250 characters...
			if (inputLine.length() > 250) {
//				// If it is larger than 250 characters, display an error message on the console
//				System.out.println("\n***Error*** Line " + numberOfLinesInTheInputFile + " contains "
//						+ inputLine.length() + " characters, which is greater than the limit of 250.");

				// Stop reading the input and tell the user this data file has a problem
				return false;
			}
		}
		// Should the execution reach here, the input file appears to be valid
		errorMessage_FileContents = ""; // Clear any messages
		return true; // End of file - data is valid
	}

	public void fileOpener() {
		str_FileName = enter.getText().trim();
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(str_FileName)));
			String line;
			line = br.readLine();

			while ((line = br.readLine()) != null) {

				data.appendText(line);
				data.appendText("\n");

			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		button_load.setVisible(false);
		button_Search.setVisible(true);
		button_Add.setVisible(true);
		button_Edit.setVisible(true);
		button_Delete.setVisible(true);
		button_RunExpression.setVisible(true);
		button_RunProgram.setVisible(true);
		lbl_FileFound.setVisible(false);
		enter.setText("");
		label_detail.setText("Enter search Program");
	}
	
	public void additionPopUp() {
		
			Group additionControls = new Group();

			// Set up the pop-up window
			Scene dialogScene = new Scene(additionControls, 500, 400);
			
			editDialog.setTitle("Write Name, Content and Then Add Program");
			
			setupLabelUI(lbl_New, "Arial", 18, 100, Pos.BASELINE_LEFT, 10, 10);
			setupLabelUI(lbl_Name, "Arial", 12, 100, Pos.BASELINE_LEFT, 30, 60);
			setupTextUI(fld_Name, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 80, true);
			setupLabelUI(lbl_Content, "Arial", 12, 100, Pos.BASELINE_LEFT, 30, 120);
			setupTextAreaUI(txt_Content, "Arial", 14, 30, 140, 450, 150, true);
			setupLabelUI(lbl_AddProgram, "Arial", 14, 100, Pos.BASELINE_LEFT, 30, 335);

			fld_Name.setText("");
			txt_Content.setText("");

			// Set the screen so we can see it
			editDialog.setScene(dialogScene);

			setupButtonUI(btn_SaveAddedChanges, "Arial", 12, 50, Pos.BASELINE_LEFT, 30, 360);
			btn_SaveAddedChanges.setOnAction((event) -> {
				addDataIntoFile();
			});

			// Populate the pop-up window with the GUI elements
			additionControls.getChildren().addAll(lbl_New,lbl_Name, fld_Name, lbl_Content, txt_Content,
					lbl_AddProgram, btn_SaveAddedChanges);

			// Show the pop-up window
			editDialog.show();

		}
	
	public void editPopUp() {
		// Set up the pop-up modal dialogue window
				editDialog.setTitle("1. Select a program, 2. Edit it, and then 3. Save it!");
				Group dictionaryEditControls = new Group();

				// Set up the pop-up window
				Scene dialogScene = new Scene(dictionaryEditControls, 500, 400);

				// Set up the fields for the edit pop-up window
				setupLabelUI(lbl_1SelectProgram, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 10);
				setupLabelUI(lbl_2EditProgram, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 75);
				setupLabelUI(lbl_Name, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 100);
				setupTextUI(fld_Name, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 120, true);
				setupLabelUI(lbl_Content, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 155);
				setupTextAreaUI(txt_Content, "Arial", 14, 30, 180, 450, 150, true);
				setupLabelUI(lbl_Save, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 335);

				// Set up the comboBox to select one of the dictionary items that matched
				editComboBox.setLayoutX(25);
				editComboBox.setLayoutY(35);
				editComboBox.getSelectionModel().selectFirst();
				editComboBox.setOnAction((event) -> {
				});

				// Set the screen so we can see it
				editDialog.setScene(dialogScene);


				setupButtonUI(btn_SaveEditChanges, "Arial", 12, 50, Pos.BASELINE_LEFT, 30, 360);
				btn_SaveEditChanges.setOnAction((event) -> {
					
				});

				// Populate the pop-up window with the GUI elements
				dictionaryEditControls.getChildren().addAll(editComboBox, lbl_1SelectProgram, lbl_2EditProgram,
						lbl_Name, fld_Name, lbl_Content, txt_Content, lbl_Save, btn_SaveEditChanges);

				// Show the pop-up window
				editDialog.show();
	}
	
	public void deletePopUp() {
		// Set up the pop-up modal dialogue window
		editDialog.setTitle("1. Select a program, 2. Delete it!");
		Group dictionaryEditControls = new Group();

		// Set up the pop-up window
		Scene dialogScene = new Scene(dictionaryEditControls, 500, 400);

		// Set up the fields for the edit pop-up window
		setupLabelUI(lbl_1SelectProgram, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 10);
		setupLabelUI(lbl_Name, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 100);
		setupTextUI(fld_Name, "Arial", 14, 450, Pos.BASELINE_LEFT, 30, 120, true);
		setupLabelUI(lbl_Content, "Arial", 12, 100, Pos.BASELINE_LEFT, 20, 155);
		setupTextAreaUI(txt_Content, "Arial", 14, 30, 180, 450, 150, true);
		setupLabelUI(lbl_Delete, "Arial", 14, 100, Pos.BASELINE_LEFT, 10, 335);

		// Set up the comboBox to select one of the dictionary items that matched
		editComboBox.setLayoutX(25);
		editComboBox.setLayoutY(35);
		editComboBox.getSelectionModel().selectFirst();
		editComboBox.setOnAction((event) -> {
		});

		// Set the screen so we can see it
		editDialog.setScene(dialogScene);


		setupButtonUI(btn_DeletePopup, "Arial", 12, 50, Pos.BASELINE_LEFT, 30, 360);
		btn_DeletePopup.setOnAction((event) -> {
			
		});

		// Populate the pop-up window with the GUI elements
		dictionaryEditControls.getChildren().addAll(editComboBox, lbl_1SelectProgram,
				lbl_Name, fld_Name, lbl_Content, txt_Content, lbl_Delete, btn_DeletePopup);

		// Show the pop-up window
		editDialog.show();
	}
	
	public void addDataIntoFile() {
		String name = "", content = "";

        try {
            name = fld_Name.getText().trim();
            content = txt_Content.getText();

            if (name.isEmpty()) {
                message.setText("Please fill all fields!");
                return;
            }

        } catch (Exception e) {
            message.setText("Please fill all fields!");
            return;
        }
        
        try {
            message.setText("");
            FileWriter writer = new FileWriter(file, true);
            writer.write('\n');
            writer.write(name + '\n');
            writer.write(content + '\n');
            writer.write("----------" + '\n');
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        data.setText("");
        
        try {

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			line = br.readLine();

			while ((line = br.readLine()) != null) {

				data.appendText(line);
				data.appendText("\n");

			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
