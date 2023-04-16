package contacts

import kotlinx.datetime.*

class OrganizationRecord : Record() {
    private var address: String = ""

    init {
        print("Enter the organization name: ")
        this.name = readln()
        print("Enter the address: ")
        this.address = readln()
        print("Enter the number: ")
        this.phoneNumber = readln()
    }

    override fun toString() = name

    override fun edit() {
        print("Select a field (organization name, address, number): ")
        when (readln()) {
            "name" -> {
                print("Enter organization name: ")
                name = readln()
            }

            "address" -> {
                print("Enter address name: ")
                address = readln()
            }

            "number" -> {
                print("Enter number: ")
                phoneNumber = readln()
            }
        }
        timeLastEdited = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Berlin"))
        println("OrganizationRecord was updated.")
    }

    override fun print() {
        println("Organization name: $name")
        println("Address: $address")
        println("Number: ${phoneNumber.ifEmpty { "[no data]" }}")
        println("Time created: $timeCreated")
        println("Time last edit: $timeLastEdited")
    }
}