package cn.tianyu.kotlin_learn.section10

import ru.yole.jkid.*
import ru.yole.jkid.serialization.serialize
import java.text.DateFormat
import java.util.*

fun test10() {
    val person = Person("Alice", 29)
    println(serialize(person))
}

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
                   @CustomSerializer(DateSerializer::class) val birthDate:Date)

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

