package isel.tds

import org.junit.jupiter.api.Test

class StackTest {

    @Test
    fun `Imutable Stack creation`() {
        val empty = Stack<Char>()   // Immutable empty stack
        val one = empty.push('A')
        val two = one.push('B')
        print(empty.isEmpty())   // Output: true
        print(one.top)           // Output: A
        print(two.top)           // Output: B

        var stk1 = Stack<Int>().push(1).push(2).push(3)
        while (!stk1.isEmpty()) {
            print(stk1.top)
            stk1 = stk1.pop()
        } // Output: 321

        var stk2 = Stack<Int>().push(1).push(2).push(3)
        while (!stk2.isEmpty()) {
            val res = stk2.pop2()
            print(res.first)
            stk2 = res.second
        }  // Output: 321
    }
}