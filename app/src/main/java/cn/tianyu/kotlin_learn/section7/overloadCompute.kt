package cn.tianyu.kotlin_learn.section7

import android.graphics.Point
import android.os.Build
import java.math.BigDecimal
import java.text.NumberFormat
import java.time.LocalDate

fun test7() {
    val p1 = Point(10, 20)
    val p2 = Point(30, 40)
    println(p1 + p2)
    println(p1 * 1.5)
    println('a' * 3)
    //0
    println(0x0F and 0xF0)
    //255
    println(0x0F or 0xF0)
    //16
    println(0x1 shl 4)
    //16
    println(0xFF xor 0xF0)
    //0
    println(0xFF.inv())
    val list = arrayListOf(1, 2)
    list += 3
    val newList = list + listOf(4, 5)
    println(list)
    println(newList)
    println(-p1)
    var bd = BigDecimal.ZERO
    //0
    println(bd++)
    //2
    println(++bd)
    val numberFormat = NumberFormat.getNumberInstance()
    numberFormat.minimumFractionDigits = 2
    println(numberFormat.format(bd))
    println(String.format("%.2f", 21.456))
    println(p1 == p2)
    println(p1 != p2)
    val person1 = Person("Alice", "Smith")
    val person2 = Person("Bob", "Johnson")
    //false
    println(person1 < person2)
    //10
    println(p1[0])
    val p3 = MutablePoint(10, 20)
    p3[1] = 42
    println(p3)
    val rect = Rectangle(p1, p2)
    println(p2 in rect)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val now = LocalDate.now()
        val vacation = now..now.plusDays(10)
        println(now.plusWeeks(1) in vacation)
    }
    for (c in "abc"){
        println(c)
    }
}

data class Point(val x: Int, val y: Int) {
    operator fun minus(other: Point): Point {
        return Point(x - other.x, y - other.y)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Point) return false
        return other.x == x && other.y == y
    }
}

operator fun Point.plus(other: Point): Point {
    return Point(x + other.x, y + other.y)
}

operator fun Point.unaryMinus(): Point {
    return Point(-x, -y)
}

operator fun Point.times(scale: Double): Point {
    return Point((x * scale).toInt(), (y * scale).toInt())
}

operator fun Point.get(index: Int): Int {
    return when (index) {
        0 -> x
        1 -> y
        else -> throw IndexOutOfBoundsException("Invalid coordinate $index")
    }
}

operator fun Char.times(count: Int): String {
    return toString().repeat(count)
}

class Person(val firstName: String, val lastName: String) : Comparable<Person> {
    override fun compareTo(other: Person): Int {
        return compareValuesBy(this, other, Person::lastName, Person::firstName)
    }
}

data class MutablePoint(var x: Int, var y: Int)

operator fun MutablePoint.set(index: Int, value: Int) {
    when (index) {
        0 -> x = value
        1 -> y = value
        else -> throw IndexOutOfBoundsException("Invalid coordinate $index")
    }
}

data class Rectangle(val upperLeft: Point, val lowerRight: Point)

operator fun Rectangle.contains(p: Point): Boolean {
    return p.x in upperLeft.x..lowerRight.x &&
            p.y in upperLeft.y..lowerRight.y
}

data class NameComponents(val name:String, val extension:String)

fun splitFilename(fullName: String):NameComponents{
    val result = fullName.split('.', limit = 2)
    return NameComponents(result[0], result[1])
}

