package me.yailya.serialization.encoding.named

import me.yailya.serialization.Serializer
import me.yailya.serialization.encoding.Decoder
import me.yailya.serialization.serializer

abstract class NamedDecoder<N> : Decoder {
    abstract fun decodeName(): N

    fun decodeNamedBoolean(): Pair<N, Boolean> {
        return decodeName() to decodeBoolean()
    }

    fun decodeNamedByte(): Pair<N, Byte> {
        return decodeName() to decodeByte()
    }

    fun decodeNamedShort(): Pair<N, Short> {
        return decodeName() to decodeShort()
    }

    fun decodeNamedInt(): Pair<N, Int> {
        return decodeName() to decodeInt()
    }

    fun decodeNamedLong(): Pair<N, Long> {
        return decodeName() to decodeLong()
    }

    fun decodeNamedFloat(): Pair<N, Float> {
        return decodeName() to decodeFloat()
    }

    fun decodeNamedDouble(): Pair<N, Double> {
        return decodeName() to decodeDouble()
    }

    fun decodeNamedChar(): Pair<N, Char> {
        return decodeName() to decodeChar()
    }

    fun decodeNamedString(): Pair<N, String> {
        return decodeName() to decodeString()
    }

    fun <T> decodeNamedObject(serializer: Serializer<T>): Pair<N, T> {
        return decodeName() to decodeObject(serializer)
    }

    companion object {
        inline fun <N, reified T> NamedDecoder<N>.decodeNamedObject(): Pair<N, T> {
            return decodeNamedObject(serializer<T>())
        }
    }
}