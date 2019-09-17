package javaLambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.ObjIntConsumer;

/*
	람다식 : 추상 Method가 1개인 Interface의 객체를 생성하는 표현식 
	-> 이때 사용하는 Interface를 우리가 직접 만들어서 사용하나요??
	====> 아니요!!!!
	
	====
	람다식이 대입되는 Target Type은 일반적으로 Java가 제공하는 API를 이용 
	대표적으로, Runnable , Event 처리 Interface를 람다의 Target Type으로 사용
	
	SO 
	Java 에서는 람다으 Target Type으로 사용될 수 있는 Interface를 여러 개 만들어서 우리에게 Package 형태로 제공 
	(java.util.function package)
	
	제공되는 Interface는 총 5가지 종류로 분류할 수 있어요 (분류가 5종류)
	1. Consumer
	2. Supplier
	3. Function
	4. Operator
	5. Predicate
	
	Consumer : 함수적 인터페이스 (람다식이 대입될 수 있는 Target Type으로 사용할 수 있는 Interface를 지칭)
	- Java가 우리에게 제공하는 Interface 이고 , 추상 Method를 단 1개만 가지고 있어요, accept()라는  Method를 제공
	- 값을 소비만 하는 역할을 담당 -> accept()라는 함수의 리턴 타입은 void
	
	
 */



public class Exam04_LambdaUsingConsumer {
	
	public static List<String> names = Arrays.asList("홍길동", "김길동", "최길동", "박길동");

	// Method를 하나 정의하는데, Instance를 만들지 않고 바로 가져다 쓰기 위해 Static으로 정의합니다. (편하게 사용하려구)
	// 일반적인 Method 호출은 사용하는 data가 인자로 전달되는 형태 
	// 람다식을 이용하면 Method를 호출할 때 data가 아니라 실행 코드를 넘겨줄 수 있어요. (눈에 보이는 형태는 코드가 넘어가는 것처럼 보이지만, 실제 넘어가는 것은 자바 객체 (Reference))
	// 일반적으로 프로그래밍 언어에서 이렇게 함수를 다른 함수의 인자로 사용할 수 있는데, 이런 함수를 통 틀어서 일급함수 (First-Classes Function) 이라고 해요.
	// Java Script 가 대표적
	
	// Java 언어 에서도 람다를 도입해서 마치 1급 함수 (First-Classes Function) 를 사용하는 것처럼 쓸 수 있어요. 
	public static void printName(Consumer<String> consumer) {
		for (String name : names) {
//			System.out.println(name);
			consumer.accept(name);
		}
	}
	
	public static void main(String[] args) {
		
		printName(t->{System.out.println(t);});
		
		Consumer<String> consumer = t -> {
			System.out.println(t);
		};
		consumer.accept("소리 없는 아우성");
		
		BiConsumer<String, String> biConsumer = (a,b) -> {
			System.out.println(a+b);
		};
		biConsumer.accept("소리 없는", " 아우성");
		
		IntConsumer intConsumer = i -> {
			System.out.println(i);
		};
		intConsumer.accept(100);
		
		// 객체와 정수
		ObjIntConsumer<String> objIntConsumer = (a,b)-> {
			System.out.println(a+b);
		};
		objIntConsumer.accept("안뇽", 100);
	
	}
}
