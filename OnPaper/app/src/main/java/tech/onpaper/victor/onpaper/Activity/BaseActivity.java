package tech.onpaper.victor.onpaper.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
/**
 * Created by victor888 on 1/19/2017.
 */

public class BaseActivity extends AppCompatActivity {

  ProgressDialog mProgressDialog;
  public void showProgressDialog() {

    if (mProgressDialog == null) {
      mProgressDialog = new ProgressDialog(this);
      mProgressDialog.setCancelable(false);
      mProgressDialog.setMessage("Loading...");
    }

    mProgressDialog.show();
  }


  public void hideProgressDialog() {
    if (mProgressDialog != null && mProgressDialog.isShowing()) {
      mProgressDialog.dismiss();
    }
  }

  public String getUid(){
    return FirebaseAuth.getInstance().getCurrentUser().getUid();
  }
}
