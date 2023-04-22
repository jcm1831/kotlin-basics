package flashcards

fun main(args: Array<String>) {
    // initialize file handler with command-line arguments
    FileHandler.getFilenames(args)
    // generate user menu
    val menu = FlashcardsMenu(FlashcardCollection())
    // ask user for action
    menu.inputAction()
}
