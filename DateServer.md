# 하나의 클라이언트에게 날짜를 전송하는 서버 프로그램을 작성하자

- 접속한 클라이언트에게 날짜를 전송한다.
- 클라이언트가 서버에 접속하면 서버는 현재 시간을 구해서 클라이언트에게 전송하고 서버프로그램을 종료한다.
- 클라이언트는 서버가 보내준 데이터를 화면에 출력하고 프로그램을 종료 

### Server 

- 수동적으로 누군가 접속하기를 대기함 (수동적 접속 대기)
- 클라이언트의  Socket 접속을 기다린다.
  - Server Socket  이용
    - 서버 쪽 에서만 나오는 Class

### Client 

- 능동적으로 접속함

- 서버와 클라이언트가 Network으로 접속이 되면 각각 Socket 객체가 생성된다.
  - stream을 열어 데이터를 주고 받는다.



### Exam01_DateServer

- 서버쪽 프로그램은 클라이언트의 소켓 접속을 기다린다.

  ```java
  ServerSocket server = null;
  ```

- 클라이언트와 접속된 후 Socket 객체가 있어야 클라이언트와 데이터 통신이 가능하다

  ```java
  Socket socket = null;
  ```

- ServerSocket의 객체 생성 시, port 번호를 인자를 넣어 생성한다.

  - port 번호는 0~65535 까지 사용 가능

    - 0~1023  까지는 예약되어 있다.

    ```java
    // 클라이언트와 연결될 서버 쪽의 Socket
    server = new ServerSocket(5554);
    ```

- Client의 접속을 기다린다.

  - 여기서 서버쪽 프로그램이 BLOCK

    ```java
    socket = server.accept();
    ```

- 만약 Client 가 접속해 오면 Socket 객체를 하나 리턴한다.

  - 출력 시스템을 얻어 클라이언트에게 보낼 Stream을 생성한다.

    ```java
    PrintWriter out = new PrintWriter(socket.getOutputStream());
    ```

- 오늘 날짜를 구하고 클라이언트에게 전송한다.

  -  일반적으로 Reader와 Writer는 내부 buffer를 가지고 있다.

    - out.print()
      - 데이터가 바로 전송되지 않는다.
      - 내부 버퍼가 꽉 차거나 close() 될 때 전달 된다.
    - out.flush() 
      - 명시적으로 내부 버퍼를 지우고 데이터를 전달하도록 명령한다.

    ```java
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    			out.print(format.format(new Date()));
    // 명시적으로 내부 buffer를 비우고 데이터를 전달 명령
    out.flush(); 
    			
    ```

- 사용한 자원 반납

  ```java
  out.close();		
  socket.close();
  server.close();
  ```

- 전체 코드

  ```java
  package javaNetwork;
  
  import java.io.PrintWriter;
  import java.net.ServerSocket;
  import java.net.Socket;
  import java.text.SimpleDateFormat;
  import java.util.Date;
  
  /*
  		Server 쪽 프로그램은 Client의 소켓 접속을 기다려야 해요.
  		Server Socket (서버 쪽에서만 나오는 Class) 이라는 Class를 이용해서 기능을 구현 
  		: 누군가 나에게 Network 요청하는 것을 기다린다. 
  
  
  
  
   */
  
  public class Exam01_DateServer {
  
      public static void main(String[] args) {
  
          //Server 쪽 프로그램은 Client의 소켓 접속을 기다린다.
          ServerSocket server = null;
  
          // Client 와 접속된 후 Socket 객체가 있어야지 Client와 데이터 통신이 가능
          Socket socket = null;
  
          try {
              // ServerSocket의 객체 생성 시, port 번호를 인자를 넣어 생성한다.
              // port 번호는 0~65535 까지 사용 가능 -> 0~1023  까지는 예약되어 있다. 
              // Client와 연결될 서버 쪽의  socket
              server = new ServerSocket(5554);
              System.out.println("클라이언트 접속 대기");
  
              // accept() : Client의 접속을 기다린다. --> 여기서 서버쪽 프로그램이 BLOCK
              socket = server.accept();
  
              // 만약 Client 가 접속해 오면 Socket 객체를 하나 리턴한다. 
              // 서버 쪽의  socket
              // 출력 시스템을 얻어온다.
              PrintWriter out = new PrintWriter(socket.getOutputStream());
  
              SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
              out.print(format.format(new Date()));
  
              // 일반적으로 Reader와 Writer는 내부 buffer를 가지고 있어요.
              // print() 한뒤 바로 보내지는게 아니다. 
              // 내부 버퍼가 꽉 차거나 close 될 때 전달이 된다. 
              out.flush(); // 명시적으로 내부 buffer를 비우고 데이터를 전달 명령
  
              out.close();		
  
              socket.close();
              server.close();
  
          }catch (Exception e) {
              System.out.println(e);
          }
  
      }
  
  }
  
  ```

  

### Exam01_DateClient

- btn 버튼을 누르면 서버에 Socket 접속을 시도한다.

  - 만약, 접속에 성공하면 socket 객체를 하나 획득한다.

    - 접속에 대한 증표 : socket에 대한 객체

      ```java
      Socket socket = new Socket("127.0.0.1", 5554);
      ```

- 입출력 Stream을 생성한다.

  ```java
  InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
  BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
  
  ```

- 서버가 보낸 한 줄을 받아서 읽는다.

  ```java
  String msg = bufferedReader.readLine();
  printMsg(msg);
  
  ```

- 사용한 자원 반납

  ```java
  bufferedReader.close();
  inputStreamReader.close();
  socket.close();
  ```

- 전체 코드

  ```java
  package javaNetwork;
  
  import java.io.BufferedReader;
  import java.io.InputStreamReader;
  import java.net.Socket;
  
  import javafx.application.Application;
  import javafx.application.Platform;
  import javafx.scene.Scene;
  import javafx.scene.control.Button;
  import javafx.scene.control.TextArea;
  import javafx.scene.layout.BorderPane;
  import javafx.scene.layout.FlowPane;
  import javafx.stage.Stage;
  
  public class Exam01_DateClient extends Application{
  
      TextArea textarea;
  
      Button btn;
  
      private void printMsg(String msg) {
          Platform.runLater(()->{
              textarea.appendText(msg + "\n"); 
          });
      }
  
      public static void main(String[] args) {
          launch();
      }
  
      @Override
      public void start(Stage primaryStage) throws Exception {
          BorderPane root = new BorderPane();
  
          root.setPrefSize(700, 500);
  
          textarea = new TextArea();
          root.setCenter(textarea);
  
          btn = new Button("Date 서버 접속!!");
          btn.setPrefSize(100, 100);
  
          btn.setOnAction(t->{
              // 버튼에서 Action이 발생(클릭)했을 때 호출!
              try {
                  // Client는 버튼을 누르면 서버 쪽에 Socket 접속을 시도한다.
                  // Socket socket = new Socket("localhost");
                  Socket socket = new Socket("127.0.0.1", 5554);
  
  
  
                  // 만약에 접속에 성공하면 socket 객체를 하나 획득한다.
                  // 접속에 대한 증표 -> socket에 대한 객체 
                  // client 쪽의 socket
                  // 버튼을 누르고 접속을 하는 순간 
  
                  InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                  BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
  
                  // 서버가 보낸 한 줄을 받아서 읽는다.
                  String msg = bufferedReader.readLine();
                  printMsg(msg);
                  bufferedReader.close();
                  inputStreamReader.close();
                  socket.close();
  
  
              }catch (Exception e) {
  
              }
  
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
  ```

  