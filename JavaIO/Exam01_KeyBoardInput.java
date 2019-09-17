package JavaIO;
/*
 	기본 Stream 사용하는 방식
 	
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Exam01_KeyBoardInput {

	public static void main(String[] args) {
		
		// System.out : 표준 출력에 대한 Stream 객체 
		// System.out.println(""); : 표준 출력이 모니터에 찍힘
		System.out.println("키보드로 부터 한줄을 입력해요~");
		
		/*
		 표준 입력 (keyboard)으로 부터 입력을 받기 위해선 keyboard와 연결된 Stream 객체가 필요해요
		 Java는 표준 입력에 대한 Stream을 객체 형태로 기본적으로 제공
		 System.in (InputStream) : 기본적으로 byteStream 인데 문자열을 읽어들이는 Stream으로 만들기 
		
		 그러나, Stream은 이렇게 InputStream과 OutputStream으로 구분
		 byteStream (Integet, Boolean, Character등의 기본 데이터 형 (문자열 형태가 아닌 데이터) 입출력)과 reader, writer 계열 (문자열에 특화됨) stream으로 구분

		 
		 System.in (InputStream) : 기본적으로 byteStream 인데 문자열을 읽어들이는 Stream으로 만들기
		 
		 */
		// 우리에게 주어진 InputStream을 이용해서 조금 더 나은 Stream을 만든다.
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		
		// BufferedReader ; 효율도 좋고, 문자열 처리에 좋은 Reader
		// System.in으로 BufferedReader를 바로 만들지 못하기 때문에 InputStreamReader를 이용하여 만든다.
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		//BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(System.in));
		
		try {
		
			// IO의 대부분의 코드는 Exception 발생 여지가 있기 때문에 예외처리 코드를 작성한다.
			// readLine() : 데이터가 들어올 때까지 대기하는 블록킹 메소드 
			// -> 엔터는 치기 직전 에는 bufferedReader.readLine() 여기에서 대기하다가 엔터를 빵치는 순간 bufferedReader.readLine() 완료 
			
			String input = bufferedReader.readLine();
			System.out.println(input);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
