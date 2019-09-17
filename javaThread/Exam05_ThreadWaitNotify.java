package javaThread;

// 공용 객체를 생성하기 위한 Class define
class MyShared{
	public synchronized void printNum() {
		for(int i =0; i<10; i++) {
			try {
				// 1초 잤다가 깨서 Thread 이름 찍기 
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName() + " :: " + i);				

				// Monitor 객체를 획득하기 위해서는 synchronized 키워드가 있어야 한다. 
				
				// notify () : 현재 wait 상태에 있는 Thread를 깨워 Runnable상태로 만든다.
				notify(); 
				
				// wait () : 자신이 가지고 있는 Monitor객체를 놓고 스스로 wait 상태로 빠진다.
				wait();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
	}
}

class Runnable05 implements Runnable{
	
	MyShared myShared;
	
	public Runnable05() {
	}
		
	public Runnable05(MyShared myShared) {
		super();
		this.myShared = myShared;
	}
	

	@Override
	public void run() {
		myShared.printNum();
	}
}

public class Exam05_ThreadWaitNotify {

	public static void main(String[] args) {
		
		MyShared myShared = new MyShared();
		
		Thread t1 = new Thread(new Runnable05(myShared));
		Thread t2 = new Thread(new Runnable05(myShared));

		t1.start();
		t2.start();
	}

}
