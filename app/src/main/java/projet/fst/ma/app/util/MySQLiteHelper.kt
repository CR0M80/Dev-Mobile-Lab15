package projet.fst.ma.app.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Helper pour la gestion de la base de données SQLite.
 */
class MySQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ecole.db"

        const val TABLE_ETUDIANT = "etudiant"
        const val COL_ID = "id"
        const val COL_NOM = "nom"
        const val COL_PRENOM = "prenom"

        private const val CREATE_TABLE_ETUDIANT = """
            CREATE TABLE $TABLE_ETUDIANT (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOM TEXT NOT NULL,
                $COL_PRENOM TEXT NOT NULL
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_ETUDIANT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ETUDIANT")
        onCreate(db)
    }
}
