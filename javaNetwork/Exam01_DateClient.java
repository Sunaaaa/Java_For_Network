package javaNetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam01_DateClient extends Application{

	TextArea textarea;
	
	Button btn;

	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n"); 
		});
	}
	
	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		
		root.setPrefSize(700, 500);
		
		textarea = new TextArea();
		root.setCenter(textarea);
		
		btn = new Button("Date 서버 접속!!");
		btn.setPrefSize(100, 100);

		btn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			try {
				// Client는 버튼을 누르면 서버 쪽에 Socket 접속을 시도한다.
				// Socket socket = new Socket("localhost");
				Socket socket = new Socket("127.0.0.1", 5554);
				
			
				
				// 만약에 접속에 성공하면 socket 객체를 하나 획득한다.
				// 접속에 대한 증표 -> socket에 대한 객체 
				// client 쪽의 socket
				// 버튼을 누르고 접속을 하는 순간 
				
				InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				
				// 서버가 보낸 한 줄을 받아서 읽는다.
				String msg = bufferedReader.readLine();
				printMsg(msg);
				bufferedReader.close();
				inputStreamReader.close();
				socket.close();

			
			}catch (Exception e) {
				
			}
			
		});
		
		// 긴 Panel 하나를 생성
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		// FloxPane에 Button을 올려요!
		flowpane.getChildren().add(btn);
		root.setBottom(flowpane);
		
		// 화면에 띄우기
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread 예제 입니다.");
		primaryStage.show();
		
		
	}

}
