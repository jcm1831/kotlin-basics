package contacts

typealias action = (ContactsApp) -> Unit

abstract class Menu {
    abstract fun enterAction(app: ContactsApp)
}

class MainMenu : Menu() {
    private val actionCallbacks = mapOf<String, action>(
        "add" to ContactsApp::addRecord,
        "list" to ContactsApp::listRecords,
        "search" to ContactsApp::searchRecords,
        "count" to ContactsApp::countRecords
    )

    override fun enterAction(app: ContactsApp) {
        while (true) {
            print("[menu] Enter action (add, list, search, count, exit): ")
            val action = readln()
            if (action == "exit") break
            val callback = getAction(action)
            if (callback != null) callback(app)
            println()
        }
    }

    private fun getAction(action: String): action? {
        return if (actionCallbacks.contains(action)) {
            actionCallbacks[action]
        } else {
            println("Unknown main menu action!")
            null
        }
    }
}


class RecordMenu(private val index: Int) : Menu() {
    override fun enterAction(app: ContactsApp) {
        print("[record] Enter action (edit, delete, menu): ")
        when (readln()) {
            "edit" -> {
                app.editRecord(index)
                app.toJson()
            }

            "delete" -> {
                app.removeRecord(index)
                app.toJson()
            }

            "menu" -> return
        }
    }
}

abstract class SubMenu(private val actions: Array<String>) : Menu() {
    abstract val typeId : String
    override fun enterAction(app: ContactsApp) {
        print("[$typeId] Enter action ([number], ${actions.joinToString(", ")}): ")
        when (val action = readln()) {
            "again" -> app.searchRecords()
            "back" -> return
            else -> {
                try {
                    val index = action.toInt() - 1
                    app.printRecord(index)
                    RecordMenu(index).enterAction(app)
                    return
                } catch (ex: NumberFormatException) {
                    return
                }
            }
        }
    }

}

class SearchMenu : SubMenu(arrayOf("back", "again")) {
    override val typeId: String = "search"
}

class ListMenu : SubMenu(arrayOf("back")) {
    override val typeId: String = "list"
}