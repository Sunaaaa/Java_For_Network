# Stream

- Java 8에서 새롭게 도입 ( ** 주의 : java.io 안에 있는 Stream과는 다름 ) 

- 컬렉션 (List, Set, Map, 배열, ..) 처리를 위해서 사용됨

  - 컬렉션 안의 데이터를 반복시키는 반복자의 역할을 한다. 

  - 예
    - ArrayList 안에 학생 객체 5개가 있으면, 그 5개의 객체를 하나로 가져와 람다식을 이용해 연산 등을 수행하여 처리한다.

- Stream API

  1. Stream 생성 
  2. 초기 Stream을 다른 Stream으로 변환하는 중간 연산 (중개 연산) 들을 하나 이상의 단계로 지정한다. 
  3. 결과를 산출하기 위해 최종 연산을 적용한다. 이 연산은 앞선 지연 연산들의 실행을 강제한다. 
  4. 이후, 해당 Stream을 더는 사용할 수 없다.
     - 중개 연산
       - Stream을 받아 Stream을 리턴한다.
       - 기본적으로 지연 연산 처리 -> 성능 최적화
     - 최종 연산자
       - Stream의 요소들을 연산 후 결과 값을 반환한다 .
       - 최종 연산 시 모든 연산을 수행한다. -> 반복 작업 최소화
       - 연산이 끝난 이후, 더 이상 Stream을 사용할 수 없다.

- Stream 예 

  - 파이프-필터 기반 API

  ```java
  Stream<Exam01_Student> sStream2 = students.stream();
  double dd = sStream2.filter(t->t.getName().length()==3).mapToInt(t->t.getKor()).average().getAsDouble();
  ```



- Stream 특징

  1. 반복의 내재화

     - 반복 구조 캡슐화 (제어의 역전)
     - 최적화와 알고리즘 분리

  2. 지연 연산

     - Stream을 반환하는 필터-맵 API는 기본적으로 지연 연산 수행
     - 지연 연산을 통해 성능 최적화 (반복 작업 최소화 및 무상태 중개 연산)

  3. 병렬 처리

     - 동일한 코드로 순차 또는 병렬 연산을 쉽게 처리

     - Stream 의 2가지 유형

       1. 순차 스트림 (Sequential Stream)
       2. 병렬 스트림 (Parallel Stream)

       

- Collection VS Stream

  |           Collection           |               Stream               |
  | :----------------------------: | :--------------------------------: |
  |   for (Student c : students)   | students.forEach( c -> {  ...  } ) |
  | 외부 반복 (External Iteration) |   내부 반복 (Internal Itertion)    |
  |        반복을 통해 생성        |          반복을 통해 연산          |
  | 효율적이고 직접적인 요소 처리  |        파이프-필터 기반 API        |
  |        유한 데이터 구조        |     무한 연속 흐름 데이터 구조     |
  |       반복적 재사용 가능       |            재사용 불가             |

- **<u>데이터 보관이 아닌 데이터 처리에 집중</u>**





### Exam01_StreamBasic

