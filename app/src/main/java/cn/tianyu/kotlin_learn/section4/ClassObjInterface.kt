package cn.tianyu.kotlin_learn.section4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import cn.tianyu.dailypractice.utils.LogUtil
import org.json.JSONObject
import java.io.File

interface Clickable {
    fun click()
    fun showOff() = LogUtil.d(TAG, "I'm clickable")
}

interface Focusable {
    fun setFocus(b: Boolean) = LogUtil.d(TAG, "I ${if (b) "got" else "lost"} focus.")
    fun showOff() = LogUtil.d(TAG, "I'm focusable!")
}

const val TAG = "ClassObjInterface"

class Button : Clickable, Focusable {
    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }

    override fun click() = LogUtil.d(TAG, "I was clicked")
}

open class RichButton : Clickable {
    fun disable() {}

    open fun animate() {}

    final override fun click() {}
}

abstract class Animated {
    abstract fun animate()

    open fun stopAnimating() {
    }

    fun animateTwice() {
    }
}

internal open class TalkativeButton : Focusable {
    private fun yell() = LogUtil.d(TAG, "Hey!")
    protected fun whisper() = LogUtil.d(TAG, "Let's talk!")
}

/* wrong eg.
fun TalkativeButton.giveSpeech(){
    yell()
    whisper()
}*/
class Outer {
    inner class Inner {
        fun getOuterReference(): Outer = this@Outer
    }
}

private class User constructor(_nickname: String) {
    val nickname: String

    init {
        nickname = _nickname
    }
}

/**
 * 私有构造器的写法
 */
class Secretive private constructor() {}

/**
 * 4.2.5 修改访问器的可见性
 */
class LengthCounter {
    var counter: Int = 0
        private set

    fun addWord(word: String) {
        counter += word.length
    }
}

class CountingSet<T>(val innerSet: MutableSet<T> = HashSet<T>())
    : MutableCollection<T> by innerSet {
    var objectsAdded = 0

    override fun add(element: T): Boolean {
        objectsAdded++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectsAdded += elements.size
        return innerSet.addAll(elements)
    }
}

object Payroll {
    private val allEmployees = arrayListOf<User>()

    fun calculateSalary() {
        for (person in allEmployees) {

        }
    }
}

/**
 * 4.4
 */
object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(o1: File?, o2: File?): Int {
        if (null === o1 && null === o2) {
            return 0
        }
        if (null === o2 || null === o1) {
            return if (null === o1) -1 else 1;
        }
        return o1.path.compareTo(o2.path, true)
    }

}

class User2 private constructor(val nickname: String) {

    companion object Loader : JSONFactory<User2> {
        override fun fromJSON(jsonText: String) =
                User2(JSONObject(jsonText).optString("nickname"))

        fun newSubscribingUser(email: String): User2 = User2(email.substringBefore('@'))
        fun newFacebookUser(accountId: Int) = User2(getFacebookName(accountId))

        private fun getFacebookName(facebookAccountId: Int): String = "facebookUser${facebookAccountId}"
    }
}

interface JSONFactory<T> {
    fun fromJSON(jsonText: String): T
}

fun <T> String.loadFromJSON(factory: JSONFactory<T>): T = factory.fromJSON(this)

class CustomView : View {
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0)
            : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.RED)
    }
}

class CustomView2 : View {
    constructor(context: Context) : this(context, null) {
        //TODO ...

    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0)
            : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.RED)
    }
}