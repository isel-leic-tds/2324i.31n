fun main(args: Array<String>) {


    val a1 = 1
    val a2 = 1

    println("a1==a2="+ (a1==a2))
    println("a1===a2="+ (a1===a2))

    val aa1 = A(1)
    val aa2 = A(1)

    println("aa1==aa2="+ (aa1==aa2))
    println("aa1===aa2="+ (aa1===aa2))
    println(aa1)


    val b = B(1,"AA")

    val (i1,s1) = b
}

 class A( val a1: Int)


data class B( val b1: Int, val b2: String)