package javaStream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class testt {

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
    	
		boolean result = employees.stream()
				.filter(t->t.getAge()>=50)
				.allMatch(t->t.getAge()>100);
		System.out.println(result);
		
    }
}