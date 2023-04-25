package tasklist

import java.io.File

typealias actionCallback = () -> Unit

const val PATHNAME = "tasklist.json"

class Menu {
    private val tasks = mutableListOf<Task>()
    private val callbacks = mapOf(
        "add" to ::add,
        "print" to ::print,
        "edit" to ::edit,
        "delete" to ::delete,
        "end" to ::end
    )

    init {
        val jsonFile = File(PATHNAME)
        if (jsonFile.exists()) {
            tasks.addAll(Serializer.fromJson(jsonFile.readText())!!)
        }
    }

    fun inputAction() {
        while (true) {
            println("Input an action (add, print, edit, delete, end):")
            val action = readln().lowercase()
            val callback = getAction(action)
            if (callback != null) {
                callback.invoke()
                if (action == "end") {
                    File(PATHNAME).writeText(Serializer.toJson(tasks))
                    break
                }
            } else {
                continue
            }
        }
    }



    private fun getAction(action : String) : actionCallback? {
        return if (callbacks.containsKey(action)) {
            callbacks[action]
        } else {
            println("The input action is invalid")
            null
        }
    }

    private fun add() {
        tasks.add(Task().makeFromUserInput())
    }

    private fun print() {
        if (tasks.isNotEmpty()) {
            Printer.printTasklist(tasks)
        } else {
            println("No tasks have been input")
        }
    }
    private fun edit() {
        print()
        if (tasks.isNotEmpty()) {
            outerLoop@ while (true) {
                println("Input the task number (1-${tasks.size}):")
                val taskNumber = readln().toIntOrNull()
                if (taskNumber != null && taskNumber in 1..tasks.size) {
                    while (true) {
                        println("Input a field to edit (priority, date, time, task):")
                        when (val field = readln()) {
                            "priority", "date", "time", "task" -> {
                                tasks[taskNumber-1].editField(field)
                            }
                            else -> {
                                println("Invalid field")
                                continue
                            }
                        }
                        println("The task is changed")
                        break@outerLoop
                    }
                } else {
                    println("Invalid task number")
                    continue
                }
            }
        }
    }
    private fun delete() {
        print()
        if (tasks.isNotEmpty()) {
            while (true) {
                println("Input the task number (1-${tasks.size}):")
                val taskNumber = readln().toIntOrNull()
                if (taskNumber != null && taskNumber in 1..tasks.size) {
                    tasks.removeAt(taskNumber-1)
                    println("The task is deleted")
                    break
                } else {
                    println("Invalid task number")
                    continue
                }
            }
        }
    }
    private fun end() = println("Tasklist exiting!")
}