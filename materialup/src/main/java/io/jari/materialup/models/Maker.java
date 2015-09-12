
package io.jari.materialup.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Maker {

  @Expose
  private String url;
  @Expose
  private String nickname;
  @SerializedName("full_name")
  @Expose
  private String fullName;

  /**
   * @return The url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @param url The url
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * @return The nickname
   */
  public String getNickname() {
    return nickname;
  }

  /**
   * @param nickname The nickname
   */
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  /**
   * @return The fullName
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * @param fullName The full_name
   */
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

}
