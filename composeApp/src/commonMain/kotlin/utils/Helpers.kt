package utils

fun formattedAmountLong(amount: Long): String {
    val formattedAmount = StringBuilder()
    val amountString = amount.toString()
    var count = 0

    for (i in amountString.length - 1 downTo 0) {
        formattedAmount.append(amountString[i])
        count++
        if (count % 3 == 0 && i > 0) {
            formattedAmount.append(",")
        }
    }

    return formattedAmount.reverse().toString()
}
fun formatAmountDouble(amount: Double): String {
    val formattedAmount = StringBuilder()
    val amountString = amount.toString()
    var count = 0

    for (i in amountString.length - 1 downTo 0) {
        formattedAmount.append(amountString[i])
        count++
        if (count % 3 == 0 && i > 0) {
            formattedAmount.append(",")
        }
    }

    return formattedAmount.reverse().toString()
}
fun Boolean?.toLong() = if (this == true) 1L else 0L

fun Long?.toBoolean() = if (this == 1L) true else false