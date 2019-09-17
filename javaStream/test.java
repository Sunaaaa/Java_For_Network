//package javaStream;
//	
//import java.util.Arrays;
//import java.util.List;
//
//public class test {
//	
//	private static List<Exam03_Employee> employees = 
//			Arrays.asList(new Exam03_Employee("홍길동", 55, "IT", "남자", 5000),
//					new Exam03_Employee("홍길동", 55, "IT", "남자", 5000),
//					new Exam03_Employee("김길동", 50, "Sales", "여자", 3000),
//					new Exam03_Employee("강길동", 21, "IT", "남자", 1000),
//					new Exam03_Employee("오길동", 29, "Sales", "남자", 3500),
//					new Exam03_Employee("유관순", 118, "IT", "여자", 7000),
//					new Exam03_Employee("하길동", 45, "IT", "여자", 4000),
//					new Exam03_Employee("공길동", 58, "IT", "남자", 1000),
//					new Exam03_Employee("임길동", 28, "Sales", "남자", 5000));
//
//	public static void main(String[] args) {
//		// mapToInt() => mapXXX()
//		// 정렬 (부서가 It인 사람을 연봉 순으로 출력)
//		// 숫자와 문자와는 다르게 객체를 크다 작다의 개념이 없기 때문에 
//		// 이를 인식시켜 줘야 한다.
//							
//		// sorted() 를 통해 정렬을 하기 위해서는 반드시 정렬이 가능한 형태여야 한다.
//		
//		// 만약 객체인 경우 에러 발생!!!
//		// 해당 클래스를 정렬이 가능한 클래스로 지정한다. --> Comparable Interface를 구현해서 클래스를 작성해야 한다.
//		
//		// sorted() : 기본 오름차순
//		// sorted(Comparator.reverseOrder()) : 내림차순
//		employees.stream()
//				 .filter(t->t.getDept().equals("IT"))
//				 .mapToInt(t->t.getSalary())
//				 .sorted()
//				 .forEach(t->System.out.println(t));	
//	}
//
//}
//
//class Exam03_Employee{
//	private String name; // 이름
//	private int age; // 나이
//	private String dept; // 부서
//	private String gender; // 성별
//	private int salary; // 연봉
//	
//	public Exam03_Employee() {
//		// TODO Auto-generated constructor stub
//	}
//
//	public Exam03_Employee(String name, int age, String dept, String gender, int salary) {
//		super();
//		this.name = name;
//		this.age = age;
//		this.dept = dept;
//		this.gender = gender;
//		this.salary = salary;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public int getAge() {
//		return age;
//	}
//
//	public void setAge(int age) {
//		this.age = age;
//	}
//
//	public String getDept() {
//		return dept;
//	}
//
//	public void setDept(String dept) {
//		this.dept = dept;
//	}
//
//	public String getGender() {
//		return gender;
//	}
//
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//
//	public int getSalary() {
//		return salary;
//	}
//
//	public void setSalary(int salary) {
//		this.salary = salary;
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + age;
//		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
//		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
//		result = prime * result + ((name == null) ? 0 : name.hashCode());
//		result = prime * result + salary;
//		return result;
//	}
//	
//	// 객체 비교 
//	@Override
//	public boolean equals(Object obj) {
//		// 오버라이딩을 하지 않으면 메모리 주소를 가지고 비교하기 때문에 중복되는 new Exam03_Employee("홍길동", 55, "IT", "남자", 5000) 의 객체 들을 서로 다른 메모리 주소를 가지기 떄문에 중복 제거가 제대로 수행되지 않는다.
//		// => 오버라이딩을 해서 특정 조건을 만족하면 객체가 같아! 라는 방식으로 코드를 작성한다.
//		
//		boolean result = false;
//		Exam03_Employee target = (Exam03_Employee) obj;
//		if (this.getName().equals(target.getName())) {
//			if (this.getAge()==target.getAge()) {
//				return true;				
//			}
//			return false;
//		}
//		return true;
//	}
//
//	// Comparable Interface의 오버라이딩 함수 0.
//
//	
//}
