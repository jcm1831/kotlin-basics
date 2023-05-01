package cryptography

data class Password(var value : String = "") {
    var bits = BitArray()
    init {
        println("Password:")
        value = readln()
        bits.addAll(value.encodeToByteArray().toBinary())
    }
}
