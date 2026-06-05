package projet.fst.ma.app.service

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import projet.fst.ma.app.classes.Etudiant
import projet.fst.ma.app.util.MySQLiteHelper

/**
 * Service gérant les opérations CRUD pour les étudiants.
 */
class EtudiantService(context: Context) {

    private val helper = MySQLiteHelper(context)

    fun create(e: Etudiant): Long {
        return helper.writableDatabase.use { db ->
            val values = ContentValues().apply {
                put(MySQLiteHelper.COL_NOM, e.nom)
                put(MySQLiteHelper.COL_PRENOM, e.prenom)
            }
            db.insert(MySQLiteHelper.TABLE_ETUDIANT, null, values).also {
                Log.d("EtudiantService", "Étudiant inséré avec ID: $it")
            }
        }
    }

    fun update(e: Etudiant): Int {
        return helper.writableDatabase.use { db ->
            val values = ContentValues().apply {
                put(MySQLiteHelper.COL_NOM, e.nom)
                put(MySQLiteHelper.COL_PRENOM, e.prenom)
            }
            db.update(
                MySQLiteHelper.TABLE_ETUDIANT,
                values,
                "${MySQLiteHelper.COL_ID} = ?",
                arrayOf(e.id.toString())
            )
        }
    }

    fun findById(id: Int): Etudiant? {
        helper.readableDatabase.use { db ->
            val cursor = db.query(
                MySQLiteHelper.TABLE_ETUDIANT,
                null,
                "${MySQLiteHelper.COL_ID} = ?",
                arrayOf(id.toString()),
                null, null, null
            )
            return cursor?.use {
                if (it.moveToFirst()) it.toEtudiant() else null
            }
        }
    }

    fun delete(id: Int): Int {
        return helper.writableDatabase.use { db ->
            db.delete(
                MySQLiteHelper.TABLE_ETUDIANT,
                "${MySQLiteHelper.COL_ID} = ?",
                arrayOf(id.toString())
            )
        }
    }

    fun findAll(): List<Etudiant> {
        val list = mutableListOf<Etudiant>()
        helper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM ${MySQLiteHelper.TABLE_ETUDIANT}", null)
            cursor?.use {
                while (it.moveToNext()) {
                    list.add(it.toEtudiant())
                }
            }
        }
        return list
    }

    private fun Cursor.toEtudiant(): Etudiant {
        return Etudiant(
            id = getInt(getColumnIndexOrThrow(MySQLiteHelper.COL_ID)),
            nom = getString(getColumnIndexOrThrow(MySQLiteHelper.COL_NOM)),
            prenom = getString(getColumnIndexOrThrow(MySQLiteHelper.COL_PRENOM))
        )
    }
}
