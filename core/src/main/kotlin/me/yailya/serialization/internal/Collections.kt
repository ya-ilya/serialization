package me.yailya.serialization.internal

import me.yailya.serialization.Serializer
import me.yailya.serialization.encoding.Decoder
import me.yailya.serialization.encoding.Encoder

@PublishedApi
internal abstract class CollectionSerializer<E, C, B>(
    private val elementSerializer: Serializer<E>
) : Serializer<C> {
    abstract fun collectionBuilder(): B
    abstract fun B.collection(): C
    abstract fun B.insert(element: E)
    abstract fun C.collectionIterator(): Iterator<E>
    abstract fun C.collectionSize(): Int

    override fun serialize(value: C, encoder: Encoder) {
        encoder.encodeInt(value.collectionSize())

        for (element in value.collectionIterator()) {
            elementSerializer.serialize(element, encoder)
        }
    }

    override fun deserialize(decoder: Decoder): C {
        val builder = collectionBuilder()

        repeat(decoder.decodeInt()) {
            builder.insert(elementSerializer.deserialize(decoder))
        }

        return builder.collection()
    }
}

@PublishedApi
internal abstract class MapSerializer<K, V, C, B : Map<K, V>>(
    keySerializer: Serializer<K>,
    valueSerializer: Serializer<V>
) : CollectionSerializer<Map.Entry<K, V>, C, B>(MapEntrySerializer(keySerializer, valueSerializer))

@PublishedApi
internal abstract class PrimitiveArraySerializer<A, E>(
    elementSerializer: Serializer<E>
) : CollectionSerializer<E, A, ArrayList<E>>(elementSerializer) {
    override fun collectionBuilder() = arrayListOf<E>()

    override fun ArrayList<E>.insert(element: E) {
        this.add(element)
    }
}

@PublishedApi
internal class ArraySerializer<E>(
    elementSerializer: Serializer<E>
) : CollectionSerializer<E, Array<E>, ArrayList<E>>(elementSerializer) {
    companion object {
        @Suppress("UNCHECKED_CAST")
        private fun <T> arrayOfAnyNulls(size: Int): Array<T> = arrayOfNulls<Any>(size) as Array<T>
    }

    override fun collectionBuilder() = arrayListOf<E>()

    override fun ArrayList<E>.collection(): Array<E> {
        val result = arrayOfAnyNulls<E>(size)
        var index = 0
        for (element in this) result[index++] = element
        @Suppress("USELESS_CAST")
        return result as Array<E>
    }

    override fun ArrayList<E>.insert(element: E) {
        this.add(element)
    }

    override fun Array<E>.collectionIterator() = this.iterator()
    override fun Array<E>.collectionSize() = this.size
}

@PublishedApi
internal class IterableSerializer<E>(
    elementSerializer: Serializer<E>
) : CollectionSerializer<E, Iterable<E>, ArrayList<E>>(elementSerializer) {
    override fun collectionBuilder() = arrayListOf<E>()

    override fun ArrayList<E>.collection() = this
    override fun ArrayList<E>.insert(element: E) {
        this.add(element)
    }

    override fun Iterable<E>.collectionIterator() = this.iterator()
    override fun Iterable<E>.collectionSize() = this.toList().size
}

@PublishedApi
internal class ArrayListSerializer<E>(
    elementSerializer: Serializer<E>
) : CollectionSerializer<E, List<E>, ArrayList<E>>(elementSerializer) {
    override fun collectionBuilder() = arrayListOf<E>()

    override fun ArrayList<E>.collection() = this
    override fun ArrayList<E>.insert(element: E) {
        this.add(element)
    }

    override fun List<E>.collectionIterator() = this.iterator()
    override fun List<E>.collectionSize() = this.size
}

@PublishedApi
internal class HashSetSerializer<E>(
    elementSerializer: Serializer<E>
) : CollectionSerializer<E, Set<E>, HashSet<E>>(elementSerializer) {
    override fun collectionBuilder() = hashSetOf<E>()

    override fun HashSet<E>.collection() = this
    override fun HashSet<E>.insert(element: E) {
        this.add(element)
    }

    override fun Set<E>.collectionIterator() = this.iterator()
    override fun Set<E>.collectionSize() = this.size
}

@PublishedApi
internal class LinkedHashSetSerializer<E>(
    elementSerializer: Serializer<E>
) : CollectionSerializer<E, Set<E>, LinkedHashSet<E>>(elementSerializer) {
    override fun collectionBuilder() = linkedSetOf<E>()

    override fun LinkedHashSet<E>.collection() = this
    override fun LinkedHashSet<E>.insert(element: E) {
        this.add(element)
    }

    override fun Set<E>.collectionIterator() = this.iterator()
    override fun Set<E>.collectionSize() = this.size
}

@PublishedApi
internal class HashMapSerializer<K, V>(
    keySerializer: Serializer<K>,
    valueSerializer: Serializer<V>
) : MapSerializer<K, V, Map<K, V>, HashMap<K, V>>(keySerializer, valueSerializer) {
    override fun collectionBuilder() = hashMapOf<K, V>()

    override fun HashMap<K, V>.collection() = this
    override fun HashMap<K, V>.insert(element: Map.Entry<K, V>) {
        this[element.key] = element.value
    }

    override fun Map<K, V>.collectionIterator() = this.iterator()
    override fun Map<K, V>.collectionSize() = this.size
}

@PublishedApi
internal class LinkedHashMapSerializer<K, V>(
    keySerializer: Serializer<K>,
    valueSerializer: Serializer<V>
) : MapSerializer<K, V, Map<K, V>, LinkedHashMap<K, V>>(keySerializer, valueSerializer) {
    override fun collectionBuilder() = linkedMapOf<K, V>()

    override fun LinkedHashMap<K, V>.collection() = this
    override fun LinkedHashMap<K, V>.insert(element: Map.Entry<K, V>) {
        this[element.key] = element.value
    }

    override fun Map<K, V>.collectionIterator() = this.iterator()
    override fun Map<K, V>.collectionSize() = this.size
}