package me.yailya.serialization.encoding

import me.yailya.serialization.Serializer
import me.yailya.serialization.serializer

interface Encoder {
    fun encodeBoolean(value: Boolean)
    fun encodeByte(value: Byte)
    fun encodeShort(value: Short)
    fun encodeInt(value: Int)
    fun encodeLong(value: Long)
    fun encodeFloat(value: Float)
    fun encodeDouble(value: Double)
    fun encodeChar(value: Char)
    fun encodeString(value: String)
    fun <T> encodeObject(value: T, serializer: Serializer<T>)

    companion object {
        inline fun <reified T> Encoder.encodeObject(value: T) {
            encodeObject(value, serializer<T>())
        }
    }
}