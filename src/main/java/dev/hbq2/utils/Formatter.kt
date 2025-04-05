package dev.hbq2.utils

import net.minecraft.text.Text
import java.util.Locale

object Formatter {
    fun stringToText(
        base: String,
        vararg args: Any?,
    ): Text = Text.of(String.format(base, *args))

    fun Long.getInGameDay(): Long = this / 24000L

    fun Long.getInGameHour(): Long = (this / 1000 + 6) % 24

    fun Long.getInGameMinute(): Long = ((this % 1000) / 1000.0 * 60).toLong()

    fun String.capitalizeWords(): String =
        split(" ").joinToString(" ") { s ->
            s.replaceFirstChar {
                if (it.isLowerCase()) {
                    it.titlecase(
                        Locale.getDefault(),
                    )
                } else {
                    it.toString()
                }
            }
        }
}
