package io.jari.materialup.api;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import io.jari.materialup.models.Post;
import retrofit.Call;
import retrofit.Callback;
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

  private static OkHttpClient client = new OkHttpClient();
  private static Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create())
      .build();

  private static final ApiService apiService = retrofit.create(ApiService.class);

  static {
    client.interceptors().add(new Interceptor() {
      @Override public Response intercept(Chain chain) throws IOException {
        Request origin = chain.request();
        Request newRequest = origin.newBuilder()
            .addHeader("Authorization", "Token token=\"TLG2vYM5xf84vKwtc56yHg\"")
            .build();
        return chain.proceed(newRequest);
      }
    });
  }

  public static void postList(Callback<List<Post>> callback) {
    apiService.postList(0).enqueue(callback);
  }

  public static void postList(int daysAgp, Callback<List<Post>> callback) {
    apiService.postList(daysAgp).enqueue(callback);
  }

  interface ApiService {

    @GET("posts") Call<List<Post>> postList(@Query("days_ago") int days_ago);

  }
}
