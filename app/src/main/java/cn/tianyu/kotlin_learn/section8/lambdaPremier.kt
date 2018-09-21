package cn.tianyu.kotlin_learn.section8

import java.io.BufferedReader
import java.io.FileReader
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

var canReturnNull: (Int, Int) -> Int? = { a, b -> null }
var funOrNull: ((Int, Int) -> Int)? = null

fun test8() {
    twoAndThree { a, b ->
        a + b
    }
    twoAndThree { a, b ->
        a * b
    }
    println("ab1c".filter { it in 'a'..'z' })
    //根据不同寄送方式获得不同计算函数
    val calculator = getShippingCostCalculator(Delivery.EXPEDITED)
    println("Shipping costs ${calculator(Order(3))}")

    val contacts = listOf(Person("Dmitry", "Jemerov", "123-4567"),
            Person("Svetlana", "Isakova", null))
    val contactListFilters = ContactListFilters()
    with(contactListFilters) {
        prefix = "Dm"
        onlyWithPhoneNumber = true
    }
    println(contacts.filter { contactListFilters.getPredicate()(it) })
    val averageWindowsDuration = log.filter { it.os == OS.WINDOWS }
            .map { (SiteVisit::duration)(it) }
            .average()
    println(averageWindowsDuration)
    println(log.averageDurationFor { it.os == OS.ANDROID })
    println(log.averageDurationFor({ it.os in setOf(OS.ANDROID, OS.IOS) }))
    println(log.averageDurationFor({ it.os == OS.IOS && it.path == "/signup" }))
    val l = ReentrantLock()
    synchronized(l) {
        //...
    }
    foo(l)
    l.withLock {

    }
}

fun foo(l: Lock) {
    println("Before sync")
    synchronized(l) {
        println("Action")
    }
    println("After sync")
}

fun twoAndThree(operation: (Int, Int) -> Int) {
    val result = operation(2, 3)
    println("The result is $result")
}

fun String.filter(predicate: (Char) -> Boolean): String {
    val sb = StringBuilder()
    for (index in 0 until length) {
        val element = get(index)
        if (predicate(element)) sb.append(element)
    }
    return sb.toString()
}

fun foo(callback: (() -> Unit)?) {
    callback?.invoke()
}

enum class Delivery {
    STANDARD, EXPEDITED
}

class Order(val itemCount: Int)

fun getShippingCostCalculator(delivery: Delivery): (Order) -> Double {
    if (delivery == Delivery.EXPEDITED) {
        return { order -> 6 + 2.1 * order.itemCount }
    }
    return { order -> 1.2 * order.itemCount }
}

class ContactListFilters {
    var prefix: String = ""
    var onlyWithPhoneNumber: Boolean = false

    fun getPredicate(): (Person) -> Boolean {
        val startsWithPrefix = { p: Person ->
            p.firstName.startsWith(prefix) || p.lastName.startsWith(prefix)
        }
        if (!onlyWithPhoneNumber) {
            return startsWithPrefix
        }
        return { startsWithPrefix(it) && it.phoneNumber !== null }
    }
}

data class Person(val firstName: String,
                  val lastName: String,
                  val phoneNumber: String?)

data class SiteVisit(val path: String,
                     val duration: Double,
                     val os: OS)

enum class OS {
    WINDOWS, LINUX, MAC, IOS, ANDROID
}

val log = listOf(
        SiteVisit("/", 34.0, OS.WINDOWS),
        SiteVisit("/", 22.0, OS.MAC),
        SiteVisit("/login", 12.0, OS.WINDOWS),
        SiteVisit("/signup", 8.0, OS.IOS),
        SiteVisit("/", 16.3, OS.ANDROID)
)

fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) =
        filter(predicate).map(SiteVisit::duration).average()

inline fun <T> synchronized(lock: Lock, action: () -> T): T {
    lock.lock()
    try {
        return action()
    } finally {
        lock.unlock()
    }
}

fun readFirstLineFromFile(path: String) =
        BufferedReader(FileReader(path)).use {
            it.readLine()
        }

fun lookForAlice(people: List<Person>) {
    people.forEach label@{
        if (it.firstName == "Alice") return@label
    }
    println("Alice might be somewhere")
}
fun lookForAlice2(people: List<Person>) {
    people.forEach {
        if (it.firstName == "Alice") return@forEach
    }
    println("Alice might be somewhere")
}