package cl.fmujica.examen_progii.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RegistroDao {
    @Query("SELECT * FROM Registro")
    suspend fun getAll():List<Registro>

    @Insert
    suspend fun insertAll(vararg registro: Registro)

    @Update
    suspend fun update(registro: Registro)

    @Delete
    suspend fun  delete(resgistro:Registro)
}