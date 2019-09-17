package JavaIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

class MyClass implements Serializable{
	/**
	 * 
	 */
	// 직렬화와 역직렬화를 할 때 같은 타입 인자를 비교하기 위해서 내부적으로 사용하는 static field
	private static final long serialVersionUID = 1L;
	
	
	// Class의 field도 직렬화가 가능한 Class 여야 한다. 
	String name;	// 직렬화 가능
	int Kor;		// 직렬화 가능
	
	// 만약 직렬화가 안되는 Field가 하나라도 있으면 직렬화가 불가능하다. 
	// Socket socket; 	// 직렬화 불가능
	transient Socket socket; 	// 직렬화 제외
	
	
	public MyClass() {
		// TODO Auto-generated constructor stub
	}

	public MyClass(String name, int kor) {
		super();
		this.name = name;
		Kor = kor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKor() {
		return Kor;
	}

	public void setKor(int kor) {
		Kor = kor;
	}
	
	
}

public class Exam04_Serialization {

	public static void main(String[] args) {
		// ObjectOutStream을 이용해서 File에 내가 만든 class의 instance를 저장
		
		// 1. 저장할 객체를 생성
		MyClass obj = new MyClass("홍길동", 70);
		
		// 2. 객체를 저장할 File 객체를 생성
		File file = new File("asset/student.txt");
		
		// 3. File 객체를 이용해서 ObjectOutputStream을 생성
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			
			// 4. ObjectOutputStream을 통해서 객체를 File에 저장한다. 
			// 		저장될 객체는 반드시 직렬화가 가능한 객체여야 한다.
			//		- 직렬화가 가능한 객체만 저장 가능 --> 현재의 Class의 객체는 직렬화가 가능한 객체가 아니다
			//			==> 직렬화가 가능하게 하려면 어떻게 해야 하나요?
			//				--> Serializable Interface를 Implements 하면된다.
			// 					& Class의 Field가 전부 직렬화가 가능해야한다. 
			
			//				--> transient keyword를 통해 직렬화가 불가능한 Field를 직렬화 대상에서 제외
			//				=====> Object가 File에 들어갈 때 해당 Field는 들어가지 않음 
			
			objectOutputStream.writeObject(obj);
			
			// 5. 저장이 끝나면 Stream을 close() 한다.
			objectOutputStream.close();
			fileOutputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
