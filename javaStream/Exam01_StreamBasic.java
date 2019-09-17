package javaStream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

/*
 	Stream
 		- Java 8에서 새롭게 도입이 되었어요. (주의 : java.io 안에 있는 Stream과는 다른 놈이에요.)
 		- 사용 용도 : 컬렉션 (List, Set, Map, 배열, ..) 처리를 위해서 사용이 되요!
 				==> 즉, 컬랙션 안의 데이터를 반복시키는 반복자의 역할을 하는게 stream
 				-----> 예를 들어, ArrayList 안에 학생 객체가 5개 있으면 그 5개를 하나로 가져오는 역할을 수행
 				======> 이렇게 가져온 데이터를 람다식을 이용해서 처리할 수 있다. 
 */

public class Exam01_StreamBasic {
	private static List<String> myBuddy = Arrays.asList("홍길동", "김길동", "홍길동", "이순신", "신사임당");
	private static List<Exam01_Student> students = Arrays.asList( 
			new Exam01_Student("홍길동",10,20), 
			new Exam01_Student("김길동", 87, 46),
			new Exam01_Student("이순신", 94, 12), 
			new Exam01_Student("신사임당", 70, 88));
	public static void main(String[] args) {
		
		// 사람 이름을 출력하려고 해요 
		// 방법 1. 일반 for문 (i라는 첨자 사용)을 이용해서 처리 
		// 방법 2. 첨자를 이용한 반복을 피하기 위해 Iterator를 이용
		// 방법 3. 반복자가 필요가 없어요. 내부 반복자를 이용한다. 
		// 			병렬 처리가 가능
		Stream<String> stream = myBuddy.stream();

		// 병렬 처리하는 stream
		Consumer<String> consumer = t-> {
			System.out.println(t + " : " + Thread.currentThread());
		};
		
		Stream<String> pstream = myBuddy.parallelStream();
//		stream.forEach(t->System.out.println(t));
		stream.forEach(consumer);
		pstream.forEach(consumer);
		
		Stream<Exam01_Student> sStream = students.stream();
		Stream<Exam01_Student> sStream2 = students.stream();
		
		// 객체를 정수형으로 변환
		//  홍길동이라는 객체는 숫자 (국어 성적)로 변환된다.
		double d = sStream.mapToInt(t -> t.getKor()).average().getAsDouble();
		System.out.println("국어 성적의 평균 : " + d);
		
		double dd = sStream2.filter(t->t.getName().length()==3).mapToInt(t -> t.getKor()).average().getAsDouble();
		System.out.println("국어 성적의 평균 : " + dd);
		
	}
}

class Exam01_Student{
	private String name;
	private int kor;
	private int eng;
	
	public Exam01_Student() {
		// TODO Auto-generated constructor stub
	}

	public Exam01_Student(String name, int kor, int eng) {
		super();
		this.name = name;
		this.kor = kor;
		this.eng = eng;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}
	
}
