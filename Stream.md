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
       - 기본적으로 지연 연산 (최종 연산이 있는 경우에만 중간 처리를 수행한다.) 처리 -> 성능 최적화
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

     - lazy (지연) 처리 For 효율 

       - 맨 끝의 최종 처리가 있는지 확인  후 Reduction 수행

       - 만약에 없는 경우 중간 처리를 하지 않는다. 

         => 있는 경우에만 중간 처리를 수행한다.

     

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

         ![1566286978500](https://user-images.githubusercontent.com/39547788/63345614-16fd9280-c38e-11e9-88ee-886cda5e8322.png)

       

       

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

           ![1566289574095](https://user-images.githubusercontent.com/39547788/63345618-1bc24680-c38e-11e9-8eee-1300ad9967a5.png)



- Stream의 함수 

  - 중개 연산 (Stream  연산)

    - mapToInt()

      - 인자로 ToIntFunction의 람다식이 제공된다.

      - 특정 객체를 정수로 반환 (Mapping) 한다.

        - 홍길동이라는 객체를 해당 객체의 국어 성적인 10으로 Mapping한다. 

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

        ![1566290660249](https://user-images.githubusercontent.com/39547788/63345630-1fee6400-c38e-11e9-8987-c0f850957df9.png)

    - filter () 

      - 조건을 충족하는 요소를 제공하는 새로운 Stream 생성
      - 특정 조건의 요소를 필터링한다.
      - 인자로 Predicate 람다식을 작성한다.

    - skip(long n)

      - 처음 n 개의 요소를 제외하는 Stream 생성
      - 인자로 제외할 요소의 개수를 작성한다.

    - map (T,R)

      - 입력 T 타입 요소를 R 타입 요소로 변환하는 Stream 생성
      - 인자로 Function 람다식을 작성한다. 
        - Function 람다식의 첫번 째 입력 매개변수는 변환 전의 타입을 작성한다.
        - Function 람다식의 두번 째 입력 매개변수는 변환 후의 타입을 작성한다.

    - limit (long maxSize) 

      - maxSize 까지의 요소만 제공하는 Stream 생성
      - 인자로 최대로 제공하고자 하는 요소의 위치를 작성한다.

    - distinct ()

      - 같은 값을 갖는 요소를 중복해서 발생하지 않는 Stream 생성

    - peek ()

      - 

  - 종단 연산

    - forEach ()
      - 인자로 Consumer 람다식을 제공한다.
    - sum () / min () / max () / average () / count()

  

  

### Exam02_StreamSource

- Stream 종류

  - java.util.stream package 안에 우리가 사용할 수 있는 stream이 존재한다.

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

    

### Exam03_StreamPipline

#### Reduction

- 대량의 데이터를 가공해서 축소하는 개념

- sum, average, count, max, min

- Collection을 사용할 때 Stream을 이용해 Reduction 작업을 쉽게 수행할 수 있다.

- 만약, Collection 안에 Reduction 작업을 수행하기 쉽지 않은 형태로 데이터가 들어가 있으면, 중간 연산을 통해 Reduction 작업을 수행하기 좋은 형태로 변환한다. 

- Reduction 사용

  - 직원 DTO를 위한 Exam03_Employee를 작성한다.

    ```java
    class Exam03_Employee {
        private String name; // 이름
        private int age; // 나이
        private String dept; // 부서
        private String gender; // 성별
        private int salary; // 연봉
    
        public Exam03_Employee() {
            // TODO Auto-generated constructor stub
        }
    
        public Exam03_Employee(String name, int age, String dept, String gender, int salary) {
            super();
            this.name = name;
            this.age = age;
            this.dept = dept;
            this.gender = gender;
            this.salary = salary;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public int getAge() {
            return age;
        }
    
        public void setAge(int age) {
            this.age = age;
        }
    
        public String getDept() {
            return dept;
        }
    
        public void setDept(String dept) {
            this.dept = dept;
        }
    
        public String getGender() {
            return gender;
        }
    
        public void setGender(String gender) {
            this.gender = gender;
        }
    
        public int getSalary() {
            return salary;
        }
    
        public void setSalary(int salary) {
            this.salary = salary;
        }		
    }
    ```

    

  - 부서가 IT인 사람들 중 남자에 대한 연봉 평균을 구하기

    - filter( ) : 중간 처리 함수로, 주어진 조건에 대해 Stream을 필터링 한다.

    - mapToInt () : 주어진 객체를 Int Type으로 Mapping 한다.

    - average() : 평균 값을 구한다.

      ```java
      public class Exam03_StreamPipline {
      	
      	private static List<Exam03_Employee> employees = 
      			Arrays.asList(new Exam03_Employee("홍길동", 55, "IT", "남자", 5000),
      					new Exam03_Employee("김길동", 50, "Sales", "여자", 3000),
      					new Exam03_Employee("강길동", 21, "IT", "남자", 1000),
      					new Exam03_Employee("오길동", 29, "Sales", "남자", 3500),
      					new Exam03_Employee("유관순", 118, "IT", "여자", 7000),
      					new Exam03_Employee("하길동", 45, "IT", "여자", 4000),
      					new Exam03_Employee("공길동", 58, "IT", "남자", 1000),
      					new Exam03_Employee("임길동", 28, "Sales", "남자", 5000));
      
      	public static void main(String[] args) {
      		// TODO Auto-generated method stub
      		
      		// 부서가 IT인 사람들 중 남자에 대한 연봉 평균을 구해 보아요!
      		Stream<Exam03_Employee> stream = employees.stream();
      		
      		// Stream의 중간 처리와 최종 처리를 이용해서 원하는 작업을 해 보아요
      		double d = stream.filter(t->t.getDept().equals("IT")).filter(t->t.getGender().contentEquals("남자")).mapToInt(t->t.getSalary()).average().getAsDouble();
      		System.out.println("부서가 IT인 사람들 중 남자에 대한 연봉 평균" + d);		
      	}
      }
      ```

  - 나이가 35세 이상인 직원 중 남자 직원의 이름을 출력

    ```java
    stream.filter(t->t.getAge()>=35).filter(t->t.getGender().equals("남자")).forEach(t->System.out.println("직원의 이름은 : " + t.getName()));
    ```

  - 중복 제거

    - distinct()

      - 배열의 중복 

        - Int Type의 숫자 배열을 출력하는데, 중복되는 데이터는 한번만 출력한다.

          ```java
          public class Exam03_StreamPipline {	
          	public static void main(String[] args) {
          		
          		// Int Type 배열의 중복 제거 
          		int temp[] = {10, 30, 10, 20, 30, 30, 40, 50, 50};
          		IntStream s = Arrays.stream(temp);
          		s.distinct().forEach(t->System.out.println(t));		
              }
          }
          ```

        - 실행화면 

          ![1566385027621](https://user-images.githubusercontent.com/39547788/63433198-b3429a80-c45d-11e9-9c8d-87dfdf7ad821.png)

      - 객체의 중복

        - 동일한 사람의 정보가 중복으로 들어있는 경우, 해당 중복 데이터는 한번만 출력한다. 

          - 객체의 중복을 처리하기 위해서, 해당 클래스에 hashCode()와 equals()를 오버라이딩 하여 구현한다. 

            - equals() 를 오버라이딩을 하지 않은 경우

              - 각 객체들의 메모리 주소를 가지고 비교한다.

                ==> 중복되는 객체 (new Exam03_Employee("홍길동", 55, "IT", "남자", 5000))는 서로 다른 메모리 주소를 갖는다.

            - 오버라이딩으로 equals()를 구현하여 특정 조건을 만족하면 객체가 같음 알리도록 한다. 

              ```java
              @Override
              public int hashCode() {
                  final int prime = 31;
                  int result = 1;
                  result = prime * result + age;
                  result = prime * result + ((dept == null) ? 0 : dept.hashCode());
                  result = prime * result + ((gender == null) ? 0 : gender.hashCode());
                  result = prime * result + ((name == null) ? 0 : name.hashCode());
                  result = prime * result + salary;
                  return result;
              }
              
              // 객체 비교 
              // 반드시 메모리 주소가 아닌 데이터를 비교하는 방식으로 재구현한다. 
              @Override
              public boolean equals(Object obj) {
                  boolean result = false;
                  // Object Type의 객체를 Exam03_Employee Type으로 다운 캐스팅을 수행한다.
                  Exam03_Employee target = (Exam03_Employee) obj;
                  if (this.getName().equals(target.getName())) {
                      if (this.getAge()==target.getAge()) {
                          return true;				
                      }
                      return false;
                  }
                  return true;
              }
              ```

          

          - stream을 열고 distinct() 를 이용해 중복을 제거 한뒤, 객체의 이름을 출력한다. 

            ```java
            public class Exam03_StreamPipline {
            
                private static List<Exam03_Employee> employees = 
                    Arrays.asList(new Exam03_Employee("홍길동", 55, "IT", "남자", 5000),
                                  new Exam03_Employee("홍길동", 55, "IT", "남자", 5000),
                                  new Exam03_Employee("김길동", 50, "Sales", "여자", 3000),
                                  new Exam03_Employee("강길동", 21, "IT", "남자", 1000),
                                  new Exam03_Employee("오길동", 29, "Sales", "남자", 3500),
                                  new Exam03_Employee("유관순", 118, "IT", "여자", 7000),
                                  new Exam03_Employee("하길동", 45, "IT", "여자", 4000),
                                  new Exam03_Employee("공길동", 58, "IT", "남자", 1000),
                                  new Exam03_Employee("임길동", 28, "Sales", "남자", 5000));
            
                public static void main(String[] args) {
             
                    // hasCode() 와 equals() Method 구현 
                    System.out.println("중복 가능!");
                    employees.stream().distinct().forEach(t->System.out.println(t.getName()));
                }
            }
            ```

          - 실행 화면

            ![1566386433900](https://user-images.githubusercontent.com/39547788/63433217-bc336c00-c45d-11e9-9cda-ea6026cf3189.png)

  - 정렬하기

    - sorted()

      - sorted() : 오름차순 정렬

      - sorted(Comparator.reverseOrder()) : 내림차순 정렬

      - 숫자 배열의 정렬

        - Int Type의 배열의 모든 요소를 오름 차순으로 정렬한다. 

          ```java
          int a[] = {50,40,20,70,100,10};
          IntStream stream = Arrays.stream(a);
          stream.sorted().forEach(t->System.out.print(t + " -- "));
          ```

        - Int Type의 배열의 모든 요소를 내림 차순으로 정렬한다. 

          ```java
          ,,,,,,,,,,,,,,,,,,,,,,,,,?
          ```

      - 객체의 정렬

        - 숫자와 문자와는 다르게 객체는 크다 또는 작다의 개념이 없다. 

          이를 인식시켜 줘야 한다.

        - sorted()를 사용하기 위해서는 정렬이 가능한 형태로 만들어야 한다. 

          - Exam03_Employee Class 타입의 Comparable Interface를 Implements 한다.

            - compareTo() 를 오버라이딩 한다. 

              : 정수 값을 리턴하는 함수 

              - 해당 함수를 호출한 this와 인자로 주어진 Exam03_Employee Type의 객체와 비교한다.

                - 만약 this의 salary가 더 큰 경우

                  - this가 앞에 위치하도록 순서를 바꾼다.
                  - 리턴되는 result 값이 양수 이다. 

                - 만약 this의 salary가 더 작거나 같은 경우

                  - 순서에 변동이 없다. 
                  - 같은 경우 0을 리턴하고, 작은 경우 음수 값을 리턴한다.

                  ```java
                  class Exam03_Employee implements Comparable<Exam03_Employee>{	
                  	@Override
                  	public int compareTo(Exam03_Employee o) {
                  		
                  		// 정수 값을 리턴
                  		// 양수가 리턴되면 순서를 바꿔요 
                  		// 0이나 음수가 리턴되면 순서를 바꾸지 않아요 
                  		int result = 0;		
                  		
                  		// this 객체와 Exam03_Employee o 객체를 비교 
                  		// 내가 앞, o가 뒤
                  		// 내가 더 크면, 뒤로 값을 보낸다. => 순서를 바꾼다. => result 가 양수값
                  		if (this.getSalary() > o.getSalary()) {
                  			result = 1;
                  		}
                  		// 같으면 아예 자리 변동을 하지 않음
                  		else if (this.getSalary() == o.getSalary()) {
                  			result = 0;
                  		}
                  		else {
                  			result = -1;
                  		}
                  		return result;
                  	}
                  }
                  ```

                  

        - 부서가 "IT"인 직원의 이름을 연봉에 대하여 내림차순으로 정렬하여 출력한다. 

          ```java
          public class testt {
          
              private static List<Exam03_Employee> employees = 
                  Arrays.asList(new Exam03_Employee("홍길동", 55, "IT", "남자", 5000),
                                new Exam03_Employee("홍길동", 55, "IT", "남자", 5000),
                                new Exam03_Employee("김길동", 50, "Sales", "여자", 3000),
                                new Exam03_Employee("강길동", 21, "IT", "남자", 1000),
                                new Exam03_Employee("오길동", 29, "Sales", "남자", 3500),
                                new Exam03_Employee("유관순", 118, "IT", "여자", 7000),
                                new Exam03_Employee("하길동", 45, "IT", "여자", 4000),
                                new Exam03_Employee("공길동", 58, "IT", "남자", 1000),
                                new Exam03_Employee("임길동", 28, "Sales", "남자", 5000));
          
              public static void main(String[] args) {    	
              	employees.stream()
          		 .filter(t->t.getDept().equals("IT"))
          		 .distinct()
          		 .sorted(Comparator.reverseOrder())
          		 .forEach(t->System.out.println("이름 : " + t.getName() + " == 연봉 : " + t.getSalary()));
              }
          }
          ```

        - 실행 화면

          ![1566388408004](https://user-images.githubusercontent.com/39547788/63433239-c48ba700-c45d-11e9-8ffe-4aa55a257075.png)

    

  - 반복

    - forEach()를 이용하면 Stream 안의 요소를 반복한다. 

      그러나, forEach()는 최종 처리 함수이기 때문에, 중간 연산에 사용할 수 없다. 

      ==> peek()

    - peek () 

      - 중간 연산에 사용할 수 있는 반복 처리 함수

        ```java
        employees.stream()
            .peek(t->System.out.println(t.getName()))
            .mapToInt(t->t.getSalary())
            .forEach(t->System.out.println(t));
        ```

  - 확인용 최종 처리 함수 

    - Predicate를 이용해 Boolean으로 리턴한다.

    - 50세 이상인 모든 객체에 대해 모두 100세 이상인지에 대한 boolean

      - allMatch()

        ```java
        boolean result = employees.stream()
            .filter(t->t.getAge()>=50)
            .allMatch(t->t.getAge()>100);
        System.out.println(result);
        ```

        - 실행화면

          ![1566389330421](https://user-images.githubusercontent.com/39547788/63433275-d2d9c300-c45d-11e9-8e74-fef48061d45f.png)

    - 50세 이상인 모든 객체에 대해 100세 이상인 객체가 하나라도 있는지에 대한 boolean

      - anyMatch()

        ```java
        boolean result = employees.stream()
            .filter(t->t.getAge()>=50)
            .anyMatch(t->t.getAge()>100);
        System.out.println(result);
        ```

        - 실행 화면 

          ![1566389306828](https://user-images.githubusercontent.com/39547788/63433301-e1c07580-c45d-11e9-8590-36fdcf7088fe.png)

  - 추출한 결과 데이터 받기

    - 결과로 추출한 데이터를 Set, Map, List의 형태로 받을 수 있다.

    - collect()

      - 나이가 50살 이상인 사람들의 연봉을 구해서 List< Integer > 형태의 ArrayList에 저장한다.

        ```java
        List<Integer> resultList = employees.stream().filter(t->t.getAge()>=50)
            .map(t->t.getSalary())
            .collect(Collectors.toList());
        
        System.out.println(resultList);
        ```

      - 나이가 50살 이상인 사람들의 연봉을 구해서 Set Integer > 형태의 Set에 저장한다.

        ```java
        Set<Integer> resultSet = employees.stream().filter(t->t.getAge()>=50)
            .map(t->t.getSalary())
            .collect(Collectors.toCollection(HashSet :: new ));
        
        System.out.println(resultSet);
        ```

        

