package javaLambda;

import javaLambda.OuterClass.InnerClass;

/*
 	=람다식을 정의해서 사용할 때 주의해야 할 점=
 	
 	// 변수 사용 시에 주의해야 할 점
 	1. 클래스의 멤버 (필드 + 메소드)와 지역변수 (Local Variable)의 사용에 약간의 제약이 있어요..
 	   
 	   - 특히, this Keyword를 사용할 때 주의해야 해요!
 	   - this : 현재 사용되는 객체의 reference
 	   - 람다식은 익명 객체를 만들어 내는 코드에요. 그래서 람다식의 실행 코드 내에서 this Keyword를 쓰면 익명 객체를 지칭하지 않아요!
 	   --> 자신의 상위 객체를 지칭 
 	   --> 본인 람다 객체를 지칠하기 위해서는 실제 구현을 통해 myFunc() 내부에서 this Keyword를 사용해야 한다. 
 	2. 람다식 안에서는 지역변수를 ReadOnly 형태로 사용되어야 해요 
 */

@FunctionalInterface
interface Exam03_LUV_Interface{
	public void myFunc();
}

class OuterClass{
	
	public int outerField = 100;
	public OuterClass() {
		// Default 생성자 
		System.out.println(this.getClass().getName());
	}
	
	
	class InnerClass {

		// Field
		int innerField = 200;
		
		// Method 내의 Field ==> 해당 객체는 사라지지 않지만, 그 안에 지역 변수는 함수 호출이 끝나면 사라지기 때문에 해당 객체에서 사용할 수 없다.
		Exam03_LUV_Interface localLambda;
		
		// Field
		Exam03_LUV_Interface fieldLambda = () -> {

			System.out.println("outerField : " + outerField);
			System.out.println("OuterClass의 객체를 찾아요" + OuterClass.this.outerField);			
			System.out.println("innerField : " + innerField);
			System.out.println("this.innerField : " + this.innerField);
			System.out.println(this.getClass().getName());
			
		};
		
		Exam03_LUV_Interface a = new Exam03_LUV_Interface() {
			
			@Override
			public void myFunc() {
				// TODO Auto-generated method stub
				System.out.println(this.getClass().getName());
			}
		};

		// Constructor
		public InnerClass() {
			System.out.println(this.getClass().getName());
		}
		
		// Method
		public void innerMethod() {
			// Method 내에서 선언된 지역 변수 (Local Variable) - 메모리 영역 Stack에 저장이 되고, Method가 호출되면 생기고 Method가 끝나면 없어져요
			int localVal = 100;
			// Local Variable
			localLambda = ()->{

				System.out.println(localVal);

				// Local Variable의 값을 변경 불가 -- ReadOnly
				// localVal = 50;  내부적으로 Local Variable을 final로 지정 
			};
			localLambda.myFunc();
		}
		
	}
	
}

// 프로그램의 시작을 위한 Dummy Class로 사용
public class Exam03_LambdaUsingVariable {
	
	public static void main(String[] args) {

		// 외부 클래스의 객체 생성
		OuterClass outer = new OuterClass();
		
		// 내부 클래스의 객체 생성 
		OuterClass.InnerClass inner = outer.new InnerClass();
		inner.fieldLambda.myFunc();
		inner.innerMethod();
		inner.a.myFunc();
		
	
	}
}
