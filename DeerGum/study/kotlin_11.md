## 배열
- 배열
    - 데이터를 연속적으로 나열한 형태
    - `arrayOf()` 나 `Array()` 생성자를 사용해 배열 생성
    - `arrayOfNulls()`은 빈배열
- 다양한 자료형의 혼합 가능

```kotlin
val mixArr = arrayOf(4,5,7,3,"Chike", false) // 정수, 문자열, Boolean 혼합
```

- 특정 자료형을 제한할 경우
    - `arrayOf<자료형>()` 혹은
    - 자료형이름 + `ArrayOf()`
- 연산자를 통한 접근
    - `arr.get(index) -> value = arr[index]`
    - `arr.set(index) -> arr[index] = value`
- 표현식을 통한 생성
    - `val | var 변수명 = Array(요소개수, 초기값)`
        - 초기값으로 람다식 함수로 `init: (Int) → T` 와 같이 정의
        - 예) 2씩 곱해지는 정수의 짝수 5개의 요소(0, 2, 4, 6 ,8)

        ```kotlin
        val arr3 = Array(5, {i -> i * 2})
        ```

- 많은 양의 배열 생성

    ```kotlin
    var a = arrayOfNulls <Int>(1000) //1000개의 null로 채워진 정수 배열
    var b = Array(100, {0}) // 0으로 채워진 배열
    ```

- 특정 클래스 객체로 배열 생성

    ```kotlin
    var a = Array(1000, { i -> myClass(i) })
    ```

- 배열에 요소 추가하고 잘라내기

    ```kotlin
    val arr1 = intArrayOf(1,2,3,4,5)
    val arr2 = arr1.plus(6) //하나의 요소를 추가한 새 배열 생성
    val arr3 = arr1.sliceArray(0..2) //인자에 잘라낼 인덱스의 범위를 지정
    ```

- 기타 배열 관련 API

    ```kotlin
    arr.first() //첫번쨰 요소 확인
    arr.last() //마지막 요소 확인
    arr.indexOf(3) //인덱스 3의 요소 반환
    arr.average() //배열의 평균 값
    arr.count() //요소 개수
    ```

- 존재 여부 확인

    ```kotlin
    //선언부
    operator fun <T> Array<out T>.contains(element: T): Boolean

    println(arr.contains(4)) //arr 배열에 요소 4가 포함되었는지 확인하고 true 반환
    println(4 in arr)
    ```

- 배열의 순환
    - 순환 메서드의 사용

        ```kotlin
        //forEach에 의한 요소 순환
        arr.forEach { element -> print("$element ") }

        //forEachIndexed에 의한 요소 순환
        arr.forEachIndexed({i, e -> println("arr[$i] = $e")})
        ```

        - `forEachIndexed` - 인덱스는 `i`로, 요소는 `e`로 받아 화살표 표현식 오른쪽의 구문처리
    - `Iterator`의 이용

        ```kotlin
        //Iterator를 사용한 요소 순환
        val iter: Iterator<Int> = arr.iterator()
        while(iter.hasNext()) {
            val e = iter.next()
            print("$e ")
        }
        ```

- 배열의 정렬
    - 정렬 (Sort)
        - 오름차순으로 정렬하거나 내림차순 정렬
        - Array는 기본적인 정렬 알고리즘 제공
    - 정렬된 배열을 반환
        - `sortedArray()`
        - `sortedArrayDescending()`
    - 원본 배열에 대한 정렬을 진행
        - `sort()`
        - `sortDescending()`
    - 특정 표현식에 따른 정렬 - `sortBy`

        ```kotlin
        val items = arrayOf<String>("Dog", "Cat", "Lion", "Kangaroo", "Po")
        items.sortBy { item -> item.length }
        println(Arrays.toString(items))
        ```

- 배열 필터링
    - filter() 메서드를 활용하면 원하는 데이터를 골라낼 수 있다.

        ```kotlin
        // 0보다 큰 수 골라내기
        val arr = arryOf(1, -2, 3, 4, -5, 0)
        arr.filter { e -> e > 0}.forEach { e-> print("$e ") }
        ```

    - 체이닝을 통한 필터링 예

        ```kotlin
        fun main() {
            val fruits = arrayOf("banana", "avocado", "apple", "kiwi")
            fruits
            .filter { it.startWith("a") }
            .sortedBy { it }
            .map { it.toUpperCase() }
            .forEach { println(it) }
        }
        ```

    - when 문을 사용한 요소 확인

        ```kotlin
        when {
            "apple" in fruits -> println("Apple!")
            ...
        }
        ```

    - 큰 값 작은 값 골라내기

        ```kotlin
        //지정된 필드의 가장 작은/큰 값 골라내기
        println(products.minBy { it.price })
        println(products.maxBy { it.price })
        ```

    - 배열 평탄화하기
        - `flatten()` - 다차원 배열을 단일 배열 생성