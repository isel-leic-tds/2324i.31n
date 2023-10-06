package isel.tds

interface Stack<T> : Iterable<T> {
    val top: T
    fun push(elem: T): Stack<T>
    fun pop(): Stack<T>
    fun isEmpty(): Boolean


}

fun <T> stack(): Stack<T> = StackEmpty as Stack<T>

private class Node<T>(val elem: T, val next: Node<T>?)

private object StackEmpty : Stack<Any> {
    override val top: Nothing get() = throwEmpty()

    override fun pop() = throwEmpty()

    private fun throwEmpty(): Nothing = throw NoSuchElementException()


    override fun isEmpty() = true

    override fun push(elem: Any) = StackNotEmpty(Node(elem, null))


    override fun iterator() = object : Iterator<Nothing> {
        override fun hasNext() = false
        override fun next() = throwEmpty()
    }


}

private class StackNotEmpty<T>(private val head: Node<T>) : Stack<T> {
    override val top: T get() = head.elem

    override fun pop(): Stack<T> =
        head.next?.let { StackNotEmpty(it) } ?: stack()

    override fun isEmpty() = false

    override fun push(elem: T) = StackNotEmpty(Node(elem, head))
    override fun iterator() = object : Iterator<T> {
        var node: Node<T>? = head
        override fun hasNext() = node != null
        override fun next() =
            (node ?: throw NoSuchElementException("no more elements"))
                .also { node = it.next }.elem
    }


}

//fun <T> stackOf(vararg elements: T): Stack<T> {
//    var stk = stack<T>()
//    for (e in elements) stk = stk.push(e)
//    return stk
//}
//fun <T> stackOf(vararg elements: T): Stack<T> {
//    return elements.fold(stack<T>(), { s, e -> s.push(e)})
//}

//fun <T> stackOf(vararg elements: T): Stack<T> {
//    if (elements.isEmpty()) return stack()
//    var n: Node<T>? = null
//    for (e in elements) n = Node(e, n)
//    return StackNotEmpty<T>(n as Node<T>)
//}

fun <T> stackOf(vararg elements: T): Stack<T> {
    if (elements.isEmpty()) return stack()
    return StackNotEmpty<T>(
        elements.fold(
            null as Node<T>?
        )
        { n, e -> Node(e, n) }
                as Node<T>)
}
