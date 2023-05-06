@file:Suppress("UNCHECKED_CAST")

package me.yailya.serialization

import me.yailya.serialization.encoding.Decoder
import me.yailya.serialization.encoding.Encoder
import me.yailya.serialization.internal.builtinParametrizedSerializer
import me.yailya.serialization.internal.builtinSerializer
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure
import kotlin.reflect.jvm.jvmName
import kotlin.reflect.typeOf

interface Serializer<T> {
    fun serialize(value: T, encoder: Encoder)
    fun deserialize(decoder: Decoder): T
}

fun KType.serializer(): Serializer<out Any> {
    return when {
        arguments.isNotEmpty() -> builtinParametrizedSerializer(
            arguments.map { it.type!!.serializer() }
        )

        else -> builtinSerializer()
    } ?: throw SerializationException("Serializer for class ${this.jvmErasure.jvmName} not found.")
}

inline fun <reified T> serializer(): Serializer<T> {
    return typeOf<T>().serializer() as Serializer<T>
}