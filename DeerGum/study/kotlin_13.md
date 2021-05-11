## 컬렉션
- Collection
    - 자주 사용되는 기초적인 자료구조를 모아놓은 일종의 프레임워크로 표준 라이브러리로 제공
- 코틀린의 Collection
    - 컬렉션의 종류로는 List, Set, Map 등이 있으며 자바와는 다르게 불변형(`immutable`)과 가변형(`mutable`)으로 나뉘어 컬렉션을 다룰 수 있다.

    ![https://user-images.githubusercontent.com/47655983/114827382-9366a400-9e03-11eb-959b-81cfb5302344.png](https://user-images.githubusercontent.com/47655983/114827382-9366a400-9e03-11eb-959b-81cfb5302344.png)

- Collection 인터페이스의 특징
    - Iterable로부터 확장
    - 불변형이므로 Collection으로부터 확장된 SEt과 List는 읽기 전용의 컬렉션됨
    - Collection 인터페이스의 멤버
        - `size` - 컬렉션의 크기를 나타냄
        - `isEmpty()` - 컬렉션이 비어있는 경우 `true` 반환
        - `contains(element: E)` - 특정 요소가 있다면 `true`를 반환
        - `containAll(element: Collection<E>)` - 인자로 받아들인 Collection이 있다면 `true`를 반환
- MutableCollection 인터페이스의 특징
    - 가변형 컬렉션을 지원하기 위해 준비된 인터페이스
    - 요소를 추가하거나 제거하는 등의 기능을 수행할 수 있음
    - MutableCollection의 멤버
        - `add(element: E)` - 인자로 전달받은 요소를 추가하고 `true`를 반환하며, 이미 요소가 있거나 중복이 허용되지 않으면 `false`를 반환
        - `remove(element: E)` 인자로 받은 요소를 삭제하고 `true`를 반환하며, 삭제하려는 요소가 없다면 `false`를 반환
        - `addAll(elements: Collection<E>)` - 컬렉션을 인자로 전달받아 모든 요소를 추가하고 `true`를 반환하며, 실패 시 `false`를 반환
        - `removeAll(elements: Collection<E>)` - 컬렉션을 인자로 전달받아 모든 요소를 추가하고 `true`를 반환하며, 실패 시 `false`를 반환
        - `retainAll(elements: Collection<E>)` - 인자로 전달받은 컬렉션의 요소만 보유한다. 성공 시 `true`를 반환하고, 실패시 `false`를 반환
        - `clear()` - 컬렉션의 모든 요소를 삭제
- List
    - 순서에 따라 정렬된 요소를 가지는 컬렉션
    - 불변형 List를 만들기 위해 헬퍼 함수인 `listOf()` 사용
    - 가변형을 표현하기 위해서는 `mutableListOf()` 사용
    - 인자는 원하는 만큼의 가변 이자를 가지도록 `vararg`로 선언 가능
    - `for`와 `.indices` 멤버를 통한 접근

        ```kotlin
        val fruits = listOf("apple", "banana", "kiwi")
        for(index in fruits.indices)
            println("fruits[$index] = ${fruits[index]}")
        ```

    - 기타 List 생성 함수
        - `emptyList()` - 빈 리스트 생성
        - `listOfNotNull()` - null을 제외한 요소만 반환
    - 추가 멤버 메서드들
        - `get(index: Int)` - 특정 인덱스를 인자로 받아 해당 요소 반환
        - `indexOf(element: E)` - 인자로 받은 요소가 첫 번째로 나타나는 인덱스를 반환하며 없으면 -1 반환
        - `lastIndexOf(element: E)` - 인자로 받은 요소가 마지막으로 나타나는 인덱스를 반환하고, 없으면 -1 반환
        - `listIterator()` - 목록에 있는 iterator를 반환
        - `subList(fromIndex: Int, toIndex: Int)` - 특정 인덱스의 from과 to 범위에 있는 요소 목록을 반환
    - 가변형 List 생성
        - `arrayListOf()`
            - 가변형 헬퍼 함수를 사용하면 손쉽게 요소를 추가하거나 삭제할 수 있다.
            - `arrayListOf()`는 가변형 List를 생성하지만 반환자료형은 자바의 `ArrayList`
        - `mutableListOf()`
            - 요소의 추가, 삭제 또는 교체를 위해 mutableListOf()를 통해 생성
            - MutableList 형으로 반환
        - `toMutableList()`
            - 불변형 리스트를 가변형 리스트로 변환
    - List와 배열의 차이
        - `Array` 클래스에 의해 생성되는 객체는 내부 구조상 고정된 크기
        - 코틀린의 `List<T>`와 `MutableList<T>`는 인터페이스로 설계되어 있고 이것을 하위에서 특정한 자료구조로 구현
            - `LinkedList<T>`, `ArrayList<T>`
            - 고정된 크기의 메모리가 아니기 때문에 자료구조에 따라 늘리거나 줄이는 것이 가능
        - `Array<T>`는 제네릭 관점에서 상/하위 자료형 관계가 성립하지 않는 무변성
        - `List<T>`는 공변성이기 때문에 하위인 `List<Int>`가 `List<Number>`에 지정될 수 있음
- Set
    - 정해진 순서가 없는 요소들의 집합
    - 집합의 개념이기 때문에 동일한 요소를 중복해서 가질 수 없다
    - 생성 헬퍼 함수
        - 불변형 `Set` : `setOf()`
        - 가변형 `Set` : `mutableSetOf()`
    - `hashSetOf()`
        - 해시 테이블에 요소를 저장할 수 있는 자바의 `HashSet` 컬렉션을 만듬
        - `HashSet`은 불변성 선언이 없기 때문에 추가 및 삭제 등의 기능을 수행할 수 있음
    - 해시 테이블(Hash Table)
        - 해시 테이블이란 내부적으로 키와 인덱스를 이용해 검색과 변경 등을 매우 빠르게 처리할 수 있는 자료구조
    - 자바의 TreeSet 컬렉션
        - `sortedSetOf()`
            - 자바의 `TreeSet` 컬렉션을 정렬된 상태로 반환
            - `java.util.*` 패키지를 임포트 해야 한다.
            - `TreeSet`은 저장된 데이터의 값에 따라 정렬
                - 이진 탐색 트리(binary-search tree)인 RB(red-black) 트리 알고리즘 사용
            - `HashSet`보다 성능이 좀 떨어지고 데이터를 추가하거나 삭제하는데 시간이 걸리지만 검색과 정렬이 더 뛰어나다는 장점
        - 이진 탐색 트리와 RB 트리
            - 이진 탐색 트리가 한쪽으로 치우친 트리 구조를 가지게 되는 경우 트리 높이만큼 시간이 걸리게 되는 최악의 경우 시간이 만들어 질 수 있다.
            - RB 트리는 이 단점을 Red와 Black의 색상으로 치우친 결과 없이 구분되도록  해 최악의 경우에도 검색 등의 처리에서 일정 시간을 보장하는 자료구조
    - 자바의 LinkedHashSet
        - `linkedSetOf()`
            - 자바의 `LinkedHashSet` 자료형을 반환하는 헬퍼 함수
            - 자료구조고 중 하나인 링크트 리스트를 사용해 구현된 해시 테이블에 요소를 저장
            - `HashSet`, `TreeSet`보다 느리지만 데이터 구조상 다음 데이터를 가리키는 포인터 연결을 통해 메모리 저장 공간을 좀 더 효율적으로 사용
- Map
    - 키(`key`)와 값(`value`)으로 구성된 요소를 저장
        - 키와 값은 모두 객체
    - 키는 중복될 수 없지만 값을 중복 저장될 수 있다.
    - `Map`의 생성
        - 불변형 : `mapOf()`

            ```kotlin
            val map: Map<키자료형, 값자료형> = mapOf(키 to 값, ...)
            ```

        - 가변형 : `mutableMapOf()`
    - `Map`의 멤버
        - `size` - 맵 컬렉션의 크기 반환
        - `keys` - 이 프로퍼티는 `Set`의 모든 키를 반환
        - `values` - 이프로퍼티는 `Set`의 모든 값을 반환
        - `isEmpty()` - 맵이 비어있으면 `true`, 아니면 `false` 반환
        - `containsKey(key: K)` - 인자에 해당하는 키가 있다면 `true`를, 아니면 `false`를 반환
        - `containsValue(value: V)` - 인자에 해당하는 값이 있다면 `true`를, 아니면 `false`를 반환
        - `get(key: K)` - 키에 해당하는 값을 반환하며, 없으면 `null`을 반환
    - `MutableMap`의 추가 멤버
        - `put(key: K, value: V)` - 키와 값의 쌍을 맵에 추가한다.
        - `remove(key: K)` - 키에 해당하는 요소를 맵에서 제거한다.
        - `putAll(from: Map<out K, V>` - 인자로 주어진 `Map` 데이터를 갱신하거나 추가한다.
        - `clear()` - 모든 요소를 지운다.
    - `Map`을 위한 기타 자료구조
        - 특정 자료구조 이용
            - `Map`에서도 자바의 `HashMap`, `SortedMap`과 `LinkedHashMap`을 사용할 수 있다.
            - `hashMapOf()`, `sortedMapOf()`, `linkedMapOf()`로 각각 초기화
            - `SortedMap`의 경우 기본적으로 키에 대해 오름차순 정렬된 형태로 사용
            - 내부 구조는 앞서 설명했던 `Set`의 구조와 비슷하게 해시, 트리, 링크드 리스트의 자료구조로 구현
- 컬렉션의 확장 함수
    - 코틀린은 컬렉션을 위한 많은 확장 함수를 제공
    - 확장 함수 범주
        - 연산자(operators) 기능의 메서드: 더하고 빼는 등의 기능
        - 집계(aggregators) 기능의 메서드: 최대, 최소, 집합, 총합 등의 계산 기능
        - 검사(checks) 기능의 메서드: 요소를 검사하고 순환하기 위한 기능
        - 필터(filtering) 기능의 메서드: 원하는 요소를 골라내기 위한 기능
        - 변환(transformers) 기능의 메서드: 뒤집기, 정렬, 자르기 등의 변환 기능
    - 요소의 처리와 집계에 대한 연산
        - 요소를 집게를 위한 확장 함수
            - `forEach`, `forEachIndexed`, `onEach`, `count`, `max`, `min`, `maxBy`, `minBy`, `fold`, `reduce`, `sumBy` 등
        - 요소의 순환
            - `forEach` : 각 요소를 람다식으로 처리한 후 컬렉션을 반환하지 않음
            - `onEach` : 각 요소를 람다식으로 처리한 후 컬렉션을 반환 받음
        - 각 요소의 정해진 식 적용
            - `fold`와 `reduce`: 초기값과 정해진 식에 따라 요소에 처리하기 위해
                - `fold`는 초기값과 정해진 식에 따라 처음 요소부터 끝 요소에 적용해 값 반환
                - `reduce`는 `fold`와 동일하지만 초기값을 사용하지 않음
            - `foldRight`, `reduceRight`: 위의 개념과 동일하지만 요소의 마지막(오른쪽) 요소에서 처음 요소로 순서대로 적용한다.
- 매핑 관련 연산
    - `map()`
        - 주어진 컬렉션의 요소를 일괄적으로 모든 요소에 식을 적용해 새로운 컬렉션을 만듦
        - `forEach`와는 다르게 주어진 컬렉션을 건드리지 않는다.
    - `flatMap()`
        - 각 요소에 식을 적용한 후 이것을 다시 하나로 합쳐 새로운 컬렉션을 반환
    - `groupBy()`
        - 주어진 식에 따라 요소를 그룹화하고 이것을 다시 `Map`으로 반환
- element관련 연산
    - 보통 인덱스와 함께 해당 요소의 값을 반환
    - 식에 따라 처리하거나 인덱스를 벗어나면 `null`을 반환
- `sequence`
    - 순차적으로 요소의 크기를 특정하지 않고 추후에 결정하는 특수한 컬렉션
    - 예를 들어 특정 파일에서 줄 단위로 읽어서 요소를 만들 때
    - 시퀀스는 처리 중에는 계산하고 있지 않다가 `toList()`나 `count()`같은 최종 연산에 의해 결정
- `asSequence()`
    - 중간 연산 결과 없이 한 번에 끝까지 연산한 후 결과를 반환
        - filter나 map을 메서드 체이닝해서 사용할 경우 순차적 연산이기 때문에 시간이 많이 걸릴 수 있지만 `asSequence()`를 사용하면 병렬 처리되기 때문에 처리 성능이 좋아짐