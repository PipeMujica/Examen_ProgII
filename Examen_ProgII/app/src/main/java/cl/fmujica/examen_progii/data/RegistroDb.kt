package cl.fmujica.examen_progii.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Registro::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class RegistroDb:RoomDatabase() {
    abstract fun registroDao():RegistroDao
}
