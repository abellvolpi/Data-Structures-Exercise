package com.example.myapplication

const val DEFAULT_LIST_INIT_SIZE = 5

interface PSList<T : Any> {
    fun contains(item: T): Boolean
    operator fun get(position: Int): T
    fun add(item: T)
    fun remove(position: Int): Boolean
    fun size(): Int
    fun realSize(): Int

}