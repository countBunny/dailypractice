package cn.tianyu.dailypractice.extensions

fun <K, V : Any?> Map<K, V>.toVarargArray(): Array<out Pair<K, V>> =
        map {
            it.key to it.value
        }.toTypedArray()