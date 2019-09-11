## Exam04_ChatServer

- 공유 객체

  - Thread가 가져야 하는 클라이언트와 관련된 모든 기능을 가져야 한다.

  - Thread의 공유 객체는 Thread가 가지고 있어야 하는 자료구조, 기능을 구현해 놓은 객체를 지칭한다.

    -> Thread의 Field로 해당 공유 객체가 선언되어 사용된다. 

  - SharedObject Class 선언

    - Inner Class

      - 해당 패키지, 프로그램에서만 사용하는 경우 접근에 용이하다.

      - Outer Class의 모든 Field에 접근할 수 있다. 

    - 외부 Class

      - 재사용성이 좋다. 

    - 클라이언트 Thread를 저장하는 list를 생성한다.

      ```java
      List<ClientRunnable> clients = new ArrayList<ClientRunnable>();
      ```

    - Thread가 클라이언트로부터 데이터를 받아서 모든 클라이언트 Thread에게 데이터를 전달한다.

      - 기본 

        ```java
        public void broadcast(String msg) {
            clients.stream().forEach(t->{
                t.out.println(msg);
                t.out.flush();
            });
        }
        ```

      - 공유 객체의 method (broadcast) 는 여러 Thread에 의해서 동시에 사용.

        - 이런 경우에는 동기화 처리를 해 줘야 문제 없이 출력될 수 있어요.

        - 순차적으로 메시지를 출력할 수 있다. 

          - 동기화 처리를 하지 않으면 메시지가 섞일 위험이 있다.

        - 동기화 처리 추가 코드

          ```java
          public synchronized void broadcast(String msg) {
              clients.stream().forEach(t->{
                  t.out.println(msg);
                  t.out.flush();
              });
          }
          ```

          

  

- Thread 생성

  - 클라이언트와 Mapping되는 Thread를 만들기 위한 Runnable Class

  - Thread가 관리하는 Field 선언

    ```java
    // 공유 객체
    private SharedObject sharedObject;
    
    // 클라이언트와 연결된 socket
    private Socket socket;
    
    // 압출력 Stream -> socket으로 부터 뽑아오는 것이기 때문에 생성자의 인자로 받아올 수 없다.
    private BufferedReader br;
    private PrintWriter out;
    ```

  - 생성자 

    - 클라이언트가 서버에 접속해서 클라이언트에 대한 Thread가 생성될 때 Thread에는 2개의 객체가 전달되어야 한다.

      - 생성자를 2개의 인자 (공유 객체와 소켓)를 받는 형태로 작성

      - 일반적으로 생성자는 Field 초기화를 담당하기 때문에 생성자에서 Stream을 생성

        ```java
        public ClientRunnable() {
        }		
        // 일반적으로 생성자는 Field 초기화를 담당하기 때문에 생성자에서 Stream을 생성한다.
        public ClientRunnable(SharedObject sharedObject, Socket socket) {
            super();
            this.sharedObject = sharedObject;
            this.socket = socket;
            try {
                this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.out = new PrintWriter(socket.getOutputStream());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        ```

  - run 함수 오버라이드 

    - 클라이언트의 Thread가 시작되면 run 함수 가 호출되서 각각의 클라이언트와 데이터 통신을 시작

    - 반복적으로 클라이언트의 데이터를 받아서 공유 객체를 이용해 broadcasting

      ```java
      @Override
      public void run() {
          String msg = "";
          try {
              while ((msg = br.readLine()) != null) {
                  // 클라이언트가 채팅을 종료하면 while문 탈출
                  if (msg.equals("/EXIT/")) {
                      break;
                  }					
                  // 일반적인 채팅 메시지인 경우 모든 클라이언트에게 전달
                  sharedObject.broadcast(msg);
              }
          }catch (Exception e) {
              System.out.println(e);
          }
      }
      ```

