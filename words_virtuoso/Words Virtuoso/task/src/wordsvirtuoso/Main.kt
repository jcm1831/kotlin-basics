package wordsvirtuoso

fun main(args: Array<String>) {
    if (args.size == 2) {
        val (wordsFilepath, candidatesFilepath) = args
        val game = Game(wordsFilepath, candidatesFilepath)
        game.run()
    } else {
        println("Error: Wrong number of arguments.")
    }
}
