package javaNetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Exam02_EchoServer2 {
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
			
			// br로 부터 데이터를 읽어서 out을 통해 다시 전달
			String msg = br.readLine();
			out.println(msg);
			out.flush();

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
