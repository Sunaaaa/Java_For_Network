package javaThread;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam07_ThreadDaemon extends Application{

	// Message 창을 위한 TextArea
	// Field로 선언
	TextArea textarea;
	
	// Button 클릭시 Thread 시작
	Button btn;

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
		btn = new Button("버튼 클릭!!");
		btn.setPrefSize(100, 100);

		// 람다식을 사용해서 이벤트를 핸들링한다.
		btn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			// Thread 생성(for loop를 1초마다 sleep 하면서 10번 반복)
			// Thread가 Dead 상태에 진입하기 위해서는 10초가 소요된다.
			Thread thread = new Thread(()->{
				try {
					for (int i = 0; i < 10; i++) {
						Thread.sleep(1000);						
					}
					
				}catch (Exception e) {
				}
			}) ;

			// 해당 Thread를 Daemon Thread로 설정하면
			// 부모 Thread가 종료 되면 자동으로 자식 Thread도 중지 된다. 
			thread.setDaemon(true);
			thread.start();
			
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
