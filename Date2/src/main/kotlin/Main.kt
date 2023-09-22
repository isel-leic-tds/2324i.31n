import isel.tds.DateOptimized
import isel.tds.DateOptimizedValue

fun main(args: Array<String>) {


    val a1 = 1
    val a2 = 1

    println("a1==a2=" + (a1 == a2))
    println("a1===a2=" + (a1 === a2))

    val aa1 = A(1)
    val aa2 = A(1)

    println("aa1==aa2=" + (aa1 == aa2))
    println("aa1===aa2=" + (aa1 === aa2))
    println(aa1)


    val b = B(1, "AA")

    val (i1, s1) = b

    val d1 = DateOptimizedValue(2020, 9, 12)

    val d2 = d1

    println(d2)

    val dd1 = DateOptimized(2020, 9, 12)

    val dd2 = dd1

    println(dd2)

}

class A(val a1: Int)


data class B(val b1: Int, val b2: String)