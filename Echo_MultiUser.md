## Thread 를 이용하여 여러 명의 클라이언트에게 Echo  서비스 제공하는 프로그램 작성

![1566819865091](C:\Users\student\AppData\Roaming\Typora\typora-user-images\1566819865091.png)

- Echo 서비스가 1명의 클라이언트에 대해서는 잘 동작하지만, 다수의 클라이언트에 대해서는 서비스를 제공하지 못한다. 
- 다수의 클라이언트에게 똑같은 Echo 서비스를 제공해 주기 위해서는 **<u>"Multi Thread"</u>**를 이용한다.

### Exam03_EchoServer_MultiUser

- 능동적으로 늘었다가 줄었다가를 반복하는 Thread Pool 생성 

  ```java
  ExecutorService executorService = Executors.newCachedThreadPool();
  ```

  

- Client의 접속을 받기 위한 ServerSocket 선언

  - 예외처리가 필요한 코드이기 때문에, Field에서 바로 생성할 수 없다. 

    ```java
    ServerSocket serverSocket;
    ```

    

- 서버 프로그램을 시작

  - startBtn 버튼 클릭시 서버 프로그램을 시작한다.

    - 서버

      - 클라이언트의 접속을 기다린다.

      - 클라이언트의 접속이 되면 Thread를 하나 생성한다.

      - socket을 이용해서 Thread를 시작해서 클라이언트의 Thread가 통신하도록 만든다.

      - 서버는 다시 다른 클라이언트의 접속을 기다린다.

        - 방법 1

          - ServerSocket을 생성하고, 클라이언트의 접속을 기다린다. 

            ```java
            startBtn.setOnAction(t->{
                try {
                    // Client는 버튼을 누르면 서버 쪽에 Socket 접속을 시도한다.
                    serverSocket = new ServerSocket(7777);
                    printMsg("Echo Server 가동!!");
            
                    while(true) {
                        printMsg("Client 접속 대기!!");
                        Socket s = serverSocket.accept();
            
                        // 클라이언트가 접속을 했으니, Thread를 만들고 시작해야 해요!
                        printMsg("Client 접속 성공!!");
                    }			
            
                }catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ```

          *** 이 방식은 startBtn 클릭시, 윈도우를 멈추게 한다. 

          ​	Why ? 클라이언트의 접속이 있기 전까지 JavaFX가 현재 서버를 잡고 있기 때문에 

          ​				윈도우가 Block이 된다. 

          ​	How? JavaFX에서 클라이언트의 Server 접속을 수행하는 별도의 Thread를 생성한다.

          

        - 방법 2

          -  JavaFX에서 클라이언트의 Server 접속을 수행하는 별도의 Thread를 생성]

            ```java
            startBtn.setOnAction(t->{
                // 버튼에서 Action이 발생(클릭)했을 때 호출!
                // 서버 프로그램을 시작
            
                // 화면이 BLock되지 않는다. 
                Runnable runnable = ()->{
                    try {
                        // Client는 버튼을 누르면 서버 쪽에 Socket 접속을 시도한다.
                        serverSocket = new ServerSocket(7777);
            
                        printMsg("Echo Server 가동!!");
                        while(true) {
                            printMsg("Client 접속 대기!!");
                            Socket s = serverSocket.accept();
            
                            // 클라이언트가 접속을 했으니, Thread를 만들고 시작해야 해요!
                            printMsg("Client 접속 성공!!");
                            EchoRunnable echoRunnable = new EchoRunnable(s);
                            executorService.execute(echoRunnable);
                        }			
            
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                };
                executorService.execute(runnable);
            });
            ```

            



- Thread 생성

  - Runnable Interface를 이용하여 EchoRunnable Class를 생성한다. 

    - Client와의 통신을 위한 socket을 인자로 받는 EchoRunnable  Class

      ```java
      class EchoRunnable implements Runnable{
      
          private Socket s;
          private BufferedReader br;
          private PrintWriter out;
      
          public EchoRunnable() {
          }
      
          public EchoRunnable(Socket s) {
              this.s = s;
              try {
                  this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                  this.out = new PrintWriter(s.getOutputStream());
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      
          @Override
          public void run() {
              // Client와 Echo처리 구현
              // Client가 문자열을 보내면 해당 문자열을 받아서 다시 클라이언트에게 전달한다.
              // 한번하고 종료하는게 아니라 클라이언트가 "EXIT"라는 문자열을 보낼때까지 지속한다.
              System.out.println(Thread.currentThread().getName());
              String line ="";
      
              // (line=br.readLine())!=null : 접속이 끊긴 경우
              try {
                  while ((line=br.readLine())!=null) {
                      if (Thread.currentThread().interrupted()) {
                          break;
                      }
                      // 문자열 비교는 반드시 equals() 함수 이용
                      if (line.equals("EXIT")) {
                          break; // 가장 근접한 loop (while 문) 를 탈출										
                      }else {
                          out.println(line);
                          out.flush();
                      }
                  }
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      ```

