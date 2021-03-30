# 람다식

## 변수에 지정된 람다식

```kotlin
//일반 변수에 람다식 할당
val multi: (a: Int, b: Int) -> Int = {a, b -> a * b}

result = multi(10,20)
```

- 표현식이 2줄 이상일 때 - 마지막 표현식이 반환됨

    ```kotlin
    //일반 변수에 람다식 할당
    val multi2: (a: Int, b: Int) -> Int = {a, b -> 
        println("a * b")
        a * b //마지막 식이 반환됨 (return) 반환값 없으면 Unit으로 설정해야 함
    }
    ```

- 자표현의 생략

    ```kotlin
    val multi: (Int, Int) -> Int = {x: Int, y: Int -> x * y} //생략되지 않은 전체 표현
    val multi = {x: Int, y: Int -> x * y} //선언 자료형 생략
    val multi: (Int, Int) -> Int = {x, y -> x * y} //람다식 매개변수 자료형의 생략
    ```

- 반환 자료형이 없거나 매개변수가 하나 있을 때

    ```kotlin
    val greet: ()->Unit = {println("Hello World!") }
    val square: (Int)->Int = { x -> x * x }
    ```

- 람다식 안에 람다식이 있는 경우

    ```kotlin
    val nestedLambda: ()->()->Unit = { { println("nested") } }
    ```

- 선언부의 자료형 생략

    ```kotlin
    val greet = { println("Hello World!") } // 추론 가능
    val square = { x: Int -> x * x } // 선언 부분을 생략하려면 x의 자료형을 명시해야 함
    val nestedLambda = { { println("nested") } } // 추론 가능
    ```

## 람다식과 고차함수 호출하기

- 값에 의한 호출
    - 함수가 인자로 전달될 경우
        - 람다식 함수는 값으로 처리되어 그 즉시 함수가 수행된 후 값을 전달

## 람다식 함수의 매개변수

- 매개변수 개수에 따라 람다식을 구성하는 방법
    - 매개변수가 없는 경우

    ```kotlin
    fun main() {
        // 매개변수 없는 람다식 함수
        noParam({"Hello World!"})
        noParam {"Hello World!"} //위와 동일 결과, 소괄호 생략 가능
    }

    // 매개변수가 없는 람다식 함수가 noParam 함수의 매개변수 out으로 지정됨
    fun noParam(out: () -> String) = println(out())
    ```

    - 매개변수가 한 개인 경우

    ```kotlin
    fun main() {
        
        oneParam( { a -> "Hello World! $a" })
        oneParam { a -> "Hello World! $a" } //위와 동일 결과, 소괄호 생략 가능
        oneParam { "Hello World! $it"} //위와 동일 결과, it으로 대체 가능
    }

    // 매개변수가 하나 있는 람다식 함수가 oneParam 함수의 매개변수 out으로 지정됨
    fun oneParam(out: (String) -> String)  {
        println(out("OneParam"))
    }
    ```

    - 매개변수가 두 개 이상인 경우

    ```kotlin
    fun main() {
        // 매개변수가 두 개 있는 람다식 함수
        moreParam { a, b -> "Hello World! $a $b" } //매개변수명 생략 불가
    }

    // 매개변수가 두개 있는 람다식 함수가 moreParam 함수의 매개변수로 지정됨
    fun moreParam(out: (String, String) -> String)  {
        println(out("OneParam", "TwoParam"))
    }
    ```

    - 매개변수를 생략하는 경우

    ```kotlin
    moreParam { _, b -> "Hello World! $b"} //첫번째 문자열은 사용하지 않고 생략
    ```

    - 일반 매개변수와 람다식 매개변수를 같이 사용

    ```kotlin
    fun main() {
        // 인자와 함께 사용하는 경우
        withArgs("Arg1", "Arg2", {a, b -> "Hello World! $a $b" })
        // withArgs()의 마지막 인자가 람다식인 경우 소괄호 바깥으로 분리 가능
        withArgs("Arg1", "Arg2") {a, b -> "Hello World! $a $b" }
    }

    // 매개변수가 두개 있는 람다식 함수가 moreParam 함수의 매개변수로 지정됨
    fun withArgs(a: String, b: String, out: (String, String) -> String)  {
        println(out(a,b))
    }
    ```