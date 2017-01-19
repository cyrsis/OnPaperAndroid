package tech.onpaper.victor.onpaper;

import android.support.v7.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
/**
 * Created by victor888 on 1/19/2017.
 */

public class BaseActivity extends AppCompatActivity {

   public void showProgressDialog()
   {

   }

  public String getUid(){
    return FirebaseAuth.getInstance().getCurrentUser().getUid();
  }
}