- startBtn 버튼 클릭

  - Server Socket을 생성해서 클라이언트의 접속을 기다린다.

    - accept() 은 BLOCKING 함수이기 때문에 JavaFX Thread가 BLOCKING된다. (창 자체가 멈춘다.)

    - 새로운 Thread를 만들어서 클라이언트의 접속을 기다려야 한다.

      - 람다 표현으로 Runnable 객체를 만들어 그 안에 코드를 작성한다.

        ```java
        serverSocket = new ServerSocket(6789);
        ```

      - while 문을 사용하여 클라이언트의 접속을 계속 받아 들인다.

        -  클라이언트의 접속을 기다리고, 클라이언트가 접속하는 순간 Socket객체거가 생성된다.

          ```java
          Socket socket = serverSocket.accept();
          ```

        - 접속한 클라이언트에 대한 Thread를 생성한다.

          ```java
          ClientRunnable clientRunnable = new ClientRunnable(sharedObject, socket);
          ```

        - 새로운 클라이언트가 접속되었으니 해당 데이터가 공용 객체의 clients라는 List에 추가된다.

          ```java
          sharedObject.clients.add(clientRunnable);
          ```

        - ThreadPool을 이용하여 클라이언트 Thread를 실행한다.

          ```java
          executorService.execute(clientRunnable);
          ```

        

    - 클라이언트의 접속을 기다리는 Thread를 실행한다. 

      ```java
      executorService.execute(runnable);
      ```

    - 코드

      ```java
      Runnable runnable = ()->{
          try {
              serverSocket = new ServerSocket(6789);
              // 클라이언트의 접속을 계속 받아들인다.
              while(true) {
                  printMsg("클라이언트 접속 대기중");
                  Socket socket = serverSocket.accept();
                  printMsg("클라이언트 접속 성공!");
                  // 해당 클라이언트에 대한 Thread를 하나 만들기
                  ClientRunnable clientRunnable = new ClientRunnable(sharedObject, socket);
      
                  // 새로운 클라이언트가 접속되었으니 해당 데이터가 공용 객체의 List 안에 들어가야 한다.
                  // Thread에 의해서 공용객체의 데이터가 사용될 때는 동기화 처리를 해줘야 안전하다.
                  sharedObject.clients.add(clientRunnable);
                  printMsg("[ 현재 클리어언트 수 : " + sharedObject.clients.size() + " ]");
                  executorService.execute(clientRunnable);
              }
      
          }catch (Exception e) {
              System.out.println(e);
          }
      };
      executorService.execute(runnable);
      ```

