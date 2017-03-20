package hu.bme.aut.mobsoft.lab.mobsoft;

import android.app.Application;

import hu.bme.aut.mobsoft.lab.mobsoft.ui.UIModule;

public class MobSoftApplication extends Application {

    public static MobSoftApplicationComponent injector;

    @Override
    public void onCreate() {
        super.onCreate();

        injector =
                DaggerMobSoftApplicationComponent.builder().
                        uIModule(
                                new UIModule(this)
                        ).build();
    }
}