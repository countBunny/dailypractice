@file:JvmName("StringFuctions")

package cn.tianyu.kotlin_learn.section3

fun <T> joinToString(collection: Collection<T>,
                     separator: String = ", ",
                     prefix: String = "",
                     postfix: String = ""): String {
    val result = StringBuilder(prefix)
    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

fun <T> Collection<T>.joinToString2(
        separator: String = ", ",
        prefix: String = "",
        postfix: String = ""): String {
    val result = StringBuilder(prefix)
    for ((index, element) in withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

val String.lastChar: Char
    get() = get(length - 1)

var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value) {
        setCharAt(length -1, value)
    }