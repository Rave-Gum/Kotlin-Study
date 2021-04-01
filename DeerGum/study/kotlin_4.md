# 다양한 함수

## 익명 함수(anonymous functions)

- 함수가 이름이 없는 것

```kotlin
fun (x: Int, y: Int): Int = x + y // 함수 이름이 생략된 익명 함수
```

## 인라인(inline) 함수

- 함수가 호출되는 곳에 내용을 모두 복사
- 함수의 분기 없이 처리 → 성능 증가
- 단점 - 코드가 복사되므로 내용이 많은 함수에 사용하면 코드가 늘어남
- noinline 키워드
    - 일부 람다식 함수를 인라인 되지 않게 하기 위해 사용

    ```kotlin
    inline fun sub(out1: () -> Unit, noinline out2: () -> Unit) { ... }
    ```

## 확장 함수 (extension function)

- 클래스의 멤버 함수를 외부에서 더 추가할 수 있다.

```kotlin
fun 확장대상.함수명(매개변수, ...): 반환값 {
    ...
    return 값
}
```

```kotlin
fun main() {
    val source = "Hello World!"
    val target = "Kotlin"
    println(source.getLongString(target))
}

// String을 확장해 getLongString 추가
fun String.getLongString(target: String): String =
    if (this.length > target.length) this else target
```

- this는 확장 대상에 있던 자리의 문자열인 source 객체를 나타냄
- 기존의 표준 라이브러리를 수정하지 않고도 확장

## 중위 함수

- 중위 표현법(infix notation)
    - 클래스의 멤버 호출 시 사용하는 점( . )을 생략하고 함수 이름 뒤에 소괄호를 생략해 직관적인 이름을 사용할 수 있는 표현법
    - 중위 함수의 조건
        - 멤버 메서드 또는  확장 함수여야 합니다.
        - 하나의 매개변수를 가져야 합니다.
        - infix 키워드를 사용하여 정의합니다.

    ```kotlin
    fun main() {
        //일반 표현법
        //val multi = 3.multiply(10)

        //중위 표현법
        val multi = 3 multiply 10
        println("multi: $multi")
    }

    // Int를 확장해서 multiply() 함수가 하나 더 추가되었음
    infix fun Int.multiply(x: Int): Int { // infix로 선언되므로 중위 함수
        return this * x
    }
    ```

## 꼬리 재귀 함수

- 스택에 계속 쌓이는 방식이 함수가 계속 씌워지는 꼬리를 무는 형태
- 코틀린 고유의 tailrec 키워드를 사용해 선언
- 스택을 사용하지 않음

```kotlin
fun main() {
    val number = 5
    println("Factorial: $number -> ${factorial(number)}")
}

tailrec fun factorial(n: Int, run: Int = 1): Long {
    return if (n == 1) run.toLong() else factorial(n-1, run*n)
}
```
