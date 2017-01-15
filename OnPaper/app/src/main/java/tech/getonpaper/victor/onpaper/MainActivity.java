package tech.getonpaper.victor.onpaper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();
  private static final String PATH_TOS = "";
  private FirebaseAuth firebaseAuth;

  //progress dialog
  private ProgressDialog progressDialog;
  private static final int RC_SIGN_IN = 200;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    firebaseAuth = FirebaseAuth.getInstance();
    //if the objects getcurrentuser method is not null
    //means user is already logged in
    if (firebaseAuth.getCurrentUser() != null) {
      //close this activity
      finish();
      //opening profile activity
      startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == RC_SIGN_IN) {
      if (resultCode == RESULT_OK) {
        loginUser();
      }

      if (resultCode == RESULT_CANCELED) {
        displayMessage(getString(R.string.signin_failed));
      }
      return;
    }
    displayMessage(getString(R.string.unknown_response));
  }

  private void loginUser() {
    Intent loginIntent = new Intent(MainActivity.this, SigninActivity.class);
    startActivity(loginIntent);
    finish();
  }

  private void displayMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }
}
