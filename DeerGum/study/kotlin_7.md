# 클래스와 객체
## 객체지향의 기본 용어
    - 추상화(abstraction): 특정 클래스를 만들 때 기본 형식을 규정하는 방법
    - 인스턴스(instance): 클래스로부터 객체를 생성
    - 상속(inheritance): 부모 클래스의 내용을 자식 클래스가 그대로 물려 받는 것
    - 다형성(polymorphism): 하나의 이름으로 다양한 처리를 제공하는 것
    - 캡슐화(encapsulation): 내용을 숨기고 필요한 부분만 사용
    - 메세지 전송(message sending): 객체들 간에 주고 받는 메세지
    - 연관(association): 클래스들의 관계

## 클래스와 추상화
    - 클래스(Class)
        - 분류, 계층, 등급 등의 우리말 뜻
        - 특정 대상을 분류하고 특징(속성)과 동작 활동(함수) 내용을 기록
    - 추상화(Abstraction)
        - 목표로 하는 것에 대해 필요한 만큼 속성과 동작을 정의하는 과정

## 생성자
    - 생성자
        - 클래스를 통해 객체가 만들어질 때 기본적으로 호출되는 함수
        - 객체 생성시 필요한 값을 인자로 설정할 수 있게 한다.
        - 생성자를 위해 특별한 함수인 constructor()를 정의
    - 주 생성자
        - 클래스명과 함께 기술되며 보통의 경우 constructor 키워드를 생략할 수 있다.
    - 부 생성자
        - 클래스 본문에 기술되며 하나 이상의 부 생성자를 정의할 수 있다.

## 상속
    - 상속
        - 자식 클래스를 만들 때 상위 클래스(부모 클래스)의 속성과 기능을 물려받아 계승
        - 상위(부모) 클래스의 프로퍼티와 메서드가 자식에 적용됨
    - `open` 키워드를 통한 선언 - `open`클래스가 아니면 상속 불가

    ```kotlin
    open class 기반 클래스 {  //open으로 상속 가능
        ..
    }
    class 파생 클래스 : 기반 클래스() {  //기반 클래스로부터 상속, 최종 크래스로 상속 불가
        ..
    }
    ```

## 다형성
    - 다형성
        - 같은이름을 사용하지만 구현 내용이 다르거나 매개변수가 달라서 하나의 이름으로 다양한 기능을 수행할 수 있는 개념
        - Static Polymorphism
            - 컴파일 타임에 결정되는 다형성
            - 단순하게 보면 메서드 오버로딩(overloading)을 사용할 때
        - Dynamic Polymorphism
            - 런타임 다형성
            - 동적으로 구성되는 오버라이딩(overriding)된 메서드를 사용할 때
        - 오버라이딩(overriding)
            - 기능을 완전히 다르게 바꾸어 재설계
            - 누르다 → 행위 → push()
        - 오버로딩(overloading)
            - 기능은 같지만 인자를 다르게 하여 여러 경우를 처리
            - 출력한다 → 행위 -? print()
            - print(123), print("Hello")  인자는 다르지만 출력의 기능은 동일함
        - 오버라이딩 금지
            - 파생 클래스에서 오버라이딩을 금지할 때

            ```kotlin
            open class Lark() : Bird() {
                final override fun sing() { /*구현부 새로 정의*/ } //하위 클래스에서 재정의 금지
            }
            ```

        - 현재 클래스에서 참조의 기능
            - 상위 클래스는 `super` 키워드로 현재 클래스는 `this` 키워드로 참조가 기능
        - 바깥 클래스 호출하기
            - 엣(`@`) 기호의 이용
                - 이너 클래스에서 바깥 클래스의 상위 클래스를 호출하려면 super 키워드와 함께 엣 기호 옆에 바깥 클래스명을 작성해 호출

## 정보은닉 캡슐화
    - 캡슐화(encapsulation)
        - 클래스를 작성할 때 외부에서 숨겨야 하는 속성이나 기능
    - 가시성 지시자(visibility modifiers)를 통해 외부 접근 범위를 결정할 수 있음
        - `private`: 이 지시자가 붙은 요소는 **외부에서 접근할 수 없음**
        - `public`: 이 요소는 **어디서든 접근**이 가능 (기본값)
        - `protected`: 외부에서 접근할 수 없으나 **하위 상속 요소에서는 가능**
        - `internal`: **같은 정의의 모듈 내부**에서는 접근이 가능

## 클래스와 관계
    - 관계(relationship)
        - 연관(association)

            ```kotlin
            class Pond(_name: String, _members: MutableList<Duck>) {
                val name: String = _name
                val members: MutableList<Duck> = _members
                constructor(_name: String): this(_name, mutableListOf<Duck>())
            }

            class Duck(val name: String)

            fun main() {
                // 두 개체는 서로 생명주기에 영향을 주지 않는다.
                val pond = Pond("myFavorite")
                val duck1 = Duck("Duck1")
                val duck2 = Duck("Duck2")
                // 연못에 오리를 추가 - 연못에 오리가 집합한다
                pond.members.add(duck1)
                pond.members.add(duck2)
                // 연못에 있는 오리들
                for (duck in pond.members) {
                    println(duck.name)
                }
            }
            ```

        - 의존(dependency)

            ```kotlin
            class Patient(val name: String, var id: Int) {

                fun doctorList(d: Doctor) {
                    println("Patient: $name, Doctor: ${d.name}")
                }
            }

            class Doctor(val name: String, val p: Patient) {

                val customerId: Int = p.id

                fun patientList() {
                    println("Doctor: $name, Patient: ${p.name}")
                    println("Patient Id: $customerId")
                }
            }

            fun main() {
                val patient1 = Patient("Kildong", 1234)
                val doc1 = Doctor("Kimsabu", patient1)
                doc1.patientList()
            }
            ```

        - 집합(aggregation)
        - 구성(composition)

            ```kotlin
            class Car(val name: String, val power: String) {
                private var engine = Engine(power) // Engine 클래스 객체는 Car에 의존적

                fun startEngine() = engine.start()
                fun stopEngine() = engine.stop()
            }

            class Engine(power: String) {
                fun start() = println("Engine has been started.")
                fun stop() = println("Engine has been stopped.")
            }

            fun main() {
                val car = Car("tico","100hp")
                car.startEngine()
                car.stopEngine()
            }
            ```

## 코틀린의 프로퍼티
    - 프로퍼티(Properties)
        - 자바의 필드(Fields)와 기본적으로 동일(단순한 변수 선언)
        - 변수 선언과 기본적인 접근 메서드를 모두 가지고 있음
        - 따로 접근 메서드를 만들지 않아도 내부적으로 자동 생성
    - 접근 메서드(Access methods)
        - 게터(Getter)와 세터(Setter)를 합쳐 접근 메서드라고 함
        - `value` : 세터의 매개변수로 외부로부터 값을 가져옴
        - `field` : 프로퍼티를 참조하는 변수로 보조 필드(backing field)로 불림
            - 프로퍼티를 대신할 임시 필드로 만일 프로퍼티를 직접 사용하면 게터나 세터가 무한 호출되는 재귀에 빠짐