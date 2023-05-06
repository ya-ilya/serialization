package me.yailya.serialization.encoding

import me.yailya.serialization.Serializer
import me.yailya.serialization.serializer

interface Decoder {
    fun decodeBoolean(): Boolean
    fun decodeByte(): Byte
    fun decodeShort(): Short
    fun decodeInt(): Int
    fun decodeLong(): Long
    fun decodeFloat(): Float
    fun decodeDouble(): Double
    fun decodeChar(): Char
    fun decodeString(): String
    fun <T> decodeObject(serializer: Serializer<T>): T

    companion object {
        inline fun <reified T> Decoder.decodeObject(): T {
            return decodeObject(serializer<T>())
        }
    }
}