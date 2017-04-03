package hu.bme.aut.mobsoft.lab.mobsoft;

import android.app.Application;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.repository.Repository;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.UIModule;

public class MobSoftApplication extends Application {

    @Inject
    Repository repository;

    public static MobSoftApplicationComponent injector;

    @Override
    public void onCreate() {
        super.onCreate();

        injector =
                DaggerMobSoftApplicationComponent.builder().
                        uIModule(
                                new UIModule(this)
                        ).build();

        injector.inject(this);
        repository.open(getApplicationContext());
    }
}