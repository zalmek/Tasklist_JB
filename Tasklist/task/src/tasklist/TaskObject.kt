import kotlinx.datetime.*
import java.time.LocalTime

data class TaskObject(
    var task: String = "", var priority: String = "", var date: LocalDate = LocalDate(2022, 1, 9), var time: LocalTime = LocalTime.parse("00:01")
) {

}