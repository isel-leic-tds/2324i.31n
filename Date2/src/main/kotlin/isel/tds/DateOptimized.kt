package isel.tds

private const val DAY_BITS = 5   // 0..31
private const val MONTH_BITS = 4 // 0..15
private const val YEAR_BITS = 12 // 0..4095


class DateOptimized(year: Int, month: Int = 1, day: Int = 1) {
    private val bits: Int =
        (year shl (DAY_BITS + MONTH_BITS)) or (month shl DAY_BITS) or day


    val year: Int get() = bits shr (DAY_BITS + MONTH_BITS)
    val month: Int get() = (bits shr DAY_BITS) and ((1 shl MONTH_BITS) - 1)
    val day: Int get() = bits and ((1 shl DAY_BITS) - 1)

    override fun equals(other: Any?) = other is DateOptimized && bits == other.bits
    override fun hashCode() = bits
    operator fun compareTo(other: DateOptimized) = bits - other.bits

}
//Cannot access 'bits': it is private in 'DateOptimized'
//operator fun DateOptimized.compareTo(other: DateOptimized) = bits - other.bits

@JvmInline
value class DateOptimizedValue private constructor(private val bits: Int) {
    val year: Int get() = bits shr (DAY_BITS + MONTH_BITS)
    val month: Int get() = (bits shr DAY_BITS) and ((1 shl MONTH_BITS) - 1)
    val day: Int get() = bits and ((1 shl DAY_BITS) - 1)


    constructor(y: Int, m: Int = 1, d: Int = 1) : this(
        (y shl (DAY_BITS + MONTH_BITS)) or (m shl DAY_BITS) or d
    ) {
        require(y in 0..4000) { "Invalid year=$year" }
        require(m in 1..12) { "Invalid month=$month" }
//        require(d in 1..lastDayOfMonth) { "Invalid day=$day" }
    }

    override fun toString() = "$year-" + "%02d-%02d".format(month, day)
    operator fun compareTo(other: DateOptimizedValue) = bits - other.bits
}
