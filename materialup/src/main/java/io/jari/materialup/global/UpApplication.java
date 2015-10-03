package io.jari.materialup.global;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;


public class UpApplication extends Application {

  private static UpApplication mInstance;

  private Realm mRealm;

  public static synchronized Realm realm() {
    return getInstance().mRealm;
  }

  /**
   * Singleton pattern
   *
   * @return Current application instance
   */
  public static synchronized UpApplication getInstance() {
    return mInstance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Fabric.with(this, new Crashlytics());
    mRealm = Realm.getInstance(this);
    mInstance = this;
  }

}
