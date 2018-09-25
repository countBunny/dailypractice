package cn.tianyu.dailypractice

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
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

class MyService {
    fun performAction() = "foo"
}

class MyTest {
    /**
     * 延迟初始化属性都是var
     */
    private lateinit var myService: MyService
    @Before
    fun setUp(){
        myService = MyService()
    }

    @Test fun testAction(){
        Assert.assertEquals("foo", myService.performAction())
    }

}

class HasTempFolder {
    @get:Rule
    val folder = TemporaryFolder()

    @Test
    fun testUsingTempFolder(){
        val createdFile = folder.newFile("myfile.txt")
        val createdFolder = folder.newFolder("subFolder")

    }
}