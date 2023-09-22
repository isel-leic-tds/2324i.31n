import isel.tds.MutableStack

fun main(args: Array<String>) {
    println("Hello Stacks!")

    val myStack = MutableStack<String>()

    myStack.push("ISEL")
    myStack.push("ISCAL")

    println(myStack.pop())
    println(myStack.top)


}