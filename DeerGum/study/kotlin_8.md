## 초기화
- 지연 초기화가 필요한 이유
    - 클래스에서는 기본적으로 선언하는 프로퍼티 자료형들은 `null`을 가질 수 없음
    - 하지만, 객체의 정보가 나중에 나타는 경우 나중에 초기화 할 수 있는 방법 필요
    - 지연 초기화를 위해 lateinit과 lazy 키워드 사용
- `lateinit` 을 사용한 지연 초기화
    - 의존성이 있는 초기화나 unit 테스트를 위한 코드를 작성 시
        - 예) `Car` 클래스의 초기화 부분에 `Engine` 클래스와 의존성을 가지는 경우 `Engine` 객체가 생성되지 않으면 완전하게 초기화 할 수 없는 경우
        - 예) 단위 테스트를 위해 임시적으로 객체를 생성 시켜야 하는 경우
    - 프로퍼티 지연 초기화
        - 클래스를 선언할 때 프로퍼티 선언은 `null`을 허용하지 않는다.
        - 하지만, 지연 초기화를 위한 `lateinit` 키워드를 사용하면 프로퍼티에 값이 바로 할당되지 않아도 됨
- `lazy`를 사용한 지연 초기화
    - 특징
        - 호출 시점에 `by lazy {...}` 정의에 의해 블록 부분의 초기화를 진행한다.
        - 불변의 변수 선언인 val에서만 사용 가능하다. (읽기 전용)
        - val이므로 값을 다시 변경할 수 없다.
    - 3가지 모드 지정 가능
        - `SYNCHRONIZED` : 락을 사용해 단일 스레드만이 사용하는 것을 보장 (기본값)
        - `PUBLICATION` : 여러 군데서 호출될 수 있으나 처음 초기화된 후 반환 값을 사용
        - `NONE` : 락을 사용하지 않기 때문에 빠르지만 다중 스레드가 접근할  수 있음(값의 일관성을 보장할 수 없음)

        ```kotlin
        private val model by lazy(mode = LazyThreadSafetyMode.NONE) {
            Injector.app().transactionsModel() // 이 코드는 단일 스레드의 사용이 보장될 때
        }
        ```

- by를 이용한 위임
    - 위임(delegation)
        - 하나의 클래스가 다른 클래스에 위임하도록 선언
        - 위임된 클래스가 가지는 멤버를 참조없이 호출
        
        ```kotlin
        < val|var|class > 프로퍼티 혹은 클래스 이름: 자료형 by 위임자
        ```

        - 클래스의 위임
            - 다른 클래스의 멤버를 사용하도록 위임

            ```kotlin
            interface Animal {
                fun eat() { ... }
                ...
            }
            class Cat : Animal { }
            val cat = Cat()
            class Robot : Animal by cat // Animal의 정의된 Cat의 모든 멤버를 Robot에 위임
            ```

            - cat은 Animal 자료형의 private 멤버로 Robot 클래스 내에 저장
            - Cat에서 구현된 모든 Animal의 메소드는 정적 메소드로 생성
            - 따라서, Animal에 대한 명시적인 참조를 사용하지 않고도 eat()을 바로 호출
        - 위임을 사용하는 이유
            - 코틀린의 기본 라이브러리는 open되지 않은 최종 클래스
                - 표준 라이브러리의 무분별한 상속의 복잡한 문제들을 방지
                - 단, 상속이나 직접 클래스의 기능 확장을 하기 어렵다.
            - 위임을 사용하면?
                - 위임을 통해 상속과 비슷하게 최종 클래스의 모든 기능을 사용하면서 동시에 기능을 추가 확장 구현할 수 있다.
        - observable
            - 프로퍼티를 감시하고 있다가 특정 코드의 로직에서 변경이 일어날 때 호출
        - vetoable
            - 감시보다는 수여한다는 의미로 반환값에 따라 프로퍼티 변경을 허용하거나 취소

# 정적 변수와 컴페니언 객체
- 사용 범위에 따른 분류
    - 지역(`local`), 전역(`global`)
- 보통 클래스는 동적으로 객체를 생성하는데 정적으로 고정하는 방법은?
    - 동적인 초기화 없이 사용할 수 있는 개념으로 자바에서는 static 변수 또는 객체
    - 코틀린에서는 이것을 컴페니언 객체(`Companion object`)로 사용
    - 프로그램 실행 시 고정적으로 가지는 메모리로 객체 생성 없이 사용
    - 단, 자주 사용되지 않는 변수나 객체를 만들면 메모리 낭비
- 코틀린에서 자바의 static 멤버의 사용

```java
//자바의 Customer 클래스
public class Customer {
    public static final String LEVEL = "BASIC";
    public static void login() {
        System.out.println("Login...");
    }
}
```

```kotlin
// 코틀린에서 자바의 static 접근
fun main() {
    println(Customer.LEVEL)
    Customer.login()
}
```

- 자바에서 코틀린 컴패니언 객체 사용
    - @JvmStatic
        - 자바에서는 코틀린의 컴패니언 객체를 접근하기 위해 @JvmStatic 애노테이션(annotation) 표기법을 사용

        ```kotlin
        class KCustomer {
            companion object {
                const val LEVEL = "INTERMERDIATE"
                @JvmStatic fun login() = println("Login...") // 애노테이션 표기 사용
        ```

        ```java
        public class KCustomerAccess {
            public static void main(String[] args) {
                // 코틀린 클래스의 컴패니언 객체를 접근
                System.out.println(KCustomer.LEVEL);
                KCustomer.login(); //애노테이션을 사용할 때 접근 방법
                Kcustomer.Companion.login(); //위와 동일한 결과로 애노테이션 안 쓸 때 접근 방법
            }
        }
        ```

- 자바에서 코틀린의 최상위 함수 접근
    - 코틀린의 최상위 함수는 클래스가 없으나 자바와 연동시 내부적으로 파일명에 Kt 접미사가 붙은 클래스를 자동 생성
    - 자동 변환되는 클래스명을 명시적으로 지정하고자 하는 경우 `@file:JvmName("ClassName")`을 코드 상단에 명시한다.
- object 선언과 컴패니언 객체의 비교

```java
// (1) object 키워드를 사용한 방식
object OCustomer {
    var name = "Kildong"
    fun greeting() = println("Hello World!")
    val HOBBY = Hobby("Basketball")
    init {
        println("Init!")
    }
}

// (2) companion object를 사용한 방식
class CCustomer {
    companion object {
        const val HELLO = "hello"  // 상수 표현
        var name = "Joosol"  
        @JvmField val HOBBY = Hobby("Football")
        @JvmStatic fun greeting() = println("Hello World!")
    }
}
```

- object 표현식을 사용할 때
    - object 선언과 달리 이름이 없으며 싱글톤이 아님
    - 따라서 object 표현식이 사용될 때마다 새로운 인스턴스가 생성
    - 이름이 없는 익명 내부 클래스로 불리는 형태를 object 표현식을 만들 수 있다.