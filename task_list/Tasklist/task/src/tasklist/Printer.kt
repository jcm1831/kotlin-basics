package tasklist

object Printer {
    private const val TASK_CHARACTERS = 44

    fun printTasklist(tasks: MutableList<Task>) {
        printHead()
        tasks.forEachIndexed { index, task -> printTask(index, task) }
        printSeparationLine()
    }

    private fun printTask(taskIndex: Int, task: Task) {
        printSeparationLine()
        printFirstLine(taskIndex, task)
        printTasklines(task.taskLines)
    }

    private fun printHead() {
        printSeparationLine()
        println(
            "| N  |    Date    | Time  | P | D |" + " ".repeat(19) + "Task"
                    + " ".repeat(21) + "|"
        )
    }

    private fun printSeparationLine() {
        println(
            "+${"-".repeat(4)}+${"-".repeat(12)}+${"-".repeat(7)}" +
                    "${"+---".repeat(2)}+${"-".repeat(44)}+"
        )
    }

    private fun printFirstLine(taskIndex: Int, task: Task) {
        print("| ${taskIndex + 1}  | ${task.date} | ${task.time} | ${task.priorityTag.color} | ${task.dueTag.color} ")
    }

    private fun printTasklines(taskLines: MutableList<String>) {
        if (taskLines.isNotEmpty()) {
            taskLines.forEachIndexed { index, task ->
                val lines = formatTaskline(task)
                // print first line separately
                printFirstTaskline(lines.first(), index)
                // print all other lines
                lines.removeAt(0)
                lines.forEach { line -> printTaskline(line) }
            }
        } else {
            printFirstTaskline("", 0)
        }
    }

    private fun printFirstTaskline(line: String, index: Int) {
        if (index == 0) {
            println("|${line}${" ".repeat(TASK_CHARACTERS - line.length)}|")
        } else {
            printTaskline(line)
        }
    }

    private fun printTaskline(line: String) {
        println(
            "|${" ".repeat(4)}|${" ".repeat(12)}|${" ".repeat(7)}" +
                    "${"|   ".repeat(2)}|${line}${
                        " ".repeat(TASK_CHARACTERS - line.length)
                    }|"
        )
    }

    private fun formatTaskline(taskLine: String): ArrayList<String> {
        val formattedLines = ArrayList<String>()
        formattedLines.addAll(taskLine.chunked(TASK_CHARACTERS))
        return formattedLines
    }
}