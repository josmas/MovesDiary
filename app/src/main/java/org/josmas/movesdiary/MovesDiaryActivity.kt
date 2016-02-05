package org.josmas.movesdiary

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_moves_diary.*
import kotlinx.android.synthetic.main.content_moves_diary.*

class MovesDiaryActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_moves_diary)
    setSupportActionBar(toolbar)

    fab.setOnClickListener {
      view -> Snackbar.make(view, R.string.add_entry, Snackbar.LENGTH_SHORT)
        .setAction("Action", null).show()
    }

    auth_button.setOnClickListener { doRequestAuthInApp() }
  }

  /**
   * App-to-app. Creates an intent with data uri starting moves://app/authorize/xxx to be handled
   * by Moves app. This code comes from their docs.
   * The result of this user interaction is delivered to
   * [.onActivityResult]
   */
  private fun doRequestAuthInApp() {

    val uri = createAuthUri("moves", "app", "/authorize").build()
    val intent = Intent(Intent.ACTION_VIEW, uri)
    try {
      startActivityForResult(intent, REQUEST_AUTHORIZE)
    } catch (e: ActivityNotFoundException) {
      Toast.makeText(this, "Moves app not installed", Toast.LENGTH_SHORT).show()
    }

  }

  /**
   * Handle the result from Moves authorization flow. The result is delivered as an uri:
   * ?code=.
   */
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    when (requestCode) {
      REQUEST_AUTHORIZE -> if (data != null) {
        val resultUri = data.data
        //TODO (jos) I need to extract the 'code' out of the resultUri, which will later be
        // exchanged for an authorisation Token (used to authenticate every call to the API).
        Log.d(TAG, resultUri.toString())
        Toast.makeText(this,
            if (resultCode == Activity.RESULT_OK)
              "Authorisation granted."
            else
              "Authorisation Failed; please try again ", Toast.LENGTH_LONG).show()
      } else {
        Toast.makeText(this,
            "Authorisation Failed! Please make sure you have the Moves app installed.",
            Toast.LENGTH_LONG).show()
      }
    }
  }

  /**
   * Helper method for building a valid Moves authorize uri.
   */
  private fun createAuthUri(scheme: String, authority: String, path: String): Uri.Builder {
    return Uri.Builder().scheme(scheme).authority(authority).path(path).appendQueryParameter("client_id", CLIENT_ID).appendQueryParameter("redirect_uri", REDIRECT_URI).appendQueryParameter("scope", "location activity").appendQueryParameter("state", SystemClock.uptimeMillis().toString())
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_moves_diary, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    val id = item.itemId

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true
    }

    return super.onOptionsItemSelected(item)
  }

  companion object {

    private val TAG = "MovesDiaryActivity"
    private val CLIENT_ID = "0Z5UOm7tpViK5Ls242Padd4d4xS1AQ1j"
    private val REDIRECT_URI = "https://moves-api-demo.herokuapp.com/auth/moves/callback"
    private val REQUEST_AUTHORIZE = 1
  }
}