- 전체 코드 

  ```java
  package javaNetwork;
  
  import java.io.BufferedReader;
  import java.io.IOException;
  import java.io.InputStreamReader;
  import java.io.PrintWriter;
  import java.net.ServerSocket;
  import java.net.Socket;
  import java.util.ArrayList;
  import java.util.List;
  import java.util.concurrent.ExecutorService;
  import java.util.concurrent.Executors;
  
  import javafx.application.Application;
  import javafx.application.Platform;
  import javafx.scene.Scene;
  import javafx.scene.control.Button;
  import javafx.scene.control.TextArea;
  import javafx.scene.layout.BorderPane;
  import javafx.scene.layout.FlowPane;
  import javafx.stage.Stage;
  
  
  public class Exam04_ChatServer extends Application{
  
      // 메시지 창
      TextArea textarea;
  
      // 서버 시작 (클라이언트가 접속할 수 있도록 프로그램 시작) , 서버 종료 버튼 (모든 자원 반납 / 프로그램 종료)
      Button startBtn, stopBtn;
  
      // Client의 접속을 받기 위한 ServerSocket 생성
      ServerSocket serverSocket;
  
      // Thread Pool
      ExecutorService executorService = Executors.newCachedThreadPool();
  
      // Singleton 형태의 공유 객체를 생성
      SharedObject sharedObject = new SharedObject();
  
      private void printMsg(String msg) {
          Platform.runLater(()->{
              textarea.appendText("꼴통전사 : " + msg + "\n"); 
          });
      }
  
      // 공유 객체 (Thread가 가져야 하는 클라이언트와 관련된 모든 기능을 가져야 한다.)
      // 클라이언트와 연결된 클라이언트가 사용하는 공유 객체를 만들기 위한 클래스를 Inner Class로 선언
      // Inner Class : 해당 패키지, 프로그램에서만 사용하는 경우 접근에 용이하다.
      //				- Outer Class의 모든 Field에 접근할 수 있다. 
      // 외부 Class : 재사용성이 좋다. 	
      // Thread의 공유 객체는 Thread가 가지고 있어야 하는 자료구조, 기능을 구현해 놓은 객체를 지칭한다.
      class SharedObject{
          // 클라이언트 Thread를 저장하고 있어야 한다.
          List<ClientRunnable> clients = new ArrayList<ClientRunnable>();
  
          // 우리가 필요한 기능은 Broadcast
          // Thread가 클라이언트로부터 데이터를 받아서 모든 클라이언트 Thread에게 데이터를 전달하는 기능을 구현해요~
          // 공유 객체의 method (broadcast) 는 여러 Thread에 의해서 동시에 사용될 수 있어요~
          // 이런 경우에는 동기화 처리를 해 줘야 문제 없이 출력될 수 있어요.
          public synchronized void broadcast(String msg) {
              clients.stream().forEach(t->{
                  t.out.println(msg);
                  t.out.flush();
              });
          }
  
      }
  
      // 클라이언트와 Mapping되는 Thread를 만들기 위한 Runnable Class
      class ClientRunnable implements Runnable{
          // Thread가 관리하는 Field
          private SharedObject sharedObject;
          // 클라이언트와 연결된 socket
          private Socket socket;
          // 압출력 Stream -> socket으로 부터 뽑아오는 것이기 때문에 생성자의 인자로 받아올 수 없다.
          private BufferedReader br;
          private PrintWriter out;
  
          // 클라이언트가 서버에 접속해서 클라이언트에 대한 Thread가 생성될 때
          // Thread에는 2개의 객체가 전달되어야 한다.
          // 생성자를 2개의 인자 (공유 객체와 소켓)를 받는 형태로 작성
          public ClientRunnable() {
          }		
          // 일반적으로 생성자는 Field 초기화를 담당하기 때문에 생성자에서 Stream을 생성한다.
          public ClientRunnable(SharedObject sharedObject, Socket socket) {
              super();
              this.sharedObject = sharedObject;
              this.socket = socket;
              try {
                  this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                  this.out = new PrintWriter(socket.getOutputStream());
              } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }
          }
  
          // 클라이언트의 Thread가 시작되면 run method가 호출되서 각각의 클라이언트와 데이터 통신을 시작
          // 반복적으로 클라이언트의 데이터를 받아서 공유 객체를 이용해 broadcasting
          // -> 무한 loop
          @Override
          public void run() {
              String msg = "";
              try {
                  while ((msg = br.readLine()) != null) {
                      // 클라이언트가 채팅을 종료하면 while문 탈출
                      if (msg.equals("/EXIT/")) {
                          break;
                      }					
                      // 일반적인 채팅 메시지인 경우 모든 클라이언트에게 전달
                      sharedObject.broadcast(msg);
                  }
  
              }catch (Exception e) {
                  System.out.println(e);
              }
  
          }
  
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
  
  
          startBtn = new Button("Chatting 서버 시작!!");
          startBtn.setPrefSize(150, 100);
  
          startBtn.setOnAction(t->{
              // 버튼에서 Action이 발생(클릭)했을 때 호출!
              // 서버 프로그램을 시작
              // 화면이 BLock되지 않는다. 
              textarea.clear();
              printMsg("채팅 서버 기동 - 6789!");
  
              // Server Socket을 생성해서 클라이언트의 접속을 기다려야 해요 
              // accept() 은 BLOCKING 함수이기 때문에 JavaFX Thread가 BLOCKING된다. (창 자체가 멈춘다.)
              // -> 새로운 Thread를 만들어서 클라이언트의 접속을 기다려야 한다.
              Runnable runnable = ()->{
                  try {
                      serverSocket = new ServerSocket(6789);
  
                      // 클라이언트의 접속을 계속 받아들인다.
                      while(true) {
                          printMsg("클라이언트 접속 대기중");
                          Socket socket = serverSocket.accept();
                          printMsg("클라이언트 접속 성공!");
                          // 해당 클라이언트에 대한 Thread를 하나 만들기
                          ClientRunnable clientRunnable = new ClientRunnable(sharedObject, socket);
  
                          // 새로운 클라이언트가 접속되었으니 해당 데이터가 공용 객체의 List 안에 들어가야 한다.
                          // Thread에 의해서 공용객체의 데이터가 사용될 때는 동기화 처리를 해줘야 안전하다.
                          sharedObject.clients.add(clientRunnable);
                          printMsg("[ 현재 클리어언트 수 : " + sharedObject.clients.size() + " ]");
                          executorService.execute(clientRunnable);
                      }
  
                  }catch (Exception e) {
                      System.out.println(e);
                  }
              };
              executorService.execute(runnable);
          });
  
          stopBtn = new Button("Chatting 서버 종료 ");
          stopBtn.setPrefSize(150, 100);
          stopBtn.setOnAction((t)->{
              // 버튼에서 Action이 발생(클릭)했을 때 호출!
              // 서버 프로그램을 종료 
  
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
          primaryStage.setTitle("방 1개 짜리 채팅입니다.");
          primaryStage.show();		
  
      }
  
  }
  
  ```

  



