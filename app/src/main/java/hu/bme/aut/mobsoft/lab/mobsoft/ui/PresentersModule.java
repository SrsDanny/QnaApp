package hu.bme.aut.mobsoft.lab.mobsoft.ui;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.AnswersInteractor;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.question.QuestionsInteractor;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.create.CreateNewAnswerPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list.AnswersPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.create.CreateNewQuestionPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list.QuestionsPresenter;

@Module
public class PresentersModule {
    private Context context;

    public PresentersModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    public CreateNewQuestionPresenter provideCreateNewQuestionPresenter(
            QuestionsInteractor questionsInteractor,
            EventBus bus,
            Executor executor) {
        return new CreateNewQuestionPresenter(questionsInteractor, bus, executor);
    }

    @Provides
    @Singleton
    public QuestionsPresenter provideQuestionsPresenter(
            Executor executor,
            EventBus bus,
            QuestionsInteractor questionsInteractor) {
        return new QuestionsPresenter(executor, bus, questionsInteractor);
    }

    @Provides
    @Singleton
    public CreateNewAnswerPresenter provideCreateNewAnswerPresenter(
            EventBus bus,
            Executor executor,
            AnswersInteractor answersInteractor) {
        return new CreateNewAnswerPresenter(bus, executor, answersInteractor);
    }

    @Provides
    @Singleton
    public AnswersPresenter provideAnswersPresenter(
            Executor executor,
            AnswersInteractor answersInteractor,
            EventBus bus) {
        return new AnswersPresenter(executor, answersInteractor, bus);
    }

    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return EventBus.getDefault();
    }
}