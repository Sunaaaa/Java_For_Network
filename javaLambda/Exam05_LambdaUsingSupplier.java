package javaLambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/*
 	Consumer : 값을 소모
 	Supplier : 값을 채워 줄때 
 	Supplier라고 불리는 함수적 인터페이스는 여러 개의 Interface가 제공된다. 
 	특징 
 	1. 매개변수가 없어요 ---> ( ) 만 
 	2. return 값은 있어요  --> getXXXX() 계열의 Method 형태로 인터페이스에 선언되어 있어요!
 		==> 들어가는 인자가 없고 나오는 값이 있다.
 
 	친구 목록을 List<String> 형태로 만들어요~
 	
 */


public class Exam05_LambdaUsingSupplier {

	// 로또 번호 (1~46) 를 자동으로 생성하고 출력하는 간단한 Method를 작성
	public static void generatorLotto(IntSupplier supplier, Consumer<Integer> consumer) {
		
		Set<Integer> lotto = new HashSet<Integer>();
		while (lotto.size() != 6) {
			lotto.add(supplier.getAsInt());
		}
		
		for (Integer i : lotto) {
			consumer.accept(i);
		}
	}
	
	public static void main(String[] args) {
		final List<String> myBuddy = Arrays.asList("홍길동", "김길동", "이순신", "강감찬");
		
		// IntSupplier : 정수값을 1개 return 하는 Supplier
		// 로또 번호를 자동으로 생성하고 출력하는 간단한 Method를 작성

		generatorLotto(() -> {
			return ((int)(Math.random()*45))+1;
		}, t->{
			System.out.println(t);
		});

	}

}
