package javaNetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam02_EchoClient2 extends Application{

	TextArea textarea;
	TextField textField;
	
	Button btn;
	
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	
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
		
		textField = new TextField();
		textField.setPrefSize(500, 100);
		textField.setOnAction((t)->{
			// 입력 상자 (TextField)에서 Enter Key가 입력되면 호출
			// 서버가 보낸 한 줄을 받아서 읽는다.
			String msg = textField.getText();
			out.println(msg);
			out.flush();
			try {
				String result = br.readLine();
				printMsg(result);
				
			}catch (Exception e) {
				System.out.println(e);
			}


		});
		
		btn = new Button("Echo 서버 접속!!");
		btn.setPrefSize(100, 100);

		btn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			// 접속 버튼
			try {
				// Client는 버튼을 누르면 서버 쪽에 Socket 접속을 시도한다.
				socket = new Socket("127.0.0.1", 5252);
				
				// Stream 생성
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				out = new PrintWriter(socket.getOutputStream());
				
				printMsg("Echo Server에 접속을 성공했어요~");

				out.print(textField.getText());
				out.flush();
								
				// 사용한 자원 반납 / 해제
			
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		
		// 긴 Panel 하나를 생성
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		// FloxPane에 Button을 올려요!
		flowpane.getChildren().add(btn);
		flowpane.getChildren().add(textField);
		root.setBottom(flowpane);		
		
		// 화면에 띄우기
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread 예제 입니다.");
		primaryStage.show();		
		
	}

}
