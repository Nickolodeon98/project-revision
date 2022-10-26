# project-revision

### 👉🏼 최초의 UserDao 클래스

```
☑️ JDBC 드라이버를 사용해 MySQL에 연결하고, 환경변수로 전달된 정보를 통해 데이터베이스에 접속한 뒤,

INSERT 와 SELECT 쿼리를 사용해 데이터 추가와 접근을 구현한다.
```
### 👉🏼 UserDaoFactory 적용까지의 과정 

```
☑️ 이전의 UserDao 에서는 모든 메소드에서 중복하여 Connection 객체를 생성하고 있다. 이래서는 Connection 의 상태 하나만 바꾸려고 해도,

모든 메소드를 변경해야 한다. 그래서 Connection 이라는 관심사를 인터페이스를 사용해 분리한다.

이후에는 UserDao 에서 사용되는 Connection 객체를 변경하고 싶다면, 모든 메소드 구현을 변경할 필요가 없다.

생성자에서 매개 변수로 받는 ConnectionMaker를 상속 받는 클래스만 바꾸면 된다.

이렇게 서로 다른 Connection 을 UserDao 객체가 생성될 때 결정하도록 하면,

생성된 오브젝트를 사용하는 역할만 담당해야 할 UserDaoTest 와 같은 UserDao의 클라이언트에서

UserDao 객체를 어떤 유형으로 생성할지 정해야 하는 불필요한 책임까지 떠맡게 된다.

여기서 팩토리를 도입한다. 팩토리는 객체의 생성 방법을 결정하며, 결정을 통해 만들어진 오브젝트를 돌려주는 클래스를 말한다.
```

### 👉🏼 Spring 도입까지의 과정

```
☑️ Spring을 이용해 테스트하고 데이터베이스에 접근할 것이기 때문에

build.gradle 파일에서 dependencies 에 spring-boot-starter-test와 spring-boot-starter-jdbc를 추가해주어야 한다.

UserDaoFactory 클래스에 @Configuration 어노테이션을 추가해서 UserDaoFactory가 Spring의 어플리케이션 컨텍스트를 구성한다고 선언해준다.

그리고 각 메소드에 @Bean 을 붙여서 어플리케이션 컨텍스트를 통해 UserDao 클래스를 생성할 때 접근할 수 있는 메소드라고 선언해준다.

Spring의 어플리케이션 컨텍스트를 사용할 준비가 되었다.
```
### 👉🏼 UserDao 클래스에 메소드 추가 및 각 메소드 예외 처리

```
☑️ 테스트를 하다 보니, 반복해서 테스트를 실행하면 매번 다른 id를 사용하지 않으면

id가 Primary Key로 등록되어 있기 때문에 Duplicate Entry 에러가 난다.

용이하게 테스트 하기 위해 deleteAll() 메소드를 만들어서 데이터를 삭제할 수 있는 기능을 추가한다.

또 getCount() 메소드를 만들어 deleteAll() 이 제대로 작동하고 있는지 확인할 수 있게 한다.

현재 UserDao의 메소드들은 예외 발생 시 close() 가 실행되지 않는다. 이는 나중에 서버에 과부하를 줄 수 있는 문제이다.

그래서 예외 처리를 해줄 필요가 있다. try-catch-finally문을 추가해준다.
```

### 👉🏼 전략 패턴 사용하여 쿼리 생성 부분 분리

```
☑️ 현재 UserDao 의 각 메소드들은 공통적으로 PreparedStatement 객체를 생성하여 쿼리를 작성한다.

변경되는 부분은 쿼리의 종류 뿐이다. 이 부분을 전략 패턴을 사용하여 분리할 수 있다.

PreparedStatement 객체를 리턴하는 함수를 갖는 인터페이스와 이를 상속받는 클래스 구현체들을 통해 다른 쿼리를 생성할 수 있게 한다.

이렇게 쿼리 생성 부분을 분리시키면, 다음과 같이 add() 와 deleteAll() 메소드에

공통되는 부분을 매개 변수를 통해 설정하는 하나의 메소드를 정의할 수 있다.

지금 jdbcContextWithStatementStrategy 는 UserDao 클래스 내에 있다. 하지만 다른 DAO에서도 이 메소드를 사용하고 싶을 수 있다.

쿼리문을 작성하는 메소드는 데이터베이스를 활용하는 모든 DAO에서 필요로 하기 때문이다. JdbcContext 클래스를 만들어서 기능을 분리해보자.

+ 여기서는 DataSource를 도입해서 더이상 ConnectionMaker 인터페이스를 사용하지 않는다.

 여기서 JdbcContext 클래스에 executeSql(String sql) 메소드를 추가하여
 
 매개변수로 주어진 문자열에 해당하는 쿼리를 작성하도록 변경할 수도 있다.
 
 그러면 deleteAll() 메소드는 한 줄로 줄어들게 된다.
```

### 👉🏼 JdbcTemplate 도입

```
☑️ 지금까지 여러 과정을 지나며 도달한 추상화 단계의 JdbcContext 에는 한계가 있다.

대표적으로 executeUpdate() 를 사용하는 add()와 deleteAll() 에 대해서는 분리가 되었지만,

executeQuery() 를 사용하여 ResultSet으로 결과값을 받는 findById() 와 getCount() 는 분리하지 못했다.

Spring 에서는 이미 지금까지 수동으로 구현한 JdbcContext보다 더 편리하게 사용할 수 있는 JdbcTemplate을 구현해두었다.

이제 JdbcTemplate을 적용함으로서 마무리한다.
```
