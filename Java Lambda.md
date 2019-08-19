## 함수적 프로그래밍 패턴 

- 독립적으로 함수를 만들어 프로그래밍을 하는 패턴
- Lambda



## Lambda

- 익명함수를 만들기 위한 Expression

- 객체지향적 언어보다는 함수지향적 언어에서 많이 사용된다. 

  - JavaScript

- 기존 자바 개발자들은 이 Lambda라는 개념에 익숙해지기가 쉽지 않다.
  ==> 그럼에도 자바가 Lambda를 도입한 이유

  1. 코드가 간결해진다.

  2. Java Stream 을 이용하기 위해서 Lambda를 도입 

     - Java Stream

       - Collection (ArrayList, Map, Set, Array, ...) 을 효율적으로 처리한다.

       - 병렬 처리를 위한 별도의 코드 없이, 몇 개의 함수로 빠른 병렬 처리를 구현할 수 있다. 

         ==> 병렬 처리를 통하여, 속도 측면에서 굉장히 빠르다. 



### 람다식의 기본 형태 

- (  ) : 매개변수 

- -> : " 실행해.."

- {  } : 실행 코드

  ```
  ( 매개변수 ) -> { 실행코드 }
  ```

- 익명함수를 정의하는 형태로 되어 있지만, 실제로는 익명 클래스의 인스턴스 (객체)를 생성한다. 

- 람다식이 만들어 내는 객체 

  - 람다식이 대입되는 Interface 변수가 어떤 Interface 인가에 달려 있다. 





### Exam01_LambdaBasic.java 

1. Thread의 생성 

   1. Thread의 Sub Class를 이용해서 Thread 생성

      - Thread라는 클래스를 Extends 해야 Thread 를 만들 수 있다. 

        ```java
        class MyThread extends Thread{
        	public void run() {
        		System.out.println("Thread가 실행됩니다.");
        	}
        }
        
        public class Exam01_LambdaBasic {
        	public static void main(String[] args) {
        		
        		MyThread t = new MyThread();
        		t.start();
        		
        	}
        }
        ```

   2. Runnable Interface를 구현한 class를 이용해서 Thread 생성 (더 좋은 방식)

      - 상속을 통해 생성한 Class로 Thread를 생성하면 유지보수가 힘들고 재사용성이 높다. 

        ```java
        package javaLambda;
        
        class MyRunnable implements Runnable{
        	public void run() {
        		System.out.println("Thread가 실행됩니다.");
        	}
        }
        
        
        public class Exam01_LambdaBasic {
        	public static void main(String[] args) {
        		MyRunnable runnable = new MyRunnable();
        		Thread t = new Thread(runnable);
                t.start();		
        	}
        }
        ```

   3. Runnable Interface를 구현한 익명 Class 를 이용해서 Thread 생성 (안드로이드에서 일반적인 형태)

      - 클래스를 외부에서 별도로 생성하는 것 보다, 익명 클래스를 통하여 생성하면 변수들을 공유하여 사용할 수 있다. 

        ```java
        // 람다식 형태 
        Runnable rRunnable = () -> {};
        
        // 변수 선언
        Thread t = new Thread(()->{
            System.out.println("Thread 실행!!");
        });
        t.start();
        
        // 변수 선언 안함
        new Thread(()->{
            System.out.println("Thread 실행!!");
        }).start();
        ```

2. 일반적인 Interface 정의 

   - Interface Type의 객체를 만들어 실행한다. 

     - 인터페이스는 추상클래스만 올 수 있다. 

       - 추상 클래스 

     - 인터페이스 이기 때문에 객체를 만들 수 없다. 

       - 객체를 만들기 위해서는 반드시 Overriding을 해야 한다. 

         ```java
         // 기존의 인터 페이스 객체 생성
         Exam01_LB_Interface sample = new Exam01_LB_Interface() {
         
             @Override
             public void myFunc(int i) {
                 // TODO Auto-generated method stub
                 System.out.println(i + "  : Interface의 인스턴스 생성!!");
             }
         };
         
         // 람다식 원형
         // 오버라이딩 하면서 Interface의 인스턴스 생성
         Exam01_LB_Interface rSample = (int i) -> {System.out.println(i + "  : Interface의 인스턴스 생성!!");};
         
         // 람다식 변형
         // 컴파일러가 유추에 의해 int 형의 변수인 것을 알기 때문에, Type을 명시하지 않아도 된다. 
         Exam01_LB_Interface rSample2 = (i) -> {System.out.println(i + "  : Interface의 인스턴스 생성!!");};
         
         // 람다식 동작 : Interface안의 실제 함수를 호출한다.
         rSample.myFunc(2);
         ```

     - <u>***제약 사항***</u> 

       - 만약, Overring해야하는 추상 Method가 여러 개 있는 경우, 컴파일러가 Type을 유추 (어떤 Method인지) 할 수 없기 때문에 해당 Interface는 람다식으로 표현할 수 없다. 
         - **Interface 에  추상 메소드는 반드시 1개 여야 한다.** 





