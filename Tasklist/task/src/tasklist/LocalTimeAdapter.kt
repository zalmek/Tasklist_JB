package tasklist

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalTime

class LocalTimeAdapter {
    @ToJson
    fun toJson(time: LocalTime): String{
        return time.toString()
    }
    @FromJson
    fun fromJson(time: String): LocalTime {
        return LocalTime.parse(time)
    }
}