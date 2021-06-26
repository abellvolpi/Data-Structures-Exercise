package com.example.myapplication

class Node<T : Any>(
    var data: T,
    var nextNode: Node<T>?
)

class MyLinkedList<T : Any> : PSList<T> {

    private var first: Node<T>? = null
    private var lastnode: Node<T>? = null
        get() {
            var currentNode = first
            while (currentNode?.nextNode != null) {
                currentNode = currentNode.nextNode
            }
            return currentNode
        }

    private var count = 0

    override fun contains(item: T): Boolean {
        var currentNode = first
        while (currentNode != null) {
            if (currentNode.data == item) {
                return true
            }
            currentNode = currentNode.nextNode
        }
        return false
    }

    override fun get(position: Int): T {
        if (position < 0 || position > count) {
            throw ArrayIndexOutOfBoundsException()
        }
        var currentNode = first
        (0 until position).forEach {
            currentNode = currentNode!!.nextNode
        }
        return currentNode!!.data
    }

    override fun add(item: T) {
        val newNode = Node(item, null)
        if (first == null) {
            first = newNode
        } else {
            lastnode?.nextNode = newNode
        }
    }


    override fun remove(position: Int): Boolean {
        if (position < 0 || position > count) {
            throw ArrayIndexOutOfBoundsException()
            return false
        }
        if (position == 0) {
            first = first?.nextNode
            return true
        }
        var before = first
        repeat(position - 1) {
            before = before?.nextNode
        }
        before?.nextNode = before?.nextNode?.nextNode
        return true
    }

    override fun size(): Int {
        count = 0
        var currentNode = first
        while (currentNode != null) {
            count++
            currentNode = currentNode.nextNode
        }
        return count
    }

    override fun realSize(): Int {
        count = 0
        var currentNode = first
        while (currentNode != null) {
            count++
            currentNode = currentNode.nextNode
        }
        return count

    }
}