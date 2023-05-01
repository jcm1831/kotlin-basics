package cryptography

class Message {
    companion object {
        const val END_SEQUENCE = "\u0000\u0000\u0003"
    }

    private val bits = BitArray()
    private var message = ""


    fun addBit(bit: Int) = bits.add(bit)

    fun getEncryptedMessage(): Message {
        println("Message to hide:")
        bits.addAll(readln().encodeToByteArray().toBinary())
        bits.encrypt(Password())
        bits.addAll(END_SEQUENCE.encodeToByteArray().toBinary())
        return this
    }

    fun iterator() = bits.iterator()

    fun isReady(): Boolean {
        return if (bits.size % 8 == 0) {
            message = bits.toByteArray().toString(Charsets.UTF_8)
            message.length >= 3 && hasEndSequence()
        } else {
            false
        }
    }

    fun length() = bits.size
    fun print(password: Password) {
        bits.encrypt(password)
        message = bits.toByteArray().toString(Charsets.UTF_8)
        println("Message:\n${message.substring(0, message.length - 3)}")
    }

    private fun hasEndSequence() =
        message.substring(message.length - 3) == END_SEQUENCE
}