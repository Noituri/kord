package com.gitlab.kordlib.common.entity.optional.delegate

import com.gitlab.kordlib.common.entity.optional.OptionalInt
import com.gitlab.kordlib.common.entity.optional.optionalInt
import com.gitlab.kordlib.common.entity.optional.value
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

fun KMutableProperty0<OptionalInt>.delegate(): ReadWriteProperty<Any?, Int?> = object : ReadWriteProperty<Any?, Int?> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int? {
        return this@delegate.get().value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int?) {
        val optional = if (value == null) OptionalInt.Missing
        else OptionalInt.Value(value)
        this@delegate.set(optional)
    }

}

@JvmName("provideNullableDelegate")
fun KMutableProperty0<OptionalInt?>.delegate(): ReadWriteProperty<Any?, Int?> = object : ReadWriteProperty<Any?, Int?> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int? {
        return this@delegate.get().value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int?) {
        this@delegate.set(value?.optionalInt())
    }

}
