package cn.tianyu.kotlin_learn.section6

import java.io.BufferedReader

fun printAllCaps(s: String?) {
    val allCaps: String? = s?.toUpperCase()
    println(allCaps)
}

class Address(val streetAddress: String, val zipCode: Int, val city: String, val country: String)
class Company(val name: String, val address: Address?)
class Person(val name: String, val company: Company?) {
    override fun equals(other: Any?): Boolean {
        val otherPerson = other as? Person ?: return false
        return otherPerson.name == name && otherPerson.company == company
    }

    override fun hashCode(): Int {
        return name.hashCode() * 37 + (company?.hashCode() ?: 0)
    }
}

fun Person.countryName(): String {
    return company?.address?.country ?: "Unknown"
}

fun printShippingLabel(person: Person) {
    val address = person.company?.address ?: throw IllegalArgumentException("No address")
    with(address) {
        println(streetAddress)
        println("$zipCode $city. $country")
    }
}

fun ignoreNulls(s: String?) {
    //传入Null下面会抛运行时异常
    val sNotNull = s!!
    println(sNotNull.length)
}

fun <T:Any> printHashCode(t:T){
    println(t.hashCode())
}

fun showProgress(progress:Int){
    val percent = progress.coerceIn(0..100)
    println("We're ${percent}% done!")
}

fun readNumbers(reader: BufferedReader) : List<Int?> {
    val result = ArrayList<Int?>()
    for (line in reader.lineSequence()){
        try {
            val number = line.toInt()
            result.add(number)
        }catch (e: NumberFormatException){
            result.add(null)
        }
    }
    return result
}