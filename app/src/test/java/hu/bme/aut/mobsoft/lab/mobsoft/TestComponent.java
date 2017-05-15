package hu.bme.aut.mobsoft.lab.mobsoft;

import javax.inject.Singleton;

import dagger.Component;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.InteractorModule;
import hu.bme.aut.mobsoft.lab.mobsoft.network.mock.MockNetworkModule;
import hu.bme.aut.mobsoft.lab.mobsoft.repository.TestRepositoryModule;

@Singleton
@Component(modules = {MockNetworkModule.class, TestModule.class,
        InteractorModule.class, TestRepositoryModule.class})
public interface TestComponent extends MobSoftApplicationComponent {
}
