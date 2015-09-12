
package io.jari.materialup.models;

import com.google.gson.annotations.Expose;

public class Category {

  @Expose
  private String name;

  /**
   * @return The name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name The name
   */
  public void setName(String name) {
    this.name = name;
  }

}
