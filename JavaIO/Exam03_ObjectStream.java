package JavaIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Exam03_ObjectStream {

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "홍길동");
		map.put("2", "강감찬");
		map.put("3", "신사임당");
		map.put("4", "최길동");
		
		// 객체가 저장된 File에 대한 File 객체부터 만들어요
		// 해당 File이 존재하는지 그렇지 않은지는 상관없어요~ 의미론적인 ...?
		File file = new File("asset/objectStream.txt");
		
		// Object 가 저장된 File에 대한 outputStream부터 열어요
		try {
			// 이 시점에 파일이 있는지 확인하고 파일이 없으면 새로운 파일을 만든다.
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			System.out.println(file.getPath());
			System.out.println(file.getName());
			
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(map);
			
			/*
			 	1. 내보내려고 하는 map 객체를 마샬링 작업을 통해서 형태를 변환
			 		-> 객체의 내용을 담고 있는 문자열 형태로 표현한다.
			 */
			
			
			
			// 저장하는 코드이다보니 close를 제대로 해줘야 해요~
			// 나중에 open한 Stream 먼저 Close() = 역순으로 
			objectOutputStream.close();
			fileOutputStream.close();
			
			// 객체가 저장된 File을 open해서 해당 객체를 프로그램으로 다시 읽어들여요 
			// 파일에서 데이터를 읽기위해 inputStream 이 필요			
			FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

			
			// 문자열로 표현된 객체를 읽어즐여서 원래 객체로 복원 
			// 언 마샬링
			Object obj = objectInputStream.readObject();
			
			
			HashMap<String, String> result = null;
		
			// File부러 읽은 obj가 Map 객체니?
			if (obj instanceof Map<?,?>) {
				// 만약 obj가 Map 객체면 다운 캐스팅
				result = (HashMap<String, String>)obj;				
			}
			System.out.println(result.get("3"));
			
			objectInputStream.close();
			fileInputStream.close();
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
