package cn.tianyu.kotlin_learn.section5

import android.content.Context
import android.widget.TextView
import java.io.File

data class Person(val name: String, val age: Int)

val sum = { x: Int, y: Int -> x + y }

private fun salute() = println("Salute!")
fun invokeSalute() = run(::salute)

val createPerson = ::Person

fun File.isInsideHiddenDirectory() =
        generateSequence(this) { it.parentFile }.any { it.isHidden }

/**
 * 用 SAM 构造进行包裹
 */
fun createAllDoneRunnable(): Runnable {
    return Runnable { println("All done!") }
}

fun createViewWithCustomAttributes(context: Context) =
        TextView(context).apply {
            text = "Sample Text"
            textSize = 20.0f
            setPadding(10, 0, 0, 0)
        }

fun alphabet() = buildString {
    for (letter in 'A'..'Z'){
        append(letter)
    }
    append("\nNow I know the alphabet!")
}