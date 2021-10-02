package Calculator;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import myOwnApi.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <p> Title: DefintionUserInterface Class. </p>
 * 
 * <p> Description: A component of a JavaFX demonstration application that performs CRUD operations </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2020 </p>
 * 
 * @author Jatin Thakur
 * 
 * @version 4.07	2020-03-12 Contains all the components of the Definition Window
 * 
 */


public class DefinitionUserInterface extends Application {
	
	//Attributes
    private Label title = new Label("Definition");
    private Label type = new Label("Type: ");
    private ComboBox<String> typeComboBox = new ComboBox<>();
    private Label name = new Label("Name: ");
    private TextField text_Name = new TextField();
    private Label fsmName = new Label("");
    private Label value = new Label("Measure Value:");
    private TextField text_Operand = new TextField();
    private Label value_FSM = new Label("");
    private Label errorValue = new Label("Error Term: ");
    private TextField text_Error = new TextField();
    private Label error_FSM = new Label("");
    private Label unitLabel = new Label("Unit: ");
    private ComboBox<String> unit_ComboBox = new ComboBox<>();
    private Button button_TableView = new Button("Table View");
    private Button button_Exit = new Button("Exit");
    private Button button_Add = new Button("Add");
    private Label message = new Label("");
    private File file;
    
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Definition Window");
        
        typeComboBox.setValue("None");
        typeComboBox.getItems().addAll("Constant","Variable");
        setupLabelUI(title, "Arial", 24, 50, Pos.CENTER_LEFT, 300, 10);
        title.setStyle("-fx-underline: true;");
        
        setupLabelUI(type, "Arial", 18, 50, Pos.CENTER_LEFT, 20, 318);
        setupComboBox(typeComboBox, 150, 320);

        setupLabelUI(name, "Arial", 18, 50, Pos.CENTER_LEFT, 20, 110);
        setupTextUI(text_Name, "Arial", 18, 200, Pos.CENTER_LEFT, 150, 100, true);
        setupLabelUI(fsmName, "Arial", 12, 200, Pos.CENTER_LEFT, 150, 135);

        setupLabelUI(value, "Arial", 18, 50, Pos.CENTER_LEFT, 20, 175);
        setupTextUI(text_Operand, "Arial", 18, 200, Pos.CENTER_LEFT, 150, 170, true);
        setupLabelUI(value_FSM, "Arial", 12, 200, Pos.CENTER_LEFT, 150, 205);

        setupLabelUI(errorValue, "Arial", 18, 50, Pos.CENTER_LEFT, 20, 250);
        setupTextUI(text_Error, "Arial", 18, 200, Pos.CENTER_LEFT, 150, 240, true);
        setupLabelUI(error_FSM, "Arial", 12, 200, Pos.CENTER_LEFT, 150, 275);

        setupLabelUI(unitLabel, "Arial", 18, 50, Pos.CENTER_LEFT, 20, 360);
        setupComboBox(unit_ComboBox, 150, 360);

        setupButtonUI(button_Add, "Arial", 18, 100, Pos.CENTER, 20, 400);
        setupButtonUI(button_TableView, "Arial", 18, 100, Pos.CENTER, 160, 400);
        setupButtonUI(button_Exit, "Arial", 18, 100, Pos.CENTER, 300, 400);

        setupLabelUI(message, "Arial", 18, 400, Pos.CENTER_LEFT, 20, 470);

        value_FSM.setTextFill(Color.RED);
        error_FSM.setTextFill(Color.RED);
        fsmName.setTextFill(Color.RED);
        message.setTextFill(Color.RED);

        // To close the current window.
        button_Exit.setOnAction(event -> stage.close());

        button_Add.setOnAction(event -> {
            file = new File("Repository/data.txt");
            try {
                if (file.createNewFile()) {
                    write();
                } else {
                    write();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // To open the table view.
        button_TableView.setOnAction(event -> {
            new TableUserInterface().start(new Stage());
        });

        
        	unit_ComboBox.setValue("None");
            unit_ComboBox.getItems().addAll("m", "km", "s", "min", "h", "day", "None");

        text_Operand.textProperty().addListener((observable, oldvalue, newValue) -> {
            value_FSM.setText(CalculatorValue.checkMeasureValue(text_Operand.getText()));
            message.setText("");
        });

        text_Error.textProperty().addListener((observable, oldvalue, newValue) -> {
            error_FSM.setText(CalculatorValue.checkMeasureValue(text_Error.getText()));
            message.setText("");
        });

        Pane pane = new Pane();

        Scene scene = new Scene(pane, 700, 500);
        stage.setScene(scene);
        stage.show();

        pane.getChildren().addAll(type, typeComboBox, name, text_Name, fsmName, value, text_Operand, value_FSM, text_Error, errorValue, error_FSM, unit_ComboBox, unitLabel
        , button_TableView, button_Add, button_Exit, message, title);

    }

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

    private void setupComboBox(ComboBox<String> b, double x, double y) {
        b.setLayoutX(x);
        b.setLayoutY(y);
    }

    // To save data inside the file
    public void write() {

        String name = "", measuredValue = "", errorValue = "", unit = "" , type = "";

        try {
            name = text_Name.getText().trim();
            measuredValue = text_Operand.getText().trim();
            errorValue = text_Error.getText().trim();
            unit = unit_ComboBox.getSelectionModel().getSelectedItem().trim();
            type = typeComboBox.getSelectionModel().getSelectedItem().trim();

            if (name.isEmpty() || measuredValue.isEmpty() || errorValue.isEmpty()) {
                message.setText("Please fill all fields!");
                return;
            }

        } catch (Exception e) {
            message.setText("Please fill all fields!");
            return;
        }
        
        if (ExistingName(name)) {
            message.setText("Name already exists in the File!");
            return;
        }

        try {
            message.setText("");
            FileWriter writer = new FileWriter(file, true);
            writer.write(name + "," + measuredValue + "," + errorValue + "," + unit + "," + type + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private boolean ExistingName(String name) {
        ArrayList<data> row = data.readDataFromFile();
        int i = 0;
        while (i <= row.size()-1) {
            String str = row.get(i).name.trim();
            if (str.equals(name)) return true;
            i++;
        }
        return false;
    }
}
