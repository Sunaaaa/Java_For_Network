package javaLambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/*
 	Function 함수적 인터페이스 : 인자도 들어가서, 리턴 값도 있다.
 	
 	Function 함수적 인터페이스는 입력 매개변수와 리턴 값이 있는 applyXXX() 계열의 Method가 제공된다. 
 	- 입력과 출력의 Type이 다를 떄 주로 사용한다. 
 	- 일반적으로 입력 매개변수를 리턴값으로 Mapping시킬 때 일반적으로 사용된다. 
 		- 예>  객체가 들어갔는데 객체 내의 특정 Field의 값 ( String ) 이 나옴
 	
 	Function<T,R> func = t->{return ~~~ };
 	- T : 입력 매개변수 generic
 	- R : 리턴 값의 generic
 	
 */

// StudentVO class를 정의해요
class Exam06_Student{
	private String sName;
	private int sKor;
	private int sEng;
	private int sMath;
	
	public Exam06_Student() {
		// TODO Auto-generated constructor stub
	}

	public Exam06_Student(String sName, int sKor, int sEng, int sMath) {
		super();
		this.sName = sName;
		this.sKor = sKor;
		this.sEng = sEng;
		this.sMath = sMath;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public int getsKor() {
		return sKor;
	}

	public void setsKor(int sKor) {
		this.sKor = sKor;
	}

	public int getsEng() {
		return sEng;
	}

	public void setsEng(int sEng) {
		this.sEng = sEng;
	}

	public int getsMath() {
		return sMath;
	}

	public void setsMath(int sMath) {
		this.sMath = sMath;
	}

}


public class Exam06_LambdaUsingFunction {
	
	private static List<Exam06_Student> students = Arrays.asList(new Exam06_Student("홍길동", 10,20,30),new Exam06_Student("김길동", 50,60,70), new Exam06_Student("홍길동", 10,20,30),new Exam06_Student("이순신", 90,20,30),new Exam06_Student("신사임당", 10,100,70));

	private static void printName(Function<Exam06_Student, String> function) {
		for (Exam06_Student a : students) {
			System.out.println(function.apply(a));
		}
	}
	
	private static void getAvg(Function<Exam06_Student, Integer> function) {
		float avg=0;

		for (Exam06_Student a : students) {
			avg += function.apply(a);
		}
		
		System.out.printf("%.2f \n", avg/students.size());
		
	}

	private static double getAvg3(Function<Exam06_Student, Integer> function) {
		int sum =0;

		for (Exam06_Student a : students) {
			sum += function.apply(a);
		}
		
		return (double)sum/students.size();
		
	}

	private static double getAvg_Int(ToIntFunction<Exam06_Student> function) {
		int sum =0;

		for (Exam06_Student a : students) {
			sum += function.applyAsInt(a);
		}
		
		return (double)sum/students.size();
		
	}
	private static double getAvg2(String subject) {
		int sum =0;
		if(subject.equals("KOR")) {
			for (Exam06_Student s : students) {
				sum += s.getsKor();
			}
		}else if(subject.equals("ENG")) {
			for (Exam06_Student s : students) {
				sum += s.getsEng();
			}
			
		}else if(subject.equals("MATH")) {
			for (Exam06_Student s : students) {
				sum += s.getsMath();
			}
			
		}

		return (double)sum/students.size();
		
	}

	public static void main(String[] args) {
		
		// 학생에 대한 객체를 인자로 받아 학생의 이름만 출력
		// printName([람다식을 넣을 거에요 - Function Type]);
		System.out.println("--- 학생 이름 ---");
		printName(t->{return t.getsName();});
		
		
		System.out.println(getAvg2("KOR"));
		
		
		// getAvg() 라는 Static Method를 만들어서 다음의 내용을 출력하세요
		// 학생들의 국어 성적 평균 => getAvg()
		System.out.println("--- 국어 성적 ---");
		getAvg(t->{return t.getsKor();});

		// 학생들의 영어 성적 평균 => getAvg()
		System.out.println("--- 영어 성적 ---");
		getAvg(t->{return t.getsEng();});
		
		// 학생들의 수학 성적 평균 => getAvg()
		System.out.println("--- 수학 성적 ---");
		getAvg(t->{return t.getsMath();});
		

		
		
		System.out.println("--- 국어 성적 ---");
		System.out.println(getAvg3(t->t.getsKor()));

		// 학생들의 영어 성적 평균 => getAvg()
		System.out.println("--- 영어 성적 ---");
		System.out.println(getAvg3(t->t.getsEng()));
		
		// 학생들의 수학 성적 평균 => getAvg()
		System.out.println("--- 수학 성적 ---");
		System.out.println(getAvg3(t->t.getsMath()));
}

}
