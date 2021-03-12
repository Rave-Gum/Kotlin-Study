package miniProject

var map = Array(3) { Array(3) { " " } }
val pieces = arrayOf("O", "X")
const val playerCnt = 2

fun main() {
    var count = 0
    while (true) {
        printBoard(count)

        if (isWin()) {
            print("Player ${(count - 1) % playerCnt + 1} 승리!")
            break
        } else if (isDraw()) {
            print("Draw")
            break
        }

        while (true) {
            print("Player ${count % playerCnt + 1} 입력(줄, 칸): ")
            val input = readLine()!!.split(",")
            try {
                val x = input[0].toInt()
                val y = input[1].toInt()
                if(isValid(x, y, count))
                    break
            } catch (exp: Exception) {
                println("Wrong format. Please enter the correct input")
                println("correct input: x,y")
            }
        }
        count++
    }
}

fun printBoard(count: Int){
    println("${count + 1}번째 턴")
    println("  0 1 2")
    println("0 ${map[0][0]}|${map[0][1]}|${map[0][2]}")
    println("  -+-+-")
    println("1 ${map[1][0]}|${map[1][1]}|${map[1][2]}")
    println("  -+-+-")
    println("2 ${map[2][0]}|${map[2][1]}|${map[2][2]}")
}

fun isWin(): Boolean {
    for (arr in map)
        if (arr[0] != " " && (arr[0] == arr[1] && arr[1] == arr[2]))
            return true
    for (index in 0 until 3)
        if (map[0][index] != " " && (map[0][index] == map[1][index] && map[1][index] == map[2][index]))
            return true
    if (map[0][0] != " " && (map[0][0] == map[1][1] && map[1][1] == map[2][2]))
        return true
    if (map[0][2] != " " && (map[0][2] == map[1][1] && map[1][1] == map[2][0]))
        return true
    return false
}

fun isDraw(): Boolean {
    for (arr in map)
        for (index in 0..2)
            if (arr[index] == " ")
                return false
    return true
}

fun isValid(x: Int, y: Int, count: Int): Boolean{
    try {
        if (map[x][y] == " ") {
            map[x][y] = pieces[count % playerCnt]
            return true
        } else
            println("Wrong place. Please take other place.")
    }catch(exp: Exception){
        println("Wrong place. Please enter  the correct place")
    }
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
        printlnBoard(count)

        if (isWin()) {
            println("Player ${(count - 1) % playerCnt + 1} 승리!")
            break;
        }

        while (true) {
            println("Player ${count % playerCnt + 1} 입력(줄, 칸): ")
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
            println("Wrong place. Please take other place.")
    } catch (exp: Exception) {
        println("Wrong place. Please enter  the corrent place")
    }
    return false
}

fun isValid(x: Int, y: Int): Boolean{
    if(map[x][y] == " ")
        return true
    return false
}

fun printlnBoard(count: Int) {
    println("${count + 1}번째 턴")
    println("  0 1 2")
    println("0 ${map[0][0]}|${map[0][1]}|${map[0][2]}")
    println("  -+-+-")
    println("1 ${map[1][0]}|${map[1][1]}|${map[1][2]}")
    println("  -+-+-")
    println("2 ${map[2][0]}|${map[2][1]}|${map[2][2]}")
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