package cn.tianyu.kotlin_learn.section10

import ru.yole.jkid.*
import ru.yole.jkid.serialization.serialize
import java.text.DateFormat
import java.util.*
import kotlin.reflect.KFunction2
import kotlin.reflect.full.memberProperties

fun test10() {
    val person = Person("Alice", 29)
    println(serialize(person))
    val kClass = person.javaClass.kotlin
    println(kClass.simpleName)
    kClass.memberProperties.forEach { println(it.name) }
    val kFunction = ::foo
    kFunction.call(42)
    //将函数声明为具体的方法
    val kFunction2: KFunction2<Int, Int, Int> = ::sum
    //这些类型成为合成的编译器生成类型，避免了对函数类型参数数量的人为限制
    println(kFunction2.invoke(1, 2))
    //顶层属性的反射调用
    val kProperty0 = ::counter
    kProperty0.setter.call(21)
    println(kProperty0.get())
    //反射访问成员属性
    val memberProperty = Person::age
    println(memberProperty.get(person))
}

var counter = 0
fun foo(x: Int) = println(x)

fun sum(x: Int, y: Int) = x + y

@Deprecated("Use removeAt(index) instead.", ReplaceWith("removeAt(index)"))
fun remove(index: Int) {
}

data class Person(
        @JsonName("alias") val name: String,
        @JsonExclude val age: Int? = null
)

annotation class JsonVoid

annotation class JsonSerialize(val name: String)

@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class BindingAnnotation

@BindingAnnotation
annotation class MyBinding

interface Company {
    val name: String
}

data class CompanyImpl(override val name: String) : Company

data class Person2(val name: String,
                   @DeserializeInterface(CompanyImpl::class) val company: Company,
                   @CustomSerializer(DateSerializer::class) val birthDate: Date)

class DateSerializer : ValueSerializer<Date> {

    companion object {
        val formatTool by lazy {
            DateFormat.getDateInstance()
        }
    }

    override fun toJsonValue(value: Date): Any? = formatTool.format(value)

    override fun fromJsonValue(jsonValue: Any?): Date = jsonValue?.let {
        val date = formatTool.parse(jsonValue.toString())
        if (date == null) Date() else date
    } ?: Date()

}

