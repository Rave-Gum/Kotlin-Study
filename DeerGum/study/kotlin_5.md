# 프로그램 흐름 제어

## 조건문

- if ~ else문
    - 간단하면 한줄에 구성가능

    ```kotlin
    val max = if (a > b) a else b
    ```

- 범위 연산자
    - `변수명 in 시작값 .. 마지막값`
    - score in 80 .. 90이면 score 범위에 80부터 89까지 포함
- when문

    ```kotlin
    when(인자) {
        인자에 일치하는 값 혹은 표현식 -> 수행할 문장
        인자에 일치하는 범위 -> 수행할 문장
        ..
        else -> 문장
    }
    ```

    ```kotlin
    when (x) {
        1 -> print("x == 1")
        2 -> print("x == 2")
        else -> { // 블록 구문 사용 가능
            print("x는 1, 2가 아닙니다.")
        }
    }
    ```

    - 인자를 사용하는 when문
        - in 연산자와 범위 지정자 사용

        ```kotlin
        when (x) {
            in 1..10 -> print("x는 1 이상 10 이하입니다.")
            !in 10..20 -> print("x는 10 이상 20 이하의 범위에 포함되지 않습니다.")
            else -> print("x는 어떤 범위에도 없습니다.")
        }
        ```

        - is 키워드 함께 사용하기

        ```kotlin
        val str = "안녕하세요."
        val result = when(str) {
            is String -> "문자열입니다."
            else -> false
        }
        ```

        - 인수 없는 when문 사용

        ```kotlin
        when {
            score >= 90.0 -> grade = 'A'
            score in 80.0..89.9 -> grade = 'B'
            score in 70.0..79.9 -> grade = 'C'
            score < 70.0 -> grade = 'F'
        }
        ```

## 반복문

- for문
    - 선언

    ```kotlin
    for(요소 변수 in 컬렉션 혹은 범위) {
        반복할 내용
    }
    ```

    - 하행 반복  `downTo`

    ```kotlin
    for (i in 5 downTo 1) print(i) // 5 4 3 2 1
    ```

    - 필요한 단계 증가 `step`

    ```kotlin
    for (i in 1..5 step 2) print(i) //1 3 5
    ```

    - 혼합 사용

    ```kotlin
    for (i in 5 downTo 1 step 2) print(i) //5 3 1
    ```

## 흐름 제어

- 흐름 제어문
    - `return` : 함수에서 결과값 반환하거나 지정된 라벨로 이동
    - `break` : for나 while의 조건식에 상관없이 반복문을 끝냄
    - `continue` : for나 while의 반복문의 본문을 모두 수행하지 않고 다시 조건으로 넘어감
- 예외 처리문
    - `try{...} catch {...}` : try 블록의 본문을 수행하는 도중 예외가 발생하면 catch 블록의 본문을 실행
    - `try{...} catch {...} finally {...}` : 예외가 발생해도 finally 블록 본문은 항상 실행
- 람다식에서 라벨과 함께 return 사용하기
    - 선언 : `라벨이름@`
    - 사용 : `@라벨이름`

        ```kotlin
        fun inlineLambda(a: Int, b: Int, out: (Int, Int) -> Unit) { // inline이 제거됨
            out(a, b)
        }

        fun retFunc() {
            println("start of retFunc")
            inlineLambda(13, 3) lit@{ a, b ->  // 1. 람다식 블록의 시작 부분에 라벨을 지정함
                val result = a + b
                if(result > 10) return@lit // 2. 라벨을 사용한 블록의 끝부분으로 반환
                println("result: $result")
            } // 3. 이 부분으로 빠져나간다
            println("end of retFunc") //  4. 이 부분이 실행됨 
        }
        ```

    - 암묵적 라벨

        ```kotlin
        fun retFunc() {
            println("start of retFunc")
            inlineLambda(13, 3) { a, b -> 
                val result = a + b
                if(result > 10) return@inlineLambda 
                println("result: $result")
            } 
            println("end of retFunc") 
        }
        ```

    - 익명 함수의 사용 - 비지역 반환이 일어나지 않음

        ```kotlin
        fun retFunc() {
            println("start of retFunc")
            inlineLambda(13, 3, fun (a, b) { 
                val result = a + b
                if(result > 10) return 
                println("result: $result")
            }) //inlineLambda()함수의 끝
            println("end of retFunc") 
        }
        ```

    - 에러와 예외

        ```kotlin
        try {
            예외 발생 가능성 있는 문장
        } catch (e: 예외처리 클래스명) {
            예외를 처리하기 위한 문장
        } finally {
            반드시 실행되어야 하는 문장
        }
        ```

    - throw를 사용해 예외 발생시키기

    ```kotlin
    fun main() {
        var amount = 600

        try {
            aount -= 100
            checkAmount(amount)
        } catch (e : Exception) {
            println(e.message)
        }
        println("amount: $amount")
    }

    fun checkAmount(amount : Int) {
        if(amount < 1000)
            throw Exception("잔고가 $amount 으로 1000 이하입니다.")
    }
    ```