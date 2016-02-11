package org.josmas.movesdiary.db

import android.content.Context
import org.jetbrains.anko.db.*
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.josmas.movesdiary.App
import org.josmas.movesdiary.Credentials
import org.josmas.movesdiary.UserProfile

class DB(ctx: Context = App.instance) : ManagedSQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {

  companion object {
    val DB_NAME = "MovesDiaryDb.db"
    val DB_VERSION = 2
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
    db.dropTable("UserProfile", true)
    onCreate(db)
    /**
     * The Profile API returns more fields than the ones below, but we don't need to store them, at
     * least for now.
     */
    db.createTable("UserProfile", true,
        "userId" to INTEGER,
        "firstDate" to TEXT,
        "language" to TEXT,
        "locale" to TEXT)
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
    }

    return returnCode;
  }

  fun getAccessToken(): String {
    var accessToken: String = ""
    database.use {
      val result = select("Credentials", "access_token").limit(1)
      accessToken = result.parseOpt(StringParser) ?: ""
    }

    return accessToken;
  }

  fun insertUserProfile(userProf: UserProfile) : Long {
    var returnCode = -1L
    database.use {
      val returnDelete = delete("UserProfile", "") // Keeping only 1 active userProfile for now
      info("Delete successful if it's not -1? " + returnDelete)
      returnCode = insert("UserProfile",
          "userId" to userProf.userId,
          "firstDate" to userProf.profile.firstDate,
          "language" to userProf.profile.localization.language,
          "locale" to userProf.profile.localization.locale)
      info("I am called; I am insertUserProfile INSIDE DATABASE.USE with a returnCode of: " + returnCode)
    }

    return returnCode;
  }
}