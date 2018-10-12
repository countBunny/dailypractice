fun test11() {
    val table = table {
        tr {
            td {
            }
        }
    }
    println(table)
    println(createAnotherTable())
    /**
     * invoke约定让对象像函数般，可调用
     */
    val bavarianGreeter = Greeter("Servus")
    bavarianGreeter("Dmitry")
    val i1 = Issue("IDEA-154446", "IDEA", "Bug", "Major", "Save Settings failed")
    val i2 = Issue("KT-12183", "Kotlin", "Feature", "Normal", "Intention: convert several calls on the same receiver to with/apply")
    val predicate = ImportantIssuesPredicate("IDEA")
    for (issue in listOf(i1, i2).filter(predicate)){
        println(issue.id)
    }
}

fun createAnotherTable() = table {
    for (i in 1..2) {
        tr {
            td { }
        }
    }
}

class TABLE : DOM("table") {
    fun tr(init: TR.() -> Unit) = doInit(TR(), init)
}

class TR : DOM("tr") {
    fun td(init: TD.() -> Unit) = doInit(TD(), init)
}

class TD : DOM("td")

open class DOM(val name: String) {
    private val children: MutableList<DOM> = ArrayList()

    protected fun <T : DOM> doInit(child: T, init: T.() -> Unit) {
        child.init()
        children.add(child)
    }

    override fun toString() = "<$name>${children.joinToString("")}</$name>"
}

fun table(init: TABLE.() -> Unit) = TABLE().apply(init)

class Greeter(val greeting: String) {
    operator fun invoke(name: String) {
        println("$greeting, $name")
    }
}

data class Issue(val id: String, val project: String, val type: String,
                 val priority: String, val description: String)

class ImportantIssuesPredicate(val project: String) : (Issue) -> Boolean {
    override fun invoke(p1: Issue): Boolean {
        return p1.project == project && p1.isImportant()
    }

    private fun Issue.isImportant(): Boolean {
        return type == "Bug" &&
                (priority == "Major" || priority == "Critical")
    }

}
