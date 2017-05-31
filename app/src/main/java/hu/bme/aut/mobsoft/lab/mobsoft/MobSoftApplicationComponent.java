package hu.bme.aut.mobsoft.lab.mobsoft;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.InteractorModule;
import hu.bme.aut.mobsoft.lab.mobsoft.network.mock.MockNetworkModule;
import hu.bme.aut.mobsoft.lab.mobsoft.repository.RepositoryModule;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.BackgroundExecutorModule;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.InjectorsModule;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.PresentersModule;

@Singleton
@Component(modules = {PresentersModule.class, RepositoryModule.class,
        InteractorModule.class, MockNetworkModule.class,
        AndroidInjectionModule.class, InjectorsModule.class,
        BackgroundExecutorModule.class})
public interface MobSoftApplicationComponent {
    void inject(MobSoftApplication mobSoftApplication);
}