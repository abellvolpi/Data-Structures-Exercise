package com.example.myapplication

class MyArrayList<T : Any> : PSList<T> {

    private var dataArray: Array<Any?> = Array(DEFAULT_LIST_INIT_SIZE) {
        Any()
    }
    private var count = 0


    override fun contains(item: T): Boolean {
        dataArray.forEach {
            if (it == item) {
                return true
            }
        }
        return false
    }

    override fun get(position: Int): T {
        if (position < 0 || position > count) {
            throw ArrayIndexOutOfBoundsException()
        }
        return dataArray[position] as T
    }

    override fun add(item: T) {
        if (count < dataArray.size) {
            dataArray[count++] = item
            return
        }
        var newdataArray: Array<Any?> = Array(dataArray.size + DEFAULT_LIST_INIT_SIZE) {
            if (it < count) {
                dataArray[it]
            } else null
        }
        newdataArray[count++] = item
        dataArray = newdataArray


    }

    override fun remove(position: Int): Boolean {
        if (position < 0 || position > count) {
            throw ArrayIndexOutOfBoundsException()
            return false
        }
        if (dataArray.size - count < DEFAULT_LIST_INIT_SIZE) {
            for (index in dataArray.indices) {
                when (index) {
                    in 0 until position -> continue

                    in position until count -> {
                        if (index + 1 == dataArray.size) {
                            dataArray[index] = Any()
                        } else {
                            dataArray[index] = dataArray[index + 1]
                        }
                    }
                    else -> dataArray[index] = Any()
                }
            }
            return true
        } else {
            val newdataArray: Array<Any?> = Array(dataArray.size) {
                when (it) {
                    in 0 until position -> dataArray[it]
                    in position until count -> {
                        if (it + 1 == dataArray.size) {
                            Any()
                        } else {
                            dataArray[it + 1]
                        }
                    }
                    else -> Any()
                }
            }
            count--
            dataArray = newdataArray
            return true
        }
    }

    override fun size(): Int {
        return count
    }

    override fun realSize(): Int {
        return dataArray.size
    }
}


