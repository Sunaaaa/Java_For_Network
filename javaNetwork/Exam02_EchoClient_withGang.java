package javaNetwork;
/*
 * Echo program을 작성해 보아요!
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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

public class Exam02_EchoClient_withGang extends Application{
   
   TextArea textarea;
   Button btn, btn2;      // 서버 접속 버튼
   TextField tf;   
   Socket socket;
   InputStreamReader isr;
   BufferedReader br;
   PrintWriter out;
   
   private void printMsg(String msg) {
      Platform.runLater(()->{
         textarea.appendText("밥통전사  : " + msg + "\n");
      });
   }
   @Override
   public void start(Stage primaryStage) throws Exception {   // primaryStage 요게 실제 윈도우를 지칭하는 reference
      // 화면 구성해서 window 띄우는 코드
      // 화면 기본 layout을 설정 => 화면을 동서남북 중앙(5개의 영역)으로 분리
      BorderPane root = new BorderPane();
      // Borderpane의 크기를 설정 => 화면에 띄우는 window의 크기 설정
      root.setPrefSize(700, 500);      
      
      // Component생성해서 BorderPane에 부착
      textarea = new TextArea();
      root.setCenter(textarea);
      
      btn = new Button("Echo 서버 접속");   // 버튼클릭!! 이들어가있는 버튼이 생성됨
      btn.setPrefSize(250, 50);
      btn.setOnAction(t -> {
         // 접속 버튼
         try {
            // 클라이언트는 버튼을 누르면 서버쪽에 Socket접속을 시도.
            // 만약에 접속에 성공하면 socket객체를 하나 획득
            Socket socket = new Socket("127.0.0.1",5577); 
                                             
            // Stream을 생성
            isr = new InputStreamReader(socket.getInputStream());
            br = new BufferedReader(isr);
            out = new PrintWriter(socket.getOutputStream());
            printMsg("Echo서버 접속 성공!!");
         } catch (Exception e) {
            System.out.println(e);
         }
      });
      
      btn2 = new Button("Echo 연결 해제");   // 버튼클릭!! 이들어가있는 버튼이 생성됨
      btn2.setPrefSize(250, 50);
      btn2.setOnAction(t -> {
         out.println("EXIT");      // 서버한테 연결된 스트림. 서버로 문자열 전송@@@!!!
         out.flush(); 
      });
      
      tf = new TextField();   // 입력상자
      tf.setPrefSize(200, 40);
      tf.setOnAction(t->{   
         // 입력상자(TextField)에서 액션이 발생햇을때 (enter key입력) 호출
         String msg = tf.getText();
         out.println(msg);      // 서버한테 연결된 스트림. 서버로 문자열 전송@@@!!!
         out.flush();
         tf.clear();
         try {
            String result = br.readLine();   // 여기가 받는부분
            printMsg(result);      // 화면에 올려주기
         } catch (IOException e) {
            e.printStackTrace();
         }
      });   
      
      
      FlowPane flowpane = new FlowPane();      // 긴 판자 하나 만든다고 생각하시면 됩니다
      flowpane.setPrefSize(700, 50);
      // flowpane에 버튼을 올려요!
      flowpane.getChildren().add(btn);
      flowpane.getChildren().add(tf);         // 입력상자 넣어주기@
      flowpane.getChildren().add(btn2);
      root.setBottom(flowpane);            // 바탐쪽에 flowpane 붙이는 작업
      
      // Scene객체가 필요해요.
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);         // 윈도우(primaryStage)에 화면을 설정해 주는 코드
      primaryStage.setTitle("Thread 예제입니다.!!");
      primaryStage.show();
   }

   public static void main(String[] args) {
      launch();      // 내부적으로 바로위에 start method가 실행됩니다.
   }
}