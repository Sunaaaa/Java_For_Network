package javaStream;

import java.io.ObjectInputStream.GetField;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
 	Reduction
 	- 대량의 데이터를 가공해서 축소하는 개념 
 		=> sum, average, count, max, min
 		
 	Collection을 사용할 때 Stream을 이용해서 이런 Reduction 작업을 쉽게 할 수 있어요 
 	
 	만약, Collection 안에 Reduction 하기가 쉽지 않은 형태로 데이터가 들어가 있으면
 	중간 처리과정을 거쳐서 Reduction 하기 좋은 형태로 변환한다. 
 	
 	Stream은 Pipline을 지원 (Stream을 연결해서 사용할 수 있어요)
 	
 	간단한 예제를 통해서 이해해 보아요~
 	- 직원 객체를 생성해서 ArrayList 안에 여러 명의 직원을 저장.
 	- 이 직원 중에 IT에 종사하고 남자인 직원을 추려서 해당 직원들의 연봉 평균을 출력
 	
 */
public class Exam03_StreamPipline {
	
	private static List<Exam03_Employee> employees = 
			Arrays.asList(new Exam03_Employee("홍길동", 55, "IT", "남자", 5000),
					new Exam03_Employee("홍길동", 55, "IT", "남자", 5000),
					new Exam03_Employee("김길동", 50, "Sales", "여자", 3000),
					new Exam03_Employee("강길동", 21, "IT", "남자", 1000),
					new Exam03_Employee("오길동", 29, "Sales", "남자", 3500),
					new Exam03_Employee("유관순", 118, "IT", "여자", 7000),
					new Exam03_Employee("하길동", 45, "IT", "여자", 4000),
					new Exam03_Employee("공길동", 58, "IT", "남자", 1000),
					new Exam03_Employee("임길동", 28, "Sales", "남자", 5000));

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 부서가 IT인 사람들 중 남자에 대한 연봉 평균을 구해 보아요!
		Stream<Exam03_Employee> stream = employees.stream();
		
		// Stream의 중간 처리와 최종 처리를 이용해서 원하는 작업을 해 보아요
		//double d = stream.filter(t->t.getDept().equals("IT")).filter(t->t.getGender().contentEquals("남자")).mapToInt(t->t.getSalary()).average().getAsDouble();
		//System.out.println("부서가 IT인 사람들 중 남자에 대한 연봉 평균" + d);
		
		// lazy (지연) 처리 -> 효율을 위해 
		// 맨 끝의 최종 처리가 있는지 확인 -> Reduction 
		//	=> 만약에 없는 경우 중간 처리를 하지 않는다. 
		
		// 	=> 있는 경우에만 중간 처리를 수행한다.
		
		
		// 그럼 Stream이 가지고 있는 Method는 무엇이 있나요??
		// 나이가 35세 이상인 직원 중 남자 직원의 이름을 출력하세요.
		stream.filter(t->t.getAge()>=35).filter(t->t.getGender().equals("남자")).forEach(t->System.out.println("직원의 이름은 : " + t.getName()));
		
		// 중복 제거를 위한 중간 처리 
		int temp[] = {10, 20, 30, 40, 50, 50};
		IntStream s = Arrays.stream(temp);
		s.distinct().forEach(t->System.out.println(t));
		
		// 객체에 대한 중복 제거를 해 보아요~
		// 중복 제거 해서 직원의 이름을 출력하기 
		// new Exam03_Employee("홍길동", 55, "IT", "남자", 5000)가 똑같은 객체로 중복 되어 있다.
		employees.stream().distinct().forEach(t->System.out.println(t.getName()));

		// 요거 중복 제거 안됨
		
		// equals() Method
		System.out.println("꺄꺄꺄꺄꺄꺄");
		employees.stream().distinct().forEach(t->System.out.println(t.getName()));
		
		// mapToInt() => mapXXX()
		
		
		
		// 정렬 (부서가 It인 사람을 연봉 순으로 출력)
		// 숫자와 문자와는 다르게 객체를 크다 작다의 개념이 없기 때문에 
		// 이를 인식시켜 줘야 한다.
							
		// sorted() 를 통해 정렬을 하기 위해서는 반드시 정렬이 가능한 형태여야 한다.
		
		// 만약 객체인 경우 에러 발생!!!
		// 해당 클래스를 정렬이 가능한 클래스로 지정한다. --> Comparable Interface를 구현해서 클래스를 작성해야 한다.
		
		// sorted() : 기본 오름차순
		// sorted(Comparator.reverseOrder()) : 내림차순
		employees.stream()
				 .filter(t->t.getDept().equals("IT"))
				 .distinct()
				 .sorted(Comparator.reverseOrder())
				 .forEach(t->System.out.println("이름 : " + t.getName() + " == 연봉 : " + t.getSalary()));
		
		
		// 반복
		// forEach()를 이용하면 Stream 안의 요소를 반복할 수 있어요.
		// forEach()는 최종 처리함수 --> 중간 연산에 사용할 수 없는 함수이다.
		
