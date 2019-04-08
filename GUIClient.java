
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

public class GUIClient extends Application{
	VBox vBoxInPane;
	VBox vBox;
	HBox hBox;
	Button button;
	TextField textField;
	ScrollPane scrollPane;
	Socket socket;
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
			socket = new Socket("localhost", 6789);
		}
		catch(Exception e) {
			displayText("Client failed to connect to server; closing application.", -1);
			System.exit(0);
		}
		displayText("Client successfully connected.", 1);
		Thread thread = new Thread(new TCPReceiver(this));
		Thread thread2 = new Thread(new TCPSender(this));
		thread.start();
		thread2.start();
		while(thread.isAlive() && thread2.isAlive()) {
			
		}
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
class TCPReceiver implements Runnable{
	private Socket client;
	private GUIClient instance;
	String modifiedSentence;
	public TCPReceiver(GUIClient s) {
		instance = s;
	}
	@Override
	public void run() {
		try {
			client = instance.socket;
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
				do {
					modifiedSentence = inFromServer.readLine();
					instance.displayText(modifiedSentence, 2);
				}while(main.getState() != 20);
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
class TCPSender implements Runnable{
	private Socket client;
	private GUIClient instance;
	DataOutputStream outToServer;
	public TCPSender(GUIClient s) {
		instance = s;
	}
	@Override
	public void run() {
		try {
			client = instance.socket;
			instance.textField.setOnKeyPressed(e -> {
				if(e.getCode() == KeyCode.ENTER) {
					String modifiedSentence = instance.textField.getText();
					try {
						outToServer = new DataOutputStream(client.getOutputStream());
						instance.displayText(modifiedSentence, 0);
						outToServer.writeBytes(modifiedSentence);
						instance.textField.clear();
						if(main.getState() == 20) {
							System.exit(0);
						}
					}
					catch(Exception f) {
						System.out.println(e);
						f.printStackTrace();
						System.exit(0);
					}
				}
			});
			instance.button.setOnAction(e -> {
				String modifiedSentence = instance.textField.getText();
				try {
					DataOutputStream outToServer = new DataOutputStream(client.getOutputStream());
					instance.displayText(modifiedSentence, 0);
					outToServer.writeBytes(modifiedSentence);
					instance.textField.clear();
					if(main.getState() == 20) {
						System.exit(0);
					}
				}
				catch(Exception f) {
					System.out.println(e);
					f.printStackTrace();
					System.exit(0);
				}
			});
			
			
				
		}
		catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			System.exit(0);
		}
	}
}