### Exam02_LambdaExpression

- 람다식을 표현하는 방식 

  1. 매개변수의 이름을 개발자가 지정할 수 있다. 

     ```java
     @FunctionalInterface
     interface Exam02_LE_Interface{
     	void myfunc(int number);
     }
     
     public class Exam02_LambdaExpression {
     	public static void main(String[] args) {
     		
     		Exam02_LE_Interface obj = (int my)-> {
     			System.out.println(my + "코드가 실행이 되요~");
     
     		};
     	}
     }
     ```

     

  2. 일반적으로 매개변수의 타입을 지정하지 않는다. 

     - 매개변수의 Type은 컴파일러의 타입 유추에 의해 알 수 있다. 

       ```
       @FunctionalInterface
       interface Exam02_LE_Interface{
       	void myfunc(int number);
       }
       
       public class Exam02_LambdaExpression {
       	public static void main(String[] args) {
       		
       		Exam02_LE_Interface obj = (my)-> {
       			System.out.println(my + "코드가 실행이 되요~");
       		};
       	}
       }
       ```

       

  3. 만약 매개변수가 1개인 경우, ( )를 생략할 수 있다. 

     ```java
     @FunctionalInterface
     interface Exam02_LE_Interface{
     	void myfunc(int number);
     }
     
     public class Exam02_LambdaExpression {
     	public static void main(String[] args) {
     		
     		Exam02_LE_Interface obj3 = my-> {
     			System.out.println(my + "코드가 실행이 되요~");
     		};
     	}
     }
     ```

     

  4. 만약 실행문이 1줄인 경우, { }를 생략할 수 있다.

     - 실행문이 여러 줄인 경우에는 반드시 { } 안에 코드를 작성해야 한다. 

       ```java
       @FunctionalInterface
       interface Exam02_LE_Interface{
       	void myfunc(int number);
       }
       
       public class Exam02_LambdaExpression {
       	public static void main(String[] args) {
       
               Exam02_LE_Interface obj4 = my-> System.out.println(my + "코드가 실행이 되요~");
       
       
       	}
       }
       
       
       ```

       

  5. 매개변수가 없는 경우, ( )는 생략할 수 없다. 

     - 반드시 ( ) 를 작성해야 한다. 

       ```java
       @FunctionalInterface
       interface Exam02_LE_Interface{
       	void myfunc();
       }
       
       public class Exam02_LambdaExpression {
       	public static void main(String[] args) {
       		
       		Exam02_LE_Interface obj4 = ()-> System.out.println(my + "코드가 실행이 되요~");
       	}
       }
       
       
       ```

       

  6. 실행문에 당연히 return 문을 작성할 수 있다. 

     ```java
     @FunctionalInterface
     interface Exam02_LE_Interface{
     	int myfunc(int a, int b);
     }
     
     public class Exam02_LambdaExpression {
     	public static void main(String[] args) {
     		
     		Exam02_LE_Interface obj6 = (a,b)-> {return a+b;};
     	}
     }
     
     ```

     

  7. 만약 실행문에 return 문이 달랑 1개인 경우, { }와 Keyword return 을 생략할 수 있다.

     ```java
     @FunctionalInterface
     interface Exam02_LE_Interface{
     	int myfunc(int a, int b);
     }
     
     public class Exam02_LambdaExpression {
     	public static void main(String[] args) {
     		
             Exam02_LE_Interface obj6 = (a,b)-> a+b;
     		System.out.println(obj6.myfunc(10, 20));
     	}
     }
     
     ```

     

  

- 람다식 정의

  - Interface Type의 Instance (객체)를 생성하는 Expression
  - Interface Type의 변수에 Assign 되는 하나의 식
  - 해당 Interface Type의 객체를 뽑아 쓰는 것 
  - ***<u>람다식은 결국 익명 객체를 생성하는 코드</u>***
    - 람다식이 생성하는 객체는 결국 어떤 Interface Type의 변수에 Assign이 되는지 달려있다.

  

  

