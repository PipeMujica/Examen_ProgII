package cl.fmujica.examen_progii.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity
data class Registro(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val categoria:String,
    val medidor: Int,
    val fecha: LocalDate
)

enum class  TipoRegistro {
    Agua,
    Luz,
    Gas
}