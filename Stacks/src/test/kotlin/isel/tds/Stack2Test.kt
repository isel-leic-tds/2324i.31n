package isel.tds

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class Stack2Test {

    @Test
    fun `Imutable Stack creation`() {
        val empty = Stack2<Char>()   // Immutable empty stack
        val one = empty.push('A')
        val two = one.push('B')
        print(empty.isEmpty())   // Output: true
        print(one.top)           // Output: A
        print(two.top)           // Output: B

        var stk1 = Stack2<Int>().push(1).push(2).push(3)
        while (!stk1.isEmpty()) {
            print(stk1.top)
            stk1 = stk1.pop()
        } // Output: 321

        var stk2 = Stack2<Int>().push(1).push(2).push(3)
        while (!stk2.isEmpty()) {
            val res = stk2.pop2()
            print(res.first)
            stk2 = res.second
        }  // Output: 321

    }

    @Test
    fun `test2`() {
        val stk = Stack2<Int>().push(1).push(2).push(3)
        var sum = 0
//        var xx = emptyList<String>()
//        stk.forEach { sum += it }
        for (e in stk) sum += e
        println("sum = $sum, top = ${stk.top}") // Output: sum = 6, top = 3
    }

    @Test
    fun `empty stack conditions`() {
        val stk = Stack2<Int>()
        assertTrue(stk.isEmpty())
        assertFailsWith<NoSuchElementException> { stk.top }
        assertFailsWith<NoSuchElementException> { stk.pop() }
    }

    @Test
    fun `not empty stack conditions`() {
        val stk = Stack2<String>().push("ISEL")
        assertFalse(stk.isEmpty())
        assertEquals("ISEL", stk.top)
        assertTrue(stk.pop().isEmpty())
    }

    @Test
    fun `stack operations`() {
        val stk = Stack2<Char>().push('A').push('B').push('C')
        assertEquals('C', stk.top)
        assertEquals('B', stk.pop().top)
        val one = stk.pop().pop()
        assertEquals('A', one.top)
    }


}