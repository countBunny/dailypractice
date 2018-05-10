package cn.tianyu.dailypractice.extensions

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object DelegateExt {

    fun <T> notNullSingleValue(): ReadWriteProperty<Any?, T> = NotNullSingleValueVar()
}

class NotNullSingleValueVar<T> : ReadWriteProperty<Any?, T> {

    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("${property.name} haven't been initialized!")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (value == null) value
        else throw IllegalStateException("${property.name} has been initialized!")
    }

}
