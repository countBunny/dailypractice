@file:JvmName("StringFuctions")

package cn.tianyu.kotlin_learn.section3

import cn.tianyu.dailypractice.utils.LogUtil

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

/*
    3.5.2
 */
fun parsePath(path: String){
    val directory = path.substringBeforeLast("/")
    val fullName = path.substringAfterLast("/")

    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")
    LogUtil.d("parsePath", "Dir: $directory, name: $fileName, ext: $extension")
}

fun parsePathInRegex(path: String){
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    if (matchResult != null) {
        val (directory, fileName, extension) = matchResult.destructured
        LogUtil.d("parsePath", "Dir: $directory, name: $fileName, ext: $extension")
    }
}

/*
    3.6
 */
class User(val id: Int, val name: String, val address: String)

fun saveUser(user: User) {
    user.validateBeforeSave()
}

fun User.validateBeforeSave() {
    fun validate(value:String, fieldName: String){
        if (value.isEmpty()) {
            throw IllegalArgumentException("Can't save user ${id}: empty $fieldName")
        }
    }
    validate(name, "Name")
    validate(address, "Address")
}