package javaNetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
	서버
	Echo Program을 작성해 보아요~
	클라이언트 프로그램으로부터 문자열을 네트워크를 통해 전달받아서 
	다시 클라이언트 에게 전달하는 Echo 서버 프로그램
	현재는 단 1번만 ECHO가 동작하는데 클라이언트가 접속을 종료할 때까지
	Echo 작업이 지속적으로 동작하도록 프로그램을 수정!
	서버는 클라이언트가 종료되면 같이 종료되도록 수정!
*/

public class Exam02_EchoServer3 {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try {
			serverSocket = new ServerSocket(5252);
			System.out.println("오이오이 가동!!");
			socket = new Socket();
			
			socket = serverSocket.accept();		
			
			// Stream 생성
			// 입력
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("오이오이 가동2!!");
			
			// 출력
			
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			String msg ="";

			while(true) {
				// br로 부터 데이터를 읽어서 out을 통해 다시 전달
				msg = br.readLine();
				if (msg.equals("EXIT")) {
					System.out.println("서버 종료");
					break;					
				}
				out.println(msg);
				out.flush();	
			}
			
			// 사용된 자원 정리 / 해제
			out.close();

			br.close();
			socket.close();
			serverSocket.close();
			System.out.println("서버 프로그램 종료!!");		
			
			
		}catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