- 람다의 Target Type 

  - 람다식으로 만들어지는 Interface의 Type

  - Interface의 종류에 따라 작성 방법이 달라지기 때문에, 람다식이 대입될 Interface를 람다식의 Target Type이라고 한다. 

  - 컴파일러가 람다의 Target Type이 될 수 있는지 Check 해준다. 

    - 아무 Interface나 사용될 수 없다.

    - 반드시 해당 Interface는 단 1개의 추상 Method만 존재해야 한다. 

  - Annotaion를 명시하여 컴파일을 이용해 Type Check를 할 수 있다. 

    ```java
    @FunctionalInterface
    interface Exam02_LE_Interface{
    	int myfunc(int a, int b);
    }
    
    ```

    - @FunctionalInterface : 함수적 Interface 
      - 람다의 Target Type이 될 수 있는 Interface
      - 해당 Interface에 두개 이상의 Method를 선언하면, 유효하지 않다는 오류 발생
        
        => 컴파일러 수준에서 오류 확인 가능!

  - 예 

    => Thread 생성 시 사용하는 Runnable Interface

    - public void run() 이라는 추상 Method 1개만 가진 Interface
      - **<u>Runnable Interface는 Target Type이 될 수 있다.</u>**
      - **<u>Runnable Interface는 함수적 Interface</u>**
        - 예 ) 이벤트를 처리하는 Interface는 대체적으로 함수적 인터페이스 (예외도 물론 있음) 





### Exam03_LambdaUsingVariable

#### 람다식을 정의해서 사용할 때 주의해야 할 점 

