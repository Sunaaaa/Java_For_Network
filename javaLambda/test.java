//package javaLambda;
//
//import javaLambda.OuterClass.InnerClass;
//
//@FunctionalInterface
//interface Exam03_LUV_Interface{
//	public void myFunc();
//}
//
//class OuterClass{
//	
//	public int outerField = 100;
//	public OuterClass() {
//		System.out.println("Outer Class : " + this.getClass().getName());
//	}
//	
//	
//	class InnerClass {
//
//		// Field
//		int innerField = 200;
//		Exam03_LUV_Interface localLambda;
//		
//		Exam03_LUV_Interface fieldLambda = () -> {
//
//			System.out.println("InnerClass 내부 fieldLambda : " + this.getClass().getName());
//			
//		};
//		
////		Exam03_LUV_Interface a = new Exam03_LUV_Interface() {
////			
////			@Override
////			public void myFunc() {
////				// TODO Auto-generated method stub
////				System.out.println("Inner Class 내부 a : " + this.getClass().getName());
////			}
////		};
//
//		// Constructor
//		public InnerClass() {
//			System.out.println("InnerClass : " + this.getClass().getName());
//		}		
//	}	
//}
//
//public class test {
//	
//	public static void main(String[] args) {
//
//		// 외부 클래스의 객체 생성
//		OuterClass outer = new OuterClass();
//		
//		// 내부 클래스의 객체 생성 
//		OuterClass.InnerClass inner = outer.new InnerClass();
//		inner.fieldLambda.myFunc();
////		inner.a.myFunc();	
//	}
//}
