package me.yailya.serialization.internal

import me.yailya.serialization.Serializable
import me.yailya.serialization.Serializer
import me.yailya.serialization.UseSerializer
import kotlin.reflect.KType
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.jvmErasure

@PublishedApi
@Suppress("UNCHECKED_CAST")
internal fun UseSerializer.builtinSerializer(): Serializer<out Any> {
    return this.with.createInstance() as Serializer<Any>
}

@PublishedApi
internal fun KType.builtinSerializer(): Serializer<out Any>? {
    return when {
        PRIMITIVES.containsKey(this.jvmErasure) -> PRIMITIVES[this.jvmErasure]
        this.jvmErasure.hasAnnotation<Serializable>() -> StructureSerializer(this.jvmErasure)
        else -> null
    }
}

@PublishedApi
internal fun KType.builtinParametrizedSerializer(serializers: List<Serializer<out Any>>): Serializer<out Any>? {
    return when (this.jvmErasure) {
        Iterable::class -> IterableSerializer(serializers[0])
        Collection::class, List::class, MutableList::class, ArrayList::class -> ArrayListSerializer(serializers[0])
        HashSet::class -> HashSetSerializer(serializers[0])
        Set::class, MutableSet::class, LinkedHashSet::class -> LinkedHashSetSerializer(serializers[0])
        HashMap::class -> HashMapSerializer(serializers[0], serializers[1])
        Map::class, MutableMap::class, LinkedHashMap::class -> LinkedHashMapSerializer(serializers[0], serializers[1])
        Pair::class -> PairSerializer(serializers[0], serializers[1])
        Triple::class -> TripleSerializer(serializers[0], serializers[1], serializers[2])
        Map.Entry::class -> MapEntrySerializer(serializers[0], serializers[1])
        else -> {
            if (this.jvmErasure.java.isArray) {
                ArraySerializer(serializers[0])
            } else {
                null
            }
        }
    }
}