- stopBtn 버튼을 클릭하면, 서버를 종료한다. 

  ```java
  stopBtn = new Button("Echo 서버 종료 ");
  stopBtn.setPrefSize(100, 50);
  stopBtn.setOnAction((t)->{
  
      try {
          serverSocket.close();
          executorService.shutdown();
      } catch (IOException e) {
          e.printStackTrace();
      }
  });
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
  
  class EchoRunnable implements Runnable{
  
      private Socket s;
      private BufferedReader br;
      private PrintWriter out;
  
      public EchoRunnable() {
      }
  
      public EchoRunnable(Socket s) {
          this.s = s;
          try {
              this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
              this.out = new PrintWriter(s.getOutputStream());
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
  
      @Override
      public void run() {
          // Client와 Echo처리 구현
          // Client가 문자열을 보내면 해당 문자열을 받아서 다시 클라이언트에게 전달한다.
          // 한번하고 종료하는게 아니라 클라이언트가 "/EXIT"라는 문자열을 보낼때까지 지속한다.
          String line ="";
  
          // (line=br.readLine())!=null : 접속이 끊긴 경우
          try {
              while ((line=br.readLine())!=null) {
                  if (Thread.currentThread().interrupted()) {
                      break;
                  }
                  // 문자열 비교는 반드시 equals() 함수 이용
                  if (line.equals("EXIT")) {
                      break; // 가장 근접한 loop (while 문) 를 탈출										
                  }else {
                      out.println(line);
                      out.flush();
                  }
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
  
      }
  }
  
  public class Exam03_EchoServer_MultiUser extends Application{
  
      TextArea textarea;
  
      Button startBtn, stopBtn;
  
      // Thread Pool 생성
      ExecutorService executorService = Executors.newCachedThreadPool();
  
      // Client의 접속을 받기 위한 ServerSocket 생성
      ServerSocket serverSocket;
  
      Socket socket;
      String msg;
  
      private void printMsg(String msg) {
          Platform.runLater(()->{
              textarea.appendText("꼴통전사 : " + msg + "\n"); 
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
  
  
          startBtn = new Button("Echo 서버 접속!!");
          startBtn.setPrefSize(100, 100);
  
          startBtn.setOnAction(t->{
              // 버튼에서 Action이 발생(클릭)했을 때 호출!
              // 서버 프로그램을 시작
              /*
  				서버 : 클라이언트의 접속을 기다려요!
  					-> 접속이 되면 Thread를 하나 생성
  					-> Thread를 시작해서 클라이언트의 Thread가 통신하도록 만들어요
  					==> 서버는 다시 다른 클라이언트의 접속을 기다려요~
  			 */
              // 화면이 BLock되지 않는다. 
              Runnable runnable = ()->{
                  try {
                      // Client는 버튼을 누르면 서버 쪽에 Socket 접속을 시도한다.
                      serverSocket = new ServerSocket(7777);
  
                      printMsg("Echo Server 가동!!");
                      while(true) {
                          printMsg("Client 접속 대기!!");
  
                          // JavaFX가 현재 서버를 잡고 있기 때문에 클라이언트의 접속이 있기 전에 
                          // window가 block이 된다. 
                          // --> JavaFX와 Thread (클라이언트의 server 접속) 를 실행시키는 부분으로 나눈다. 
                          Socket s = serverSocket.accept();
  
                          // 클라이언트가 접속을 했으니, Thread를 만들고 시작해야 해요!
                          printMsg("Client 접속 성공!!");
  
                          EchoRunnable echoRunnable = new EchoRunnable(s);
  
                          executorService.execute(echoRunnable);
                      }			
  
                  }catch (Exception e) {
                      e.printStackTrace();
                  }
  
              };
              executorService.execute(runnable);
          });
  
          stopBtn = new Button("Echo 서버 종료 ");
          stopBtn.setPrefSize(100, 50);
          stopBtn.setOnAction((t)->{
  
              try {
                  serverSocket.close();
                  executorService.shutdown();
              } catch (IOException e) {
                  e.printStackTrace();
              }
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
          primaryStage.setTitle("Multi Client Echo Server");
          primaryStage.show();		
  
      }
  
  }
  
  ```

  



