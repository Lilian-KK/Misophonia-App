package edu.moravian.csci215.misophoniaapp.survey_data

import androidx.room.TypeConverter

class Converters {
    /**
     * Convert a set of integers (representing selected options) to a single integer using bit
     * manipulation. No value in the set should be greater than 31, since we're using a 32-bit
     * integer to store the set.
     */
    @TypeConverter
    fun fromSet(value: Set<Int>): Int = value.map { 1 shl it }.reduceOrNull { acc, i -> acc or i } ?: 0

    /**
     * Convert an integer back to a set of integers by checking which bits are set.
     */
    @TypeConverter
    fun toSet(value: Int): Set<Int> {
        var remaining = value
        val result = mutableSetOf<Int>()
        var bitIndex = 0
        while (remaining != 0) {
            if (remaining and 1 == 1) {
                result.add(bitIndex)
            }
            remaining = remaining ushr 1
            bitIndex++
        }
        return result
    }
}
