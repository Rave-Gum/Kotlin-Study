## 코루틴
- 개념
    - 스레드와 달리 코틀린으 코루틴을 통해 복잡성을 줄이고도 손쉽게 일시 중단하거나 다시 시작하는 루틴을 만들어낼 수 있다.
    - 멀티태스킹을 실현하면서 가벼운 스레드라고도 불림
    - 코루틴은 문맥 교환 없이 해당 루틴을 일시 중단(`suspended`)을 통해 제어
- `kotlinx.coroutines`의 `common` 패키지
    - `launch` / `async` - 코루틴 빌더
    - `Job` /  `Deferred` - `cancellation` 지원을 위한 기능
    - `Dispatchers` - `Default`는 백그라운드 코루틴을 위한 것이고 `Main`은 `Android`나 `Swing`, `JavaFx`를 위해 사용
    - `delay` / `yield` - 상위 레벨 지연 함수
    - `Channel` / `Mutex` - 통신과 동기화를 위한 기능
    - `coroutineScope` / `supervisorScope` - 범위 빌더
    - `select` - 표현식 지원
- `launch`
    - 일단 실행하고 잊어버리는 형태의 코루틴으로 메인 프로그램과 독립되 실행할 수 있다.
    - 기본적으로 즉시 실행하며 블록 내의 실행 결과는 반환하지 않음
    - 상위 코드를 블록 시키지 않고(`NonBlocking`) 관리를 위한 Job 객체를 즉시 반환
    - `join`을 통해 상위 코드가 종료되지 않고 완료를 기다리게 할 수 있다.
- `async`
    - 비동기 호출을 위해 만든 코루틴으로 결과나 예외를 반환
    - 실행 결과는 `Deffered<T>`를 통해 반환하며 `await`을 통해 받을 수 있다.
    - `await`은 작업이 완료될 때까지 기다리게 한다.
- `delay()`의 선언부

    ```kotlin
    public suspend fun delay(timeMillis: kotlin.Long): kotlin.Unit { /* comiled code */ }
    ```

    - `suspend` 함수를 코루틴 블록 외에 사용하면 오류가 남
- 사용자 함수에 `suspend` 적용

    ```kotlin
    suspend fun doSomething() {
        println("Do something!")
    }
    ```

    - 컴파일러는 `susped` 가 붙은 함수를 자동적으로 추출해 `Continuation` 클래스로부터 분리된 루틴을 만든다.
    - 이러한 함수를 사용하기 위해 코루틴 빌더인 `launch`와 `async`에서 이용할 수 있다.
- `Job`
    - 코루틴의 생명주기를 관리하며 생성된 코루틴 작업들은 부모-자식과 같은 관계를 가질 수 있다.
- `Job` 규칙
    - 부모가 취소되거나 실행 실패하면 그 하위 자식들은 모두 취소된다.
    - 자식의 실패는 그 부모에 전달되며 부모 또한 실패한다.(다른 모든 자식도 취소 됨)
- `SupervisorJob`
    - 자식의 실패가 그 부모나 다른 자식에 전달되지 않으므로 실행을 유지할 수 있다.
- `join()` 결과 기다리기
    - `Job` 객체의 `join()`을 사용해 완료를 기다릴 수 있다.
        - `launch`에서 반환 값을 받으면 `Job` 객체가 되기 때문에 이것을 이용해 `main` 메서드에서 `join()`을 호출할 수 있다.
- 중단 (코루틴 코드 내에서)
    - `delay(시간값)` - 일정 시간을 지연하며 중단
    - `yield()` - 특정 값을 산출하기 위해 중단
- 취소 (코루틴 외부에서)
    - `Job.cancel()` - 지정된 코루틴 작업을 즉시 취소
    - `Job.cancelAndJoin()` - 지정된 코루틴 작업을 취소(완료시까지 기다림)
    - 기본적으로 부모 자식 관계에 적용될 수 있으며 부모 블록이 취소되면 모든 자식 코루틴이 취소된다.
- 동시성 처리를 위한 `async` 코루틴
    - `launch`와 다른 점은 `Deffered<T>`를 통해 결과값을 반환
    - 지연된 결과 값을 받기 위해 `await()`를 사용
