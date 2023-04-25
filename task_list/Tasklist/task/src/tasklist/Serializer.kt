package tasklist

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object Serializer {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val type = Types.newParameterizedType(MutableList::class.java,
        Task::class.java, MutableList::class.java)
    private val tasklistAdapter = moshi.adapter<MutableList<Task>>(type)

    fun toJson(tasks : MutableList<Task>) : String {
        return tasklistAdapter.toJson(tasks)
    }
    fun fromJson(jsonString: String) : MutableList<Task>? {
        return tasklistAdapter.fromJson(jsonString)
    }
}