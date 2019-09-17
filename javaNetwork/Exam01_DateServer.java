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
