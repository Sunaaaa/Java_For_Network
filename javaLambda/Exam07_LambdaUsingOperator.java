package javaLambda;

import java.util.function.IntBinaryOperator;

/*
	Operator는 Function과 하는 일이 거의 비슷해요!
	입력 매개 변수가 있고, 리턴 값이 있어요. 
	
	Function은 매핑 용도로 많이 사용 되요 (입력 매개변수를 리턴 타입으로 변환, 매핑의 용도 ) - 학생 객체를 인자로 주면 학생의 이름을 출력
	Operator는 연산 용도로 많이 사용 되요 (입력 매개변수를 연산에 이용하여 같은 타입의 리턴 값을 돌려주는 형태로 사용) - int type의 매개변수를 받아서 int type의 값을 리턴한다.
 */

public class Exam07_LambdaUsingOperator {

	private static int arr[] = {100, 92, 50, 89, 34, 27, 99, 3};
	

	// getMaxMin() : 배열 안의 최대값, 최소값을 출력하는  static Method 
	// 사용하는 Operator 는 IntBinaryOperator : 입력 값 정수 2개 -> 출력 값 정수 1개
	private static void getMaxMin1(IntBinaryOperator binaryOperator) {
		int max=arr[0], min=arr[0];
		
		for (int i = 0; i < arr.length; i=i+1) {
			int n = binaryOperator.applyAsInt(i, i+1);
			if (n>max) {
				max = n;				
			}
			if (n<min) {
				max = n;				
			}
		}
		
		System.out.println("최대값은 : " + max + " === 최소값은 : " + min);		
				
	}
	
	private static int getMaxMin(IntBinaryOperator binaryOperator) {
		int max=arr[0], min=arr[0];
		
		for (int i : arr) {
			max = binaryOperator.applyAsInt(max, i);
		}
		
		return max;

	
	}
	private static int getMaxMin2(int arr[], IntBinaryOperator binaryOperator) {
		int max=arr[0], min=arr[0];
		
		for (int i : arr) {
			max = binaryOperator.applyAsInt(max, i);
		}
		
		return max;

	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 람다를 사용하지 않는 경우 - 인자 값으로 Method 내에서 분기하여 처리
//		getMaxMin("MIN");
//		getMaxMin("MAX");
		
		// 이렇게 하지 말아요! Operator를 이용해서 처리해 보아요!
		// getMaxMin(람다식)
		// 최대값
		System.out.println("최대값 : " + getMaxMin2(arr,(a,b)->{
			return a>=b?a:b;			
		}));
		

		System.out.println("최대값 : " + getMaxMin((a,b)->{
			return a>=b?a:b;			
		}));
		
		// 최소값
		System.out.println("최소값 : " + getMaxMin((a,b)->{
			return a<=b?a:b;			
		}));

	}

}
