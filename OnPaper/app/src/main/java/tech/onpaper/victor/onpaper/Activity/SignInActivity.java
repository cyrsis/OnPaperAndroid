package tech.onpaper.victor.onpaper.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import tech.onpaper.victor.onpaper.Model.User;
import tech.onpaper.victor.onpaper.R;

/**
 * Created by victor888 on 1/20/2017.
 */

public class SignInActivity extends BaseActivity
    implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

  private static final String TAG = "SignInActivity";
  @BindView(R.id.sign_in_google_sigin) SignInButton mSignInGoogleSigin;

  private DatabaseReference mDatabase;
  private FirebaseAuth mAuth;

  private EditText mEmailField;
  private EditText mPasswordField;
  private Button mSignInButton;
  private Button mSignUpButton;

  private static final int RC_SIGN_IN = 9001;

  private GoogleApiClient mGoogleApiClient;

  private FirebaseAuth.AuthStateListener mAuthListener;
  // [START declare_auth]

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_in);
    ButterKnife.bind(this);

    GoogleSignInOptions gso =
        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
            getString(R.string.default_web_client_id)).requestEmail().build();
    // [END config_signin]

    //Google Sign-in
    mGoogleApiClient =
        new GoogleApiClient.Builder(this).enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build();

    mDatabase = FirebaseDatabase.getInstance().getReference();
    mAuth = FirebaseAuth.getInstance();

    mAuthListener = new FirebaseAuth.AuthStateListener() {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
          // User is signed in
          Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
        } else {
          // User is signed out
          Log.d(TAG, "onAuthStateChanged:signed_out");
        }
        // [START_EXCLUDE]
        //updateUI(user);
        // [END_EXCLUDE]
      }
    };

    // Views
    mEmailField = (EditText) findViewById(R.id.field_email);
    mPasswordField = (EditText) findViewById(R.id.field_password);
    mSignInButton = (Button) findViewById(R.id.button_sign_in);
    mSignUpButton = (Button) findViewById(R.id.button_sign_up);

    // Click listeners
    mSignInButton.setOnClickListener(this);
    mSignUpButton.setOnClickListener(this);
    mSignInGoogleSigin.setOnClickListener(this);
  }

  @Override public void onStart() {
    super.onStart();

    mAuth.addAuthStateListener(mAuthListener);

    // Check auth on Activity start
    if (mAuth.getCurrentUser() != null) {
      onAuthSuccess(mAuth.getCurrentUser());
    }
  }

  @Override protected void onStop() {
      super.onStop();

    if (mAuthListener != null)
    {
      mAuth.removeAuthStateListener(mAuthListener);
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
    if (requestCode == RC_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      if (result.isSuccess()) {
        // Google Sign In was successful, authenticate with Firebase
        GoogleSignInAccount account = result.getSignInAccount();
        firebaseAuthWithGoogle(account);
      } else {
        // Google Sign In failed, update UI appropriately
        // [START_EXCLUDE]
       // updateUI(null);
        // [END_EXCLUDE]
      }
    }
  }

  private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
    Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
    // [START_EXCLUDE silent]
    showProgressDialog();
    // [END_EXCLUDE]

    AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

            if (task.isSuccessful()) {
              onAuthSuccess(task.getResult().getUser());
            }


            // If sign in fails, display a message to the user. If sign in succeeds
            // the auth state listener will be notified and logic to handle the
            // signed in user can be handled in the listener.
            if (!task.isSuccessful()) {


              Log.w(TAG, "signInWithCredential", task.getException());
              Toast.makeText(SignInActivity.this, "Authentication failed.",
                  Toast.LENGTH_SHORT).show();
            }
            // [START_EXCLUDE]
            hideProgressDialog();
            // [END_EXCLUDE]
          }
        });
  }


  private void signIn() {
    Log.d(TAG, "signIn");
    if (!validateForm()) {
      return;
    }

    showProgressDialog();
    String email = mEmailField.getText().toString();
    String password = mPasswordField.getText().toString();

    mAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override public void onComplete(@NonNull Task<AuthResult> task) {
            Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
            hideProgressDialog();

            if (task.isSuccessful()) {
              onAuthSuccess(task.getResult().getUser());
            } else {
              Toast.makeText(SignInActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
            }
          }
        });
  }

  private void signUp() {
    Log.d(TAG, "signUp");
    if (!validateForm()) {
      return;
    }

    showProgressDialog();
    String email = mEmailField.getText().toString();
    String password = mPasswordField.getText().toString();

    mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override public void onComplete(@NonNull Task<AuthResult> task) {
            Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
            hideProgressDialog();

            if (task.isSuccessful()) {
              onAuthSuccess(task.getResult().getUser());
            } else {
              Toast.makeText(SignInActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
            }
          }
        });
  }

  private void onAuthSuccess(FirebaseUser user) {
    String username = usernameFromEmail(user.getEmail());

    // Write new user
    writeNewUser(user.getUid(), username, user.getEmail());

    // Go to MainActivity
    startActivity(new Intent(SignInActivity.this, MainActivity.class));
    finish();
  }

  private String usernameFromEmail(String email) {
    if (email.contains("@")) {
      return email.split("@")[0];
    } else {
      return email;
    }
  }

  private boolean validateForm() {
    boolean result = true;
    if (TextUtils.isEmpty(mEmailField.getText().toString())) {
      mEmailField.setError("Required");
      result = false;
    } else {
      mEmailField.setError(null);
    }

    if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
      mPasswordField.setError("Required");
      result = false;
    } else {
      mPasswordField.setError(null);
    }

    return result;
  }

  // [START basic_write]
  private void writeNewUser(String userId, String name, String email) {
    User user = new User(name, email);

    mDatabase.child("users").child(userId).setValue(user);
  }
  // [END basic_write]

  @Override public void onClick(View v) {
    int i = v.getId();
    if (i == R.id.button_sign_in) {
      signIn();
    } else if (i == R.id.button_sign_up) {
      signUp();
    } else if (i == R.id.sign_in_google_sigin) {
      signinWithGoogle();
    }
  }

  private void signinWithGoogle() {

    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Log.d(TAG, "onConnectionFailed:" + connectionResult);
    Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
  }
}