## Exam04_ChatClient

- Field 선언

  ```java
  // 채팅 창
  TextArea textarea;
  // 아이디 입력 칸, 메시지 입력 칸
  TextField idTf, msgTf;
  
  // 서버 접속, 접속 끊기 버튼
  Button connBtn, disConnBtn;
  
  Socket socket;
  BufferedReader br;
  PrintWriter out;
  
  // 클라이언트 Thread는 1개만 만들어져요~
  // 그래서 ThreadPool을 사용할 경우, 오버헤드 발생 -> 굳이 만들 필요는 없다.
  ExecutorService executorService = Executors.newCachedThreadPool();
  
  ```

  

- Thread 생성

  - 서버로부터 들어오는 메시지를 계속 받아서 화면을 출력하기 위한 Thread를 만들기 위한 Runnable 생성

  - Field 선언 & 생성자 생성

    - 서버로부터 들어오는 메시지를 받아들이는 역할

    - 소켓에 대한 입력 Stream만 있어도 된다.

      ```java
      private BufferedReader br;
      
      public ReceiveRunnable(BufferedReader br) {
          this.br = br;
      }
      ```

  - run 함수 오버라이드

    - 입력 Stream으로부터 한줄을 읽어 printMsg() 함수를 이용해 TextArea에 해당 메시지를 출력한다.

      ```java
      @Override
      public void run() {
          String msg ="";
          try {
              while((msg = br.readLine())!=null) {
                  printMsg(msg);
              }
          }catch (Exception e) {
              System.out.println(e);
          }
      }
      ```

  - msgTf 에서 Enter Key가 입력되면 서버에 메시지를 보낸다.

    ```java
    msgTf = new TextField();
    msgTf.setPrefSize(500, 100);
    msgTf.setOnAction((t)->{
        // 입력 상자 (TextField)에서 Enter Key가 입력되면 호출
        // 서버가 보낸 한 줄을 받아서 읽는다.
        String msg = idTf.getText() + ">" + msgTf.getText();
        out.println(msg);
        out.flush();
        msgTf.clear();
    });
    ```

