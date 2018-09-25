package cn.tianyu.kotlin_learn.section9

import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.KClass

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
    enumerateCats(Animal::getIndex)
    val list: MutableList<out Number> = ArrayList()
    //不能调用做为实参的方法
//    list.add(5)
    val list2: MutableList<Any?> = mutableListOf('a', 1, "qwe")
    val chars = mutableListOf('a', 'b', 'c')
    val unknownElements: MutableList<*> =
            if (Random().nextBoolean()) list2 else chars
//    unknownElements.add(42)
    println(unknownElements.first())
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

open class Animal

class Herd<out T : Animal>(private var leadAnimal: T, vararg animals: T)

class Cat : Animal()

fun enumerateCats(f: (Cat) -> Number) {

}

fun Animal.getIndex(): Int = this.hashCode()

fun <T> copyData(source: MutableList<out T>, destination: MutableList<T>) {
    for (item in source) {
        destination.add(item)
    }
}

interface FieldValidator<in T> {
    fun validate(input: T): Boolean
}

object DefaultStringValidator : FieldValidator<String> {
    override fun validate(input: String) = input.isNotEmpty()
}

object DefaultIntValidator : FieldValidator<Int> {
    override fun validate(input: Int) = input >= 0
}

object Validators {
    private val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()

    fun <T : Any> registerValidator(kClass: KClass<T>, fieldValidator: FieldValidator<T>) {
        validators[kClass] = fieldValidator
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(kClass: KClass<T>): FieldValidator<T> =
            validators[kClass] as? FieldValidator<T>
                    ?: throw  IllegalArgumentException("No Validator for ${kClass.simpleName}")
}
