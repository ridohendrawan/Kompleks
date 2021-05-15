package avanger.co.id

import java.util.*

class Utilities {
    companion object {
        fun unixToTime(unixTime: Long?): String {
            unixTime?.let {
                if (unixTime == 0L) {
                    return "Tamu belum selesai berkunjung!"
                }

                val date = java.util.Date(unixTime * 1000L)
                val dateFormat = java.text.SimpleDateFormat.getDateTimeInstance()

                dateFormat.timeZone = java.util.TimeZone.getTimeZone("GMT+7")

                return dateFormat.format(date)
            }

            return "Waktu tidak ada!"
        }

        fun getCurrentDay(): Int {
            return Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        }
    }
}