- 전체 코드

  ```java
  package javaNetwork;
  
  import java.io.BufferedReader;
  import java.io.InputStreamReader;
  import java.io.PrintWriter;
  import java.net.Socket;
  import java.util.concurrent.ExecutorService;
  import java.util.concurrent.Executors;
  
  import javafx.application.Application;
  import javafx.application.Platform;
  import javafx.scene.Scene;
  import javafx.scene.control.Button;
  import javafx.scene.control.TextArea;
  import javafx.scene.control.TextField;
  import javafx.scene.layout.BorderPane;
  import javafx.scene.layout.FlowPane;
  import javafx.stage.Stage;
  
  
  public class Exam04_ChatClient extends Application{
  
      // 채팅 창
      TextArea textarea;
      // 아이디 입력 칸, 메시지 입력 칸
      TextField idTf, msgTf;
  
      // 서버 접속, 접속 끊기 버튼
      Button connBtn, disConnBtn;
  
      Socket socket;
      BufferedReader br;
      PrintWriter out;
  
      // 클라이언트 Thread는 1개만 만들어져요~
      // 그래서 ThreadPool을 사용할 경우, 오버헤드 발생 -> 굳이 만들 필요는 없다.
      ExecutorService executorService = Executors.newCachedThreadPool();
  
      private void printMsg(String msg) {
          Platform.runLater(()->{
              textarea.appendText("꼴통전사 : " + msg + "\n"); 
          });
      }
  
      // 서버로부터 들어오는 메시지를 계속 받아서 화면을 출력하기 위한 용도의 Thread
      class ReceiveRunnable implements Runnable{
          // 서버로부터 들어오는 메시지를 받아들이는 역할을 수행
          // 소켓에 대한 입력 스트림만 있으면 되요 
          private BufferedReader br;
  
          public ReceiveRunnable(BufferedReader br) {
              this.br = br;
          }
  
          @Override
          public void run() {
              String msg ="";
              try {
                  while((msg = br.readLine())!=null) {
                      printMsg(msg);
                  }
              }catch (Exception e) {
                  System.out.println(e);
              }
          }
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
  
          idTf = new TextField();
          idTf.setPrefSize(150, 100);
  
          msgTf = new TextField();
          msgTf.setPrefSize(500, 100);
          msgTf.setOnAction((t)->{
              // 입력 상자 (TextField)에서 Enter Key가 입력되면 호출
              // 서버가 보낸 한 줄을 받아서 읽는다.
              String msg = idTf.getText() + ">" + msgTf.getText();
              out.println(msg);
              out.flush();
              msgTf.clear();
          });
  
          connBtn = new Button("Echo 서버 접속!!");
          connBtn.setPrefSize(100, 100);
          connBtn.setOnAction(t->{
              // 버튼에서 Action이 발생(클릭)했을 때 호출!
              // 접속 버튼
              try {
                  // Client는 버튼을 누르면 서버 쪽에 Socket 접속을 시도한다.
                  socket = new Socket("70.12.115.60", 7777);
  
                  // Stream 생성
                  br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                  out = new PrintWriter(socket.getOutputStream());
                  printMsg("Echo Server에 접속을 성공했어요~");
  
                  // 접속을 성공했으니 이제 Thread를 만들어서 서버가 보내준 데이터를 받을 준비를 해요!
                  ReceiveRunnable receiveRunnable = new ReceiveRunnable(br);
                  executorService.execute(receiveRunnable);
  
              }catch (Exception e) {
                  e.printStackTrace();
              }
          });
  
          disConnBtn = new Button("접속 종료 ");
          disConnBtn.setPrefSize(100, 50);
          disConnBtn.setOnAction((t)->{
              try {
                  // 종료 문자열 (protocol)을 "/EXIT/"로 설정
                  // 우리가 정한 (protocol) 서버 접속 종료를 위한 문자열을 보낸다.
                  out.println("/EXIT/");
                  out.flush();
                  out.close();
                  printMsg("서버 접속 종료!!");
              }catch (Exception e) {
                  System.out.println(e);
              }
          });
  
  
          // 긴 Panel 하나를 생성
          FlowPane flowpane = new FlowPane();
          flowpane.setPrefSize(700, 50);
          // FloxPane에 Button을 올려요!
          flowpane.getChildren().add(idTf);
          flowpane.getChildren().add(msgTf);
          flowpane.getChildren().add(connBtn);
          flowpane.getChildren().add(disConnBtn);
          root.setBottom(flowpane);		
  
          // 화면에 띄우기
          Scene scene = new Scene(root);
          primaryStage.setScene(scene);
          primaryStage.setTitle("Thread 예제 입니다.");
          primaryStage.show();		
  
      }
  
  }
  
  ```

  



