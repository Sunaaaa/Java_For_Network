package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Echo program을 작성해 보아요!
 * 클라이언트 프로그램으로부터 문자열을 네트워크를 통해 전달받아서
 * 다시 클라이언트에게 전달하는 echo 프로그램.
 * 현재는 단 1번만 echo가 동작하는데 클라이언트가 접속을 종료할때까지
 * echo작업이 지속적으로 동작하도록 프로그램을 수정!
 * 서버는 클라이언트가 종료되면 같이 종료되도록 수정.
 * 
 * 지금 프로그램은 서버가 클라이언트 1명만 서비스할 수 있어요!
 * 다수의 클라이언트를 서비스 하려면 어떻게 해야 하나요?
 * ==> Thread를 이용해서 이 문제를 해결.
 * 어떻게 Thread를 적용시켜야 하나 ??
 * - Server Socket이 Thread를 만들고, Thread 가 Socket 객체를 갖는다. 해당 Socket으로 Client와 소통한다. 
 * 
 */

public class Exam02_EchoServer_withGang {

   private static ExecutorService executorService;
   // Thread Pool : Thread Pool
   private static ExecutorCompletionService<Integer> executorCompletionService;
   private static ServerSocket server;
   public static void main(String[] args) {
      server = null;
      Socket socket = null;
      int counter = 0;
      executorService = Executors.newCachedThreadPool(); // 이거보다(이걸가지고)
      executorCompletionService = new ExecutorCompletionService<Integer>(executorService); // 좀더 성능이 향상된 쓰레드 풀 만들기

      try {
         // Port 번호 : 5577
         server = new ServerSocket(5577);
         System.out.println("서버 프로그램 기동 - 5577");

         while(true) {
            // br로부터 데이터를 읽어서 out을 통해 다시 전달
            socket = server.accept();
            System.out.println(socket.toString());

            SocketRunnable runnable = new SocketRunnable(5577, socket);
            executorService.execute(runnable);
         }
      } catch (Exception e) {
         System.out.println(e);
      }
   }
}

class SocketRunnable implements Runnable{

   private int serverPort = 5577;
   Thread runningThread = null;
   Socket socket = null;
   BufferedReader br;
   PrintWriter out;

   public SocketRunnable() {
      // TODO Auto-generated constructor stub
   }

   public SocketRunnable(int serverPort, Socket socket) {
      this.serverPort = serverPort;      
      this.socket = socket;
   }



   @Override
   public void run() {
      // TODO Auto-generated method stub
      try {
         br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
         out = new PrintWriter(this.socket.getOutputStream());      
         System.out.println(this.socket.toString());

         String msg = br.readLine();
         if(msg.equals("EXIT") ) {
            // 사용된 리소스를 해제
            out.close();
            br.close();
            socket.close();
//            this.server.close();
//            executorService.shutdown();
            System.out.println("서버 프로그램 종료!!");
         }
         out.println(msg);   // 데이터를 보내주기 ?! 여기가 쏘는건가 -> 클라이언트한테  데이터 보내기 입니다 밥통전사님!!
         out.flush();      // 명시적으로 내부  buffer를 비우고 데이터를 전달 명령

      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }



   }

}