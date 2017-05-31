package hu.bme.aut.mobsoft.lab.mobsoft.util;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SchedulerModule {
    @Provides
    @Singleton
    public SchedulerFactory providesSchedulerFactory() {
        return new ConcreteSchedulerFactory();
    }
}
