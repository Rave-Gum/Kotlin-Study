import java.util.*

fun main() {
    var arr = Array(3) { charArrayOf(' ',' ',' ') }
    var player = 1
    var win: Int
    while(true) {
        viewBoard(arr,player)
        win = isWin(arr)
        if (win == 1 || win == 2) {
            println("Player $player 승리!")
            break
        }
        else if (win == 3) {
            println("무승부!")
            break
        }

        val input = inputNum(arr,player)
        changeBoard(arr,input,player)

        player = if (player == 1) 2 else 1
    }



}

inline fun viewBoard(board: Array<CharArray>, player: Int) {
    println("${player}번째 턴")
    println("  0 1 2")
    println("0 ${board[0][0]}|${board[0][1]}|${board[0][2]}")
    println("  -+-+-")
    println("1 ${board[1][0]}|${board[1][1]}|${board[1][2]}")
    println("  -+-+-")
    println("2 ${board[2][0]}|${board[2][1]}|${board[2][2]}")
}



inline fun inputNum(board: Array<CharArray>, player: Int): IntArray {
    var r:Int
    var c:Int
    loop@ while (true) {
        print("Player $player 입력(줄,칸): ")
        val st = StringTokenizer(readLine(),",")
        r = st.nextToken().toInt()
        c = st.nextToken().toInt()

        when {
            (r !in 0..2 && c !in 0..2) ->  {
                println("인덱스를 벗어났습니다 다시 입력해주세요.")
                continue@loop
            }
            board[r][c] != ' ' -> {
                println("이미 입력된 칸입니다. 다시 입력해주세요.")
                continue@loop
            }
        }
        break
    }
    return intArrayOf(r,c)
}

fun changeBoard(board: Array<CharArray>, input: IntArray, player: Int) {
    //배열의 값 변경
    val r = input[0]
    val c = input[1]

    if (player == 1)
        board[r][c] = 'O'
    else
        board[r][c] = 'X'
}

fun isWin(board: Array<CharArray>): Int {   // 0: 게임진행 1: 플레이어1승리 2: 플레이어2승리 3: 무승부
    var prev: Char
    for (i in 0..2) {
        prev = ' '
        for (j in 0..2) {   //가로방향 확인
            if (board[i][j] == ' ')
                break
            if(j == 0) {
                prev = board[i][j]
                continue
            }
            if (prev != board[i][j])
                break

            if (j == 2) {
                return if (board[i][j] == 'O') 1 else 2
            }
        }
    }

    for (i in 0..2) {   //세로방향 확인
        prev = ' '
        for (j in 0..2) {
            if (board[j][i] == ' ')
                break
            if(j == 0) {
                prev = board[j][i]
                continue
            }
            if (prev != board[j][i])
                break
            if (j == 2) {
                return if(board[j][i] == 'O') 1 else 2
            }
        }
    }
    if (board[1][1] != ' ') {   //x자 방향 확인
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return if(board[1][1] == 'O') 1 else 2
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return if(board[1][1] == 'O') 1 else 2
        }
    }

    for (i in 0..2) {   //비어있는 칸 확인
        for (j in 0..2) {
            if (board[i][j] == ' ')
                return 0
        }
    }

    return 3
}
