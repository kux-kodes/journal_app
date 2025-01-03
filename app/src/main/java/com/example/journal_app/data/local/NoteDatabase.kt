package com.example.journal_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date
object DateConverters{
    @TypeConverter
    fun fromTimeStamp(value:Long?):Date?{
        return if(value==null) null else Date(value)
    }
    @TypeConverter
    fun dateToTimestamp (date:Date?):Long?{
        return date?.time
    }
}
@Database(entities = [NoteModel::class], version = 1, exportSchema = true)

@TypeConverters(DateConverters::class)
abstract class NoteDatabase: RoomDatabase(){
    abstract fun dao(): NoteDAO
}
