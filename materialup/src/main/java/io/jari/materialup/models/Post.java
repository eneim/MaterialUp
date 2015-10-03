
package io.jari.materialup.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import java.util.List;

public class Post extends RealmObject {

  @Expose
  private String name;
  @PrimaryKey
  @Expose
  private int id;
  @Expose
  private String slug;
  @Expose
  private String url;
  @Expose
  private String label;
  @SerializedName("redirect_url")
  @Expose
  private String redirectUrl;
  @Expose
  private Thumbnails thumbnails;
  @SerializedName("upvotes_count")
  @Expose
  private int upvotesCount;
  @SerializedName("comments_count")
  @Expose
  private int commentsCount;
  @SerializedName("view_count")
  @Expose
  private int viewCount;
  @Expose
  private String platform;
  @Expose
  private Source source;
  @SerializedName("published_at")
  @Expose
  private String publishedAt;
  @Expose
  private Submitter submitter;

//  @Ignore
//  @SerializedName("makers")
//  @Expose
//  private List<Maker> makersList = new ArrayList<Maker>();

  @Expose
  private RealmList<Maker> makers = new RealmList<>();
  @Expose
  private Category category;

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

  /**
   * @return The id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id The id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return The slug
   */
  public String getSlug() {
    return slug;
  }

  /**
   * @param slug The slug
   */
  public void setSlug(String slug) {
    this.slug = slug;
  }

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
   * @return The label
   */
  public String getLabel() {
    return label;
  }

  /**
   * @param label The label
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * @return The redirectUrl
   */
  public String getRedirectUrl() {
    return redirectUrl;
  }

  /**
   * @param redirectUrl The redirect_url
   */
  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }

  /**
   * @return The thumbnails
   */
  public Thumbnails getThumbnails() {
    return thumbnails;
  }

  /**
   * @param thumbnails The thumbnails
   */
  public void setThumbnails(Thumbnails thumbnails) {
    this.thumbnails = thumbnails;
  }

  /**
   * @return The upvotesCount
   */
  public int getUpvotesCount() {
    return upvotesCount;
  }

  /**
   * @param upvotesCount The upvotes_count
   */
  public void setUpvotesCount(int upvotesCount) {
    this.upvotesCount = upvotesCount;
  }

  /**
   * @return The commentsCount
   */
  public int getCommentsCount() {
    return commentsCount;
  }

  /**
   * @param commentsCount The comments_count
   */
  public void setCommentsCount(int commentsCount) {
    this.commentsCount = commentsCount;
  }

  /**
   * @return The viewCount
   */
  public int getViewCount() {
    return viewCount;
  }

  /**
   * @param viewCount The view_count
   */
  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }

  /**
   * @return The platform
   */
  public String getPlatform() {
    return platform;
  }

  /**
   * @param platform The platform
   */
  public void setPlatform(String platform) {
    this.platform = platform;
  }

  /**
   * @return The source
   */
  public Source getSource() {
    return source;
  }

  /**
   * @param source The source
   */
  public void setSource(Source source) {
    this.source = source;
  }

  /**
   * @return The publishedAt
   */
  public String getPublishedAt() {
    return publishedAt;
  }

  /**
   * @param publishedAt The published_at
   */
  public void setPublishedAt(String publishedAt) {
    this.publishedAt = publishedAt;
  }

  /**
   * @return The submitter
   */
  public Submitter getSubmitter() {
    return submitter;
  }

  /**
   * @param submitter The submitter
   */
  public void setSubmitter(Submitter submitter) {
    this.submitter = submitter;
  }

  /**
   * @return The category
   */
  public Category getCategory() {
    return category;
  }

  /**
   * @param category The category
   */
  public void setCategory(Category category) {
    this.category = category;
  }

//  public List<Maker> getMakersList() {
////    return makersList;
//    return makers.subList(0, makers.size());
//  }

//  public void setMakersList(List<Maker> makersList) {
//    this.makersList = makersList;
//  }

  public RealmList<Maker> getMakers() {
    return makers;
  }

  public void setMakers(RealmList<Maker> makers) {
    this.makers = makers;
  }

  public void setMakers(List<Maker> makerList) {
    for (Maker maker : makerList) {
      this.makers.add(maker);
    }
  }
}
