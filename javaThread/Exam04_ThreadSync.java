package javaThread;

/*
	2개의 Thread를 파생시켜 공용 객체를 이용하도록 한다. 
	Thread가 공용 객체를 동기화 해서 사용하는 경우와 그렇지 않은 경우를 비교 하자.
	
	공용 객체를 위한 Class를 정의해 보아요!
	
 */

// 해당 class에서 만들어지는 객체의 Field를 Thread가 공유해서 사용한다. 
class ShardObject{
	// 공용 객체가 가지는 Field
	private int number;

	// getter & setter (Thread에 의해서 사용되요)
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
		
	// Thread에 의해서 사용되는 Business Method
	public void assignNumber(int number) {
		// synchronized를 쓰면 Monitor 객채를 먼저 들어온 Thread에게 넘겨줘
		// Monitor 객체를 획득한 Thread만 해당 synchronized Block을 수행할 수 있다. 
		// 없으면 Lock
		synchronized (this) {
			this.number = number;
			try {
				Thread.sleep(3000);
				System.out.println(" 현재 공용 객체의 number" + number);
				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
}

// Thread의 생성자의 인자로 이용될 
// Runnable Interface를 구현한 Class
class MyRunnable implements Runnable{
	
	ShardObject shardObject;
	int input;
	
	// Thread가 하나의 객체를 공용으로 사용하겠다는 의미
	public MyRunnable(ShardObject shardObject, int input) {
		this.shardObject = shardObject;
		this.input = input;
	}

	@Override
	public void run() {
		shardObject.assignNumber(input);
	}
	
}

public class Exam04_ThreadSync {
	
	public static void main(String[] args) {
		// 공용 객체를 생성
		ShardObject shardObject = new ShardObject();
		
		// 내부적으로 공용 객채를 갖는 Thread 2개 생성
		// 생성자 Injection 
		Thread t1 = new Thread(new MyRunnable(shardObject, 100));
		Thread t2 = new Thread(new MyRunnable(shardObject, 500));
		
		// Thread를 Runnable 상태 (실행 가능한 상태) 로 전환 
		t1.start();
		t2.start();
		
		
	}
}
