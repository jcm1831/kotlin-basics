package cryptography

fun Byte.toBinary(): BitArray {
    val bits = arrayListOf(0, 0, 0, 0, 0, 0, 0, 0)
    var (byte, index) = Pair(this.toInt(), 0)
    while (byte > 0) {
        bits[index++] = byte % 2
        byte = byte shr 1
    }
    return BitArray(ArrayList(bits.reversed()))
}

fun ByteArray.toBinary(): BitArray {
    val bits = BitArray();
    forEach { byte -> bits.addAll(byte.toBinary()) }
    return bits
}

class BitArray(private val bits: ArrayList<Int> = arrayListOf()) {
    val size : Int
        get() = bits.size

    fun add(element : Int) = bits.add(element)
    fun addAll(elements: BitArray) = bits.addAll(elements.bits)
    fun iterator() = bits.iterator()
    fun toByteArray(): ByteArray {
        var (bytes, byte, placeValue) = Triple(arrayListOf<Byte>(), 0, 1)
        val binaries = bits.chunked(8)
        for (binary in binaries) {
            for (bit in binary.reversed()) {
                byte += bit * placeValue;
                placeValue = placeValue shl 1
            }
            bytes.add(byte.toByte())
            byte = 0
            placeValue = 1
        }
        return bytes.toByteArray()
    }
    fun encrypt(password : Password) {
        var it = password.bits.iterator()
        for (i in bits.indices) {
            if (!it.hasNext()) it = password.bits.iterator()
            bits[i] = bits[i] xor it.next()
        }
    }
}