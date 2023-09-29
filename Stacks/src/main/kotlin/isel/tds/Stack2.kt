package isel.tds

class Stack2<T> private constructor(private val head: Node2<T>?) : Iterable<T> {
    val top: T get() = first.elem

    private val first: Node2<T> get() = head ?: throw NoSuchElementException("Stack is empty.")

    private class Node2<T>(val elem: T, val next: Node2<T>?)

    constructor() : this(null)

    fun push(elem: T): Stack2<T> = Stack2(Node2(elem, head))

    fun isEmpty(): Boolean = head == null
    fun pop(): Stack2<T> = Stack2(first.next)

    fun pop2(): Pair<T, Stack2<T>> = Pair(top, Stack2(first.next))

    //    fun pop2(): Pair<T, Stack<T>> = top to Stack(first.next)
    fun forEach(action: (T) -> Unit) {
        var node = head
        while (node != null) {
            action(node.elem)
            node = node.next
        }
    }

    override fun iterator(): Iterator<T> = object : Iterator<T> {
        private var node = head
        override fun hasNext(): Boolean = node != null

        override fun next(): T =
            (node ?: throw NoSuchElementException())
                .also {
                    node = it.next
                }
                .elem
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Stack2<*>) return false
        var n = other.head
        for (elem in this) {
            if (n == null || elem != n.elem) return false
            n = n.next
        }
        return n == null
    }

    override fun hashCode() =
        this.fold(0) { acc, elem -> 31 * acc + elem.hashCode() }


}