- Stream 사용

  - 친구 목록 (myBuddy) 을 출력하자!

    1. 일반 for문 (i라는 첨자 사용)을 이용해서 처리 

       ```java
       for (int i = 0; i<myBuddy.size(); i++) {
           System.out.println(myBuddy.get(i));
       }
       ```

       

    2. 첨자를 이용한 반복을 피하기 위해 Iterator 사용

       - 첨자는 없앴지만, 반복을 피하지 못함

       ```java
       Iterator<String> iterator = myBuddy.iterator();
       while(iterator.hasNext()) {
           System.out.println(iterator.next());
       }
       ```

    3. Stream 사용

       - 내부 반복자를 이용하기 때문에 반복자가 필요 없다. 

       - 병렬 처리가 가능한다.

       - 기본 Stream 사용

         - myBuddy 의 모든 요소를 출력하자

         - Stream의 forEach() Method는 Consumer Type의 람다식을 인자로 작성한다. 

           ```java
           public class Exam01_StreamBasic {
           	private static List<String> myBuddy = Arrays.asList("홍길동", "김길동", "홍길동", "이순신", "신사임당");
           	public static void main(String[] args) {
           		
           		// 사람 이름을 출력하려고 해요 
           		// Stream을 이용하여 for문의 효과 
           		Stream<String> stream = myBuddy.stream();
           		stream.forEach(t->System.out.println(t));
           	}
           }
           ```

           

         - 실행 화면

         ![1566286978500](C:\Users\student\AppData\Roaming\Typora\typora-user-images\1566286978500.png)

       

       

       - 병렬 처리가 가능한 Stream 

         - Thread.currentThread() : 현재 실행 중인 Thread를 반환한다.

         - Stream의 forEach() Method는 Consumer Type의 람다식을 인자로 작성한다. 

           ```java
           public class Exam01_StreamBasic {
               private static List<String> myBuddy = Arrays.asList("홍길동", "김길동", "홍길동", "이순신", "신사임당");
               public static void main(String[] args) {
           
                   // 병렬 처리하는 stream
                   Consumer<String> consumer = t-> {
                       System.out.println(t + " : " + Thread.currentThread());
                   };
           
                   Stream<String> pstream = myBuddy.parallelStream();
                   pstream.forEach(consumer);				
               }
           }
           ```

           

         - 실행 화면 

           ![1566289574095](C:\Users\student\AppData\Roaming\Typora\typora-user-images\1566289574095.png)



- Stream의 함수 

  - 중개 연산 (Stream  연산)
    - mapToInt()

      - 인자로 ToIntFunction의 람다식이 제공된다.

      - 특정 객체를 정수로 반환 (Mapping) 한다.

        -  홍길동이라는 객체를 해당 객체의 국어 성적인 10으로 Mapping한다. 

      - 학생 전체의 국어 성적의 평균을 구하기 

        - 학생 DTO를 위한 Exam01_Student Class를 작성한다.

          ```java
          class Exam01_Student{
              private String name; // 학생 이름
              private int kor;	// 국어 성적
              private int eng;	// 영어 성적
          
              public Exam01_Student() {
                  // TODO Auto-generated constructor stub
              }
          
              public Exam01_Student(String name, int kor, int eng) {
                  super();
                  this.name = name;
                  this.kor = kor;
                  this.eng = eng;
              }
          
              public String getName() {
                  return name;
              }
          
              public void setName(String name) {
                  this.name = name;
              }
          
              public int getKor() {
                  return kor;
              }
          
              public void setKor(int kor) {
                  this.kor = kor;
              }
          
              public int getEng() {
                  return eng;
              }
          
              public void setEng(int eng) {
                  this.eng = eng;
              }
          }
          ```

        - sStream.mapToInt(t -> t.getKor()) : 모든 객체를 해당 객체의 국어 성적으로 Mapping한다. 

        - .average() : 모든 객체의 국어 성적의 평균을 구한다. 

        - .getAsDouble() : 컬랙션 요소에 저장된 값을 Double Type으로 가져온다.

          ```java
          public class Exam01_StreamBasic {
              private static List<Exam01_Student> students = Arrays.asList( 
                  new Exam01_Student("홍길동",10,20), 
                  new Exam01_Student("김길동", 87, 46),
                  new Exam01_Student("이순신", 94, 12), 
                  new Exam01_Student("신사임당", 70, 88));
              public static void main(String[] args) {
          
                  Stream<Exam01_Student> sStream = students.stream();
          
                  double d = sStream.mapToInt(t -> t.getKor()).average().getAsDouble();
                  System.out.println("국어 성적의 평균 : " + d);		
              }
          }
          ```

      

      - 실행 화면

        ![1566290660249](C:\Users\student\AppData\Roaming\Typora\typora-user-images\1566290660249.png)

    - filter () 

      - 조건을 충족하는 요소를 제공하는 새로운 Stream 생성
      - 특정 조건의 요소를 필터링한다.
      - 인자로 Predicate 람다식을 작성한다.

    - skip(long n)

      - 처음 n 개의 요소를 제외하는 Stream 생성
      - 인자로 제외할 요소의 개수를 작성한다.

    - map ()

      - 입력 T 타입 요소를 R 타입 요소로 변환하는 Stream 생성
      - 인자로 Function 람다식을 작성한다. 
        - Function 람다식의 첫번 째 입력 매개변수는 변환 전의 타입을 작성한다.
        - Function 람다식의 두번 째 입력 매개변수는 변환 후의 타입을 작성한다.

    - limit (long maxSize) 

      - maxSize 까지의 요소만 제공하는 Stream 생성
      - 인자로 최대로 제공하고자 하는 요소의 위치를 작성한다.

    - distinct ()

      - 같은 값을 갖는 요소를 중복해서 발생하지 않는 Stream 생성

  - 종단 연산

    - forEach ()
      - 인자로 Consumer 람다식을 제공한다.
    - sum () / min () / max () / average () / count()

  

  

