## 문자열
- 문자열은 불변(immutable)값으로 생성
    - 참조되고 있는 메모리가 변경될 수 없다.
- 문자열에서 특정 범위의 문자열 추출
    - `substring()`, `subsequence()`

        ```kotlin
        String.substring(인덱스 범위 지정): String
        CharSequence.subSequence(인덱스 범위 지정): CharSequence

        s = "abcdef"
        println(s.substring(0..2)) // 인덱스 0~2범위의 abc 반환
        ```

- 문자열의 비교
    - `a.compareTo(b)`를 사용한 비교
        - a와 b가 같다면 0을 반환하고, a가 b보다 작으면 양수, 그렇지 않으면 음수

            ```kotlin
            var s1 = "Hello Kotlin"
            var s2 = "Hello KOTLIN"
            // 같으면 0, s1<s2 이면 양수, 반대면 음수 반환
            println(s1.compareTo(s2))
            println(s1.compareTo(s2, true)) // 대소문자 무시

            ```

- `StringBuilder`
    - 문자열이 자주 변경되는 경우에 사용
    - 단 기존의 문자열보다는 처리가 좀 느리고, 단어를 변경하지 않는 경우 불필요한 메모리 낭비
- 기타 문자열 처리
    - 소문자/대문자 변경
        - `toLowerCase()`, `toUpperCase()`
    - 특정 문자 단위로 잘라내기
        - `split("분리문자")` - 분리된 내용은 `List`로 반환
    - 앞뒤 공백 제거
        - `trim()`
- 이스케이프(Escape) 문자
    - `\t` , `\b`, `\n` 등등
- 3중 따옴표 부호(`"""`)의 이용 - 원본 문자열 그대로 나타냄

    ```kotlin
    val text = """
        |Tell me and I forget.
        |Teach me and I remember.
        |Involve me and I learn.
        |(Benjamin Franklin)
        """.trimMargin() //trim default는 |
    ```

- `format()`를 사용한 형식문자

    ```kotlin
    val pi = 3.1415926
    val dec = 10
    val s = "hello"
    println("pi = %.2f, %3d, %s".format(pi,dec,s))
    ```