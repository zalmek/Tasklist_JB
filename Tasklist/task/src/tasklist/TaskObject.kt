import com.squareup.moshi.JsonClass
import kotlinx.datetime.*
import java.time.LocalTime
@JsonClass(generateAdapter = true)
data class TaskObject(
    var task: String = "", var priority: String = "", var date: LocalDate = LocalDate(2022, 1, 9), var time: LocalTime = LocalTime.parse("00:01")
) {

}