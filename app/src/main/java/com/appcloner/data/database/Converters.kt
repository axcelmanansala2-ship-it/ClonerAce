package com.appcloner.data.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(value: String?): List<String> =
        value?.split(",")?.filter { it.isNotEmpty() } ?: emptyList()

    @TypeConverter
    fun fromList(list: List<String>): String = list.joinToString(",")
}
