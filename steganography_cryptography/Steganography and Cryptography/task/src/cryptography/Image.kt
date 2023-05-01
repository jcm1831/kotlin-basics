package cryptography

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.IIOException
import javax.imageio.ImageIO

class ImageSizeException : Exception()

class Image(outputFileSwitch : Boolean = false) {
    private var inputFilename: String
    private var outputFilename: String = ""
    private lateinit var image : BufferedImage

    init {
        println("Input image file:")
        inputFilename = readln()
        if (outputFileSwitch) {
            println("Output image file:")
            outputFilename = readln()
            println("Input Image: $inputFilename")
            println("Output Image: $outputFilename")
        }
        try {
            readImage()
        } catch (e: IIOException) {
            println("Can't read input file!")
        }
    }

    fun hideMessage() {
        try {
            val message = Message().getEncryptedMessage()
            if (message.length() > size()) throw ImageSizeException()

            val it = message.iterator()
            loop@ for (y in 0 until image.height) {
                for (x in 0 until image.width) {
                    // get color of current pixel
                    val color = Color(image.getRGB(x, y))
                    // erase lsb and encode message bit
                    val colorNew = Color(
                        color.red, color.green,
                        (color.blue and 254) or it.next()
                    )
                    image.setRGB(x, y, colorNew.rgb)
                    // check for end of message
                    if (!it.hasNext()) break@loop
                }
            }

            saveImage()
        } catch (e: ImageSizeException) {
            println("The input image is not large enough to hold this message.")
        }
    }

    fun showMessage() {
        val message = Message()
        val password = Password()
        loop@ for (y in 0 until image.height) {
            for (x in 0 until image.width) {
                // reconstruct lsb
                message.addBit(Color(image.getRGB(x, y)).blue and 1)
                // regularly check message
                if (message.isReady()) {
                    message.print(password)
                    break@loop
                } else {
                    continue
                }
            }
        }
    }

    private fun size() = image.width * image.height

    private fun readImage() {
        val imageFile = File(inputFilename)
        image = ImageIO.read(imageFile)
    }

    private fun saveImage() {
        if (outputFilename.isNotEmpty()) {
            ImageIO.write(image, "png", File(outputFilename))
            println("Message saved in $outputFilename image.")
        } else {
            println("No output filename specified!")
        }
    }
}





