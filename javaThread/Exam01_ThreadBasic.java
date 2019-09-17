package javaThread;

import org.omg.CORBA.portable.ApplicationException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/*

	Java Application은 Main Thread가 main() Method를 호출해서 실행
	
	프로그램은 Main Method()가 종료될 때 종료되는게 아니라, 프로그램 내에서 파생된 모든 Thread가 종료될 때 종료되요!
	
	1. Thread의 생성
		1. Thread Class를 상속받아서 Class를 정의하고 객체 생성해서 사용 (거의 사용되지 않음)
		2. Runnable Interface를 구현한 Class를 정의하고 객체를 생성해서 Thread 생성자의 인자로 넣어서 Thread 생성
	2. 현재 사용되는 Thread의 이름을 출력
	
	3. 실제 Thread의 생성 (new) 
		-> start() (thread를 실행시키는게 아니라 runnable 상태로 전환)
		-> JVM 안에 있는 Thread Scheduler에 의해 하나의 Thread가 선택되서 thread가 running 상태로 전환
		-> 다른 Thread를 선택해서 running 상태로 전환


*/
public class Exam01_ThreadBasic extends Application{

	// Message 창을 위한 TextArea
	// Field로 선언
	TextArea textarea;
	
	// Button 클릭시 Thread 시작
	Button btn;

	public static void main(String[] args) {
		
		System.out.println(Thread.currentThread().getName());
		// main이 호출되어 launch() 를 호출하면 start()가 자동으로 호출된다.
		launch();
		// currentThread() : 해당 코드를 수행하고 있는 Thread를 찾는다.
		// Thread의 이름을 설정하고 가져올 수 있다. 
		// Thread.currentThread().getName() : 현재 파생된 Thread의 reference를 획득해서 이름을 리턴한다.
		System.out.println(Thread.currentThread().getName());

	}
	
	@Override
	// 화면을 구성해서 Window를 띄우는 코드
	// primaryStage : 윈도우를 지칭하는 reference
	public void start(Stage primaryStage) throws Exception {
		
		// start()를 호출하는 Thread가 누구냐 
		System.out.println(Thread.currentThread().getName());

		// JavaFX는 내부적으로 화면을 제어하는 Thread를 생성해서 사용해요~
		
		
		
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

//		// 람다식을 사용해서 이벤트를 핸들링한다.
//		btn.setOnAction(t->{
//			// 버튼에서 Action이 발생(클릭)했을 때 호출!
//			// 버튼을 클릭하면 Thread 생성 (람다식 이용)
//			new Thread(new Runnable() {
//				public void run() {
//					
//				}
//			}).start();			
//			
//		});
		// 람다식을 사용해서 이벤트를 핸들링한다.
		btn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			// 버튼을 클릭하면 Thread 생성 (람다식 이용)
			new Thread(()->{
				System.out.println(Thread.currentThread().getName());
				
				// 화면 제어를 JavaFX Application Thread가 담당한다. 
				//textarea.appendText("소리 없는 아우성!! \n");

				// Thread-4가 아닌 JavaFX Application Thread가 담당해야 한다. 
				//  => JavaFX Application Thread에 부탁을 해서 JavaFX Application Thread가 화면에 출력하도록 한다.
				
				// textarea에 출력하기 위해서 JavaFX Application Thread에 부탁을 한다.
				Platform.runLater(()->{
					System.out.println(Thread.currentThread().getName());
					textarea.appendText("소리 없는 아우성!!");
					
				});
				
			}).start();			
			
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
