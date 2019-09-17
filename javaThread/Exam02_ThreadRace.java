package javaThread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

class UserPanel extends FlowPane{
	// 주황색 타원 만들고 있어요 
	private TextField nameField = new TextField();
	
	// 인자로 double Type의 초기값 
	// 0.0 : 하나도 안채워져 있는 형태 
	private ProgressBar progressBar = new ProgressBar(0.0);
	private ProgressIndicator indicator = new ProgressIndicator();
	
	public UserPanel() {
	}
	public UserPanel(String name) {
		setPrefSize(700, 50);
		nameField.setText(name);
		nameField.setPrefSize(100, 50);
		progressBar.setPrefSize(500, 50);
		indicator.setPrefSize(50, 50);
		getChildren().add(nameField);
		getChildren().add(progressBar);
		getChildren().add(indicator);
	}
	public TextField getNameField() {
		return nameField;
	}
	public void setNameField(TextField nameField) {
		this.nameField = nameField;
	}
	public ProgressBar getProgressBar() {
		return progressBar;
	}
	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	public ProgressIndicator getIndicator() {
		return indicator;
	}
	public void setIndicator(ProgressIndicator indicator) {
		this.indicator = indicator;
	}

}		

class ProgressRunnable implements Runnable{
	
	private String name;
	private ProgressBar progressBar;
	private ProgressIndicator indicator;
	private TextArea textarea;
	
	public ProgressRunnable() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ProgressRunnable(String name, ProgressBar progressBar, ProgressIndicator indicator, TextArea textarea) {
		super();
		this.name = name;
		this.progressBar = progressBar;
		this.indicator = indicator;
		this.textarea = textarea;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public ProgressBar getProgressBar() {
		return progressBar;
	}


	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}


	public ProgressIndicator getIndicator() {
		return indicator;
	}


	public void setIndicator(ProgressIndicator indicator) {
		this.indicator = indicator;
	}


	public TextArea getTextarea() {
		return textarea;
	}


	public void setTextarea(TextArea textarea) {
		this.textarea = textarea;
	}


	@Override
	public void run() {
		// Thread가 동작해서 progress Bar를 제어하면 될 것 같아요 
		Random r = new Random();
		double k = 0;
		while(this.progressBar.getProgress() < 1.0) {
			try {
				// 1초 동안 현재 Thread를 sleep
				Thread.sleep(1000);
				// 0부터 0.01 사이의 값
				// Thread가 잤다 깼다 하면서 k 값이 지속적으로 증가
				k += r.nextDouble() * 0.1;
				
				final double tt = k;
				// 람다식에서 지역변수를 사용하기 위해서는 final 처리가 됨 
				Platform.runLater(()->{
					this.indicator.setProgress(tt);
					this.progressBar.setProgress(tt);
				});
				
				if (k>1.0) break;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		System.out.println(this.getName() + "끝!!!");
		
		Platform.runLater(()->{
			textarea.appendText( "등" + getName() + "\n");
		});
		
	}
}

public class Exam02_ThreadRace extends Application{

	private List<String> names = Arrays.asList("홍길동","이순신", "강감찬");
	private List<ProgressRunnable> uRunnable = new ArrayList<ProgressRunnable>();
	
	// progressBar를 제어할 Thread가 FlowPane 당 1개 씩 존재해야 한다.
	TextArea textarea;
	Button btn;

	public static void main(String[] args) {
		launch();

	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();		
		root.setPrefSize(700, 500);
		
		// Center 부분을 차지할 TilePane을 생성해야 한다.
		TilePane center = new TilePane();

		// 1열만 존재하는 TilePane
		center.setPrefColumns(1);
		
		// 4행이 존재하는 TilePane
		center.setPrefRows(4);	
		
		// 메세지가 출력되는 TextArea 생성 및 크기 결정
		textarea = new TextArea();
		textarea.setPrefSize(600, 100);
		
		// 이제 만들어진 TilePane에 3개의 FlowPane과 TextArea를 부착
		for (String name : names) {
			UserPanel panel = new UserPanel(name);
			center.getChildren().add(panel);
			uRunnable.add(new ProgressRunnable(
					panel.getNameField().getText(),
					panel.getProgressBar(),
					panel.getIndicator(),
					textarea));
		}
		center.getChildren().add(textarea);
		root.setCenter(center);

		btn = new Button("버튼 클릭!!");
		btn.setPrefSize(100, 100);

		btn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			for (ProgressRunnable runnable : uRunnable) {
				new Thread(runnable).start();
			}
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(btn);
		root.setBottom(flowpane);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread 예제 입니다.");
		primaryStage.show();
		
		
	}

}
