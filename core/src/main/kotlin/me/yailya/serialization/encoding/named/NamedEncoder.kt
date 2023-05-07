package me.yailya.serialization.encoding.named

import me.yailya.serialization.Serializer
import me.yailya.serialization.encoding.Encoder
import me.yailya.serialization.serializer

abstract class NamedEncoder<N> : Encoder {
    abstract fun encodeName(name: N)

    fun encodeNamedBoolean(name: N, value: Boolean) {
        encodeName(name)
        encodeBoolean(value)
    }

    fun encodeNamedByte(name: N, value: Byte) {
        encodeName(name)
        encodeByte(value)
    }

    fun encodeNamedShort(name: N, value: Short) {
        encodeName(name)
        encodeShort(value)
    }

    fun encodeNamedInt(name: N, value: Int) {
        encodeName(name)
        encodeInt(value)
    }

    fun encodeNamedLong(name: N, value: Long) {
        encodeName(name)
        encodeLong(value)
    }

    fun encodeNamedFloat(name: N, value: Float) {
        encodeName(name)
        encodeFloat(value)
    }

    fun encodeNamedDouble(name: N, value: Double) {
        encodeName(name)
        encodeDouble(value)
    }

    fun encodeNamedChar(name: N, value: Char) {
        encodeName(name)
        encodeChar(value)
    }

    fun encodeNamedString(name: N, value: String) {
        encodeName(name)
        encodeString(value)
    }

    fun <T> encodeNamedObject(name: N, value: T, serializer: Serializer<T>) {
        encodeName(name)
        encodeObject(value, serializer)
    }

    companion object {
        inline fun <N, reified T> NamedEncoder<N>.encodeNamedObject(name: N, value: T) {
            encodeNamedObject(name, value, serializer<T>())
        }
    }
}