1. 클래스의 멤버 변수 (Field + Method) 와 지역 변수 (Local Variable) 의 사용에 제약

   - **this** : 현재 사용되는 객체의 reference

     - 람다식은 익명 객체를 만들어 내는 코드 

       =>  람다식의 실행 코드 내에서 this Keyword를 쓰면 익명 객체를 지칭하지 않는다.

       - 익명 객체가 아닌 상위 클래스를 지칭한다. 

         ```java
         @FunctionalInterface
         interface Exam03_LUV_Interface{
         	public void myFunc();
         }
         
         class OuterClass{
         	
         	public int outerField = 100;
         	public OuterClass() {
         		System.out.println("Outer Class : " + this.getClass().getName());
         	}
         	
         	
         	class InnerClass {
         
         		// Field
         		int innerField = 200;
         		Exam03_LUV_Interface localLambda;
         		
         		Exam03_LUV_Interface fieldLambda = () -> {
         
         			System.out.println("InnerClass 내부 fieldLambda : " + this.getClass().getName());
         			
         		};
         		
         		// Constructor
         		public InnerClass() {
         			System.out.println("InnerClass : " + this.getClass().getName());
         		}		
         	}	
         }
         
         public class test {
         	
         	public static void main(String[] args) {
         
         		// 외부 클래스의 객체 생성
         		OuterClass outer = new OuterClass();
         		
         		// 내부 클래스의 객체 생성 
         		OuterClass.InnerClass inner = outer.new InnerClass();
         		inner.fieldLambda.myFunc();
         	}
         }
         ```

       - 실행화면

         ![1566213449479](https://user-images.githubusercontent.com/39547788/63262488-10551980-c2c1-11e9-8552-929f740ec3e4.png)

         

       - 만약 익명 객체를 지칭하고 싶은 경우, new Keyword를 통하여 해당 Interface를 직접 구현한다. (람다식으로 구현하는 것이 아님)

         ```java
         @FunctionalInterface
         interface Exam03_LUV_Interface{
         	public void myFunc();
         }
         
         class OuterClass{
         	
         	public int outerField = 100;
         	public OuterClass() {
         		System.out.println("Outer Class : " + this.getClass().getName());
         	}
         	
         	
         	class InnerClass {
         
         		// Field
         		int innerField = 200;
         		Exam03_LUV_Interface localLambda;
         		
         		Exam03_LUV_Interface fieldLambda = () -> {
         
         			System.out.println("InnerClass 내부 fieldLambda : " + this.getClass().getName());
         			
         		};
         		
         		Exam03_LUV_Interface a = new Exam03_LUV_Interface() {
         			
         			@Override
         			public void myFunc() {
         				// TODO Auto-generated method stub
         				System.out.println("Inner Class 내부 a : " + this.getClass().getName());
         			}
         		};
         
         		// Constructor
         		public InnerClass() {
         			System.out.println("InnerClass : " + this.getClass().getName());
         		}		
         	}	
         }
         
         public class test {
         	
         	public static void main(String[] args) {
         
         		// 외부 클래스의 객체 생성
         		OuterClass outer = new OuterClass();
         		
         		// 내부 클래스의 객체 생성 
         		OuterClass.InnerClass inner = outer.new InnerClass();
         		inner.fieldLambda.myFunc();
         		inner.a.myFunc();	
         	}
         }
         ```

       - 실행 화면 

         ![1566212609527](https://user-images.githubusercontent.com/39547788/63262484-0cc19280-c2c1-11e9-9c97-a634a0c878ea.png)

         

       

2. 람다식 안에서는 지역 변수를 ReadOnly 형태로 사용되어야 한다. 

   - Inner Class 의 Method 내에서 선언된 지역 변수 (Local Variable)

     - 메모리 영역 Stack에 저장이 되고, Method가 호출되면 생기고 Method가 끝나면 없어진다.

   - Inner Class 의 Field로 선언된 localLambda

     - localLambda : Exam03_LUV_Interface Type의 객체 

     - 해당 객체는 사라지지 않지만, Inner Class 의 Method 내에서 선언된 지역 변수는 해당 함수의 호출이 끝나면 사라진다 

       ==> 같은 Method 내의 지역 변수라고 하더라도, 해당 람다식에서 Local Variable의 값을 변경할 수 없다. (**<u>Read Only</u>**)

       ==> 내부적으로 Local Variable을 final으로 지정해서 사용된다. 

       ```java
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
       
               // Method 내의 Field 
               Exam03_LUV_Interface localLambda;
       
               // Field
               Exam03_LUV_Interface fieldLambda = () -> {
                   System.out.println(this.getClass().getName());
               };
       
               // Constructor
               public InnerClass() {
                   System.out.println(this.getClass().getName());
               }
       
               // Method
               public void innerMethod() {
                   // Method 내에서 선언된 지역 변수 (Local Variable)
                   int localVal = 100;
       
                   // Local Variable
                   localLambda = ()->{
       
                       System.out.println(localVal);
                   };
                   localLambda.myFunc();
               }
       
           }
       
       }
       ```

       



### Exam04_LambdaUsingConsumer

#### Consumer

- Consumer

  - 함수적 인터페이스 (람다식이 대입될 수 있는 Target Type으로 사용할 수 있는 Interface를 지칭)

  - 해당 패키지 내에 Consumer라는 Interface가 있다. 

    ```java
    import java.util.function.Consumer;
    
    ```

    

  - 값을 소비만 하는 역할을 담당

  - 람다를 만들고, 값을 넣어 값을 해당 { } 안에서 소비만 한다.

    - return 타입이 void
    - return 되는 값이 없음

  - Java가 우리에게 제공하는 Interface이고, 추상 Method를 단 1개만 가지고 있다.

    - accept()라는  Method를 제공

    - 함수의 리턴 타입은 void

    - Consumer가 가진 람다를 호출한다. 

      ```java
      public class Exam04_LambdaUsingConsumer {
      	public static void main(String[] args) {
      		Consumer<String> consumer = t -> {
      			System.out.println(t);
      		};
      		consumer.accept("소리 없는 아우성");
      	}
      }
      
      ```

- BiConsumer

  - 인자가 2개인 경우 

  - 2개를 소비하는 경우에 해당 Interface를 사용한다. 

    ```java
    BiConsumer<String, String> biConsumer = (a,b) -> {
        System.out.println(a+b);
    };
    biConsumer.accept("소리 없는", " 아우성");
    
    ```

- IntConsumer

  - Int Type의 인자인 경우 해당 Interface를 사용한다. 

    ```java
    IntConsumer intConsumer = i -> {
    			System.out.println(i);
    		};
    intConsumer.accept(100);
    
    ```

- ObjIntConsumer

  - 객체와 Int Type의 2개의 인자인 경우 해당 Interface를 사용한다. 

    ```java
    ObjIntConsumer<String> objIntConsumer = (a,b)-> {
        System.out.println(a+b);
    };
    objIntConsumer.accept("안뇽", 100);
    
    ```

- Consumer 응용

  - printName() 을 호출하면 이름 List를 출력한다. 

  - 람다 객체를 인자로 제공한다. 

    - Consumer가 해야하는지 코드를 넘겨준다.

    - 해당 Method를 호출하면서 값이 아닌 코드를 전달한다.

    - 실제로는 람다 객체가 넘어간다. 

      ```java
      public class Exam04_LambdaUsingConsumer {
      
          public static List<String> names = Arrays.asList("홍길동", "김길동", "최길동", "박길동");
      
          // Method를 하나 정의하는데, Instance를 만들지 않고 바로 가져다 쓰기 위해 Static으로 정의랍니다. (편하게 사용하려구)
          public static void printName(Consumer<String> consumer) {
              for (String name : names) {
                  //			System.out.println(name);
                  consumer.accept(name);
              }
          }
      
          public static void main(String[] args) {
      
              // 값이 아닌 코드를 넘겨준다.
              printName(t->{System.out.println(t);});
      
          }
      }
      
      
      ```

      



### Exam05_LambdaUsingSupplier

#### Supplier

- Supplier 

  - 값을 채워 준다. 

  - Supplier  응용

    - 친구 목록 중에 Random으로 한명의 친구를 출력

      ```java
      public class Exam05_LambdaUsingSupplier {
      	
      	public static void main(String[] args) {
      		final List<String> myBuddy = Arrays.asList("홍길동", "김길동", "이순신", "강감찬");
      		
      		// Supplier를 이용해서 Random으로 한명의 친구를 출력해 보아요 
      		// Math.random() : 0이상 1미만의 실수
      		Supplier<String> supplier = () -> {
      			// 0이상 4미만의 정수 : 0,1,2,3
      			return myBuddy.get((int)(Math.random() * 4));
      		};
      		supplier.get();
      	}
      }
      ```

- IntSupplier

  - 정수값을 1개 return 하는 경우

  - IntSupplier 응용

    - 로또 번호 (1~46) 를 자동으로 생성하고 출력

      - Supplier 사용

        ```java
        public class Exam05_LambdaUsingSupplier {
        
        	// 로또 번호 (1~46) 를 자동으로 생성하고 출력하는 간단한 Method를 작성
        	public static void generatorLotto(Supplier<String> supplier, Consumer<String> consumer) {
        		consumer.accept(supplier.get());
        	}
        	
        	public static void main(String[] args) {
        
                // 로또 번호를 자동으로 생성하고 출력하는 간단한 Method를 작성
        		// generatorLotto(Supplier, Consumer);
        		List<String> lotto = new ArrayList<String>();
        		Supplier<String> lSupplier = () -> {
        			for (int i=0; i<6; i++) {
        				String l = String.valueOf(((int)(Math.random()*45))+1);
        				System.out.println(l);
        				lotto.add(l);		
        			}
        			return lotto.toString();
        		};
        
        		generatorLotto(lSupplier, t->{
        			System.out.println(t);
        		});
        	}
        }
        
        ```

      - IntSupplier 사용 1

        ```java
        public class Exam05_LambdaUsingSupplier {
        
        	// 로또 번호 (1~46) 를 자동으로 생성하고 출력하는 간단한 Method를 작성
        	public static void generatorLotto(IntSupplier supplier, Consumer<String> consumer) {
        		List<Integer> lotto = new ArrayList<Integer>();
        		for (int i = 0; i < 6; i++) {
        			lotto.add(supplier.getAsInt());
        		}
        		
        		consumer.accept(lotto.toString());
        	}
        	
        	public static void main(String[] args) {
        		final List<String> myBuddy = Arrays.asList("홍길동", "김길동", "이순신", "강감찬");
        		
        		// Supplier를 이용해서 Random으로 한명의 친구를 출력해 보아요 
        		// Math.random() : 0이상 1미만의 실수
        		Supplier<String> supplier = () -> {
        			// 0이상 4미만의 정수 : 0,1,2,3
        			return myBuddy.get((int)(Math.random() * 4));
        		};
        		System.out.println(supplier.get());
        		
        		// IntSupplier : 정수값을 1개 return 하는 Supplier
        		// 로또 번호를 자동으로 생성하고 출력하는 간단한 Method를 작성
        		// generatorLotto(Supplier, Consumer);
        		IntSupplier lSupplier = () -> {
        			int l = ((int)(Math.random()*45))+1;
        			return l;
        		};
        
        		generatorLotto(lSupplier, t->{
        			System.out.println(t);
        		});
        
        	}
        
        }
        
        ```

      - IntSupplier 사용 2

        ```java
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
        ```

        
