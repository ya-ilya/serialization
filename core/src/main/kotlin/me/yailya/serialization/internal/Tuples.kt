package me.yailya.serialization.internal

import me.yailya.serialization.Serializer
import me.yailya.serialization.encoding.Decoder
import me.yailya.serialization.encoding.Encoder

@PublishedApi
internal class PairSerializer<K, V>(
    private val keySerializer: Serializer<K>,
    private val valueSerializer: Serializer<V>
) : Serializer<Pair<K, V>> {
    override fun serialize(value: Pair<K, V>, encoder: Encoder) {
        keySerializer.serialize(value.first, encoder)
        valueSerializer.serialize(value.second, encoder)
    }

    override fun deserialize(decoder: Decoder): Pair<K, V> {
        return Pair(
            keySerializer.deserialize(decoder),
            valueSerializer.deserialize(decoder)
        )
    }
}

@PublishedApi
internal class TripleSerializer<A, B, C>(
    private val aSerializer: Serializer<A>,
    private val bSerializer: Serializer<B>,
    private val cSerializer: Serializer<C>
) : Serializer<Triple<A, B, C>> {
    override fun serialize(value: Triple<A, B, C>, encoder: Encoder) {
        aSerializer.serialize(value.first, encoder)
        bSerializer.serialize(value.second, encoder)
        cSerializer.serialize(value.third, encoder)
    }

    override fun deserialize(decoder: Decoder): Triple<A, B, C> {
        return Triple(
            aSerializer.deserialize(decoder),
            bSerializer.deserialize(decoder),
            cSerializer.deserialize(decoder)
        )
    }
}

@PublishedApi
internal class MapEntrySerializer<K, V>(
    private val keySerializer: Serializer<K>,
    private val valueSerializer: Serializer<V>
) : Serializer<Map.Entry<K, V>> {
    override fun serialize(value: Map.Entry<K, V>, encoder: Encoder) {
        keySerializer.serialize(value.key, encoder)
        valueSerializer.serialize(value.value, encoder)
    }

    override fun deserialize(decoder: Decoder): Map.Entry<K, V> {
        return object : Map.Entry<K, V> {
            override val key = keySerializer.deserialize(decoder)
            override val value = valueSerializer.deserialize(decoder)
        }
    }
}