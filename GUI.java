

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage; 

public class GUI extends Application{
	VBox vBoxInPane;
	VBox vBox;
	HBox hBox;
	Button button;
	TextField textField;
	ScrollPane scrollPane;
	public void start(Stage stage) { 
		vBoxInPane = new VBox(2);
		vBox = new VBox(2);
		hBox = new HBox(2);
		button = new Button();
		textField = new TextField();
		scrollPane = new ScrollPane();
		vBoxInPane.heightProperty().addListener(new ChangeListener<Object>() {
		    @Override
		    public void changed(ObservableValue<?> observable, Object oldvalue, Object newValue) {
		        scrollPane.setVvalue((Double)newValue);  
		    }
		});
		textField.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER) {
				displayText(textField.getText(), 0);
				displayText(main.respond(textField.getText()), 2);
				textField.clear();
				if(main.getState() == 20) {
					System.exit(0);
				}
			}
		});
		button.setOnAction(e -> {
			displayText(textField.getText(), 0);
			displayText(main.respond(textField.getText()), 2);
			textField.clear();
			if(main.getState() == 20) {
				System.exit(0);
			}
		});
		scrollPane.setContent(vBoxInPane);
		scrollPane.setMaxSize(520, 300);
		scrollPane.setMinSize(520, 300);
		button.setMaxSize(100, 75);
		button.setMinSize(100, 75);
		button.setText("Enter");
		textField.setMaxSize(400, 75);
		textField.setMinSize(400, 75);
		textField.setAlignment(Pos.TOP_LEFT);
		hBox.setSpacing(20);
		hBox.getChildren().addAll(textField, button);
		vBox.setSpacing(20);
		vBox.setPadding(new Insets(20, 20, 20, 20));
		vBox.getChildren().addAll(scrollPane, hBox);
		vBoxInPane.setPadding(new Insets(10, 10, 10, 10));
		Scene scene = new Scene(vBox); 
	    stage.setTitle("Customer Help"); 
	    stage.setScene(scene); 
	    stage.setResizable(false);
	    stage.show();
	    displayText("Hello there, welcome to SuperWet online customer service. How may I help you today?", 2);
	    displayText(main.getOptions(), 2);
	}
	   	public static void main(String args[]){ 
	   		Application.launch(args); 
	   	} 
	   	public void displayText(String s, int i) {
	   		Label temp = new Label();
	   		if(i == -1) {
	   			temp.setFont(Font.font(temp.getFont().getName(), FontPosture.ITALIC, temp.getFont().getSize())); 
	   			temp.setTextFill(Color.RED);
	   		}
	   		else if(i == 1) {
	   			temp.setFont(Font.font(temp.getFont().getName(), FontPosture.ITALIC, temp.getFont().getSize())); 
	   			temp.setTextFill(Color.GREEN);
	   		}
	   		else if(i == 2) {
	   			temp.setTextFill(Color.DARKBLUE);
	   		}
	   		temp.setText(s);
	   		temp.setWrapText(true);
	   		temp.setMaxWidth(480);
	   		vBoxInPane.getChildren().add(temp);
	   	}
}
