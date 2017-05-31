package hu.bme.aut.mobsoft.lab.mobsoft.interactor;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorModule {

    @Provides
    public QuestionsInteractor provideQuestionsInteractor() {
        return new QuestionsInteractor();
    }

    @Provides
    public AnswersInteractor provideAnswersInteractor() {
        return new AnswersInteractor();
    }
}
