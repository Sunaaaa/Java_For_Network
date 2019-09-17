package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


class SharedObject {

	private List<EchoRunnable> threadList = new ArrayList<EchoRunnable>();
	private String message = "";
	
	public void addClientThread(EchoRunnable echoRunnable) {
		threadList.add(echoRunnable);
		System.out.println("추가됩");
	}
	
	public void broadcast() {
		for (EchoRunnable thread: threadList) {
			thread.broadcast(this.message);
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}

class EchoRunnable implements Runnable{
	
	private Socket s;
	private BufferedReader br;
	private PrintWriter out;
	private SharedObject sharedData;
	public EchoRunnable() {
	}
	
	public EchoRunnable(Socket s, SharedObject sharedData) {
		this.s = s;
		this.sharedData = sharedData;
		try {
			this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.out = new PrintWriter(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void broadcast(String str) {
		out.println(str);
		out.flush();
	}
	@Override
	public void run() {
		// Client와 Echo처리 구현
		// Client가 문자열을 보내면 해당 문자열을 받아서 다시 클라이언트에게 전달한다.
		// 한번하고 종료하는게 아니라 클라이언트가 "/EXIT"라는 문자열을 보낼때까지 지속한다.
		System.out.println(Thread.currentThread().getName());
		String line ="";
		
		// (line=br.readLine())!=null : 접속이 끊긴 경우
		try {
			while ((line=br.readLine())!=null) {
				if (Thread.currentThread().interrupted()) {
					break;
				}
				
				// 문자열 비교는 반드시 equals() 함수 이용
				if (line.equals("EXIT")) {
					break; // 가장 근접한 loop (while 문) 를 탈출										
				}else {
					sharedData.setMessage(line);
					sharedData.broadcast();
				}
			}
		} catch (IOException e) {
			System.out.println("설마 여기?");
			e.printStackTrace();
			
		}
		
	}
}

public class Exam03_EchoServer_MultiUser extends Application{

	TextArea textarea;
	
	Button startBtn, stopBtn;
	
	// Thread Pool 생성
	ExecutorService executorService = Executors.newCachedThreadPool();
	
	// Client의 접속을 받기 위한 ServerSocket 생성
	ServerSocket serverSocket;
	
	Socket socket;
	String msg;
	
	SharedObject sharedData;
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText("꼴통전사 : " + msg + "\n"); 
		});
	}
	
	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		sharedData = new SharedObject();
		BorderPane root = new BorderPane();
		
		root.setPrefSize(700, 500);
		
		textarea = new TextArea();
		root.setCenter(textarea);
		
		
		startBtn = new Button("Echo 서버 접속!!");
		startBtn.setPrefSize(100, 100);

		startBtn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			// 서버 프로그램을 시작
			/*
				서버 : 클라이언트의 접속을 기다려요!
					-> 접속이 되면 Thread를 하나 생성
					-> Thread를 시작해서 클라이언트의 Thread가 통신하도록 만들어요
					==> 서버는 다시 다른 클라이언트의 접속을 기다려요~
			 */
			// 화면이 BLock되지 않는다. 
			Runnable runnable = ()->{
				try {
					// Client는 버튼을 누르면 서버 쪽에 Socket 접속을 시도한다.
					serverSocket = new ServerSocket(7777);
					
					printMsg("Echo Server 가동!!");
					while(true) {
						printMsg("Client 접속 대기!!");
						
						// JavaFX가 현재 서버를 잡고 있기 때문에 클라이언트의 접속이 있기 전에 
						// window가 block이 된다. 
						// --> JavaFX와 Thread (클라이언트의 server 접속) 를 실행시키는 부분으로 나눈다. 
						Socket s = serverSocket.accept();
						
						// 클라이언트가 접속을 했으니, Thread를 만들고 시작해야 해요!
						printMsg("Client 접속 성공!!");
						
						EchoRunnable echoRunnable = new EchoRunnable(s, sharedData);
						System.out.println("러너블 만들어");
						
						sharedData.addClientThread(echoRunnable);						
						System.out.println("러너블 추가");
						
						executorService.execute(echoRunnable);
						System.out.println("러너블 실행");

					}			
				
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			};
			executorService.execute(runnable);
		});
		
		stopBtn = new Button("Echo 서버 종료 ");
		stopBtn.setPrefSize(100, 50);
		stopBtn.setOnAction((t)->{
			
			try {
				serverSocket.close();
				executorService.shutdown();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		// 긴 Panel 하나를 생성
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		
		// FloxPane에 Button을 올려요!
		flowpane.getChildren().add(startBtn);
		flowpane.getChildren().add(stopBtn);
		root.setBottom(flowpane);		
		
		// 화면에 띄우기
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Multi Client Echo Server");
		primaryStage.show();		
		
	}

}
