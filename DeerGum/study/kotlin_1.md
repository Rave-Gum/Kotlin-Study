# 코틀린의 기본


## 코틀린의 탄생 배경
- 목표 : 풀스택 웹 개발, Android와 ISO앱, 그리고 임베디드, IOT등 모든 개발을 다양한 플랫폼에서 개발할 수 있도록 하는 것

- 특징
    1. IDE(Android Studio)로 유명한 JetBrains에서 개발하고 보급
    2. 코드가 간결하고 다재 다능하며 호환성이 높다.
    3. 문장 끝에 세미콜론은 옵션이다.
    4. Android Studio에서 안드로이드 공식 언어로 추가
    5. 변수는 Nullable(Null 사용가능)과 NotNull로 나뉘는데, 변수 선언시 '?'를 붙여 Nullable로 만들 수 있다.

- 사용 가능한 플랫폼
    1. Kotlin/JVM - 자바 가상 머신 상에서 동작하는 앱을 만들 수 있다.
    2. Kotlin/JS - 자바스크립트에 의해 브라우저에서 동작하는 앱을 만들 수 있다.
    3. Kotlin/Native - LLVM기반의 네이티브 컴파일을 지원해 여러 타깃의 앱을 만들 수 있다.

- Kotlin/Navtive에서의 타깃
    1. IOS (arm32, arm64, emulator x86_64)
    2. MacOS (x86_64)
    3. Android (arm32, arm64)
    4. Windows (mingw x86_64)
    5. Linux (x86_64, arm32, MIPS, MIPS little endian)
    6. WebAssembly (wasm32)

- Kotlin의 장점
    1. 자료형에 대한 오류를 미리 잡을 수 있는 정적 언어(Statically typed)
        - 정적 형식: 컴파일러가 타입을 검증해 준다.
    2. 널 포인터로 인한 프로그램의 중단을 예방할 수 있다.
        - 보통 개발자들은 코틀린의 이런 특징을 'NPE에서 자유롭다'라고 한다.
        - NPE는 Null Pointer Exception을 줄여 말한 것
    3. 데이터형 선언 시 널 가능한 형식과 불가능한 형식 지원
    4. 자바와 완벽하게 상호운영이 가능하다.
    5. 아주 간결하고 효율적
    6. 함수형 프로그래밍과 객체 지향 프로그래밍이 모두 가능 (멀티 패러다임 언어)
    7. 세미콜론 생략 가능


## 기존 자료형과 변수 선언방법
- 자료형
    1. 기본형 (Primitive Data Type)
        - 가공되지 않은 순수한 자료형
        - `int`, `long`, `float`, `double` 등등
    2. 참조형 (Reference Type)
        - 공적 공간에 데이터를 둔 다음 이것을 참조하는 자료형 (코틀린에서는 코딩할때 참조형만 사용함)
        - `Int`, `Long`, `Float`, `Double` 등등
    - `Int`
    - `String`
    - `Float`
    - ... 등등
- 변수
    - `val` (value) - 불변형 (immutable)
    - `var` (variable) - 가변형 (mutable)

```kotlin
val username: String = "kildong"
```

- 변수 선언
    - 자료형을 지정하지 않은 변수는 사용할 수 없음
    - val은 사용전 혹은 생성자 시점에서 초기화를 해야함
    - 변수이름은 카멜 표기법(Camel Expression)을 사용하는 것이 좋음


## Null
- 코틀린은 기본적으로 `null`을 허용하지 않음
- `null`을 사용하려면 자료형에 `?`을 붙여줘야함

```kotlin
val test: String?    //null 가능
val test2: String = "test2"    //null 불가능
```

- `?` 연산자 - nullable한 변수 뒤에 붙여주면 변수가 null일경우 뒤의 메소드를 실행하지 않음
- `!!` 연산자 - nullable한 변수 뒤에 붙여주면 null일리 없다는 가정하에 실행시켜줌
- 엘비스 연산자

```kotlin
var str1: String? = "Hello Kotlin"
str1 = null
println("str1: $str1 length: ${str1?.length ?: -1}")
```

- `null`을 최대한 피해야하는 이유
    - 사용할 수 없는 `null`인 변수에 접근하면 예외가 발생(NPE)
    - `val`선언처럼 변경 불가능한 변수를 적극 활용하는 이유 - 값을 변경하면 안되는 변수일 경우 안전해짐
    - 코틀린은 함수형 프로그래밍의 특징을 가지고 있는데 함수형 프로그래밍의 특징 중에 하나가 스레드 안전(Thread-safe)
    - 불변성은 스레드를 안전하게 만드는데 큰 역할을 함
    - 그러므로 불변성을 권유


## 기본형과 참조형 자료형의 비교 원리
- 이중 등호와 삼중 등호의 사용
    - `==` : 값만 비교
    - `===` : 값과 참조 주소를 비교 (java에선 `==`와 같음)

    ```kotlin
    val a: Int = 128
    val b: Int = 128
    println(a == b)  //true
    println(a === b) //true
    ```

    ```kotlin
    val a: Int = 128     //컴파일러를 거치면 기본형으로 변환
    val b: Int? = 128    //컴파일러를 거쳐도 참조형
    println(a == b)  //true
    println(a === b) //false 참조주소가 다름
    ```


## 스마트캐스트
- 구체적으로 명시되지 않은 자료형을 자동 변환
- `Number`형
    - 숫자를 저장하기 위한 특수한 자료형으로 스마트 캐스트 됨

    ```kotlin
    var test: Number = 12.2  //Float형으로 캐스트

    test = 12  //Int형으로 캐스트

    test = 120L  //Long형으로 캐스트

    test += 12.0f  //Float형으로 캐스트
    ```

- `is` 키워드를 사용한 검사

    ```kotlin
    val num = 256

    if (num is Int) {  // num이 Int형일 때
        print(num)
    } else if (num !is Int) {  //num이 Int형이 아닐 때, !(num is Int)와 동일
        print("Not a Int")
    }
    ```

- `Any`
    - 자료형이 정해지지 않은 경우
    - 모든 클래스의 뿌리 - `int`나 `String`은 `Any`형의 자식 클래스이다.
    - `Any`는 언제든 필요한 자료형으로 자동 변환 (스마트 캐스트)
    
    
## 비트연산자

| 비트 연산자        | 설명                                 |
|---------------|------------------------------------|
| `4.shl(bits)`   | 4를 표현하는 비트를 bits만큼 왼쪽으로 이동(부호 있음)  |
| `7.shr(bits)`   | 7을 표현하는 비트를 bits만큼 오른쪽으로 이동(부호 있음) |
| `12.ushr(bits)` | 12를 표현하는 비트를 bits만큼 오른쪽 이동(부호 없음)  |
| `9.and(bits)`   | 9를 표현하는 비트와 bits를 표현하는 비트로 논리곱 연산  |
| `4.or(bits)`    | 4를 표현하는 비트와 bits를 표현하는 비트로 논리합 연산  |
| `24.xor(bits)`  | 23를 표현하는 비트와 bits를 표현하는 비트의 배타적 연산 |
| `78.inv`        | 78을 표현하는 비트를 모두 뒤집음                |
