package tasklist

import TaskObject
import kotlinx.datetime.*
import java.time.LocalTime
import kotlin.math.ceil
import kotlin.math.roundToInt

fun daysUntil(currentDate: LocalDate, taskDate: LocalDate): String {
    val numberOfDays = currentDate.daysUntil(taskDate)
    if (numberOfDays > 0) {
        return "I"
    } else if (numberOfDays == 0) {
        return "T"
    } else
        return "O"
}

private fun setTask(
    taskObject: TaskObject,
) {
    println("Input a new task (enter a blank line to end):")
    var inputLine1: String
    inputLine1 = readln().trim()
    var line = ""
    line += inputLine1
    while (inputLine1 != "") {
        inputLine1 = readln().trim()
        line += "\n$inputLine1"
    }
    if (line == "") {
        println("The task is blank")

    } else {
        taskObject.task = line.trimEnd()
    }
}

private fun getTime(): String {
    println("Input the time (hh:mm):")
    var time = correctTime(readln().trim())
    while (true) {
        try {
            LocalTime.parse(time)
            break
        } catch (e: Exception) {
            println("The input time is invalid")
            println("Input the time (hh:mm):")
            time = correctTime(readln().trim())
        }
    }
    return time
}

private fun getDate(): String {
    println("Input the date (yyyy-mm-dd):")
    var date = correctDate(readln().trim())
    while (true) {
        try {
            //println(date)
            date.toLocalDate()
            break
        } catch (e: Exception) {
            println("The input date is invalid")
            println("Input the date (yyyy-mm-dd):")
            date = correctDate(readln().trim())
        }
    }
    return date
}


fun getPriority(): String {
    while (true) {
        println("Input the task priority (C, H, N, L):")
        when (readln().trim()) {
            "C" -> {
                return "C"
            }

            "H" -> {
                return "H"
            }

            "N" -> {
                return "N"
            }

            "L" -> {
                return "L"
            }

            "c" -> {
                return "C"
            }

            "h" -> {
                return "H"
            }

            "n" -> {
                return "N"
            }

            "l" -> {
                return "L"
            }

            else -> continue
        }
    }
}

fun correctDate(date: String): String {
    var correctdate: String = date
    if (!correctdate.contains(Regex("-\\d{1,2}-"))) {
        return date
    }
    if (date.length == 10) {
        return date
    } else {
        if (date[6] == '-') {
            correctdate = date.slice(0..4) + "0" + date.slice(5 until date.length)
        }
        if (correctdate.length != 10) {
            return correctdate.slice(0..7) + "0" + date[date.length - 1]
        } else {
            return correctdate
        }
    }
}

fun correctTime(time: String): String {
    if (time.length == 4) {
        if (time[1] == ':') {
            return "0$time"
        } else
            return time.slice(0..2) + "0" + time[time.length - 1]
    } else if (time.length == 3) {
        return "0" + time.slice(0..1) + "0" + time[time.length - 1]
    } else
        return time
}

fun convertTaskToStr(task: String): String {
    var count=0
    var initStr = ""
    for (i in task.indices){
        if (task[i]=='\n' || count==44){
            initStr+=" ".repeat(44-count)+"|\n|    |            |       |   |   |"
            count=0
            if (task[i]!='\n'){
                initStr+=task[i]
                count+=1
            }
        }
        else{
            initStr+=task[i]
            count+=1
        }
    }
    initStr+=" ".repeat(44-count)+"|"+"\n+----+------------+-------+---+---+--------------------------------------------+"
    return initStr
}


