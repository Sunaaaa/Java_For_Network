package javaThread;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/*

	javaFX 
	- 화면에 UI를 만드는 클래스 
	- 윈도우를 띄우기 위해 
	
	Application : 추상 클래스 이기 때문에 추상 메소드를 오버라이딩

*/
public class Exam05_ThreadInterrupt extends Application{

	// Message 창을 위한 TextArea
	// Field로 선언
	TextArea textarea;
	
	// Button 클릭시 Thread 시작, 종료 버튼
	Button startBtn, stopBtn;
	
	Thread counterThread;
	
	// textarea에 문자열을 출력하는 Method
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg);
		});
	}

	public static void main(String[] args) {
		// main이 호출되어 launch() 를 호출하면 start()가 자동으로 호출된다.
		launch();

	}
	
	@Override
	// 화면을 구성해서 Window를 띄우는 코드
	// primaryStage : 윈도우를 지칭하는 reference
	public void start(Stage primaryStage) throws Exception {
		// 화면 기본 layout을 설정 => 화면을 동-서-남-북-중앙 (5개의 영역) 으로 분리 
		// 화면을 5개 영역으로 분리하는 Layout Manager : Component를 어떻게 위치 시킬지를 담당하는 역할 수행
		BorderPane root = new BorderPane();
		
		// 가로 700px, 세로 500px의 레이아웃 사이즈 설정
		root.setPrefSize(700, 500);
		
		// Component 생성해서 BorderPane에 부착
		textarea = new TextArea();
		// 중앙에 textArea를 부착 -> 동-서-남-북에 따라 사이즈가 알아서 조절되기 떄문에 별도의 사이즈를 지정하지 않아도 된다.
		root.setCenter(textarea);
		
		// 버튼 클릭이라는 이름의 버튼을 생성
		startBtn = new Button("Thread 시작!!");
		startBtn.setPrefSize(100, 100);

		// 람다식을 사용해서 이벤트를 핸들링한다.
		startBtn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			counterThread = new Thread(()-> {
				try {
					
					for (int i = 0; i < 10; i++) {
						Thread.sleep(1000);		
						printMsg(i + "--" + Thread.currentThread().getName() + "\n");
					}
					
				} catch (Exception e) {
					// Interrupt 가 걸려 있지 않으면 위의 try 문이 수행 
					// 만약 Interrupt 가 걸려 있는 상태에서 block 상태로 진입하면 
					// Exception을 내면서 catch문으로 이동한다.
					printMsg(counterThread.getName()+" :: Thread가 종료 되었어유~~");
				}
			});
			counterThread.start();
			
		});
		
		stopBtn = new Button("Thread 종료!!");
		stopBtn.setPrefSize(100, 100);
		stopBtn.setOnAction(t->{
			// Thread에 Interrupt를 건다.
			// Method가 실행되어도 바로 Thread가 종료되지 않는다.
			// Interrupt Method가 호출되는 Thread는 
			// sleep과 같이 Block 상태에 등러가야 Interrupt 시킬 수 있다.
			counterThread.interrupt();

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
		primaryStage.setTitle("Thread 예제 입니다.");
		primaryStage.show();
		
		
	}

}
