package cl.mcsalazar.examen_p2.data

import android.app.Application
import androidx.room.Room

class Aplicacion: Application (){
    val db by lazy { Room.databaseBuilder(this, BaseDeDatos::class.java, "cobros.db").build() }
    val cobroDao by lazy { db.cobroDao() }
}