- 실행 화면

  - 서버 시작 버튼을 누르면 서버는 클라이언트의 접속을 기다리며 Blocking  상태가 된다.

    ![1567426855451](https://user-images.githubusercontent.com/39547788/64115047-a14fe880-cdc9-11e9-9f9d-54f0133c2ac6.png)

  - 첫번 째 클라이언트가 접속하면, 서버는 현재 클라이언트의 수를 출력하고 다시 클라이언트의 접속을 기다린다.

    ![1567426965082](https://user-images.githubusercontent.com/39547788/64115048-a1e87f00-cdc9-11e9-9f80-97a9e5ae10a1.png)

  - 두번째 클라이언트가 접속하면, 서버는 현재 클라이언트의 수를 출력하고 또 다시 클라이언트의 접속을 기다린다.

    ![1567427053676](https://user-images.githubusercontent.com/39547788/64115049-a14fe880-cdc9-11e9-9ad4-214bbed0a2e9.png)

  - 첫번째 클라이언트 (수박) 가 '안녕'이라는 메시지를 보내면 두번째 클라이언트 (딸기)가 메시지를 받게 된다.

    - 메시지를 보내기 전

      ![1567427144913](https://user-images.githubusercontent.com/39547788/64115050-a1e87f00-cdc9-11e9-8e69-2595a45d6b61.png)

    - 메시지를 보낸 후 

      ![1567427171753](https://user-images.githubusercontent.com/39547788/64115051-a1e87f00-cdc9-11e9-824e-905cd8dd25fc.png)

  - 반대로 두번째 클라이언트 (딸기) 가 '안녕'이라는 메시지를 보내면 첫번째 클라이언트 (수박)가 메시지를 받게 된다

    - 메시지를 보내기 전

      ![1567427205769](https://user-images.githubusercontent.com/39547788/64115052-a2811580-cdc9-11e9-8c05-de60b20195a0.png)

    - 메시지를 보낸 후

      ![1567427254963](https://user-images.githubusercontent.com/39547788/64115053-a2811580-cdc9-11e9-95b2-f6f7061e869f.png)

  - 만약, 두번째 클라이언트 (딸기) 가 접속 종료 버튼을 누르면 서버는 두번째 클라이언트 (딸기) 와의 연결을 끊는다.

    ![1567427464478](https://user-images.githubusercontent.com/39547788/64115055-a2811580-cdc9-11e9-89b5-337bf9253825.png)

    - 두번째 클라이언트 (딸기)가 메시지를 작성하여 전송해도 전해지지 않는다.

      - 메시지를 보내기 전

        ![1567427528641](https://user-images.githubusercontent.com/39547788/64115056-a2811580-cdc9-11e9-830f-0107ddca41fe.png)

      - 메시지를 보낸 후 -> '접속 종료된 딸기'라는 메시지는 전송되지 않았다.

        ![1567427550809](https://user-images.githubusercontent.com/39547788/64115057-a319ac00-cdc9-11e9-8c61-1a94de860ecd.png)