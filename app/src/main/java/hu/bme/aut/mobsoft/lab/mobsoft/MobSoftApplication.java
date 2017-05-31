package hu.bme.aut.mobsoft.lab.mobsoft;

import android.app.Activity;
import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;

import dagger.android.HasActivityInjector;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.PresentersModule;
import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.repository.Repository;

public class MobSoftApplication extends Application implements HasActivityInjector {
    @Inject DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    @Inject Repository repository;

    public static MobSoftApplicationComponent injector;

    private Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        injector =
                DaggerMobSoftApplicationComponent.builder().
                        presentersModule(
                                new PresentersModule(this)
                        ).build();

        injector.inject(this);
        repository.open(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        repository.close();
    }

    public void setInjector(MobSoftApplicationComponent appComponent) {
        injector = appComponent;
        injector.inject(this);
        repository.open(getApplicationContext());
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}