package javaLambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/*
 	Predicate 
 	- 입력 매개변수가 있어요. 리턴 값도 있어요. --> Boolean 리턴
 	- 사용되는 Method는 testXXX () 계열의 Method 가 사용되요!
 	- 입력 매개변수 값을 조사하여 True, False 값을 리턴하는 경우 
 	
 	학생 객체를 만들어서 List로 유지할 거에요!
 	static Method를 만들어서 람다식으로 인자를 넘겨줄거에요.
 	성별에 따른 특정 과목의 평균을 구할 수 있도록 Method를 작성해 보세요.
 	
 	
 */
public class Exam08_LambdaUsingPredicate {
	private static List<Exam08_Student> students = Arrays.asList(
			new Exam08_Student("홍길동", 15,29,35,"여자"),
			new Exam08_Student("김길동", 54,66,76,"남자"), 
			new Exam08_Student("홍길동", 15,25,31,"남자"),
			new Exam08_Student("이순신", 91,20,30,"남자"),
			new Exam08_Student("신사임당", 12,100,72,"여자"));
	
	// 성별에 따른 특정 과목의 평균을 구하는 작업을 수행하는 static Method를 하나 정의
	private static double avg(Predicate<Exam08_Student> predicate, ToIntFunction<Exam08_Student> function) {
		int sum = 0;
		int count = 0;
		
		for (Exam08_Student s : students) {
			if (predicate.test(s)) {
				count ++;
				sum += function.applyAsInt(s);
			}
		}
		
		return (double)sum/students.size();		
	}
	
	public static void main(String[] args) {
		
		// 남자들의 수학 성적
			//True/False 값으로 떨어진다.
		System.out.println(avg(t->t.getGender().equals("남자"), t->t.getsMath()));

		// 여자들의 영어 성적
		System.out.println(avg(t->t.getGender().equals("남자"), t->t.getsEng()));
	}
}

class Exam08_Student{
	private String sName; // 학생이름
	private int sKor;	// 국어 성적
	private int sEng;	// 영어 성적
	private int sMath;	// 수학 성적
	private String gender;	// 성별
	
	public Exam08_Student() {
		// TODO Auto-generated constructor stub
	}

	public Exam08_Student(String sName, int sKor, int sEng, int sMath, String gender) {
		super();
		this.sName = sName;
		this.sKor = sKor;
		this.sEng = sEng;
		this.sMath = sMath;
		this.gender = gender;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}
