package com.seguro.taxis.utils

import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class StringUtils {
    companion object {
        const val STRING_DATE = "dd/MM/yyyy"
        const val STRING_MATCH_DATE = "MMMM dd"
        const val STRING_TEAM_PLAYER_DATE = "dd, MMMM yy"
        const val STRING_SCORE_RESULT_DATE = "dd, MMM yy"
        const val STRING_MATCH_HOUR = "HH:mm"

        fun getStringDate(date: Date?): String {
            return SimpleDateFormat(STRING_DATE, Locale.getDefault()).format(date)
        }

        fun getStringMatchDate(date: Date?): String {
            return SimpleDateFormat(STRING_MATCH_DATE, Locale.getDefault()).format(date)
        }

        fun getStringScoreResultDate(date: Date?): String {
            return SimpleDateFormat(STRING_SCORE_RESULT_DATE, Locale.getDefault()).format(date)
        }

        fun getStringTeamPlayertDate(date: Date?): String {
            return SimpleDateFormat(STRING_TEAM_PLAYER_DATE, Locale.getDefault()).format(date)
        }

        fun getStringMatchHour(date: Date?): String {
            return SimpleDateFormat(STRING_MATCH_HOUR, Locale.getDefault()).format(date)
        }

        fun isEmailValid(email: String?): Boolean {
            return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun getDateInMilis(): String {
            return Date().time.toString()
        }

        fun getTimeInMillis(value: String?): Long {
            var timeInMillis = 0L
            if (value != null) {
                val format: SimpleDateFormat
                if (value.contains(".", true)) {
                    format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                } else {
                    format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                }
                try {
                    val strToDate = format.parse(value)
                    timeInMillis = strToDate.time
                } catch (e: ParseException) {
                    Timber.e(e)
                }
            }
            return timeInMillis
        }

        fun getDateFromString(value: String?): Date {
            var date = Date()
            if (value != null) {
                val format = SimpleDateFormat(STRING_DATE, Locale.getDefault())
                date = format.parse(value)
            }
            return date
        }

        fun isValidText(text: String?): Boolean {
            return !text.isNullOrBlank()
        }

        fun isValidPhone(phone: String): Boolean {
            return if (!Pattern.matches("[a-zA-Z]+", phone)) {
                phone.length > 9 && phone.length <= 11
            } else false
        }
    }
}
