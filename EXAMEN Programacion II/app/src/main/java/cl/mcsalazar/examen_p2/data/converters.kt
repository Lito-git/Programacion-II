package cl.mcsalazar.examen_p2.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromTipoCobro(value: TipoCobro): String = value.name

    @TypeConverter
    fun toTipoCobro(value: String): TipoCobro = TipoCobro.valueOf(value)


}