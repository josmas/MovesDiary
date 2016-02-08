package org.josmas.movesdiary.db

import android.content.Context
import org.jetbrains.anko.db.*
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.josmas.movesdiary.App
import org.josmas.movesdiary.Credentials

class DB(ctx: Context = App.instance) : ManagedSQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {

  companion object {
    val DB_NAME = "MovesDiaryDb.db"
    val DB_VERSION = 1
    val instance: DB by lazy { DB() }
  }

  override fun onCreate(db: SQLiteDatabase) {
    //TODO (jos) need to find a way to DRY table names and attributes (here and in the object below)
    db.createTable("Credentials", true,
        "token_type" to TEXT,
        "expires_in" to INTEGER,
        "access_token" to TEXT,
        "user_id" to INTEGER,
        "refresh_token" to TEXT)
  }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    db.dropTable("Credentials", true)
    //TODO (jos) this will need a proper upgrading policy when storing real data.
  }
}

object dbOperations: AnkoLogger {
  val database: DB = DB.instance

  fun insertCredentials(cred: Credentials) : Long {
    var returnCode = -1L
    database.use {
      val returnDelete = delete("Credentials", "")
      info("Delete successful if it's not -1? " + returnDelete)
      returnCode = insert("Credentials",
          "token_type" to cred.token_type,
          "access_token" to cred.access_token,
          "expires_in" to cred.expires_in,
          "user_id" to cred.user_id,
          "refresh_token" to cred.refresh_token)
      info("I am called; I am insertCredentials INSIDE DATABASE.USE with a returnCode of: " + returnCode)
    }

    return returnCode;
  }
}