		// 불가 --> 반복 구문은 항상 맨 마지막?? 너무 불편하구만 ==> 반복하는 함수가 하나 더 있다!!!!!!!!!!!!
		// employees.stream().forEach(t->System.out.println(t.getName())).mapToInt();
		
		// peek () : 중간 처리로 사용할 수 있는 반복 처리 함수 
		employees.stream()
						.peek(t->System.out.println(t.getName()))
						.mapToInt(t->t.getSalary())
						.forEach(t->System.out.println(t));
		
		// Predicate를 이용해서 boolean으로 return 
		// 확인용 최종 처리 함수 
		// 50살 이상인 사람만 추출해서 이름 출력
//		employees.stream()
//					.filter(t->t.getAge()>=50)
//					.distinct()
//					.forEach(t->System.out.println(t.getName()));
		
		
		// 최종 처리 함수는 여러개가 나올 수 없다. ==> 단 1개 
		// Stream을 열어 50세 이상만 filter 하고 , 50세 이상인 모든 객체에 대해서 100세 이상인지를 확인해서 모두가 만족하면 True
//		boolean result = employees.stream()
//				.filter(t->t.getAge()>=50)
//				.allMatch(t->t.getAge()>100);

		// Stream을 열어 50세 이상만 filter 하고 , 50세 이상인 모든 객체에 대해서 100세 이상인지를 확인해서 단 하나라고 만족시키는 객체가 있으면 True
		boolean result = employees.stream()
				.filter(t->t.getAge()>=50)
				.anyMatch(t->t.getAge()>100);
		System.out.println(result);
		
		
		/*
		  	결과로 추출한 데이터를 Set, Map, List의 형태로 받을 수 있어요
		  	최종 확인 용 함수로 forEach() 함수를 많이 사용했는데
		  	forEach 말고 collect() 를 이용해 보아요!
		  	나이가 50살 이상인 사람들의 연봉을 구해서 
		  	List<Integer> 형태의 ArrayList에 저장해 보아요!
		 */
		List<Integer> resultList = employees.stream().filter(t->t.getAge()>=50)
						.map(t->t.getSalary())
						.collect(Collectors.toList());
		
		System.out.println(resultList);
		
		// 당연히 set, map으로도 저장할 수 있고요 
		Set<Integer> resultSet = employees.stream().filter(t->t.getAge()>=50)
				.map(t->t.getSalary())
				.collect(Collectors.toCollection(HashSet :: new ));

		System.out.println(resultSet);

	}

}

class Exam03_Employee implements Comparable<Exam03_Employee>{
	private String name; // 이름
	private int age; // 나이
	private String dept; // 부서
	private String gender; // 성별
	private int salary; // 연봉
	
	public Exam03_Employee() {
		// TODO Auto-generated constructor stub
	}

	public Exam03_Employee(String name, int age, String dept, String gender, int salary) {
		super();
		this.name = name;
		this.age = age;
		this.dept = dept;
		this.gender = gender;
		this.salary = salary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + salary;
		System.out.println(dept.hashCode());
		System.out.println(gender.hashCode());
		System.out.println(name.hashCode());
		System.out.println(result);
		return result;
	}
	
	// 객체 비교 
	@Override
	public boolean equals(Object obj) {
		// 오버라이딩을 하지 않으면 메모리 주소를 가지고 비교하기 때문에 중복되는 new Exam03_Employee("홍길동", 55, "IT", "남자", 5000) 의 객체 들을 서로 다른 메모리 주소를 가지기 떄문에 중복 제거가 제대로 수행되지 않는다.
		// => 오버라이딩을 해서 특정 조건을 만족하면 객체가 같아! 라는 방식으로 코드를 작성한다.
		
		boolean result = false;
		Exam03_Employee target = (Exam03_Employee) obj;
		if (this.getName().equals(target.getName())) {
			if (this.getAge()==target.getAge()) {
				return true;				
			}
			return false;
		}
		return true;
	}

	// Comparable Interface의 오버라이딩 함수 0.
	
	@Override
	public int compareTo(Exam03_Employee o) {
		
		// 정수 값을 리턴
		// 양수가 리턴되면 순서를 바꿔요 
		// 0이나 음수가 리턴되면 순서를 바꾸지 않아요 
		int result = 0;		
		
		// this 객체와 Exam03_Employee o 객체를 비교 
		// 내가 앞, o가 뒤
		// 내가 더 크면, 뒤로 값을 보낸다. => 순서를 바꾼다. => result 가 양수값
		if (this.getSalary() > o.getSalary()) {
			result = 1;
		}
		// 같으면 아예 자리 변동을 하지 않음
		else if (this.getSalary() == o.getSalary()) {
			result = 0;
		}
		else {
			result = -1;
		}
		
		return result;
	}
	
}
