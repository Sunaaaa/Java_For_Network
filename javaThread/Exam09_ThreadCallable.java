package javaThread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


// 리턴 값이 있는 Thread를 실행하는 방법
public class Exam09_ThreadCallable extends Application{

	TextArea textarea;
	
	// initBtn : Thread pool을 생성하는 버튼
	// startBtn : Thread pool을 이용해서 Thread를 실행시키는 버튼
	// stopBtn : 프로그램이 종료 될 때 Thread Pool 객체를 종료 시키는 버튼
	Button initBtn, startBtn, stopBtn;
	
	ExecutorService esevice;
		
	public static void main(String[] args) {
		launch();
	}
	
	private void printName(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg);
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		
		root.setPrefSize(700, 500);
		
		textarea = new TextArea();
		root.setCenter(textarea);
		
		initBtn = new Button("Thread Pool 생성 클릭!!");
		initBtn.setPrefSize(150, 100);

		// 람다식을 사용해서 이벤트를 핸들링한다.
		initBtn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			// textarea에 글자를 넣어요 
			textarea.appendText("소리없는 아우성 \n");
			esevice = Executors.newCachedThreadPool();

			int threadCount=((ThreadPoolExecutor)esevice).getPoolSize();
			printName("현재 Thread Pool 안에 있는 Thread 개수 : " + threadCount + "\n");

		});
		
		startBtn = new Button("Thread 실행!!");
		startBtn.setPrefSize(150, 100);

		// 람다식을 사용해서 이벤트를 핸들링한다.
		startBtn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			// Thread 실행  

			
			// Callable<V> : 해당 Method의 리턴 데이터 타입을 명시한다.
			//				: Runnable과는 다르게 Return 타입을 갖는다.
			
			for (int i = 0; i < 10; i++) {
				final int ii = i;
				
				Callable<String> callable = new Callable<String>() {

					@Override
					public String call() throws Exception {
						Thread.currentThread().setName("MyThread_" + ii);
						String msg = "Thread 이름 : " + Thread.currentThread().getName() + " --- Pool의 개수 : " + ((ThreadPoolExecutor)esevice).getPoolSize();
						//printName(msg + "\n");
						
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return Thread.currentThread().getName() + " 가 종료 되었다.";
						
					}
					
					
					// get 이 블록킹 메소드 이기 때문에 순차 처리 되는 위험. 
					// 2초 쉬고, 
					// get에서 받아온 결과 값 (종료 메시지) 을 찍고 
					// 다음 i 수행
					// Thread 인데 순차처리하면 안좋지 - Thread를 사용할 이유가 없지 
					// --> Exam10_ThreadCompleteService 여기서 이것을 해결해보자!!!!
					
				};
				
				// Thread.start() 하지 않고, Thread 실헹을 위해 구현한 runnable 객체를 인자로 넣어주면, Thread Pool 에서 알아서 만들고 실행한다. 

				Future<String> future = esevice.submit(callable);
				// Future 가 panding 객체 
				// -> THread 시작해서 객체가 바로 들어옴 -> 결과 값이 바로 들어와 있지 않음 
				// 여기는 결과를 return하는게 아니라, 결과를 담을 바구니를 만들은 것 
				
				try {
					// futuer라는 바구니에 결과 값이 담김 
					// get() : 바구니 안에 담긴 결과를 가져온다. 
					// future 바구니 안에 값을 다 가져올 때 까지 block된다. 
					String result = future.get();
					// ==> Thread가 완료 된 후, 값을 다 가져온 뒤 다음 코드 실행 
					System.out.println(result);
					
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		});
		
		stopBtn = new Button("Thread Pool 종료 ");
		stopBtn.setPrefSize(150, 100);

		// 람다식을 사용해서 이벤트를 핸들링한다.
		stopBtn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			// Thread 종료  
			esevice.shutdown();
			
		});
		
		// 긴 Panel 하나를 생성
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(initBtn);
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
