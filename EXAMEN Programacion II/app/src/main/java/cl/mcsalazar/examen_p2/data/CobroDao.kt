package cl.mcsalazar.examen_p2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CobroDao {

    @Query("SELECT * FROM Cobro ORDER BY fecha DESC")
    suspend fun getCobro(): List<Cobro>

    @Insert
    suspend fun addCobro(cobro: Cobro)

    @Delete
    suspend fun deleteCobro(cobro: Cobro)

}
