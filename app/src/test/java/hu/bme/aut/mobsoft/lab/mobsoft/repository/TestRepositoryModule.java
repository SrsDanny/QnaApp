package hu.bme.aut.mobsoft.lab.mobsoft.repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestRepositoryModule {

	@Singleton
	@Provides
	public Repository provideRepository() {
		return new MemoryRepository();
	}
}