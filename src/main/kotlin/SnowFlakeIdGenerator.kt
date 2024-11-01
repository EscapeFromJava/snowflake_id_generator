package com.reske

import java.time.LocalDateTime
import java.util.*

fun main(args: Array<String>) {
    val worker = "0001"
    val counter = "abcdef000023"
    val uuid = generate(worker, counter)
    println(uuid)
}

fun generate(
    worker: String,
    counter: String
): UUID {
    if (worker.length != 4) {
        throw IllegalArgumentException("Worker $worker does not match expected 4 characters long.")
    }
    if (counter.length != 12) {
        throw IllegalArgumentException("Counter $counter does not match expected 12 characters long.")
    }
    if (notForbidden(counter)) {
        throw IllegalArgumentException("Counter $counter contains not forbidden characters.")
    }
    val sb = StringBuilder()
    val dateTime = LocalDateTime.now()
    sb.append(dateTime.year)
    sb.append(getValueWithZero(dateTime.monthValue))
    sb.append(getValueWithZero(dateTime.dayOfMonth))
    sb.append("-")
    sb.append(getValueWithZero(dateTime.hour))
    sb.append(getValueWithZero(dateTime.minute))
    sb.append("-")
    sb.append(getValueWithZero(dateTime.second))
    sb.append("00")
    sb.append("-")
    sb.append(worker)
    sb.append("-")
    sb.append(counter)
    return UUID.fromString(sb.toString())
}

const val notForbidden = "abcdef0123456789"

fun notForbidden(value: String): Boolean {
    value.toCharArray().forEach {
        if (!notForbidden.contains(it)) {
            return true
        }
    }
    return false
}

fun getValueWithZero(value: Int): String {
    return if (value < 10) {
        "0$value"
    } else {
        value.toString()
    }
}