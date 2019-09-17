package JavaIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javaStream.testt;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
	JavaFX를 이용해서 GUI 프로그램을 만들려고 한다.
	화면에 창을 띄우기 위해서는 Applicaton이라는 Class의 Instanace를 생성
	-> Application 클래스를 상속을 받아 Is-a 관계가 생성
 */
public class Exam02_NotePad extends Application {

	TextArea textarea;

	// 파일 열기, 파일 저장 버튼
	Button openBtn, saveBtn;

	// 특정 파일을 읽고 쓰기 위한 File에 대한 reference : 어떤 파일을 이용하니...?
	File file;

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

		openBtn = new Button("파일 열기");
		openBtn.setPrefSize(150, 50);
		openBtn.setOnAction((t)->{
			// 기존의 TextArea의 내용을 다 지운다. -> 새로운 파일의 내용을 붙이기 위해 
			textarea.clear();

			// 파일을 선택하기 위한 FileChooser Class 사용
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Open 할 파일을 선택하세요.");

			// 파일을 선택하는 대화 상자 Dialog 뜸 --> 선택한 파일에 대한 File 객체를 리턴 받는다.
			// File chooser로 부터 오픈할 파일에 대한 reference를 획득
			file = chooser.showOpenDialog(primaryStage);

			// 획득한 파일 객체로 부터 Input stream을 열어요~
			// 데이터 통로를 열어 데이터를 슉슉슉슉 받아 파일의 내용을 읽고 출력한다.
			// FileReader : 파일로 부터 읽어 들이는 Stream 
			try {
				// 얻은 File로 Stream을 열었다.
				FileReader fileReader = new FileReader(file);

				// BufferedReader가 더 좋기 때문에 fileReader로 BufferedReader를 만든다.
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line = "";

				// (line = bufferedReader.readLine()) != null : 읽어 온게 없다 == File의 끝
				while ((line = bufferedReader.readLine()) != null) {
					// 람다식에서 사용하는 지역변수 line을 사용하기 위해 별도의 함수를 작성하여 print를 수행한다. 
					printMsg(line);

				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}

		});
		
		saveBtn = new Button("파일 저장");
		saveBtn.setPrefSize(150, 50);
		saveBtn.setOnAction(t->{
			String  content = textarea.getText();
			
			// File에 데이터를 쓸 때
			try {
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write(content);
				// 반드시 close 처리를 해줘야 해요
				fileWriter.close();
				
				// 경고창 띄우기 		// 안내 또는 경고
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("파일 저장 !!");
				alert.setHeaderText("File Save !!");
				alert.setContentText("파일에 내용이 저장되었어요~");
				
				// 확인 버튼 누를 때까지 경고창으로 띄우고 대기
				alert.showAndWait();

			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		FlowPane flowPane = new FlowPane();
		flowPane.setPrefSize(700, 50);
		flowPane.getChildren().add(openBtn);
		flowPane.getChildren().add(saveBtn);
		
		root.setBottom(flowPane);
		
		// 장면을 만들기 
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		

	}

	public static void main(String[] args) {
		launch();
	}

}
