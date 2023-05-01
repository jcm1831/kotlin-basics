package cryptography

typealias callback = () -> Unit

class Menu {
    private val callbacks = mapOf(
        "show" to ::show,
        "hide" to ::hide,
        "exit" to ::exit
    )

    fun inputAction() {
        do {
            println("Task (hide, show, exit):")
            val action = readln()
            val callback = getCallback(action)
            callback?.invoke()
        } while (action != "exit")
    }

    private fun getCallback(action: String): callback? {
        return if (callbacks.containsKey(action)) {
            callbacks[action]
        } else {
            println("Wrong task: $action")
            null
        }
    }

    private fun show() {
        val image = Image(outputFileSwitch = false)
        image.showMessage()
    }

    private fun hide() {
        val image = Image(outputFileSwitch = true)
        image.hideMessage()
    }
    private fun exit() = println("Bye!")
}