package tech.onpaper.victor.onpaper.Model;

/**
 * Created by victor888 on 1/20/2017.
 */

public class User {

   public String username;
  public String email;

  public User() {
  }

  public User(String email, String username) {
    this.email = email;
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
