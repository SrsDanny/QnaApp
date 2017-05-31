package hu.bme.aut.mobsoft.lab.mobsoft;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.mobsoft.lab.mobsoft.utils.SimpleExecutor;

@Module
public class UiExecutorModule {
    @Provides
    @Singleton
    Executor providesExecutor() {
        return new SimpleExecutor();
    }
}