### Exam03_EchoClient_MultiUser

- startBtn 버튼을 클릭하면, 서버 쪽의 Socket에 접속을 시도하고 Stream을 생성한다.

  ```java
  socket = new Socket("127.0.0.1", 7777);
  br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  out = new PrintWriter(socket.getOutputStream());
  ```

- textField에서 Enter Key가 입력되면, 서버가 보내는 한 줄을 받아서 읽는다.

  ```java
  textField.setOnAction((t)->{
      // 입력 상자 (TextField)에서 Enter Key가 입력되면 호출
      // 서버가 보낸 한 줄을 받아서 읽는다.
      msg = textField.getText();
      out.println(msg);
      out.flush();
      try {
          String result = br.readLine();
          printMsg(result);
  
      }catch (Exception e) {
          System.out.println(e);
      }
      textField.clear();  
  });
  ```

- stopBtn 버튼을 클릭하면 서버와의 연결을 종료한다. 

  ```java
  stopBtn = new Button("Echo 서버 종료 ");
  stopBtn.setPrefSize(100, 50);
  stopBtn.setOnAction((t)->{
      try {
          out.println("EXIT");
          out.flush();
          out.close();
          br.close();
          socket.close();
      }catch (Exception e) {
          System.out.println(e);
      }
  });
  ```

- 전체 코드

  ```java
  package javaNetwork;
  
  import java.io.BufferedReader;
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
  
  
  public class Exam03_EchoClient_MultiUser extends Application{
  
      TextArea textarea;
      TextField textField;
  
      Button startBtn, stopBtn;
  
      Socket socket;
      BufferedReader br;
      PrintWriter out;
      String msg;
  
      private void printMsg(String msg) {
          Platform.runLater(()->{
              textarea.appendText("꼴통전사 : " + msg + "\n"); 
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
  
          textField = new TextField();
          textField.setPrefSize(500, 100);
          textField.setOnAction((t)->{
              // 입력 상자 (TextField)에서 Enter Key가 입력되면 호출
              // 서버가 보낸 한 줄을 받아서 읽는다.
              msg = textField.getText();
              out.println(msg);
              out.flush();
              try {
                  String result = br.readLine();
                  printMsg(result);
  
              }catch (Exception e) {
                  System.out.println(e);
              }
              System.out.println("요기");
              textField.clear();
  
          });
  
          startBtn = new Button("Echo 서버 접속!!");
          startBtn.setPrefSize(100, 100);
  
          startBtn.setOnAction(t->{
              // 버튼에서 Action이 발생(클릭)했을 때 호출!
              // 접속 버튼
              try {
                  // Client는 버튼을 누르면 서버 쪽에 Socket 접속을 시도한다.
                  socket = new Socket("127.0.0.1", 7777);
  
                  // Stream 생성
                  br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  
                  out = new PrintWriter(socket.getOutputStream());
  
                  printMsg("Echo Server에 접속을 성공했어요~");
  
  
              }catch (Exception e) {
                  e.printStackTrace();
              }
          });
  
          stopBtn = new Button("Echo 서버 종료 ");
          stopBtn.setPrefSize(100, 50);
          stopBtn.setOnAction((t)->{
              try {
                  out.println("EXIT");
                  out.flush();
                  out.close();
                  br.close();
                  socket.close();
              }catch (Exception e) {
                  System.out.println(e);
              }
          });
  
  
          // 긴 Panel 하나를 생성
          FlowPane flowpane = new FlowPane();
          flowpane.setPrefSize(700, 50);
          // FloxPane에 Button을 올려요!
          flowpane.getChildren().add(startBtn);
          flowpane.getChildren().add(textField);
          flowpane.getChildren().add(stopBtn);
          root.setBottom(flowpane);		
  
          // 화면에 띄우기
          Scene scene = new Scene(root);
          primaryStage.setScene(scene);
          primaryStage.setTitle("Thread 예제 입니다.");
          primaryStage.show();		
  
      }
  
  }
  
  ```

  