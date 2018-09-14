package cn.tianyu.kotlin_learn

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import cn.tianyu.dailypractice.R
import cn.tianyu.dailypractice.utils.LogUtil
import cn.tianyu.kotlin_learn.section2.*
import cn.tianyu.kotlin_learn.section3.joinToString
import cn.tianyu.kotlin_learn.section3.parsePathInRegex
import cn.tianyu.kotlin_learn.section4.*
import cn.tianyu.kotlin_learn.section5.Person
import cn.tianyu.kotlin_learn.section5.createAllDoneRunnable
import cn.tianyu.kotlin_learn.section5.createPerson
import cn.tianyu.kotlin_learn.section5.sum
import cn.tianyu.kotlin_learn.section7.test7
import kotlinx.android.synthetic.main.activity_console.*
import org.jetbrains.anko.toast

class ConsoleActivity : AppCompatActivity() {

    companion object {
        val TAG = "ConsoleActivity"
        val REQUEST_CODE_PERMISSION = 101;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_console)
        test();
        test4()
        test5()
        test6()
        test7()
    }

    private fun test6() {
        val x = 1
        println(x.toLong() in listOf(1L, 2L, 3L))
        val binaryVal = 0B00000101
        val letters = Array(26) { i -> ('a' + i).toString() }
        println(letters.joinToString(""))
        val strings = listOf("a", "b", "c")
        println("%s/%s/%s".format(*strings.toTypedArray()))
    }

    private fun test5() {
        val people = listOf(createPerson("Alice", 27), Person("Bob", 31))
        LogUtil.d(TAG, "people max age is ${people.maxBy { it.age }}")
        console2.text = "${console2.text} \nlambda sum result sum(1, 4) = ${sum(1, 4)}"
//        { println(43)}()
        run { println(43) }
        val getName = Person::name
        val aliceAge = people[0]::age
        println(people.joinToString(" ", transform = getName))
        val numbers = mapOf(0 to "zero", 1 to "one")
        println(numbers.mapValues { it.value.toUpperCase() })
        val canBeInClub27 = { p: Person -> p.age <= 27 }
        println(people.all(canBeInClub27))
        println(people.any(canBeInClub27))
        println(people.count(canBeInClub27))
        println(people.find(canBeInClub27))
        println(people.firstOrNull(canBeInClub27))
        //类型Map<Int, List<Person>>
        println(people.groupBy { it.age })
        val list = listOf("a", "ab", "b")
        println(list.groupBy(String::first))
        val strings = listOf("abc", "def")
        println(strings.flatMap { it.toList() })
        people.asSequence()
                .map(Person::name)
                .filter { it.startsWith("A") }
                .toList()
        val naturalNumbers = generateSequence(0) { it + 1 }
        val numberTo100 = naturalNumbers.takeWhile { it <= 100 }
        println(numberTo100.sum())
        createAllDoneRunnable().run()
        //SAM 写可重用的监听器
        val listener = View.OnClickListener {
            val text = when (it.id) {
                R.id.console1 -> "First Console"
                R.id.console2 -> "Second Console"
                else -> "Unknown button"
            }
            toast(text)
        }
//        console1.setOnClickListener(listener)
//        console2.setOnClickListener(listener)

    }

    private fun test4() {
        val button = Button()
        button.showOff()
        button.setFocus(true)
        button.click()
        val cset = CountingSet<Int>()
        cset.addAll(listOf(1, 1, 2))
        console2.text = """${console2.text}
            _${cset.objectsAdded} objects were added, ${cset.size} remain
            """.trimIndent().trimMargin("_")
        Payroll.calculateSalary()
        val user2 = "{nickname: 'Dmitry'}".loadFromJSON(User2)
        LogUtil.d(TAG, "user2's nickname is ${user2.nickname}")
        registerBean(object : TalkativeButton(), Clickable {
            override fun click() {
                LogUtil.d(TAG, "TalkativeButton inner immplementation been clicked!")
            }

            override fun showOff() {
                super<Clickable>.showOff()
            }
        })
    }

    private fun registerBean(widget: Focusable) {
        widget.showOff()
        widget.setFocus(false)
    }

    private fun test() {
        console1.setText("${getMnemonic(Color.BLUE)} \n1 + 2 + 4 = ${eval(Sum(Sum(Num(1), Num(2)), Num(4)))}")

        for (i in 1..100) {
            LogUtil.d(TAG, fizzBuzz(i))
        }
        val list = arrayListOf("10", "11", "1001")
        for ((index, element) in list.withIndex()) {
            LogUtil.d(TAG, "$index: $element")
        }
        val list2 = listOf(1, 2, 3)
        console2.setText("${console2.text} \n${joinToString(list2, ";", "(", ")")}")
        InvokeFromJava.invokeKotlinMethod()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION)
        } else {
            readExternalFileInfo()
        }
        LogUtil.d(TAG, """
            | //
            |//
            |/ \
        """)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            for ((index, value) in permissions.withIndex()) {
                if (value == Manifest.permission.READ_EXTERNAL_STORAGE && grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                    readExternalFileInfo()
                }
            }
        }
    }

    private fun readExternalFileInfo() {
        val externalFilesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        LogUtil.d(TAG, "externalStoragePath is ${externalFilesDir.absolutePath}")
        val listFiles = externalFilesDir.listFiles()
        if (listFiles != null && !listFiles.isEmpty()) {
            val firstFile = listFiles.asSequence().filter { it.isFile }.first()
            if (firstFile != null) {
                parsePathInRegex(firstFile.absolutePath)
            } else {
                LogUtil.d(TAG, "No file found!")
            }
        } else {
            LogUtil.d(TAG, "list file result is NULL!")
        }
    }
}
