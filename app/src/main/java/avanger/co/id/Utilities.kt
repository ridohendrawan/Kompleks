package avanger.co.id

class Utilities {
    companion object {
        fun unixToTime(unixTime: Long?): String {
            unixTime?.let {
                val date = java.util.Date(unixTime * 1000L)
                val dateFormat = java.text.SimpleDateFormat.getDateTimeInstance()

                dateFormat.timeZone = java.util.TimeZone.getTimeZone("GMT+7")

                return dateFormat.format(date)
            }

            return ""
        }
    }
}