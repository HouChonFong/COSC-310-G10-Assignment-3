import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

public class GUIServer extends Application{
	static VBox vBoxInPane;
	VBox vBox;
	HBox hBox;
	Button button;
	TextField textField;
	ScrollPane scrollPane;
	static ServerSocket serverSocket;
	static Socket client;
	static String modifiedSentence;
	static DataOutputStream outToServer;
	static BufferedReader inFromServer;
	static String options = "- Looking for items \n- Show business hours and location \n- Tracking or cancel orders \n- Rate and leave comments";
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
	    stage.setTitle("Customer Help (Server Side)"); 
	    stage.setScene(scene); 
	    stage.setResizable(false);
	    stage.show();
	    Thread thread = new Thread(new Runnable() {
	    	public void run() {
	    		try {
	    			serverSocket = new ServerSocket(6789);
	    			client = serverSocket.accept();
	    			outToServer = new DataOutputStream(client.getOutputStream());
    				inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
	    			outToServer.writeBytes("Hello there, welcome to SuperWet online customer service. How may I help you today?");
	    			outToServer.writeBytes(options);
	    			while(main.getState() != 20) {
	    				modifiedSentence = inFromServer.readLine();
	    				outToServer.writeBytes(main.respond(modifiedSentence));
	    				}
	    			}
	    		catch (Exception e) {
	    			System.out.println(e);
	    			e.printStackTrace();
	    		}
	    		finally {
	    			System.exit(0);
	    		}
	    	}
	    });
	    thread.setDaemon(true);
	   	}   
	   	public static void main(String args[]){ 
	   		Application.launch(args); 
		    
	   	} 
	   	public static void displayText(String s, int i) {
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