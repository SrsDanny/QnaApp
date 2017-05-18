package hu.bme.aut.mobsoft.lab.mobsoft.interactor;

import org.greenrobot.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.AnswersInteractor;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.question.QuestionsInteractor;
import hu.bme.aut.mobsoft.lab.mobsoft.network.AnswerApi;
import hu.bme.aut.mobsoft.lab.mobsoft.network.QuestionApi;
import hu.bme.aut.mobsoft.lab.mobsoft.repository.Repository;

@Module
public class InteractorModule {

    @Provides
    public QuestionsInteractor provideQuestionsInteractor(
            Repository repository,
            EventBus bus,
            QuestionApi questionApi) {
        return new QuestionsInteractor(repository, bus, questionApi);
    }

    @Provides
    public AnswersInteractor provideAnswersInteractor(
            Repository repository,
            EventBus bus,
            AnswerApi answerApi,
            QuestionApi questionApi) {
        return new AnswersInteractor(repository, bus, answerApi, questionApi);
    }
}