- 코루틴 문맥
    - `Coroutine Context`
        - 코루틴을 실행하기 위한 다양한 설정값을 가진 관리 정보
            - 코루틴 이름, 디스패처, 작업 상세사항, 예외 핸들러 등
        - 디스패처는 코루틴 문맥을 보고 어떤 스레드에서 실행되고 있는 식별이 가능해진다.
        - 코루틴 문맥은 + 연산을 통해 조합될 수 있다.
    - `CoroutineName`
        - 코루틴에 이름을 주며 디버깅을 위해서 사용됨
    - `Job`
        - 작업 객체를 지정할 수 있으며 취소가능 여부에 따라 `SupervisorJob()`사용
    - `CoroutineDispatcher`
        - `Dispatchers.Default`, ... `IO`, 등을 지정할 수 있으며 필요에 따라 스레드 생성가능
    - `CoroutineExceptionHandler`
        - 코루틴 문맥을 위한 예외처리를 담당하며 코루틴에서 예외가 던져지면 처리한다.
        - 예외가 발생한 코루틴은 상위 코루틴에 전달되어 처리될 수 있다.
            - 스코프를 가지는 경우 예외 에러를 잡아서 처리할 수 있다.
        - 만일 예외처리가 자식에만 있고 부모에 없는 경우 부모에도 예외가 전달되므로 주의
            - 이 경우 앱이 깨지게(`crash`) 됨
        - 예외가 다중으로 발생하면 최초 하나만 처리하고 나머지는 무시됨
    - 코루틴의 스코프
        - `GlobalScope`
            - 독립형코루틴을 구성, 생명주기는 프로그램 전체에 해당하는 범위를 가지며 main의 생명주기가 끝나면 같이 종료됨
            - `Dispatchers.Unconfined`와 함께 작업이 서로 무관한 전역 범위 실행
            - 보통 `GlobalScope` 상에서는 `launch`나 `async` 사용이 권장되지 않음
        - `CoroutineScope`
            - 특정 목적의 디스패처를 지정한 범위를 블록으로 구성할 수 있다.
            - 모든 코루틴 빌더는 `CoroutineScope`의 인스턴스를 갖는다.
            - `launch {..}`와 같이 인자가 없는 경우에는 `CoroutineScope`에서 상위의 문맥이 상속되어 결정
            - `launch(Dispatchers.옵션인자) {...}`와 같이 디스패처의 스케줄러를 지정가능
                - `Dispatchers.Default`는 `GlobalScope`에서 실행되는 문맥과 동일하게 사용
    - 스레드풀(thread pool)의 사용
        - 보통 `CommonPool`이 지정되어 코루틴이 사용할 스레드의 공동 풀을 사용
        - 이미 초기화되어 있는 스레드 중 하나 혹은 그 이상이 선택되며 초기화하기 때문에 스레드를 생성하는 오버헤드가 없어 빠름
        - 하나의 스레드에 다수의 코루틴이 동작할 수 있다.
    - 빌더의 특정 속성 지정
        - 시작 시점에 대한 속성 - `launch`의 원형

        ```kotlin
        public fun launch(
            context: CoruoutineContext,
            start: CoroutineStart,
            parent: Job?,
            onCompletion: CompletionHandler?,
            block: suspend CoroutineScope.() -> Unit): Job {
            ...
            })
        ```

        - `CoroutineStart`
            - `DEFAULT` : 즉시 시작 (해당 문맥에 따라 즉시 스케줄링됨)
            - `LAZY` : 코루틴을 느리게 시작 (처음에는 중단된 상태이며 `start()`나 `await()` 등으로 시작)
            - `ATOMIC` : 원자적으로 즉시 시작(`DEFAULT`와 비슷하나 코루틴을 실행전에는 취소 불가)
            - `UNDISPATCHED` : 현재 스레드에서 즉시 시작(첫 지연함수까지, 이후 재개시 디스패치됨)
        - `start()` 혹은 `await()` 가 호출될 때 실제로 시작
            - `launch`나 `async`는 즉시 실행되지만 `start` 옵션에 따라 실행시점을 늦출 수 있다.

            ```kotlin
            val job = async(start = CoroutineStart.LAZY) { doWork1() }
            ...
            job.start() //실제 시작 시점으로 또는 job.await()으로 시작됨
            ```

            - 안드로이드의 버튼을 통한 시작 예

            ```kotlin
            val someJob = launch(start = CoroutineStart.LAZY) {
                // some code
            }
            ...
            someButton.setOnClickListener {
                someJob.start()
            }
            ```

        - `runBlocking`의 사용
            - 새로운 코루틴을 실행하고 완료되기 전까지는 현재(`caller`) 스레드를 블로킹
            - 코루틴 빌더와 마찬가지로 `CoroutineScope`의 인스턴스를 가짐

                ```kotlin
                fun <T> runBlocking(
                    context: CoroutineContext = EmptyCoroutineContext,
                    block: suspend CoroutineScope.() -> T
                ): T(source)
                ```

            - `main()`을 블로킹 모드로 동작

                ```kotlin
                import kotlinx.coroutines.*

                fun main() = runBlocking<Unit> { //메인 메서드가 코루틴 환경에서 실행
                    launch { // 백그라운드로 코루틴 실행
                        delay(1000L)
                        println("World!")
                    }
                    println("Hello")  //즉시 이어서 실행됨
                    //delay(2000L)    //delay()를 사용하지 않아도 코루틴을 기다림
                }
                ```

            - `runBlocking()`을 클래스 내의 멤버 메서드에서 사용할 때

                ```kotlin
                class MyTest {
                    ...
                    fun mySuspendMethod() = runBlocking<Unit> {
                        //코드
                    }
                }
                ```

            - 특정 디스패처 옵션을 주어줄 때

                ```kotlin
                runBlocking(Dispatchers.IO) {
                    launch {
                        repeat(5) {
                            println("counting ${it + 1}")
                            delay(1000)
                        }
                    }
                }
                ```

        - 특정 문맥과 함께 실행
            - `withContext()`
                - 인자로 코루틴 문맥을 지정하며 해당 문맥에 따라 코드 블록을 실행
                - 해당 코드 블록은 다른 스레드에서 수행되며 결과를 반환 한다.
                - 부모 스레드는 블록하지 않는다.

                ```kotlin
                resultTwo = withContext(Dispatchers.IO) { funtion2() }
                ```

            - 완료보장
                - `withContext(NonCancellable) {...}`
                    - `try { ... } finally { ... }`에서 `finally` 블록의 실행을 보장하기 위해 취소불가 블록 구성
    - 스코프 빌더(`Scope Builder`)
        - `coroutineScope` 빌더
            - 자신만의 코루틴 스코프를 선언하고 생성할 수 있다.
            - 모든 자식이 완료되기 전까지는 생성된 코루틴 스코프는 종료되지 않는다.
            - `runBlocking`과 유사하지만 `runBlocking`은 단순 함수로 현재 스레드를 블록킹, `coroutineScope`는 단순히 지연(`suspend`)함수 형태로 넌블로킹으로 사용됨
            - 만일 자식 코루틴이 실패하면 이 스코프도 실패하고 남은 모든 자식은 취소된다. (`supervisorScope`는 실패하지 않음)
        - `supervisorScope` 빌더
            - 마찬가지로 코루틴 스코프를 생성하며 이 때 `SupervisorJob`과 함께 생성하여 기존 문맥의 `Job`을 오버라이드 한다.
                - `launch`를 사용해 생성한 작업의 실패는 `CoroutineExceptionHandler`를 통해 핸들링
                - `async`를 사용해 생성한 작업의 실패는 `Deferred.await`의 결과에 따라 핸들링
                - `parent`를 통해 부모작업이 지정되면 자식작업이 되며 이 때 부모에 따라 취소여부 결정
            - 자식이 실패하더라도 이 스코프는 영향을 받지 않으므로 실패하지 않는다.
                - 실패를 핸들링하는 정책을 구현할 수 있다.
            - 예외나 의도적인 취소에 의해 이 스코프의 자식들을 취소하지만 부모의 작업은 취소하지 않는다.
    - 부모와 자식 코루틴과의 관계
        - 병렬 분해(`Parallel decomposition`)

            ```kotlin
            suspend fun loadAndCombine(name1: String, name2: String): Image {
                val deferred1 = async { loadImage(name1) }
                val deferred2 = async { loadImage(name2) }
                return combineImages(deferred1.await(), deferred2.await())
            }
            ```

            - `loadImage(name2)`는 여전히 진행된다.
            - 코루틴 문맥에서 실행하여 자식 코루틴으로 구성한다면 예외를 부모에 전달하고 모든 자식 코루틴을 취소할 수 있다.

            ```kotlin
            suspend fun loadAndCombine(name1: String, name2: String): Image =
            coroutineScope { //스코프를 주어줌으로 특정 자식 코루틴의 취소가 생기면 모든 자식을 취소하게 됨
                val deferred1 = async { loadImage(name1) }
                val deferred2 = async { loadImage(name2) }
                return combineImages(deferred1.await(), deferred2.await())
            }
            ```

    - 스코프 취소와 예외처리
        - 스코프 취소의 예

            ```kotlin
            val scope2 = CoroutineScope
            val routine1 = scope2.launch { ... }
            val routine2 = scope2.async { ... }

            scope2.cancel()
            // or
            scope2.cancelChildren()
            ```

            ```kotlin
            try {
            ...
            } catch (e: CancellationException) {
            ... 취소에 대한 예외처리...
            }
            ```

    - 코루틴의 실행 시간 지정
        - 실행 시간 제한
            - `withTimeout(시간값) { ... }` - 특정 시간값 동안만 수행하고 블록을 끝냄
                - 시간값이 되면 `TimeoutCancellationException` 예외를 발생
            - `withTimeoutOrNull(시간값) { ... }` - 동작은 위와 동일
                - 단, 예외를 발생하지 않고 `null`을 반환