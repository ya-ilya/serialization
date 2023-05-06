package me.yailya.serialization

import kotlin.reflect.KClass

annotation class Serializable

annotation class UseSerializer(
    val with: KClass<out Serializer<*>>
)