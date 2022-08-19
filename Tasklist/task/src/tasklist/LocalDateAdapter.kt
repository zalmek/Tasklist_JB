package tasklist

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import kotlinx.datetime.toLocalDate
import java.time.LocalDate

//@JsonAdapter
class LocalDateAdapter {
    @ToJson
    fun toJson(date: kotlinx.datetime.LocalDate): String{
        return date.toString()
    }
    @FromJson
    fun fromJson(date: String): kotlinx.datetime.LocalDate {
        return date.toLocalDate()
    }
}