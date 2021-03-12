package miniProject

import java.util.Scanner

var map = Array(3) { Array(3) { " " } }
val pieces = arrayOf("O", "X")
const val playerCnt = 2

fun main() {
    val sc = Scanner(System.`in`)
    var count = 0
    while (true) {
        print("${count + 1}번째 턴\n\n")
        print("  0 1 2\n")
        print("0 ${map[0][0]}|${map[0][1]}|${map[0][2]}\n")
        print("  -+-+-\n")
        print("1 ${map[1][0]}|${map[1][1]}|${map[1][2]}\n")
        print("  -+-+-\n")
        print("2 ${map[2][0]}|${map[2][1]}|${map[2][2]}\n")

        if (isWin()) {
            print("Player ${(count - 1) % playerCnt + 1} 승리!\n")
            break;
        } else if(isDraw()){
            print("Draw\n")
            break
        }

        while (true) {
            print("Player ${count % playerCnt + 1} 입력(줄, 칸): ")
            val input = sc.nextLine().split(",")
            val x = input[0].toInt()
            val y = input[1].toInt()
            try {
                if (map[x][y] == " ") {
                    map[x][y] = pieces[count % playerCnt]
                    break
                } else
                    print("Wrong place. Please take other place.\n")
            }catch(exp: Exception){
                print("Wrong place. Please enter  the corrent place\n")
            }
        }
        count++
    }
}

fun isDraw(): Boolean {
    for(arr in map)
        for(index in 0..2)
            if(arr[index] == " ")
                return false
    return true
}

private fun isWin(): Boolean {
    for (arr in map)
        if (arr[0] != " " && (arr[0] == arr[1] && arr[1] == arr[2]))
            return true
    for (index in 0 until 3)
        if (map[0][index] != " " && (map[0][index] == map[1][index] && map[1][index] == map[2][index]))
            return true
    if (map[0][0] != " " && (map[0][0] == map[1][1] && map[1][1] == map[2][2]))
        return true
    if (map[2][2] != " " && (map[2][2] == map[1][1] && map[1][1] == map[0][0]))
        return true
    return false
}


/*
package miniProject

import java.util.Scanner

var map = Array(3) { Array(3) { " " } }
val pieces = arrayOf("O", "X")
const val playerCnt = 2

fun main() {
    initBoard()
    play()
}

fun initBoard() {
    map = Array(3) { Array(3) { " " } }
}

fun play() {
    val sc = Scanner(System.`in`)
    var count = 0
    while (true) {
        printBoard(count)

        if (isWin()) {
            print("Player ${(count - 1) % playerCnt + 1} 승리!\n")
            break;
        }

        while (true) {
            print("Player ${count % playerCnt + 1} 입력(줄, 칸): ")
            val input = sc.nextLine().split(",")
            val x = input[0].toInt()
            val y = input[1].toInt()
            if(isInRange(x, y, count))
                break;
        }
        count++
    }
}

fun isInRange(x: Int, y: Int, count: Int): Boolean {
    try {
        if (isValid(x, y)) {
            map[x][y] = pieces[count % playerCnt]
            return true
        } else
            print("Wrong place. Please take other place.\n")
    } catch (exp: Exception) {
        print("Wrong place. Please enter  the corrent place\n")
    }
    return false
}

fun isValid(x: Int, y: Int): Boolean{
    if(map[x][y] == " ")
        return true
    return false
}

fun printBoard(count: Int) {
    print("${count + 1}번째 턴\n\n")
    print("  0 1 2\n")
    print("0 ${map[0][0]}|${map[0][1]}|${map[0][2]}\n")
    print("  -+-+-\n")
    print("1 ${map[1][0]}|${map[1][1]}|${map[1][2]}\n")
    print("  -+-+-\n")
    print("2 ${map[2][0]}|${map[2][1]}|${map[2][2]}\n")
}

private fun isWin(): Boolean {
    for (arr in map)
        if (arr[0] != " " && (arr[0] == arr[1] && arr[1] == arr[2]))
            return true
    for (index in 0 until 3)
        if (map[0][index] != " " && (map[0][index] == map[1][index] && map[1][index] == map[2][index]))
            return true
    if (map[0][0] != " " && (map[0][0] == map[1][1] && map[1][1] == map[2][2]))
        return true
    if (map[2][2] != " " && (map[2][2] == map[1][1] && map[1][1] == map[0][0]))
        return true
    return false
}
 */