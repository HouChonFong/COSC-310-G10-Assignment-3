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
	VBox vBoxInPane;
	VBox vBox;
	HBox hBox;
	Button button;
	TextField textField;
	ScrollPane scrollPane;
	ServerSocket serverSocket;
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
	    try {
			serverSocket = new ServerSocket(6789);
		}
		catch(Exception e) {
			displayText("Server failed to create socket; closing application.", -1);
			System.exit(0);
		}
		displayText("Server socket successfully created.", 1);
		Thread thread = new Thread(new TCPServer(this));
		thread.start();
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
class TCPServer implements Runnable{
	private ServerSocket serverSocket;
	private Socket client;
	private GUIServer instance;
	public TCPServer(GUIServer s) {
		instance = s;
	}
	@Override
	public void run() {
		try {
			serverSocket = instance.serverSocket;
			client = serverSocket.accept();
			instance.displayText("Server successfully connected with a user.", 1);
			while(main.getState() != 20) {
				String modifiedSentence;
				DataOutputStream outToServer = new DataOutputStream(client.getOutputStream());
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
				modifiedSentence = inFromServer.readLine();
				instance.displayText(modifiedSentence, 2);
				instance.displayText(main.respond(modifiedSentence), 0);
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
}