package cl.mcsalazar.examen_p2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cobro (
    @PrimaryKey (autoGenerate = true) var id: Long? = null,
    var monto: Long,
    var fecha: String,
    var tipo: TipoCobro

)
