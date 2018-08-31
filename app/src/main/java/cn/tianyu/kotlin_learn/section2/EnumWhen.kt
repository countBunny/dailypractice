package cn.tianyu.kotlin_learn.section2

enum class Color(
        val r: Int, val g: Int, val b: Int
) {
    RED(255, 0, 0), ORANGE(255, 165, 0),
    YELLOW(255, 255, 0), GREEN(0, 255, 0),
    BLUE(0, 0, 255), INDGO(75, 0, 130), VIOLET(238, 130, 238);

    fun rgb() = (r * 256 + g) * 256 + b;
}

fun getMnemonic(color: Color) =
        when (color) {
            Color.RED -> "Richard"
            Color.ORANGE -> "Of"
            Color.YELLOW -> "York"
            Color.GREEN -> "Gave"
            Color.BLUE -> "Battle"
            Color.INDGO -> "In"
            Color.VIOLET -> "Vain"
        }

fun getWarmth(color: Color) =
        when (color) {
            Color.RED, Color.ORANGE, Color.YELLOW -> "warm"
            Color.GREEN -> "netral"
            Color.BLUE, Color.INDGO, Color.VIOLET -> "cold"
        }

fun mix(c1: Color, c2: Color) =
        when {
            (c1 == Color.RED && c2 == Color.YELLOW) ||
                    (c2 == Color.RED && c1 == Color.YELLOW) -> Color.ORANGE
            (c1 == Color.YELLOW && c2 == Color.BLUE) ||
                    (c2 == Color.YELLOW && c1 == Color.BLUE) -> Color.GREEN
            (c1 == Color.BLUE && c2 == Color.VIOLET) ||
                    (c2 == Color.BLUE && c1 == Color.VIOLET) -> Color.INDGO
            else -> throw Exception("Dirty color")
        }

interface Expr {
    fun calc(): Int
}

class Num(val value: Int) : Expr {
    override fun calc(): Int = value
}

class Sum(val left: Expr, val right: Expr) : Expr {
    override fun calc(): Int = left.calc() + right.calc()
}

fun eval(expr: Expr) = expr.calc()

fun eval2(e: Expr): Int =
        when (e) {
            is Num -> {
                println("num: ${e.value}")
                e.value
            }
            is Sum -> eval2(e.left) + eval2(e.right)
            else ->
                throw IllegalArgumentException("Unknown expression")
        }

fun fizzBuzz(i:Int) = when{
    i% 15 == 0 -> "FizzBuzz "
    i% 3==0 -> "Fizz "
    i%5 == 0 -> "Buzz "
    else -> "$i "
}

fun isLetter(c: Char)= c in 'a'..'z'|| c in 'A'..'Z'
fun isNotDigit(c: Char) = c !in '0'..'9'