fun main() {
    println("Input an action (add, print, edit, delete, end):")
    var inputLine = readln().trim()
    val list = mutableListOf<TaskObject>()
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("UTC+0")).date
    while (true) {
        when (inputLine) {
            "end" -> {
                println("Tasklist exiting!")
                return
            }


            "edit" -> {
                if (list.isEmpty()) {
                    println("No tasks have been input")
                } else {
                    printTasklist(list, currentDate)
                    loop@ while (true) {
                        println("Input the task number (1-${list.size}):")
                        inputLine = readln().trim()
                        try {
                            val number = inputLine.toInt()
                            if (number in 1..list.size) {
                                while (true) {
                                    println("Input a field to edit (priority, date, time, task):")
                                    inputLine = readln().trim()
                                    when (inputLine) {
                                        "priority" -> {
                                            list[number - 1].priority = getPriority()
                                            println("The task is changed")
                                            break@loop
                                        }

                                        "date" -> {
                                            list[number - 1].date = getDate().toLocalDate()
                                            println("The task is changed")
                                            break@loop
                                        }

                                        "time" -> {
                                            list[number - 1].time = LocalTime.parse(getTime())
                                            println("The task is changed")
                                            break@loop
                                        }

                                        "task" -> {
                                            setTask(taskObject = list[number - 1])
                                            println("The task is changed")
                                            break@loop
                                        }

                                        else -> {
                                            println("Invalid field")
                                        }
                                    }
                                }
                            } else
                                println("Invalid task number")
                        } catch (e: Exception) {
                            println("Invalid task number")
                        }
                    }
                }
            }


            "delete" -> {
                if (list.isEmpty()) {
                    println("No tasks have been input")
                } else {
                    printTasklist(list, currentDate)
                    while (true) {
                        println("Input the task number (1-${list.size}):")
                        inputLine = readln().trim()
                        try {
                            val number = inputLine.toInt()
                            if (number in 1..list.size) {
                                list.removeAt(number - 1)
                                println("The task is deleted")
                                break
                            } else
                                println("Invalid task number")
                        } catch (e: Exception) {
                            println("Invalid task number")
                        }
                    }
                }
            }

            "print" -> if (list.isEmpty()) {
                println("No tasks have been input")
            } else {
                printTasklist(list, currentDate)
            }

            "add" -> {
                val taskObject = TaskObject()
                taskObject.priority = getPriority()
                taskObject.date = getDate().toLocalDate()
                taskObject.time = LocalTime.parse(getTime())
                setTask(taskObject)
                list.add(taskObject)
            }

            else -> println("The input action is invalid")
        }
        println("Input an action (add, print, edit, delete, end):")
        inputLine = readln().trim()
    }
}

private fun printTasklist(list: MutableList<TaskObject>, currentDate: LocalDate) {
    println(
        "+----+------------+-------+---+---+--------------------------------------------+\n" +
                "| N  |    Date    | Time  | P | D |                   Task                     |\n" +
                "+----+------------+-------+---+---+--------------------------------------------+"
    )
    for (i in list.indices) {
        println(
            "| ${i + 1}" +
                    " ".repeat(3 - (i + 1).toString().length)
                    + "| " + list[i].date
                    + " | " + (if (list[i].time.hour < 10) "0${list[i].time.hour}" else list[i].time.hour)
                    + ":" + (if (list[i].time.minute < 10) "0${list[i].time.minute}" else list[i].time.minute)
                    + " | " + priorityToColor(list[i].priority)
                    + " | " + dueToColor(daysUntil(currentDate, list[i].date))
                    + " |" + convertTaskToStr(list[i].task)
        )
    }
}

fun priorityToColor(priority: String): String {
    if (priority == "C") {
        return ("\u001B[101m \u001B[0m")
    } else if (priority == "H") {
        return ("\u001B[103m \u001B[0m")
    } else if (priority == "N") {
        return ("\u001B[102m \u001B[0m")
    } else if (priority == "L") {
        return ("\u001B[104m \u001B[0m")
    }
    return ("")
}

fun dueToColor(due: String): String {
    if (due == "I") {
        return ("\u001B[102m \u001B[0m")
    } else if (due == "T") {
        return ("\u001B[103m \u001B[0m")
    } else if (due == "O") {
        return ("\u001B[101m \u001B[0m")
    }
    return ("")
}