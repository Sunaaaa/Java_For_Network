package javaLambda;

/*
 함수적 프로그래밍 패턴 ( : 독립적으로 함수를 만들어서 프로그래밍하는 패턴 )을 위해 Java는 8버전 부터 Lambda를 지원해요!
 람다는 익명함수를 만들기 위한 Expression 
 --> 객체 지향적 언어보다는 함수 지향적 언어에서 많이 사용된다 (대표적으로 JavaSrcipt)
 
 기존 자바 개발자들은 이 Lambda라는 개념에 익숙해지기가 쉽지 않아요~
 그럼에도 자바가 Lambda를 도입한 이유는 크게 2가지 정도로 생각할 수 있어요 
 1. 코드가 간결해져요!
 2. Java Stream  을 이용하기 위해서 Lambda를 이용
 	Java Stream
 	  : Collection (ArrayList, Map, Set, Array, ...) 을 효율적으로 처리할 수 있다. 
 	  : 병렬 처리로 빠르게 , 속도 측면에서 굉장히 빠르다.
 	  : 병렬 처리를 위한 특별한 코딩을 하지 않아도 몇개의 함수도 빠르게 병렬 처리를 할 수 있다. 

람다식의 기본 형태 
(매개변수) -> {실행코드}
: 익명함수를 정의하는 형태로 되어 있지만, 실제로는 익명클래스의 인스턴스를 생성

람다식이 어떤 객체를 생성하느냐는  해당 람다식이 대입되는 Interface 변수가 어떤 Interface 인가에 달려 있다. 

 */

class MyThread extends Thread{
	public void run() {
		System.out.println("Thread가 실행됩니다.");
	}
}

class MyRunnable implements Runnable{
	public void run() {
		System.out.println("Thread가 실행됩니다.");
	}
}

/*
1. Thread 생성
2. 일반 적인 Interface를 정의해서 람다식으로 표현해 보아요 
 	
  
 */
// 정의와 선언이 같이 되어 있는 경우 ==> 무조건 추상 클래스 
// 선언만 존재 ==> Interface
interface Exam01_LB_Interface {
	// 기본적으로 추상 (abstract) Method 올 수 있어요 !
	// 추상 Method : Method 정의가 없고, 선언만 존재하는 Method
	// -> Method가 무엇을 하는지 정해진 것이 없고, 이런 Method가 있다 정도의 정의만 내림 
	
	// abstract 생략 가능
//	abstract void myFunc();
	void myFunc(int i);

}

public class Exam01_LambdaBasic {
	public static void main(String[] args) {
		
		/*
		 Thread를 생성
		 	1. Thread의 Sub Class를 이용해서 Thread 생성
		 	2. Runnable Interface를 구현한 class를 이용해서 Thread 생성 (더 좋은 방식)
		 	3. Runnable Interface를 구현한 익명 Class 를 이용해서 Thread 생성 (안드로이드에서 일반적인 형태)
		 */
		
//		MyThread t = new MyThread();
//		t.start();

//		MyRunnable runnable = new MyRunnable();
//		Thread t = new Thread(runnable);
//		t.start();
							// 여기부터  익명 클래스의 인스턴스를 뽑아냄 (익명 객체 만들기)
		Runnable runnable = new Runnable() {
			
			/*
			  	객체를 생성하지 못하는 이유는 추상 메소드가 존재하기 때문
			  	=> 해당 추상 메소드를 Overridding하면 객체를 생성할 수 있어욧 
			 */
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		};
		// 여기까지!!
		// () : run() 의 () 를 가져옴
		// {} : run() 의 실제 코드
		// 람다식
		Runnable rRunnable = () -> {};
		
//		Thread t = new Thread(()->{
//			System.out.println("Thread 실행!!");
//		});
//		t.start();

		// 컴파일러가 Type 유추 => Thread를 만드는데 (new Thread()), run() 함수를 생성하겠구나
		new Thread(()->{
			System.out.println("Thread 실행!!");
		}).start();
		
		
		// 익명 으로 인터 페이스 객체 생성
		Exam01_LB_Interface sample = new Exam01_LB_Interface() {
			
			@Override
			public void myFunc(int i) {
				// TODO Auto-generated method stub
				System.out.println(i + "  : Interface의 인스턴스 생성!!");
			}
		};
		
		// 람다식 원형
		// 오버라이딩 하면서 Interface의 인스턴스 생성
		Exam01_LB_Interface rSample = (int i) -> {System.out.println(i + "  : Interface의 인스턴스 생성!!");};

		// 람다식 변형
		// 컴파일러가 유추에 의해 int 형의 변수인 것을 알기 때문에, Type을 명시하지 않아도 된다. 
		Exam01_LB_Interface rSample2 = (i) -> {System.out.println(i + "  : Interface의 인스턴스 생성!!");};

		// 람다식 동작 : Interface안의 실제 함수를 호출한다.
		rSample.myFunc(2);

	}
}


















