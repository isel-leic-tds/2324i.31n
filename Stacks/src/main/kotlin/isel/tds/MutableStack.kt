package isel.tds

class MutableStack1<T> {
    private var elems: List<T> = emptyList();
    val top: T get() = elems.last()
    fun push(elem: T) {
        elems = elems + elem
    }
    fun pop(): T {
        val myTop = top
        elems = elems.dropLast(1)
        return myTop
    }

}

class MutableStack2<T> {
    private val elems = mutableListOf<T>();
    val top: T get() = elems.last()
    fun push(elem: T) {
        elems.add(elem)
    }
    fun pop(): T = top.also { println(it); elems.removeLast() }

}


class MutableStack<T> {
    private class Node<T>(val elem: T, val next: Node<T>?)
    private var head: Node<T>? = null
    val top: T get() = head?.elem ?: throw NoSuchElementException("List is empty.")
    fun push(elem: T) {
        head = Node( elem, head)
    }
    fun pop(): T = top.also { head = head?.next }
}
