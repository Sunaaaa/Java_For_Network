package javaStream;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
	java.util.stream package 안에 우리가 사용할 수 있는 stream이 존재해요!
	BaseStream 으로부터 상속받아서 몇몇 개의 Stream이 존재
	Stream --> 해당 Stream 안에 객체가 들어가 있는 경우 사용
	IntStream --> 해당 Stream 안에 Int 값이 들어가 있는 경우 사용
	LongStream
	DoubleStream
	
	여러가지 형태의 다양한 Source에서 Stream을 얻어낼 수 있어요!
	
 */
public class Exam02_StreamSource {

	private static List<String> names = 
			Arrays.asList("홍길동", "김길동", "최길동");
	
	private static int myArr[] = {10, 20, 30, 40, 50};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// List로부터 Stream을 생성할 수 있어요~
		Stream<String> stream = names.stream();
		// Arrays Class의 Static Method 사용한다.
		IntStream istream = Arrays.stream(myArr);
		System.out.println(istream.sum());

		// 인자로 주어지는 정수형 숫자를 이용해서 영역을 짓고, Stream을 생성할 수 있다. 
		IntStream istream2 = IntStream.rangeClosed(1, 10);
		
		// File로 부터 Stream을 생성할 수 있어요. 
		Path path = Paths.get("asset/readme.txt");
		// File 객체 (java.io) 와 유사한 java.nio에 포함된 Class
		// Path
		// File 처리는 Exception이 발생할 여지가 있기 때문에 반드시, try-catch로 기본 Exception 처리를 수행한다.
		try {
			Stream<String> sstream = Files.lines(path, Charset.forName("UTF-8"));
			sstream.forEach(t->System.out.println(t));
		}catch (Exception e) {
			System.out.println(e);
		}
		

	}

}
