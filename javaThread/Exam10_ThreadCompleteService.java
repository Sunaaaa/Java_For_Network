package javaThread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;

import javax.swing.JTable.PrintMode;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/*
 	1부터 100까지 숫자의 합을 구해요.
 	1부터 10까지 1개의 THread가 합을 구해서 결과를 리턴한다.
 	11부터 20까지 1개의 THread가 합을 구해서 결과를 리턴한다.
 	'''
 	91부터 100까지 1개의 THread가 합을 구해서 결과를 리턴한다.
 	--> 총 10개의 Thread의 결과 값을 이용해서 숫자의 합을 구한다. 
 	
 	Thread Pool을 이용하고, Runnable이 아닌 Callable을 이용해서 리턴 값을 받는다. 
 	--> 나중에 10개의 Thread로부터 각각 데이터를 받아들이는 Thread를 하나 만든다. 
 		(10개의 Thread를 동시에 실행했기 때문에 누가 먼저 끝났는지 알 수 없다.)
 	
 */

public class Exam10_ThreadCompleteService extends Application{

	TextArea textarea;
	
	// initBtn : Thread pool을 생성하는 버튼
	// startBtn : Thread pool을 이용해서 Thread를 실행시키는 버튼
	// stopBtn : 프로그램이 종료 될 때 Thread Pool 객체를 종료 시키는 버튼
	Button initBtn, startBtn, stopBtn;
	
	ExecutorService esevice;

	// 얘를 이용해서 Thread를 실행 시킬 예정
	ExecutorCompletionService<Integer> completionService;
	
	// 최종 결과
	private int total = 0;
	
	public static void main(String[] args) {
		launch();
	}
	
	private void printMsg(String msg) {
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
			// 기존 Thread Pool을 가지고 더 성능이 좋은 Thread Pool 생성
			completionService = new ExecutorCompletionService<Integer>(esevice);

			int threadCount=((ThreadPoolExecutor)esevice).getPoolSize();
			printMsg("현재 Thread Pool 안에 있는 Thread 개수 : " + threadCount + "\n");

		});
		
		startBtn = new Button("Thread 실행!!");
		startBtn.setPrefSize(150, 100);

		// 람다식을 사용해서 이벤트를 핸들링한다.
		startBtn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			// Thread 실행  

			for (int i = 1; i < 101; i+=10) {
				final int ii = i;
				
				Callable<Integer> callable = new Callable<Integer>() {

					@Override
					public Integer call() throws Exception {
						IntStream intStream = IntStream.rangeClosed(ii, ii+9);
						int sum = intStream.sum();
						return sum;
					}
					
				};
				
				// Thread.start() 하지 않고, Thread 실헹을 위해 구현한 runnable 객체를 인자로 넣어주면, Thread Pool 에서 알아서 만들고 실행한다. 
				
				// 리턴 값이 있는 THread를 수행한다.
				completionService.submit(callable);
				
			}
			
			// 위의 10개의 Thread는 계산을 수행하고 
			// 아래의 1개 Thread는 계산결과를 기다였다가 결과를 취합한다.-> total 에 저장
			
			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 10; i++) {
						// 위의 Thread 10개가 언제 끝나는지 알 수 없다. 
						// take() : Thread가 끝난게 있으면 결과 값을 가져온다. 
						try {
							Future<Integer> future = completionService.take();
							try {
								total += future.get();
								
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}					
					printMsg("최종 결과 값은 : " + total);
				}
				
			};
			esevice.execute(runnable);
			
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
