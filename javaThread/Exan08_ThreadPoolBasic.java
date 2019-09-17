package javaThread;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exan08_ThreadPoolBasic extends Application{

	TextArea textarea;
	
	// initBtn : Thread pool을 생성하는 버튼
	// startBtn : Thread pool을 이용해서 Thread를 실행시키는 버튼
	// stopBtn : 프로그램이 종료 될 때 Thread Pool 객체를 종료 시키는 버튼
	Button initBtn, startBtn, stopBtn;
	
	// Thread Pool
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
			// 처음에 만들어지는 Thread Pool 에는 Thread가 없어요 
			// 만약 필요하면 내부적으로 Thread 생성
			// 만드는 Thread의 수는 제한이 없음 
			// 60호 동안 Thread가 사용되지 않으면 메모리를 줄이기 위해 자동적으로 삭제

			//esevice = Executors.newFixedThreadPool(5);
			// 처음에 만들어지는 Thread Pool 에는 Thread가 없어요 
			// 만약 필요하면 내부적으로 Thread 생성
			// 인자로 주어지는 수 만큼의 Thread의 개수를 넘지 않음
			// 이 예제는 최대 5개
			// Thread가 사용되지 않더라도 만들어진 Thread는 계속 유지
 
		});
		
		startBtn = new Button("Thread 실행!!");
		startBtn.setPrefSize(150, 100);

		// 람다식을 사용해서 이벤트를 핸들링한다.
		startBtn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			// Thread 실행  
			
			for (int i = 0; i < 10; i++) {
				final int ii = i;
				Runnable runnable = () -> {
					Thread.currentThread().setName("MyThread_" + ii);
					String msg = "Thread 이름 : " + Thread.currentThread().getName() + " --- Pool의 개수 : " + ((ThreadPoolExecutor)esevice).getPoolSize();
					printName(msg + "\n");
					
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				};
				// Thread.start() 하지 않고, Thread 실헹을 위해 구현한 runnable 객체를 인자로 넣어주면, Thread Pool 에서 알아서 만들고 실행한다. 
				esevice.execute(runnable);
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
