## 클로저
- 람다식으로 표현된 내부 함수에서 외부범위에 선언된 변수에 접근할 수 있는 개념
- 람다식 안에 있는 외부 변수는 값을 유지하기 위해 람다가 포획(capture)한 변수

    ```kotlin
    fun main() {

        val calc = Calc()
        var result = 0 // 외부의 변수
        calc.addNum(2,3) { x, y -> result = x + y }  // 클로저
        println(result) // 값을 유지하여 5가 출력
    }

    class Calc {
        fun addNum(a: Int, b: Int, add: (Int, Int) -> Unit) { // 람다식 add에는 반환값이 없음
            add(a, b)
        }
    }
    ```

- 함수의 매개변수에 접근

    ```kotlin
    // 길이가 일치하는 이름만 반환
    fun filteredNames(length: Int) {
        val names = arrayListOf("Kim", "Hong", "Go", "Hwang", "Jeon")
        val filterResult = names.filter {
            it.length == length // 바깥의 length에 접근 
        }
        println(filterResult)
    }
    ...
    filteredNames(4)
    ```

- 함수 자체를 같이 포획해 해당 매개변수에 접근함


## 코틀린 표준 라이브러리
- 람다식을 사용하는 코틀린의 표준 라이브러리에서 `let()`, `apply()`, `with()`, `also()`, `run()` 등 여러가지 표준 함수를 제공
- 표준 함수들은 대략 확장 함수 형태의 람다식으로 구성
    - (`it` → 복사본 반환, `this` → 자기 자신 반환)

    [표준함수](https://www.notion.so/8bdbc2b741db451cab3fca7a402d151e)

- `let` 동작
    - 함수를 호출하는 객체 `T`를 이어지는 `block`의 인자로 넘기고 `block`의 결과값 `R`을 반환

    ```kotlin
    public inline fun <T, R> T.let(block: (T) -> R): R { ... return block(this) }
    ```

    - 매개변수 `block`은 `T`를 매개변수로 받아 `R`을 반환
    - `let()`함수 역시 `R`을 반환
    - 본문의 `this`는 객체 `T`를 가리키는데 람다식 결과 부분을 그대로 반환한다는 뜻
    - 다른 메서드를 실행하거나 연산을 수행해야 하는 경우 사용

    ```kotlin
    fun main() {
        val score: Int? = 32
        //var socre = null

        // 일반적인 null 검사
        fun checkScore() {
            if (score != null) {
                println("Score: $score")
            }
        }

        // let을 사용해 null 검사를 제거
        fun checkScoreLet() {
            score?.let { println("Score: $it") }  // 1
            val str = score.let { it.toString() } // 2
            println(str)
        }

        checkScore()
        checkScoreLet()
    }
    ```

    ```kotlin
    fun chaining(): Int {
        var a = 1
        val b = 2

        a = a.let { it + 2 }.let {
            println("a = $a")   // "a = 1"
            val i = it + b  // i = 3 + 2
            i
        }

        return a    // 5
    }
    ```

    - 중첩 사용

    ```kotlin
    var x = "Kotlin!"
    x.let { outer ->
        outer.let { inner ->
            print("Inner is $inner and outer is $outer") // 이때는 it을 사용하지 않고 명시적 이름을 사용
        }
    }
    ```

    - 반환값은 바깥쪽의 람다식에만 적용

    ```kotlin
    var x = "Kotlin!"
    x.let { outer ->
        outer.let { inner ->
            print("Inner is $inner and outer is $outer")
            "Inner String"  //이 문자열은 반환되지 않음
        }
        "outer String"  //이 문자열이 반환되어 x에 할당
    }
    ```

    - null 검사
        - let을 세이프 콜( `?.` )과 함께 사용하면 `if (null != obj)` 와 같은 null 검사 부분을 대체

        ```kotlin
        obj?.let { // obj가 null이 아닐 경우 작업 수행 (Safe calls + let 사용)
            Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
        }
        ```

    - else문이 포함된 문장 대체

    ```kotlin
    val firstName: String?
    var lastName: String

    //if문 사용
    if (null != firstName) {
        print("$firstName $lastName")
    } else {
        print("$lastName")
    }

    // let을 사용한 경우
    firstName?.let { print("$it $lastName") } ?: print("$lastName")
    ```

- `also` 동작
    - `also()`는 함수를 호출하는 객체 `T`를 이어지는 `block`에 전달하고 객체 `T` 자체를 반환

        ```kotlin
        // 표준 함수의 정의 let과 비교
        public inline fun <T, R> T.let(block: (T) -> R): R = block(this)
        public inline fun <T> T.also(block: (T) -> Unit): T { block(this); return this}
        ```

    - `also()`는 블록 안의 코드 수행 결과와 상관없이 `T`인 바로 객체 `this`를 반환

        ```kotlin
        var m = 1
        m = m.also { it + 3 }
        println(m) //원본 값 1
        ```

    - 디렉터리 생성 활용

        ```kotlin
        //기존의 디렉터리 생성 함수
        fun makeDir(path: String): File {
            val result = File(path)
            result.mkdirs()
            return result
        }

        //let과 also를 통한 개선된 함수
        fun makeDir(path: String) = path.let{ File(it) }.also{ it.mkdirs() }
        ```

    - `let`은 식의 결과를 반환하고 그 결과를 다시 `also`를 통해 넘겨짐. 이떄 중간 결과가 아니라 넘어온 결과만 반환
- `apply` 동작
    - `apply()`함수는 `also()`함수와 마찬가지로 호출하는 객체 `T`를 이어지는 `block`으로 전달하고 객체 자체인 this를 반환

        ```kotlin
        public inline fun <T, R> T.let(block: (T) -> R): R = block(this)
        public inline fun <T> T.also(block: (T) -> Unit): T { block(this); return this}
        public inline fun <T> T.apply(block: T.() -> Unit): T { block(); return this }
        ```

    - `T.()`와 같은 표현에서 람다식이 확장 함수로서 처리

        ```kotlin
        fun main() {
            data class Person(var name: String, var skills : String)
            var person = Person("Kildong", "Kotlin")

            // 여기서 this는 person 객체를 가리킴
            person.apply { this.skills = "Swift" }
            println(person)

            val retrunObj = person.apply { 
                name = "Sean" // this는 생략할 수 있음
                skills = "Java" // this 없이 객체의 멤버에 여러 번 접근
            }
            println(person)
            println(retrunObj)
        }
        ```

        ```
        Person(name=Kildong, skills=Swift)
        Person(name=Sean, skills=Java)
        Person(name=Sean, skills=Java)
        ```

- `run` 동작
    - run()함수는 인자가 없는 익명 함수처럼 동작하는 형태와 객체에서 호출하는 형태 두 가지로 사용

        ```kotlin
        public inline fun <R> run(block: () -> R): R = return block()
        public inline fun <T, R> T.run(block: T.() -> R): R = return block()
        ```

    - 간단한 예

        ```kotlin
        var skills = "Kotlin"
        println(skills) // Kotlin

        val a = 10
        skills = run {
            val level = "Kotlin Level:" + a
            level //마지막 표현식이 반환됨
        }
        println(skills) // Kotlin Level:10
        ```

        ```kotlin
        fun main() {
            data class Person(var name: String, var skills: String)
            var person = Person("Kildong", "Kotlin")

            val returnObj = person.apply {
                this.name = "Sean"
                skills = "Java" //this 생략가능
                "success"   //사용되지 않음
            }   //returnObj : Person
            println(person)
            println("returnObj: $returnObj")

            val returnObj2 = person.run {
                this.name = "Dooly"
                skills = "C#"   //this 생략가능
                "success"
            }   //returnObj2 : String
            println(person)
            println("returnObj2: $returnObj2")
        ```

- `with` 동작
    - 인자로 받는 객체를 이어지는 `block`의 `receiver`로 전달하며 결과값을 반환
        - `run()`함수와 기능이 거의 동일한데, `run`의 경우 `receiver`가 없지만 `with()`에서는 `receiver`로 전달할 객체를 처리

    ```kotlin
    public inline fun <T, R> with(receiver: T, block: T.() -> R): R = receiver.block()
    ```

    - `with`는 세이프 콜( `?.` )은 지원하지 않기 떄문에 다음과 같이 `let`과 같이 사용(확장함수가 아닌 단독으로 사용되는 함수이기 때문)

    ```kotlin
    supportActionBar?.let {
        with(it) {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_clear_white)    
        }
    }
    ```

    - `let`과 `with` 표현 병합 - `run`과 동일

- `use` 동작
    - `use()` 를 사용하면 객체를 사용한 후 `close()`등을 자동적으로 호출해 닫아준다.

    ```kotlin
    public inline fun <T: Closeable?, R> T.use(block: (T) -> R): R 
    public inline fun <T: AutoCloseable?, R> T.use(block: (T) -> R): R
    ```

    - `T`의 제한된 자료형을 보면 `Closeable?`로 `block`은 닫힐 수 있는 개체를 지정해야 함
    - Java 7이후는 `AutoCloseable?`로 사용됨

    ```kotlin
    fun main() {
        //PrintWriter는 파일을 열거나 새로 만들어서 파일에 출력가능
        PrintWriter(FileOutputStream("d:\\test\\output.txt")).use {
            it.println("hello") // 파일에 "hello"출력 이후 use에서 파일을 자동으로 닫아줌
        }
    }
    ```

- `takeIf` `takeUnless` 활용
    - `takeIf()`함수는 람다식이 `true`이면 객체 `T`를 반환하고 그렇지 않은 경우 `null`반환, `takeUnless()`함수는 람다식이 `false`이면 `T`를 반환하고 그렇지 않은 경우 `null`반환

        ```kotlin
        public inline fun <T> T.takeIf(predicate: (T) -> Boolean): T? 
        = if (predicate(this)) this else null
        ```

        ```kotlin
        //기존 코드
        if (someObject != null && someObject.status) {
            doThis()
        }
        //개선한 코드
        if (someObject?.status == true) {
            doThis()
        }
        //takeIf를 사용해 개선한 코드
        someObject?.takeIf { it.status }?.apply { doThis() }
        ```

    - 엘비스 연산자(`?:`)를 함께 사용

        ```kotlin
        val input = "Kotlin"
        val keyword = "in"

        //입력 문자열에 키워드가 있으면 인덱스를 반환하는 함수를 takeIf를 사용하여 구현
        input.indexOf(keyword).takeIf { it >= 0 } ?: error("keyword not found")

        //takeUnless를 사용하여 구현
        input.indexOf(keyword).takeUnless { it < 0 } ?: error("keyword not found")
        ```

- 시간 측정
    - `kotlin.system` 패키지에 있는 두개의 측정 함수
        - `measureTimeMillis()`와 `measureNanoTime()`
    - 선언부

        ```kotlin
        // 코틀린 system 패키지의 Timing.kt 파일
        public inline fun measureTimeMillis(block: () -> Unit): Long {
            val start = System.currentTimeMillis()
            block()
            return System.currentTimeMillis() - start
        }

        public inline fun measureNanoTime(block: () -> Unit): Long {
            val start = System.nanoTime()
            block()
            return System.nanoTime() - start
        }
        ```

    - 시간 측정 사용방법

        ```kotlin
        val executionTime = measureTimeMillis {
            // 측정할 작업 코드
        }
        println("Execution Time = $executionTime ms")
        ```

- 난수 생성
    - 자바의 `java.util.Random`을 이용할 수도 있었지만 `JVM`에만 특화된 난수를 생성하기 떄문에 코틀린에서는 멀티플랫폼에서도 사용 가능한 `kotlin.random.Random`을 제공
    - 0부터 21사이의 난수를 제공 예

        ```kotlin
        import kotlin.random.Random
        ...
        val number = Random.nextInt(21)  // 숫자는 난수 발생 범위
        println(number)
        ```