package tasklist

import kotlinx.datetime.*

enum class PriorityTags(val tag : String, val color : String) {
    CRITICAL("C", "\u001B[101m \u001B[0m"),
    HIGH("H", "\u001B[103m \u001B[0m"),
    NORMAL("N", "\u001B[102m \u001B[0m"),
    LOW("L", "\u001B[104m \u001B[0m");

    companion object Factory {
        fun create(tag : String) : PriorityTags? {
            return when(tag) {
                "C" -> CRITICAL
                "H" -> HIGH
                "N" -> NORMAL
                "L" -> LOW
                else -> null
            }
        }
    }
}

enum class DueTags(val tag : String, val color : String) {
    INTIME("I", "\u001B[102m \u001B[0m"),
    TODAY("T", "\u001B[103m \u001B[0m"),
    OVERDUE("O", "\u001B[101m \u001B[0m");

    companion object Factory {
        fun create(numberOfDays : Int) : DueTags {
            return when {
                numberOfDays == 0 -> TODAY
                numberOfDays < 0 -> OVERDUE
                else -> INTIME
            }
        }
    }
}

class Task(var taskLines : MutableList<String> = mutableListOf()) {
    lateinit var date : String
    lateinit var time : String
    lateinit var priorityTag : PriorityTags
    lateinit var dueTag : DueTags

    fun makeFromUserInput() : Task {
        inputPriorityTag()
        inputDate()
        inputTime()
        updateDueTag()
        inputTaskLines()
        return this
    }

    fun editField(field : String) {
        when (field) {
            "priority" -> {
                inputPriorityTag()
            }
            "date" -> {
                inputDate()
                updateDueTag()
            }
            "time" -> {
                inputTime()
                updateDueTag()
            }
            "task" -> {
                taskLines.clear()
                inputTaskLines()
            }
        }
    }


    private fun inputPriorityTag() {
        while (true) {
            println("Input the task priority (C, H, N, L):")
            when (val input = readln().uppercase()) {
                "C", "H", "N", "L" -> {
                    priorityTag = PriorityTags.create(input)!!
                    break
                }

                else -> {
                    continue
                }
            }
        }
    }

    private fun inputDate() {
        while (true) {
            println("Input the date (yyyy-mm-dd):")
            val input = readln()
            if ("\\d{4}-\\d{1,2}-\\d{1,2}".toRegex().matches(input)) {
                val (year, month, day) = input.split("-").map { it.toInt() }
                try {
                    val localDate = LocalDate(year, month, day)
                    date = localDate.toString()
                    break
                } catch (e: IllegalArgumentException) {
                    println("The input date is invalid")
                    continue
                }
            } else {
                println("The input date is invalid")
                continue
            }
        }
    }

    private fun inputTime() {
        while (true) {
            println("Input the time (hh:mm):")
            val input = readln()
            if ("\\d{1,2}:\\d{1,2}".toRegex().matches(input)) {
                val (year, month, day) = date.split("-").map { it.toInt() }
                val (hours, minutes) = input.split(":").map { it.toInt() }
                try {
                    val dateTime = LocalDateTime(year, month, day, hours, minutes)
                    time = dateTime.toString().substringAfter("T")
                    break
                } catch (e: IllegalArgumentException) {
                    println("The input time is invalid")
                    continue
                }
            } else {
                println("The input time is invalid")
                continue
            }
        }
    }

    private fun updateDueTag() {
        val (year, month, day) = date.split("-").map { it.toInt() }
        val taskDate = LocalDate(year, month, day)
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("UTC+0")).date
        val numberOfDays = currentDate.daysUntil(taskDate)
        dueTag = DueTags.create(numberOfDays)
    }

    private fun inputTaskLines() {
        println("Input a new task (enter a blank line to end):")
        while (true) {
            when (val taskLine = readln().trim()) {
                "" -> {
                    if (taskLines.size == 0) println("The task is blank")
                    break
                }
                else -> {
                    taskLines.add(taskLine)
                }
            }
        }
    }
}



