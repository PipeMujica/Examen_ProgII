package cl.fmujica.examen_progii.data

import android.content.Context
import androidx.room.Room

class RegistroRepository (
    private val registroDao: RegistroDao
) {
    suspend fun  obtenerTodos():List<Registro> = registroDao.getAll()

    suspend fun agregar(registro: Registro) = registroDao.insertAll(registro)

    companion object {
        @Volatile
        private var instance: RegistroRepository? = null

        fun getInstance(contexto:Context):RegistroRepository {
            return getInstanceDSDbHelper(contexto)
        }

        fun getInstanceDSDbHelper(contexto: Context):RegistroRepository{
            return instance ?: synchronized(this) {
                RegistroRepository(
                    RegistroDaoDb(contexto)
                )
            }
        }

        fun getInstanceDSRoom(contexto: Context):RegistroRepository {
            return instance ?: synchronized(this) {
                RegistroRepository(
                    Room.databaseBuilder(
                        contexto.applicationContext,
                        RegistroDb::class.java,
                        "registros.db"
                    ).build().registroDao()
                ).also {
                    instance = it
                }
            }
        }
    }
}