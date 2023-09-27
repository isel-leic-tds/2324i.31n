package isel.tds

class Stack<T> private constructor(private val head: Node<T>?) {
    val top: T get() = first.elem

    private val first: Node<T> get() = head ?: throw NoSuchElementException("Stack is empty.")

    private class Node<T>(val elem: T, val next: Node<T>?)

    constructor() : this(null)

    fun push(elem: T): Stack<T> = Stack(Node(elem, head))

    fun isEmpty(): Boolean = head == null
    fun pop(): Stack<T> = Stack(first.next)

    fun pop2(): Pair<T, Stack<T>> = Pair(top, Stack(first.next))
//    fun pop2(): Pair<T, Stack<T>> = top to Stack(first.next)


}