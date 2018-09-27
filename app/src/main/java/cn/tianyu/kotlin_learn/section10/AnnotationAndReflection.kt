package cn.tianyu.kotlin_learn.section10

import ru.yole.jkid.serialization.serialize

fun test10(){
    val person = Person("Alice", 29)
    println(serialize(person))

}

@Deprecated("Use removeAt(index) instead.", ReplaceWith("removeAt(index)"))
fun remove(index: Int) {
}

data class Person(val name: String, val age: Int)