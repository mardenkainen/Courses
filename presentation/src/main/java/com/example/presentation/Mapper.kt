package com.example.presentation

import android.content.Context
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter

val dayFormatter = DateTimeFormatter.ofPattern("d")

fun LocalDate.formatDate(context: Context): String =
    "${this.format(dayFormatter)} ${this.month.locale(context)} ${this.year}"

fun Month.locale(context: Context) =
    context.getString(
        when (this) {
            Month.APRIL -> R.string.april
            Month.AUGUST -> R.string.august
            Month.DECEMBER -> R.string.december
            Month.FEBRUARY -> R.string.february
            Month.JANUARY -> R.string.january
            Month.JULY -> R.string.july
            Month.JUNE -> R.string.june
            Month.MARCH -> R.string.march
            Month.MAY -> R.string.may
            Month.NOVEMBER -> R.string.november
            Month.OCTOBER -> R.string.october
            Month.SEPTEMBER -> R.string.september
        }
    )
