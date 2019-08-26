# Java Network Using Java IO

- Internet : Network of Network

- IP Address

  - 숫자 (168. ......)

  - 컴퓨터를 구분하기 위한 논리적인 주소 
  - 실제로 데이터 통신을 하기 위해선 물리적인 주소 (MAC Address ,실제 주소)가 필요하다.

- DNS 

  - Domain Name System /  Domain Name Server
  - www.naver.com, www.google.com
  - 사람이 기억하기 쉬운 문자열 형태로 숫자를 변환시켜준다. 

- Protocol

  - 통신규약
  - 서로 다른 컴퓨터/ 프로그램이 데이터를 주고 받을 때 지켜야할 규약
    - 한국어라는 규칙에 맞춰 말씀하시는 강사님과 한국어를 알고 있는 우리가 규칙을 이해하는 것
  - TCP / IP , ARP, FTP, TELNET, HTTP, etc..

- Port 

  - 프로그램을 16bit의 숫자로 표현 
  - Application을 인지하기 위해 사용되는 숫자
  - 0~65535 까지 사용가능
    - 0~ 1023는 예약되어 있다.

- TCP/IP protocol

  - 네트워크에서 데이터가 전송되기 위해서는 packet이라는 단위로 쪼개져서 데이터가 전송된다.
  - 받는 측에서는 이런 packet을 합쳐서 데이터를 복원하여 사용한다.
  
- Socket

  - 데이터를 보내거나 받는 창구 역할
    - 네트워크 접속을 위한 하나의 창구 / 구멍
    - 해당 창구를 통해 데이터를 주고 받는다. 
  - Port를 이용한 데이터의 전송을 추상화 시킨 것 아하..~~~~~~~~~~ 
  - 일단 Socket을 이용하여 상대 측과 접속하게 되면, 마치 file system이나 다른 IO처럼 사용이 가능한다.

- Socket API 

  - 하위의 복잡한 프로토콜과 상관없이 좀 더 쉽고 유연하게 네트워크 프로그램을 작성한다.
  - TCP/IP protocol을 사용하는 프로그램을 쉽게 작성하기 위한 프로그래밍 API
    - Java 에서는 Socket Class

- Socket

  ![1566820550309](https://user-images.githubusercontent.com/39547788/63689651-79024000-c845-11e9-8173-215b0fb429b2.png)

  - Server

    - 클라이언트가 접속을 요청하면, 이에 대한 응답을 하는 프로세스

    - 수동적으로 누군가 접속하기를 대기함 (수동적 접속 대기)

    - Socket Class를 이용해야 네트워크 통신을 할 수 있다.

      그러나, 클라이언트와는 달리 클라이언트의 접속을 대기하고 있어야 한다. 
  
  - Client 
  
    - 능동적으로 접속함
  
  
  
  - 서버와 클라이언트의 통신 순서 
  
    ![1566821142081](https://user-images.githubusercontent.com/39547788/63689664-815a7b00-c845-11e9-89a2-73d4566b43d9.png)
  
    1. 서버 쪽에서는 클라이언트의 접속을 기다리기 위해 Server Socket 객체를 생성한 후, accept()를 호출한다. 
  
    2. 클라이언트에서는 Socket 객체를 생성하면 서버에 접속할 수 있다.
  
       - 이때, 접속이 성공하면 실제로 Socket 객체를 반환한다.
  
    3. 데이터 통신을 하기 위해 클라이언트와 서버가 각각 생성한 Socket에 대해서 Stream을 생성한다.
  
       - 이때, 서버의 OutputStream은 클라이언트의 InputStream과 연결된다.
       - 이때, 서버의 InputStream은 클라이언트의 OutputStream과 연결된다.
  
    4. 접속을 종료할 때는 Socket에 대해 close() 함수를 호출한다.
  
       - 주의 !!!! 
  
         Socket으로 부터 얻은 Stream이 존재할 때는 해당 IO 객체를 먼저 close() 해야 한다.
