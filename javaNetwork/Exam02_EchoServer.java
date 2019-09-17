package javaNetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
		Echo 프로그램
 */
public class Exam02_EchoServer {


	public static void main(String[] args) {
		ServerSocket server = null;
		Socket socket = null;
		
		try {
			server = new ServerSocket(5959);
			System.out.println("Client 접속 대기");
			socket = server.accept();			

			InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
			BufferedReader br = new BufferedReader(inputStreamReader);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			String msg = "";
			String myMsg = "";
		
			while((msg=br.readLine())!=null) {
				myMsg += br.toString();			
				out.print(myMsg);
				out.flush();
			}
			System.out.println("서버에서 받은 문자 : " + myMsg);
		

			out.print(myMsg);
			
			out.flush();
			out.close();
			socket.close();
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