### Exam02_StreamSource

- Stream 종류

  -  java.util.stream package 안에 우리가 사용할 수 있는 stream이 존재한다.

  - Stream은 여러 가지 형태의 다양한 Source에서 Stream을 얻어낼 수 있다.

  - BaseStream 으로부터 상속 받은 몇몇 개의 Stream이 존재한다.

    1. Stream : 해당 Stream 안에 객체가 들어가 있는 경우 사용

       ```java
       public class Exam02_StreamSource {
       	private static List<String> names = 
       			Arrays.asList("홍길동", "김길동", "최길동");
       	
       	public static void main(String[] args) {
       		Stream<String> stream = names.stream();
       }
       ```

       

    2. IntStream: 해당 Stream 안에 Int 값이 들어가 있는 경우 사용

       ```java
       public class Exam02_StreamSource {
       	private static int myArr[] = {10, 20, 30, 40, 50};
       	
       	public static void main(String[] args) {
       		// Arrays Class의 Static Method 사용한다.
       		IntStream istream = Arrays.stream(myArr);
       		System.out.println(istream.sum());
           }
       }
       
       ```

       

    3. LongStream: 해당 Stream 안에  long 값이 들어가 있는 경우 사용

    4. DoubleStream: 해당 Stream 안에  double 값이 들어가 있는 경우 사용



- Stream 생성

  - Collection : [Collection-Object].stream()

  - Files : Stream< String >Files.lines()

    - File로 부터 Stream을 생성

      - java.io와 유사한 java.nio에 포함된 Class

      - File 처리는 Exception의 발생 여지가 있기 때문에, 반드시 try-catch 문으로 기본 예외처리를 수행하는 코드는 작성해야 한다.

        ```java
        public class Exam02_StreamSource {
        	private static List<String> names = 
        			Arrays.asList("홍길동", "김길동", "최길동");
        	
        	private static int myArr[] = {10, 20, 30, 40, 50};
        	
        	public static void main(String[] args) {
        		// File로 부터 Stream을 생성할 수 있어요. 
        		Path path = Paths.get("asset/readme.txt");
        
                // Path
        		try {
        			Stream<String> sstream = Files.lines(path, Charset.forName("UTF-8"));
        			sstream.forEach(t->System.out.println(t));
        		}catch (Exception e) {
        			System.out.println(e);
        		}
        	}
        }
        ```

        

  - Arrays : Arrays.stream( [Array-Object] )

    ```java
    IntStream istream = Arrays.stream(myArr);
    System.out.println(istream.sum());
    ```

  - Random : Random.double()/ints()/longs()

  - Stream : Stream.of()

  - range(start, end), rangeClosed(start, end) -> (: IntStream, LongStream에서 제공)

    ```java
    IntStream istream2 = IntStream.rangeClosed(1, 10);
    ```

    