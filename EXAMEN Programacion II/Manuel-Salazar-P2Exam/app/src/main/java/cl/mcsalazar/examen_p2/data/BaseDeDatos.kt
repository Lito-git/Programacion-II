package cl.mcsalazar.examen_p2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Cobro::class], version = 1)
@TypeConverters(Converters::class)
abstract class BaseDeDatos: RoomDatabase() {
    abstract fun cobroDao(): CobroDao
}