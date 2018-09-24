package cn.tianyu.kotlin_learn.section9

import java.util.*

fun test9() {
    val letters = ('a'..'z').toList()
    println(letters.slice(0..2))
    println(listOf(1, 2, 3, 4).penultimate)
    println(oneHalf(3))
    println(max("kotlin", "java"))
    val helloWorld = StringBuilder("Hello World")
    ensureTrailingPeriod(helloWorld)
    println(helloWorld)
    val nullableStringProcessor = Processor<String>()
    nullableStringProcessor.process("null")
    checkType(letters)
    printSum(listOf(1, 2, 3))
    println(isA<String>(123))
    val items = listOf("one", 2, "three")
    println(items.filterIsInstance<String>())
    val serviceImpl = loadService<Service>()
}

fun checkType(obj: Any) {
    if (obj is List<*>) {
        //是列表类型

    }
}

val <T> List<T>.penultimate: T
    get() = this[size - 2]

fun <T : Number> oneHalf(value: T): Double {
    return value.toDouble() / 2.0
}

fun <T : Comparable<T>> max(first: T, second: T): T {
    return if (first > second) first else second
}

fun <T> ensureTrailingPeriod(seq: T) where T : CharSequence, T : Appendable {
    if (!seq.endsWith('.')) {
        seq.append('.')
    }
}

class Processor<T : Any> {
    fun process(value: T) {
        value.hashCode()
    }
}

fun printSum(c: Collection<*>) {
    val intList = c as? List<Int> ?: throw IllegalArgumentException("List is expected")
    println(intList.sum())
}

inline fun <reified T> isA(value: Any) = value is T

class Service

/**
 * startActivity 可以用这种方式简化掉 ActivityImpl.class
 */
inline fun <reified T> loadService() = ServiceLoader.load(T::class.java)