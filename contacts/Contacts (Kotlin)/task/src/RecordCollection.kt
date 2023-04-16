package contacts

import com.squareup.moshi.Types

class RecordCollection(private val records: MutableList<Record> = mutableListOf()) {
    private val types = Types.newParameterizedType(
        MutableList::class.java,
        Record::class.java,
    )
    private val recordCollectionAdapter = moshi.adapter<MutableList<Record>>(types)


    fun toJson(): String {
        return recordCollectionAdapter.toJson(records)
    }

    fun fromJson(jsonString: String) {
        val storedRecords = recordCollectionAdapter.fromJson(jsonString)!!
        records.addAll(storedRecords)
    }

    fun add(record: Record?) {
        if (record != null) {
            records.add(record)
            println("The record added.")
        }
    }

    fun search(matcher: String): RecordCollection {
        val results = records.filter { it.isMatch(matcher) }.toMutableList()
        println("Found ${results.size} results")
        return RecordCollection(results)
    }

    fun count() {
        println("The app has ${records.count()} records.")
    }

    fun edit(index: Int) {
        if (records.size > 0) {
            if (index < records.size) {
                records[index].edit()
                println("Record was updated!")
            } else {
                println("Invalid record. No record edited.")
            }
        } else {
            println("No records to edit!")
        }
    }

    fun remove(index: Int) {
        if (records.size > 0) {
            if (index < records.size) {
                records.removeAt(index)
                println("Record was removed.")
            } else {
                println("Invalid record. No record removed.")
            }
        } else {
            println("No records to remove!")
        }
    }

    fun print(index: Int) {
        if (index >= 0 && index < records.size) {
            records[index].print()
            println()
        } else {
            println("Invalid record. No record printed.")
        }
    }

    fun print() {
        records.forEachIndexed { index, record ->
            println("${index + 1}. $record")
        }
        println()
    }
}