package cl.fmujica.examen_progii.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.time.LocalDate

class RegistroDaoDb (contexto:Context)
    : SQLiteOpenHelper(contexto, DB_NAME, null, DB_VERSION)
    , RegistroDao
{

    companion object {
        const val  DB_NAME = "registros.db"
        const val DB_VERSION = 1
        const val TABLE_NAME = "registros_medidor"
        const val COL_ID = "id"
        const val COL_CATEGORIA = "categoria"
        const val COL_MEDIDOR = "medidor"
        const val COL_FECHA = "fecha"
        const val DB_SQL_CREATE_TABLES = """
            CREATE TABLE IF NOT EXISTS ${TABLE_NAME}(
                ${COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${COL_CATEGORIA} TEXT,
                ${COL_MEDIDOR} INTEGER,
                ${COL_FECHA} INTEGER 
            );
        """
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DB_SQL_CREATE_TABLES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<Registro> {
        val cursor = this.readableDatabase.query(
            TABLE_NAME,
            null, // null devuelve todas las columnas
            null,
            null,
            null,
            null,
            null
        )

        val registros = mutableListOf<Registro>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COL_ID))
                val categoria = getString(getColumnIndexOrThrow(COL_CATEGORIA))
                val medidor = getInt(getColumnIndexOrThrow(COL_MEDIDOR))
                val fechaNum = getLong(getColumnIndexOrThrow(COL_FECHA))
                val fecha = LocalDateConverter().fromTimestamp(fechaNum) ?: LocalDate.now()
                val registro = Registro(id,categoria, medidor, fecha)
                registros.add(registro)
            }
        }
        return registros
    }

    suspend fun insert(registro:Registro) {
        Log.v("RegistroDaoDb", "::insert()")
        val valores = ContentValues().apply {
            put(COL_CATEGORIA, registro.categoria)
            put(COL_MEDIDOR, registro.medidor)
            put(COL_FECHA, LocalDateConverter().dateToTimestamp(registro.fecha))
        }
        this.writableDatabase.insert(
            TABLE_NAME,
            null,
            valores
        )
    }

    override suspend fun insertAll(vararg registro: Registro) {
        registro.forEach {
            insert(it)
        }
    }

    override suspend fun update(registro: Registro) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(resgistro: Registro) {
        TODO("Not yet implemented")
    }

}