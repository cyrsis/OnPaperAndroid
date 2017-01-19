package tech.onpaper.victor.onpaper.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by cyber on 2017-01-19.
 */

public class SplashActivity extends AppCompatActivity {
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent main = new Intent(this,MainActivity.class);
    startActivity(main);
    finish();
  }
}
