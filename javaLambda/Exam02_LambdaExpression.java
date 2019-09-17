package javaLambda;

/*
 	람다식을 표현하는 방식
 	: (인자1, 인자2, 인자3, ...) -> {실행 코드}
 	
 	1. 매개변수의 이름을 개발자가 지정할 수 있어요~
 		- Interface 내의 함수 원형의 매개변수의 이름이 number 이더라도 
 		   객체 생성 시에 number가 아닌 다른 이름을 부여 해도 문제가 없다. 
	2. 매개변수의 타입은 컴파일러의 타입 유추에 의해서 알 수 있기 때문에 
	    람다식에서는 일반적으로 매개변수의 타입을 지정하지 않는다.
	3. 만약, 매개변수가 1개인 경우, () 도 생략할 수 있어요!
	4. 만약, 실행문이 달랑 1개인 경우, {} 도 생략할 수 있어요!
		- 실행 코드가 여러 줄 인 경우, 반드시 {} 필요해요~
	5. 매개변수가 없으면 () 는 생략할 수 없어요!!
	6. 실행문에 당연히 return 구문이 들어 올 수 있어요!
	7. 만약, 실행문에 return 구문이 달랑 하나인 경우, {} 와 키워드 return도 생략할 수 있다. 
	
	람다식은  Interface Type의 instance(객체)를 생성하는 Expression
	: Interface Type의 변수 Assign 되는 하나의 식, 
	: 해당 Interface Type의 객체를 뽑아내는 것 
	==> 람다식은 결국 익명 객체를 생성하는 코드 
	==> 람다식이 생성하는 객체는 결국 어떤 Interface Type의 변수에 assign이 되는지에 달려있다.
	--> 람다의 Target Type : 람다식으로 만들어지는 Interface의 Type
	
	Target Type : 아무 Interface나 사용될 수 없어요!
	
	람다의 Target Type이 되기 위해서는 해당 Interface는 반드시 1개의 추상 Method만 존재해야해요!

	그래서 Interface를 사용할 때 Annotation을 이용해서 컴파일을 이용해서 Type Check를 할 수 있어요~
	
	@FunctionalInterface 를 이용해서 해당 Interface가  람다의 Target Type이 될 수 있는지 컴파일러에 의한 Check를 할 수 있어요~
	함수적 인터페이스 : 람다의 Target Type이 될 수 있는 인터페이스 
	
	====
	이벤트, 스레드 생성 + Stream 만들 때 많이 사용 
	----
	Thread를 생성할 때 Runnable Interface를 사용하는데 
	이 Runnable Interface는 public void run() 이라는 추상 Method 1개만 달랑 가지고 있어요~
	따라서, 이 Interface는 람다의 Target Type이 될 수 있고 
	Runnable Interface는 함수적 인터페이스라고 할 수 있어요!!
	
	----
	이벤트를 처리하는 Interface는 대체로 함수적 인터페이스 (몇몇 개 해당)
	
	
 */

// 컴파일러가 람다의 Target Type이 될 수 있는지 check 해주고, 반드시 추상 메소드가 1개 
@FunctionalInterface
interface Exam02_LE_Interface{
//	void myfunc(int number);
//	void myfunc();
	int myfunc(int a, int b);
}

public class Exam02_LambdaExpression {
	public static void main(String[] args) {
		
		// 1.  +  2. 
//		Exam02_LE_Interface obj = (my)-> {
//			System.out.println(my + "코드가 실행이 되요~");
//		};

		// 3. 
//		Exam02_LE_Interface obj3 = my-> {
//			System.out.println(my + "코드가 실행이 되요~");
//		};

		// 4. 
//		Exam02_LE_Interface obj4 = my-> System.out.println(my + "코드가 실행이 되요~");

		// 5. 매개변수가 없는 경우, 인자가 없다는 의미로 ()를 반드시 추가한다.
//		Exam02_LE_Interface obj4 = ()-> System.out.println(my + "코드가 실행이 되요~");


		// 6. 
//		Exam02_LE_Interface obj6 = (a,b)-> {return a+b;};
		Exam02_LE_Interface obj6 = (a,b)-> a+b;
		System.out.println(obj6.myfunc(10, 20));

	}
}
