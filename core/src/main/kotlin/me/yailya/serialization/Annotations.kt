package me.yailya.serialization

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
annotation class Serializable

@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD)
annotation class UseSerializer(
    val with: KClass<out Serializer<out Any>>
)