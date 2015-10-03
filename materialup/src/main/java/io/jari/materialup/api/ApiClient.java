package io.jari.materialup.api;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.squareup.moshi.Moshi;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import io.jari.materialup.global.UpApplication;
import io.jari.materialup.models.ItemDetails;
import io.jari.materialup.models.Post;
import io.realm.RealmObject;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

import java.io.IOException;
import java.util.List;

/**
 * Created by eneim on 9/12/15.
 */
public class ApiClient {

  private static final String BASE_URL = "http://www.materialup.com/api/v1/";
  private static final Gson GSON = new GsonBuilder()
      .setExclusionStrategies(new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
          return f.getDeclaringClass().equals(RealmObject.class);
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
          return false;
        }
      })
      .excludeFieldsWithoutExposeAnnotation()
      .create();

  private static final Moshi MOSHI = new Moshi.Builder().build();

  private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
  private static final Retrofit RETROFIT = new Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(OK_HTTP_CLIENT)
//      .addConverterFactory(MoshiConverterFactory.create(MOSHI))
      .addConverterFactory(GsonConverterFactory.create(GSON))
      .build();

  private static final ApiService API_SERVICE = RETROFIT.create(ApiService.class);

  static {
    OK_HTTP_CLIENT.interceptors().add(new Interceptor() {
      @Override public Response intercept(Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Token token=\"TLG2vYM5xf84vKwtc56yHg\"")
            .build();
        return chain.proceed(newRequest);
      }
    });
  }

  public static void postList(final Callback<List<Post>> callback) {
    API_SERVICE.postList(0).enqueue(new retrofit.Callback<List<Post>>() {
      @Override public void onResponse(retrofit.Response<List<Post>> response, Retrofit retrofit) {
        List<Post> responseItems = response.body();
        UpApplication.realm().beginTransaction();
        List<Post> result = UpApplication.realm().copyToRealmOrUpdate(responseItems);
        UpApplication.realm().commitTransaction();

        if (callback != null) {
          callback.onSuccess(result, retrofit);
        }
      }

      @Override public void onFailure(Throwable t) {
        if (callback != null) {
          callback.onFailure(t);
        }
      }
    });
  }

  @Deprecated
  public static ItemDetails getItemDetails(String id) {
    return new ItemDetails();
  }

  public static void postList(int daysAgo, retrofit.Callback<List<Post>> callback) {
    API_SERVICE.postList(daysAgo).enqueue(callback);
  }

  interface ApiService {

    @GET("posts") Call<List<Post>> postList(@Query("days_ago") int days_ago);

  }

  public interface Callback<T> {

    void onSuccess(T response, Retrofit retrofit);

    void onFailure(Throwable t);
  }
}
