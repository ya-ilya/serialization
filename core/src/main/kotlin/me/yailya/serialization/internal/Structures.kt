@file:Suppress("UNCHECKED_CAST")

package me.yailya.serialization.internal

import me.yailya.serialization.Serializer
import me.yailya.serialization.UseSerializer
import me.yailya.serialization.encoding.Decoder
import me.yailya.serialization.encoding.Encoder
import me.yailya.serialization.serializer
import sun.misc.Unsafe
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

@PublishedApi
internal class StructureSerializer<T : Any>(private val clazz: KClass<T>) : Serializer<T> {
    private companion object {
        lateinit var unsafe: Unsafe

        init {
            try {
                val unsafeField = Unsafe::class.java.getDeclaredField("theUnsafe")
                unsafeField.isAccessible = true
                unsafe = unsafeField.get(null) as Unsafe
                unsafeField.isAccessible = false
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun serialize(value: T, encoder: Encoder) {
        for (property in clazz.declaredMemberProperties) {
            property.isAccessible = true

            val annotation = property.findAnnotation<UseSerializer>()
            val serializer = (annotation?.annotationSerializer() ?: property.returnType.serializer()) as Serializer<Any>

            encoder.encodeString(property.name)
            serializer.serialize(property.get(value)!!, encoder)

            property.isAccessible = false
        }
    }

    override fun deserialize(decoder: Decoder): T {
        val instance = unsafe.allocateInstance(clazz.java) as T

        repeat(clazz.java.declaredFields.size) {
            val propertyName = decoder.decodeString()
            val property = clazz.declaredMemberProperties
                .first { it.name == propertyName }
                .also { it.isAccessible = true }

            val annotation = property.findAnnotation<UseSerializer>()
            val serializer = (annotation?.annotationSerializer() ?: property.returnType.serializer())

            property.javaField!!.set(
                instance,
                serializer.deserialize(decoder)
            )

            property.isAccessible = false
        }

        return instance
    }
}