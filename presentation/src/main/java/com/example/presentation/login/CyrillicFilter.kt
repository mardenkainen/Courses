package com.example.presentation.login

import android.text.InputFilter
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils

object CyrillicFilter: InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        var keepOriginal = true
        val sb = StringBuilder(end - start)
        for (i in start..<end) {
            val c = source.get(i)
            if (!isCyrillicCharacter(c)) {
                sb.append(c)
            } else {
                keepOriginal = false
            }
        }
        if (keepOriginal) return null
        else {
            if (source is Spanned) {
                val sp = SpannableString(sb)
                TextUtils.copySpansFrom(source, start, sb.length, null, sp, 0)
                return sp
            } else {
                return sb
            }
        }
    }
    private fun isCyrillicCharacter(c: Char): Boolean {
        val block: Character.UnicodeBlock? = Character.UnicodeBlock.of(c)
        val isCyrillicCharacter =
            (block == Character.UnicodeBlock.CYRILLIC || block == Character.UnicodeBlock.CYRILLIC_SUPPLEMENTARY || block == Character.UnicodeBlock.CYRILLIC_EXTENDED_A || block == Character.UnicodeBlock.CYRILLIC_EXTENDED_B)
        return isCyrillicCharacter
    }

}