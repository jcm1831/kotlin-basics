package contacts

import kotlinx.datetime.*

class PersonRecord : Record() {
    private var surname: String = ""
    private var birthDate: String = ""
        set(value) {
            field = value.ifEmpty {
                println("Bad birth date!")
                ""
            }
        }
    private var gender: String = ""
        set(value) {
            field = if (value == "M" || value == "F") {
                value
            } else {
                println("Bad gender!")
                ""
            }
        }
    init {
        print("Enter the name: ")
        this.name = readln()
        print("Enter the surname: ")
        this.surname = readln()
        print("Enter the birth date: ")
        this.birthDate = readln()
        print("Enter the gender (M, F): ")
        this.gender = readln()
        print("Enter the number: ")
        this.phoneNumber = readln()
    }

    override fun toString() = "$name $surname"


    override fun edit() {
        print("Select a field (name, surname, birth, gender, number): ")
        when (readln()) {
            "name" -> {
                print("Enter name: ")
                name = readln()
            }

            "surname" -> {
                print("Enter surname: ")
                surname = readln()
            }

            "birth" -> {
                print("Enter birth date: ")
                birthDate = readln()
            }

            "gender" -> {
                print("Enter gender (M, F): ")
                gender = readln()
            }

            "number" -> {
                print("Enter number: ")
                phoneNumber = readln()
            }
        }
        timeLastEdited = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin"))
        println("PersonRecord was updated.")
    }

    override fun print() {
        println("Name: $name")
        println("Surname: $surname")
        println("Birth date: ${birthDate.ifEmpty { "[no data]" }}")
        println("Gender: ${gender.ifEmpty { "[no data]" }}")
        println("Number: ${phoneNumber.ifEmpty { "[no data]" }}")
        println("Time created: $timeCreated")
        println("Time last edit: $timeLastEdited")
    }
}