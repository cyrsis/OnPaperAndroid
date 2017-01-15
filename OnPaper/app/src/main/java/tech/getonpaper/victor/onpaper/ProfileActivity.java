package tech.getonpaper.victor.onpaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by victor888 on 1/16/2017.
 */
public class ProfileActivity extends AppCompatActivity {

  private FirebaseAuth firebaseAuth;

  //view objects
  private TextView textViewUserEmail;
  private Button buttonLogout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);

    firebaseAuth = FirebaseAuth.getInstance();

    //if the user is not logged in
    //that means current user will return null
    if (firebaseAuth.getCurrentUser() == null) {
      //closing this activity
      finish();
      //starting login activity
      startActivity(new Intent(this, MainActivity.class));
    }

    //getting current user
    FirebaseUser user = firebaseAuth.getCurrentUser();

    //initializing views
    textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
    buttonLogout = (Button) findViewById(R.id.buttonLogout);

    //displaying logged in user name
    textViewUserEmail.setText("Welcome\n" + user.getEmail());

    //adding listener to button
    buttonLogout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //logging out the user
        firebaseAuth.signOut();
        //closing activity
        finish();
        //starting login activity
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
      }
    });
  }
}
