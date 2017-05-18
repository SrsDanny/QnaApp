package hu.bme.aut.mobsoft.lab.mobsoft;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.InteractorModule;
import hu.bme.aut.mobsoft.lab.mobsoft.network.mock.MockNetworkModule;
import hu.bme.aut.mobsoft.lab.mobsoft.repository.TestRepositoryModule;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.InjectorsModule;

@Singleton
@Component(modules = {MockNetworkModule.class, TestModule.class,
        InteractorModule.class, TestRepositoryModule.class,
        AndroidInjectionModule.class, InjectorsModule.class})
public interface TestComponent extends MobSoftApplicationComponent {
}
