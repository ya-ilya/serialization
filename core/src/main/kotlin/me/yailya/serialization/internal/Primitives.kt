package me.yailya.serialization.internal

import me.yailya.serialization.Serializer
import me.yailya.serialization.encoding.Decoder
import me.yailya.serialization.encoding.Encoder
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure

internal val PRIMITIVES = mapOf(
    String::class to StringSerializer,
    Char::class to CharSerializer,
    CharArray::class to CharSerializer.Array,
    Boolean::class to BooleanSerializer,
    BooleanArray::class to BooleanSerializer.Array,
    Int::class to IntSerializer,
    IntArray::class to IntSerializer.Array,
    Short::class to ShortSerializer,
    ShortArray::class to ShortSerializer.Array,
    Long::class to LongSerializer,
    LongArray::class to LongSerializer.Array,
    Float::class to FloatSerializer,
    FloatArray::class to FloatSerializer.Array,
    Double::class to DoubleSerializer,
    DoubleArray::class to DoubleSerializer.Array,
    Byte::class to ByteSerializer,
    ByteArray::class to ByteSerializer.Array
)

fun KType.isSerializationPrimitive(): Boolean {
    return PRIMITIVES.containsKey(this.jvmErasure)
}

fun KType.isSerializationPrimitiveArray(): Boolean {
    if (this.arguments.isEmpty()) return false
    val componentType = this.arguments[0].type!!
    return this == Array::class && (componentType.isSerializationPrimitive() || componentType.isSerializationPrimitiveArray())
}

@PublishedApi
internal object StringSerializer : Serializer<String> {
    override fun serialize(value: String, encoder: Encoder) = encoder.encodeString(value)
    override fun deserialize(decoder: Decoder) = decoder.decodeString()
}

@PublishedApi
internal object CharSerializer : Serializer<Char> {
    @PublishedApi
    internal object Array : PrimitiveArraySerializer<CharArray, Char>(CharSerializer) {
        override fun ArrayList<Char>.collection() = toCharArray()
        override fun CharArray.collectionIterator() = iterator()
        override fun CharArray.collectionSize() = size
    }

    override fun serialize(value: Char, encoder: Encoder) = encoder.encodeChar(value)
    override fun deserialize(decoder: Decoder) = decoder.decodeChar()
}

@PublishedApi
internal object BooleanSerializer : Serializer<Boolean> {
    @PublishedApi
    internal object Array : PrimitiveArraySerializer<BooleanArray, Boolean>(BooleanSerializer) {
        override fun ArrayList<Boolean>.collection() = toBooleanArray()
        override fun BooleanArray.collectionIterator() = iterator()
        override fun BooleanArray.collectionSize() = size
    }

    override fun serialize(value: Boolean, encoder: Encoder) = encoder.encodeBoolean(value)
    override fun deserialize(decoder: Decoder) = decoder.decodeBoolean()
}

@PublishedApi
internal object IntSerializer : Serializer<Int> {
    @PublishedApi
    internal object Array : PrimitiveArraySerializer<IntArray, Int>(IntSerializer) {
        override fun ArrayList<Int>.collection() = toIntArray()
        override fun IntArray.collectionIterator() = iterator()
        override fun IntArray.collectionSize() = size
    }

    override fun serialize(value: Int, encoder: Encoder) = encoder.encodeInt(value)
    override fun deserialize(decoder: Decoder) = decoder.decodeInt()
}

@PublishedApi
internal object ShortSerializer : Serializer<Short> {
    @PublishedApi
    internal object Array : PrimitiveArraySerializer<ShortArray, Short>(ShortSerializer) {
        override fun ArrayList<Short>.collection() = toShortArray()
        override fun ShortArray.collectionIterator() = iterator()
        override fun ShortArray.collectionSize() = size
    }

    override fun serialize(value: Short, encoder: Encoder) = encoder.encodeShort(value)
    override fun deserialize(decoder: Decoder) = decoder.decodeShort()
}

@PublishedApi
internal object LongSerializer : Serializer<Long> {
    @PublishedApi
    internal object Array : PrimitiveArraySerializer<LongArray, Long>(LongSerializer) {
        override fun ArrayList<Long>.collection() = toLongArray()
        override fun LongArray.collectionIterator() = iterator()
        override fun LongArray.collectionSize() = size
    }

    override fun serialize(value: Long, encoder: Encoder) = encoder.encodeLong(value)
    override fun deserialize(decoder: Decoder) = decoder.decodeLong()
}

@PublishedApi
internal object FloatSerializer : Serializer<Float> {
    @PublishedApi
    internal object Array : PrimitiveArraySerializer<FloatArray, Float>(FloatSerializer) {
        override fun ArrayList<Float>.collection() = toFloatArray()
        override fun FloatArray.collectionIterator() = iterator()
        override fun FloatArray.collectionSize() = size
    }

    override fun serialize(value: Float, encoder: Encoder) = encoder.encodeFloat(value)
    override fun deserialize(decoder: Decoder) = decoder.decodeFloat()
}

@PublishedApi
internal object DoubleSerializer : Serializer<Double> {
    @PublishedApi
    internal object Array : PrimitiveArraySerializer<DoubleArray, Double>(DoubleSerializer) {
        override fun ArrayList<Double>.collection() = toDoubleArray()
        override fun DoubleArray.collectionIterator() = iterator()
        override fun DoubleArray.collectionSize() = size
    }

    override fun serialize(value: Double, encoder: Encoder) = encoder.encodeDouble(value)
    override fun deserialize(decoder: Decoder) = decoder.decodeDouble()
}

@PublishedApi
internal object ByteSerializer : Serializer<Byte> {
    @PublishedApi
    internal object Array : PrimitiveArraySerializer<ByteArray, Byte>(ByteSerializer) {
        override fun ArrayList<Byte>.collection() = toByteArray()
        override fun ByteArray.collectionIterator() = iterator()
        override fun ByteArray.collectionSize() = size
    }

    override fun serialize(value: Byte, encoder: Encoder) = encoder.encodeByte(value)
    override fun deserialize(decoder: Decoder) = decoder.decodeByte()
}