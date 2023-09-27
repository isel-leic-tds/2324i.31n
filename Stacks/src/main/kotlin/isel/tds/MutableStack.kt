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
        head = Node(elem, head)
    }

    fun pop(): T = top.also { head = head?.next }
    override fun equals(other: Any?): Boolean {
        if (other !is MutableStack<*>) return false
        var n1 = head
        var n2 = other.head
        while (n1 != null && n2 != null) {
            if (n1.elem != n2.elem) return false
            n1 = n1.next
            n2 = n2.next
        }
        return n1 == null && n2 == null
    }

    override

    fun hashCode(): Int {
        var n = head
        var hash = 0
        while (n != null) {
            hash = 31 * hash + n.elem.hashCode()
            n = n.next
        }
        return hash

    }


}
