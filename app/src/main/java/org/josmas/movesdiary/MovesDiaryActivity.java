package org.josmas.movesdiary;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MovesDiaryActivity extends AppCompatActivity {

  private static final String TAG = "MovesDiaryActivity";
  private static final String CLIENT_ID = "0Z5UOm7tpViK5Ls242Padd4d4xS1AQ1j";
  private static final String REDIRECT_URI = "https://moves-api-demo.herokuapp.com/auth/moves/callback";
  private static final int REQUEST_AUTHORIZE = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_moves_diary);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, R.string.add_entry, Snackbar.LENGTH_SHORT)
            .setAction("Action", null).show();
      }
    });

    Button authButton = (Button) findViewById(R.id.auth_button);
    authButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        doRequestAuthInApp();
      }
    });
  }

  /**
   * App-to-app. Creates an intent with data uri starting moves://app/authorize/xxx to be handled
   * by Moves app. This code comes from their docs.
   * The result of this user interaction is delivered to
   * {@link #onActivityResult(int, int, android.content.Intent) }
   */
  private void doRequestAuthInApp() {

    Uri uri = createAuthUri("moves", "app", "/authorize").build();
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    try {
      startActivityForResult(intent, REQUEST_AUTHORIZE);
    } catch (ActivityNotFoundException e) {
      Toast.makeText(this, "Moves app not installed", Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * Handle the result from Moves authorization flow. The result is delivered as an uri:
   * <redirect_uri>?code=<authorizationcode>.
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    switch (requestCode) {
      case REQUEST_AUTHORIZE:
        if (data != null) {
          Uri resultUri = data.getData();
          //TODO (jos) I need to extract the 'code' out of the resultUri, which will later be
          // exchanged for an authorisation Token (used to authenticate every call to the API).
          Log.d(TAG, resultUri.toString());
          Toast.makeText(this,
              (resultCode == RESULT_OK ? "Authorisation granted." :
                  "Authorisation Failed; please try again "), Toast.LENGTH_LONG).show();
        } else {
          Toast.makeText(this,
              ("Authorisation Failed! Please make sure you have Moves app installed "),
              Toast.LENGTH_LONG).show();
        }
    }
  }

  /**
   * Helper method for building a valid Moves authorize uri.
   */
  private Uri.Builder createAuthUri(String scheme, String authority, String path) {
    return new Uri.Builder()
        .scheme(scheme)
        .authority(authority)
        .path(path)
        .appendQueryParameter("client_id", CLIENT_ID)
        .appendQueryParameter("redirect_uri", REDIRECT_URI)
        .appendQueryParameter("scope", "location activity")
        .appendQueryParameter("state", String.valueOf(SystemClock.uptimeMillis()));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_moves_diary, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
