package cn.tianyu.dailypractice

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun inherit_isCorrect() {
        val son = Sonbean("bilibili", false)
        son.name = "jane"
        son.age = Random().nextInt(10) + 10
        println(son.toString())
        val another = Sonbean("jim", Random().nextInt(10) + 10, "cancel", true)
        print("${another.name} age is ${another.age} $another")
    }

}

open class Superbean(open var gender: Boolean) {
    lateinit var name: String
    var age: Int = 0
}

data class Sonbean(val skill: String, override var gender: Boolean) : Superbean(gender) {
    constructor(name: String, age: Int, skill: String, gender: Boolean) : this(skill, gender) {
        this.name = name
        this.age = age
    }
}
